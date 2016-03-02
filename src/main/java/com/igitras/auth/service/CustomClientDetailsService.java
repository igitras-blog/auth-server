package com.igitras.auth.service;

import com.igitras.auth.domain.entity.client.Client;
import com.igitras.auth.domain.repository.client.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * @author mason
 */
public class CustomClientDetailsService implements ClientDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomClientDetailsService.class);

    private static JsonMapper mapper;

    static {
        if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
            mapper = new JacksonMapper();
        } else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
            mapper = new Jackson2Mapper();
        } else {
            mapper = new NotSupportedJsonMapper();
        }
    }

    private final ClientRepository clientRepository;

    public CustomClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Assert.hasLength(clientId, "ClientId must have length while loading client");
        LOG.debug("Client Authentication {}", clientId);

        Optional<Client> client = clientRepository.findOneByClientId(clientId);

        return client.map(c -> {
            BaseClientDetails clientDetails =
                    new BaseClientDetails(c.getClientId(), c.getResourceIds(), c.getScope(), c.getGrantTypes(),
                            c.getAuthorities(), c.getRedirectUris());
            clientDetails.setClientSecret(c.getClientSecret());
            clientDetails.setAccessTokenValiditySeconds(c.getAccessTokenValidity());
            clientDetails.setRefreshTokenValiditySeconds(c.getRefreshTokenValidity());

            if (c.getAdditionalInformation() != null) {
                try {
                    Map<String, Object> additionalInformation = mapper.read(c.getAdditionalInformation(), Map.class);
                    clientDetails.setAdditionalInformation(additionalInformation);
                } catch (Exception e) {
                    LOG.warn("Could not decode JSON for additional information: " + clientDetails, e);
                }
            }

            clientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(c.getAutoApprove()));

            return clientDetails;
        })
                .orElseThrow(
                        () -> new NoSuchClientException("Client " + clientId + " was not found in the " + "database"));
    }

    interface JsonMapper {
        String write(Object input) throws Exception;

        <T> T read(String input, Class<T> type) throws Exception;
    }

    private static class JacksonMapper implements JsonMapper {
        private org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

        @Override
        public String write(Object input) throws Exception {
            return mapper.writeValueAsString(input);
        }

        @Override
        public <T> T read(String input, Class<T> type) throws Exception {
            return mapper.readValue(input, type);
        }
    }

    private static class Jackson2Mapper implements JsonMapper {
        private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        @Override
        public String write(Object input) throws Exception {
            return mapper.writeValueAsString(input);
        }

        @Override
        public <T> T read(String input, Class<T> type) throws Exception {
            return mapper.readValue(input, type);
        }
    }

    private static class NotSupportedJsonMapper implements JsonMapper {
        @Override
        public String write(Object input) throws Exception {
            throw new UnsupportedOperationException(
                    "Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
        }

        @Override
        public <T> T read(String input, Class<T> type) throws Exception {
            throw new UnsupportedOperationException(
                    "Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
        }
    }
}

package com.igitras.auth.domain.entity.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.igitras.auth.common.audit.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author mason
 */
@Entity(name = "client_detail")
public class Client extends AbstractAuditable<Long> {

    private static final long serialVersionUID = 813493698694002254L;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]*$")
    @Column(length = 128,
            unique = true,
            nullable = false)
    private String clientId;

    @JsonIgnore
    @NotNull
    @Column(length = 128,
            nullable = false)
    private String clientSecret;

    @Size(max = 512)
    private String resourceIds;
    @Size(max = 512)
    private String scope;
    @Size(max = 512)
    private String grantTypes;
    @Size(max = 512)
    private String authorities;
    @Size(max = 512)
    private String redirectUris;

    private int accessTokenValidity;
    private int refreshTokenValidity;

    @Size(max = 4096)
    @Column(length = 4096)
    private String additionalInformation;
    @Size(max = 512)
    private String autoApprove;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public int getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(int accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public int getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(int refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }
}

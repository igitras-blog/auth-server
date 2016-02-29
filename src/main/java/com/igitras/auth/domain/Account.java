package com.igitras.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author mason
 */
@Entity
@Document(indexName = "account")
public class Account extends AbstractAuditable<Account, Long> {
    private static final long serialVersionUID = -5936880652980576016L;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$|(anonymousUser)")
    @Size(min = 6, max = 64)
    @Column(length = 64, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Column(length = 128, nullable = false)
    private String password;


    private String email;

    private String activationKey;

    private String resetKey;

    private ZonedDateTime resetDate = null;
}

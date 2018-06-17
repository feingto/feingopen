package com.feingto.cloud.oauth2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feingto.cloud.domain.IdEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = {"clientDetail", "grantType"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_client_detail_grant_type")
@DynamicUpdate
public class ClientDetailGrantType extends IdEntity {
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private ClientDetail clientDetail;

    @JsonIgnoreProperties(value = "clientDetailGrantTypes", allowSetters = true)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private GrantType grantType;
}

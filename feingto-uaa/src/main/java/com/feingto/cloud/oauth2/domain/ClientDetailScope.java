package com.feingto.cloud.oauth2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feingto.cloud.domain.IdEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = {"clientDetail", "scope"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_client_detail_scope")
@DynamicUpdate
public class ClientDetailScope extends IdEntity {
    private static final long serialVersionUID = 7806230602022368398L;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private ClientDetail clientDetail;

    @JsonIgnoreProperties(value = "clientDetailScopes", allowSetters = true)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private Scope scope;

    @NotNull
    @Column(nullable = false)
    private Boolean autoApprove;
}

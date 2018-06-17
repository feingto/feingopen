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
 * 客户端的授权资源
 *
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_client_detail_resource")
@DynamicUpdate
public class ClientDetailResourceId extends IdEntity {
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private ClientDetail clientDetail;

    @JsonIgnoreProperties(value = "clientDetailResourceIds", allowSetters = true)
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "resource_id")
    private ResourceId resourceId;
}

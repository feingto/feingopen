package com.feingto.cloud.oauth2.domain;

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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_client_detail_user")
@DynamicUpdate
public class ClientDetailUser extends IdEntity {
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private ClientDetail clientDetail;

    @NotNull
    @Column(nullable = false)
    private String username;
}

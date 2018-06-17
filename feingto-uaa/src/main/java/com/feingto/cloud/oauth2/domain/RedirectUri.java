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
@EqualsAndHashCode(of = {"clientDetail", "value"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_redirect_uri")
@DynamicUpdate
public class RedirectUri extends IdEntity {
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ClientDetail clientDetail;

    @NotNull
    @Column(nullable = false)
    private String value;
}

package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = "value", callSuper = false)
@ToString(of = "value")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_grant_type")
@DynamicUpdate
public class GrantType extends IdEntity {
    @Builder.Default
    @OneToMany(mappedBy = "grantType")
    private Set<ClientDetailGrantType> clientDetailGrantTypes = new HashSet<>();

    @Column(nullable = false)
    private String value;
}

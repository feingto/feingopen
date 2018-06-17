package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@EqualsAndHashCode(of = "value", callSuper = false)
@ToString(of = "value")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_scope")
@DynamicUpdate
public class Scope extends IdEntity {
    @Builder.Default
    @OneToMany(mappedBy = "scope")
    private Set<ClientDetailScope> clientDetailScopes = new HashSet<>();

    @NotNull
    @Column(nullable = false)
    private String value;
}

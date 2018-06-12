package com.feing.cloud.oauth2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feing.cloud.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = "clientId", callSuper = false)
@ToString(exclude = "clientSecret")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_client_detail")
@DynamicUpdate
public class ClientDetail extends BaseEntity {
    private static final long serialVersionUID = 1698363237742833368L;

    @Column
    private String name;

    @NotNull
    @Column(unique = true, nullable = false)
    private String clientId;

    @NotNull
    @Column(nullable = false)
    private String clientSecret;

    @Column
    private Integer accessTokenValiditySeconds;// AccessToken有效秒数（1 hour = 3600 s）

    @Column
    private Integer refreshTokenValiditySeconds;// RefreshToken有效秒数（30 days = 2592000s）

    @Builder.Default
    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @BatchSize(size = 30)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientDetailGrantType> clientDetailGrantTypes = new HashSet<>();

    @Builder.Default
    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @BatchSize(size = 30)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientDetailScope> clientDetailScopes = new HashSet<>();

    @Builder.Default
    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @BatchSize(size = 30)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientDetailResourceId> clientDetailResourceIds = new HashSet<>();

    @Builder.Default
    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @BatchSize(size = 30)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("value")
    private Set<RedirectUri> redirectUris = new HashSet<>();

    @Builder.Default
    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @BatchSize(size = 30)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientDetailAuthority> clientDetailAuthorities = new HashSet<>();

    @Builder.Default
    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @BatchSize(size = 30)
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "clientDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientDetailApi> clientDetailApis = new HashSet<>();

    @JsonIgnoreProperties(value = "clientDetail", allowSetters = true)
    @Fetch(FetchMode.JOIN)
    @OneToOne(mappedBy = "clientDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ClientDetailLimit clientLimit;

    @Column(unique = true)
    private String username;
}

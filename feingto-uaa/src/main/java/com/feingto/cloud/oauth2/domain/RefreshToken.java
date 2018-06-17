package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import com.feingto.cloud.oauth2.domain.converters.OAuth2RefreshTokenPersistenceConverters;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = "tokenId", callSuper = false)
@ToString(of = "tokenId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oauth_refresh_token")
@DynamicUpdate
public class RefreshToken extends IdEntity {
    @NotNull
    @Column(nullable = false, unique = true)
    private String tokenId;

    @NotNull
    @Convert(converter = OAuth2RefreshTokenPersistenceConverters.class)
    @Column(name = "serialized_token", nullable = false)
    private OAuth2RefreshToken token;

    @NotNull
    @Lob
    @Column(name = "serialized_authentication", nullable = false)
    private OAuth2Authentication authentication;

    @Builder.Default
    @OneToMany(mappedBy = "refreshToken")
    private Set<AccessToken> accessTokens = new HashSet<>();
}

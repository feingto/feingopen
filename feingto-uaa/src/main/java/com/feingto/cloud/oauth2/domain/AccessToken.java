package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import com.feingto.cloud.oauth2.domain.converters.OAuth2AccessTokenPersistenceConverters;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = "tokenId", callSuper = false)
@ToString(exclude = {"token", "authentication", "refreshToken"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauth_access_token")
@DynamicUpdate
public class AccessToken extends IdEntity {
    @NonNull
    @Column(nullable = false, unique = true)
    private String tokenId;

    @NotNull
    @Convert(converter = OAuth2AccessTokenPersistenceConverters.class)
    @Column(name = "serialized_token", nullable = false)
    private OAuth2AccessToken token;

    @NotNull
    @Column(nullable = false, length = 32)
    private String authenticationId;// 认证id

    @Size(max = 50)
    @Column(length = 50)
    private String userName;

    @Size(max = 200)
    @Column(length = 200)
    private String clientId;

    @NotNull
    @Lob
    @Column(name = "serialized_authentication", nullable = false)
    private OAuth2Authentication authentication;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private RefreshToken refreshToken;

    @Transient
    private Map<String, Object> additionalInformations = new HashMap<>();
}

package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import com.feingto.cloud.domain.type.Stage;
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
@Table(name = "oauth_client_detail_api")
@DynamicUpdate
public class ClientDetailApi extends IdEntity {
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn
    private ClientDetail clientDetail;

    @NotNull
    @Column(nullable = false)
    private String apiId;

    /**
     * 环境类型（线上、预发、测试）
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private Stage stage;
}

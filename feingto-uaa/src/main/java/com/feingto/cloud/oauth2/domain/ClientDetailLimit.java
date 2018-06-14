package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import com.feingto.cloud.domain.type.IntervalUnit;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = {"frequency", "limits", "intervalUnit"}, callSuper = true)
@ToString(of = {"frequency", "limits", "intervalUnit"}, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauth_client_detail_limit")
@DynamicUpdate
public class ClientDetailLimit extends IdEntity {
    private static final long serialVersionUID = -3764086344152512416L;

    @OneToOne
    @JoinColumn
    private ClientDetail clientDetail;

    @Builder.Default
    @Column
    private Long limits = 0L;// 限制次数

    @Builder.Default
    @Column
    private Long frequency = 1L;// 频率

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    @ColumnDefault("'MINUTES'")
    private IntervalUnit intervalUnit;// 单位时间（分钟、小时、天）
}

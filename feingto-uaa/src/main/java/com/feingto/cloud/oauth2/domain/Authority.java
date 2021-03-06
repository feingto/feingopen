package com.feingto.cloud.oauth2.domain;

import com.feingto.cloud.domain.IdEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = "value", callSuper = false)
@ToString(of = "value")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauth_authority")
@DynamicUpdate
public class Authority extends IdEntity {
    @Size(min = 1, max = 50)
    @Column(unique = true, length = 50)
    private String value;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean enabled;
}

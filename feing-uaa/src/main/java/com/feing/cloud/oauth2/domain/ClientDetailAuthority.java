package com.feing.cloud.oauth2.domain;

import com.feing.cloud.domain.IdEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author longfei
 */
@Builder
@Data
@EqualsAndHashCode(of = "authority", callSuper = true)
@ToString(of = "authority", callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oauth_client_detail_authority")
@DynamicUpdate
public class ClientDetailAuthority extends IdEntity {
    private static final long serialVersionUID = -989142546338650235L;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ClientDetail clientDetail;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Where(clause = "enabled = true")
    private Authority authority;
}

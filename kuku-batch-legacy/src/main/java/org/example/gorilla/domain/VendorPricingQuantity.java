package org.example.gorilla.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"key"}, callSuper = false)
@Entity
@Table(name = "VEN_PRD_PRICING_USE_DAY")
public class VendorPricingQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_USE_DAY_KEY")
    private Long key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOCK_USE_TIME_KEY", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private VendorProductBlockTime vendorProductBlockUseTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OPTION_KEY", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private VendorPricing productPricing;

    @Column(name = "OPTION_ID", length = 50)
    private String pricingId;

    @Column(name = "USE_DAY", nullable = false, columnDefinition = "char(8)")
    private String useDay;

    @Column(name = "USE_DAY_UTC", nullable = false)
    private LocalDateTime useDayUtc;

    @Column(name = "IS_SOLD_OUT", nullable = false)
    @Builder.Default
    private Boolean soldOut = false;

    @Column(name = "DELETED", nullable = false)
    @Builder.Default
    private Boolean deleted = false;
}

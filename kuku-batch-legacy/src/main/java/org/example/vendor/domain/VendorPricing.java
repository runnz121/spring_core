package org.example.vendor.domain;

import lombok.*;
import org.example.vendor.domain.consts.AgeGroup;
import org.example.vendor.domain.consts.GenderType;
import org.example.vendor.domain.consts.PricingType;
import org.example.vendor.domain.consts.QuantityType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = {"key"}, callSuper = false)
@Entity
@Table(name = "VEN_PRD_PRICING")
public class VendorPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_KEY")
    private Long key;

    @Column(name = "OPTION_ID", length = 50, nullable = false)
    private String pricingId;

    @Column(name = "OPTION_NAME", length = 100)
    private String pricingName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_KEY", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private VendorBlock productBlock;

    @Column(name = "OPTION_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingType pricingType;

    @Column(name = "QUANTITY_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;

    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "MIN_QUANTITY")),
            @AttributeOverride(name = "max", column = @Column(name = "MAX_QUANTITY")),
    })
    @Builder.Default
    @Embedded
    private VendorMinMax quantity = new VendorMinMax(0, 0);

    @Column(name = "SALE_QUANTITY")
    private Integer saleQuantity;

    @Column(name = "ORDER_QUANTITY")
    private Integer orderQuantity;

    @Column(name = "AGE_GROUP", nullable = false, columnDefinition = "enum ('ADULT', 'CHILD', 'NONE') default 'NONE'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AgeGroup ageGroup = AgeGroup.NONE;

    @Column(name = "MIN_AGE")
    private Integer minAge;

    @Column(name = "MAX_AGE")
    private Integer maxAge;

    @Column(name = "GENDER", nullable = false, columnDefinition = "enum ('MALE', 'FEMALE', 'NONE') default 'NONE'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private GenderType genderType = GenderType.NONE;

    @Column(name = "NORMAL_PRICE")
    private BigDecimal normalPrice;

    @Column(name = "RETAIL_PRICE")
    private BigDecimal retailPrice;

    @Column(name = "SUPPLY_PRICE")
    private BigDecimal supplyPrice;

    @Column(name = "OPTIONAL", columnDefinition = "longtext")
    @Type(type = "json")
    private String optional;

    @Column(name = "DELETED", nullable = false)
    @Builder.Default
    private Boolean deleted = false;
}

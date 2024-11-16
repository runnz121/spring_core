package org.example.vendor.domain;

import lombok.*;
import org.example.vendor.domain.consts.InputFormType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"key"}, callSuper = false)
@Entity
@ToString
@Table(name = "VEN_PRD_BLOCK")
public class VendorBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "TYPE_ID", length = 50, nullable = false)
    private String blockId;

    @Column(name = "TYPE_NAME", length = 200)
    private String blockName;

    @Column(name = "VENDOR_ID", length = 50, nullable = false)
    private String vendorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_KEY", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private VendorProduct product;

    @Column(name = "VEN_PRD_ID", length = 50, nullable = false)
    private String productId;

    @Column(name = "DISPLAY_PRICE", nullable = false)
    private BigDecimal displayPrice;

    @Column(name = "CURRENCY", length = 10)
    private String currency;

    @Column(name = "INPUT_FORM_TYPE", length = 10)
    @Enumerated(EnumType.STRING)
    private InputFormType inputFormType;

    @Column(name = "ORDER_DEADLINE_DAY", nullable = false)
    @Builder.Default
    private Integer orderDeadlineDay = 0;

    @Column(name = "ORDER_DEADLINE_TIME", length = 10)
    private String orderDeadlineTime;

    @Column(name = "OPTIONAL", columnDefinition = "longtext")
    @Type(type = "json")
    private String optional;

    @Column(name = "DELETED", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Optional {

        private Boolean returnable;

        private String rateType;

        private OrderLimit orderLimit;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderLimit {

        private Integer minimum;

        private Integer maximum;
    }
}

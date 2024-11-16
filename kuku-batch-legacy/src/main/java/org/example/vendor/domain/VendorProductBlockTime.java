package org.example.vendor.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"key"}, callSuper = false)
@Entity
@Table(name = "VEN_PRD_BLOCK_USE_TIME")
public class VendorProductBlockTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPE_USE_TIME_KEY")
    private Long key;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_KEY", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private VendorBlock productBlock;

    @Column(name = "TYPE_ID", length = 50, nullable = false)
    private String blockId;

    @Column(name = "USE_TIME", nullable = false, columnDefinition = "char(4)")
    private String useTime;

    @Column(name = "DELETED", nullable = false)
    @Builder.Default
    private Boolean deleted = false;
}

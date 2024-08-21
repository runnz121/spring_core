package kuku.mainApi.domain.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EntityListeners;
import kuku.mainApi.common.config.orm.KuKuAuditEntityListener;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(KuKuAuditEntityListener.class)
public class TestEntity {

    public static final TestEntity EMPTY = new TestEntity();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    private Integer totalQuantity;

    private Integer usedQuantity;

    private String createBy;

    private LocalDateTime createDate;

    private String updateBy;

    private LocalDateTime updateDate;

    @Builder
    public TestEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public TestEntity decreaseQuantity(int requestQuantity) {
        this.totalQuantity -= requestQuantity;
        return this;
    }

    public TestEntity changeTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }
}


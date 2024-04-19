package kuku.mainApi.domain.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {

    public static final TestEntity EMPTY = new TestEntity();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    private Integer totalQuantity;

    private Integer usedQuantity;

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


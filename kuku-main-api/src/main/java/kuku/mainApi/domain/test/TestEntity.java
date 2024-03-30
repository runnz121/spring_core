package kuku.mainApi.domain.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {

    public static final TestEntity EMPTY = new TestEntity();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    @Builder
    public TestEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

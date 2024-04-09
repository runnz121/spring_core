package org.example.sample.batch.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Product {

    @Id
    private Long id;
    private String name;
    private Integer price;
    private String type;
}

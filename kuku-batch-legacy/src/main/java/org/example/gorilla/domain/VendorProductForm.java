package org.example.gorilla.domain;

import lombok.*;
import org.example.gorilla.domain.consts.FormType;
import org.example.gorilla.domain.consts.InputType;
import org.example.gorilla.domain.consts.UseType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"key"}, callSuper = false)
@Entity
@Table(name = "VEN_PRD_FORM")
public class VendorProductForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORM_KEY")
    private Long key;

    @Column(name = "TYPE_ID", length = 50, nullable = false)
    private String blockId;

    @Column(name = "OPTION_ID", length = 50)
    private String pricingId;

    @Column(name = "QUESTION_ID", length = 50, nullable = false)
    private String questionId;

    @Column(name = "QUESTION_NAME", length = 100, nullable = false)
    private String questionName;

    @Column(name = "FIXED_VALUE", length = 255)
    private String fixedValue;

    @Column(name = "PLACE_HOLDER", length = 255)
    private String placeholder;

    @Column(name = "FORM_TYPE", length = 20, nullable = false)
    @Type(type = "json")
    private FormType formType;

    @Column(name = "USE_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private UseType useType;

    @Column(name = "INPUT_TYPE", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private InputType inputType;

    @Column(name = "IS_REQUIRED", nullable = false)
    @Builder.Default
    private Boolean required = false;

    @Column(name = "DELETED", nullable = false)
    @Builder.Default
    private Boolean deleted = false;
}

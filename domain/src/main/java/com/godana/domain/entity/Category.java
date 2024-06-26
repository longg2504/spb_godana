package com.godana.domain.entity;

import com.godana.domain.dto.category.CategoryCreResDTO;
import com.godana.domain.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categories")
@Accessors(chain = true)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "titlenumerical_order")
    private Long  titlenumericalOrder;

    private String svg;

    @OneToMany(mappedBy = "category")
    private List<Place> places;

    @OneToMany(mappedBy = "category")
    private List<Post> postList;

    public CategoryDTO toCategoryDTO() {
        return new CategoryDTO()
                .setId(id)
                .setTitle(title)
                .setTitlenumericalOrder(titlenumericalOrder)
                .setSvg(svg)
                ;
    }

    public CategoryCreResDTO toCategoryCreResDTO(){
        return new CategoryCreResDTO()
                .setId(id)
                .setTitlte(title)
                .setTitleNumericalOrder(titlenumericalOrder)
                .setSvg(svg);
    }
}

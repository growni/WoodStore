package com.WoodStore.configuration;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private Double price;
    private Set<ProductMaterial> materials;
    private ProductCategory category;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if(price != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
        }

        if(materials != null && !materials.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, root.get("material").in(materials));
        }

        if(category != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category"), category));
        }

        return predicate;
    }
}

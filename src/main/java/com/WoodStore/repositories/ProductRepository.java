package com.WoodStore.repositories;

import com.WoodStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findProductsByName(String name);
    List<Product> findAllByPriceLessThan(Double price);
    List<Product> findAllByPriceGreaterThan(Double price);
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findAvailableProducts();
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortByPriceAsc();
    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> sortByPriceDesc();

    @Query(value = "SELECT * FROM products WHERE LENGTH(emails) > 0", nativeQuery = true)
    List<Product> findAllSubscribedProducts();

}

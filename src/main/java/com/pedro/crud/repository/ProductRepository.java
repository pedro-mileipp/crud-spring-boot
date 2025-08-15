package com.pedro.crud.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pedro.crud.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}

package com.pedro.crud.controller;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.crud.dtos.ProductDto;
import com.pedro.crud.model.Product;
import com.pedro.crud.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    ProductRepository repository; 

    @GetMapping
    public ResponseEntity getAll(){
        List<Product> listProducts = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") UUID id){
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        
        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductDto dto){
        var product = new Product();
        BeanUtils.copyProperties(dto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") UUID id) {
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        String productName = product.get().getName();
        repository.delete(product.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product: '" + productName + "' deleted successfully.");

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value = "id") UUID id, @RequestBody ProductDto dto) {
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(dto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel));
    }
    
}

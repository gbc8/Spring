package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.model.Product;
import com.crud.model.ProductRequest;
import com.crud.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("")
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@PostMapping("")
	public ResponseEntity<Product> addProduct(@RequestBody ProductRequest product){
		return ResponseEntity.ok().body(service.update(product, null));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest product, @PathVariable Integer id){
		return ResponseEntity.ok().body(service.update(product, id));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
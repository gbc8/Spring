package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.model.Product;
import com.crud.model.ProductRequest;
import com.crud.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	
	public List<Product> findAll(){
		return repository.findAll();
	}
	
	public Product findById(Integer id) {
		var product = repository.findById(id);
		if(product.isEmpty()) {
			return null;
		}
		return product.get();
	}
	
	public Product update(ProductRequest product, Integer id) {
		Product newProduct = new Product(id, product.getName(), product.getCategory(), product.getDescription(), product.getQuantity());
		return repository.save(newProduct);
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
}

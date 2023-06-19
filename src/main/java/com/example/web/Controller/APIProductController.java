package com.example.web.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

import com.example.web.Model.Product;
import com.example.web.Services.ProductServices;


@RestController
@RequestMapping("/API/products")
public class APIProductController {
	@Autowired
	private ProductServices productServices;

	@GetMapping("/page/{pageNum}")
	public List<Product> list(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortType") String sortType, @Param("keyword") String keyword) {
		Page<Product> page = productServices.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
		return page.getContent();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> get(@PathVariable(name = "id") Long id) {
		System.out.println("get1");
		try {
			Product product = productServices.get(id);
			if (product == null) {
				return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/add")
	public void add(@RequestBody Product product) {
		System.out.println("add");
		productServices.save(product);
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@RequestBody Product product, @PathVariable(name = "id") Long id) {
		System.out.println("edit");
		try {
			Product FoundProduct = productServices.get(id);
	    	if (FoundProduct == null) {
				return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
			}
	    	product.setId(id);
	    	productServices.save(product);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		System.out.println("Delete");
		productServices.delete(id);
	}
}

package com.example.web.Services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.web.Model.Product;
import com.example.web.Repositories.ProductRepository;

@Service
@Transactional
public class ProductServices {
	
	int pageSize = 5;
	
	@Autowired
	private ProductRepository productRepository;
	
	public Page<Product> listAll(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		return productRepository.findAll(pageable);
	}
	
//	public List<Product> listAll() {
//		return productRepository.findAll();
//	}
	
	
	public Page<Product> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
				sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		System.out.println(keyword);
		if (keyword != null) {
			return productRepository.Search(pageable, keyword);
		}
		return productRepository.findWithOutDelete(pageable);

	}
	
	public Product save(Product product) {
		productRepository.save(product);
		return product;
	}
	
	public Product get(Long id) {
		return productRepository.findById(id).get();
	}
	
	public void delete(Long id) {
		productRepository.deleteById(id);
	}
	
}

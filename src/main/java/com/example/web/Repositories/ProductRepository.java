package com.example.web.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.web.Model.Product;



public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p WHERE p.isdeleted = false")
	Page<Product> findWithOutDelete(Pageable page);
	@Modifying
	@Query("UPDATE Product p set p.isdeleted = true where p.id = :id")
	void deleteBookById(long id);
	@Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.brand, ' ' , p.price) LIKE %:keyword% AND p.isdeleted = false")
    public Page<Product> Search(Pageable page, @Param("keyword")String keyword);
	
}

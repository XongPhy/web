package com.example.web.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "size")
public class Size {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "size_id")
	private Long id;
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	@Column(name = "description", nullable = true, length = 255)
	private String description;
	@ManyToMany(mappedBy = "sizes")
	@JsonIgnore
	private List<Product> products;
	@Column(name="isdeleted",columnDefinition = "boolean default false")
	private boolean isdeleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public boolean isIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	public Size() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Size(Long id, String name, String description, List<Product> products, boolean isdeleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.products = products;
		this.isdeleted = isdeleted;
	}
	
}

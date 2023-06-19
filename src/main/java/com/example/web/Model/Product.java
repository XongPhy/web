package com.example.web.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="product")
public class Product {
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name",nullable = false,length = 255)
	private String name;
	@Column(name = "brand",nullable = false,length = 255)
	private String brand;
	@Column(name = "price")
	private Long price;
	@Column(nullable = true, length = 255)
	private String imageUrl;
	@Column(name="isdeleted",columnDefinition = "boolean default false")
	private boolean isdeleted;
	@ManyToMany
	@JoinTable(
		name = "product_size", 
		joinColumns = @JoinColumn(name = "product_id"), 
		inverseJoinColumns = @JoinColumn(name = "size_id") 
	)
	private List<Size> sizes;

	@Transient
	public String getImageUrl() {
		if(imageUrl == null || id == null)
			return null;
		return "/photos/" + id + "/" + imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public boolean isIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	public List<Size> getSizes() {
		return sizes;
	}
	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(Long id, String name, String brand, Long price, String imageUrl, boolean isdeleted,
			List<Size> sizes) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.imageUrl = imageUrl;
		this.isdeleted = isdeleted;
		this.sizes = sizes;
	}
	
}

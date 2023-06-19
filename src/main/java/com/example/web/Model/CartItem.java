package com.example.web.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@Column(name ="cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name;
    private String brand;
    private Long price;
    private int quantity;
    private String imageUrl;
    private Long total;
    private Long userId;
    private Long productId;
    private Long sizeId;
   
    @ManyToMany
    @JoinTable(
        name = "cart_item_size",
        joinColumns = @JoinColumn(name = "cart_item_id"),
        inverseJoinColumns = @JoinColumn(name = "size_id")
    )
   
    private Set<Size> sizes;
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSizeId() {
		return sizeId;
	}
	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Set<Size> getSizes() {
		return sizes;
	}
	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}
	public CartItem(Long id, String name, String brand, Long price, int quantity, String imageUrl, Long total,
			Long userId, Long productId, Long sizeId, Set<Size> sizes) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.imageUrl = imageUrl;
		this.total = total;
		this.userId = userId;
		this.productId = productId;
		this.sizeId = sizeId;
		this.sizes = sizes;
	}

	
	
}

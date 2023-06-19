package com.example.web.Services;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.web.Model.CartItem;
import com.example.web.Model.Product;
import com.example.web.Model.Size;
import com.example.web.Model.User;
import com.example.web.Repositories.CartItemRepository;


@Service
public class CartService { 
	
	@Autowired
    private CartItemRepository cartItemRepository;
	
	public void addToCart(User user,Product product,Size size) {
		List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
		for (CartItem cartItem : cartItems) {
			if (cartItem.getProductId().equals(product.getId()) && cartItem.getSizes().contains(size)) {
				int newQuantity = cartItem.getQuantity() + 1;
				cartItem.setQuantity(newQuantity);
				cartItem.setTotal(cartItem.getPrice() * newQuantity);
			        cartItemRepository.save(cartItem);
			        return;
			    }
			}
        
			CartItem newCartItem = new CartItem();
			newCartItem.setUserId(user.getId());
			newCartItem.setName(product.getName());
			newCartItem.setBrand(product.getBrand());
			newCartItem.setPrice(product.getPrice());
			newCartItem.setQuantity(1);
			newCartItem.setImageUrl(product.getImageUrl());
			newCartItem.setTotal(product.getPrice());
			newCartItem.setProductId(product.getId());
			newCartItem.setSizes(new HashSet<>(Arrays.asList(size)));
			cartItemRepository.save(newCartItem);
	}

    public void removeFromCart(Long cartItemId) {
    	cartItemRepository.deleteById(cartItemId);
    }

    public void updateQuantity(Long cartItemId, int quantity) {
    	CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItem.setTotal(cartItem.getPrice() * quantity);
            cartItemRepository.save(cartItem);
        }
    }
    
    public List<CartItem> getCartItems(User user) {
    	return cartItemRepository.findByUserId(user.getId());
    }
    
    public Long getTotalPrice(List<CartItem> cartItems) {
    	
        Long totalPrice = 0L;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotal();
        }
        return totalPrice;
    }
}


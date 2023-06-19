package com.example.web.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.Model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);
}

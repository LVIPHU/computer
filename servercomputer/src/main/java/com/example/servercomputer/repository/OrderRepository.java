package com.example.servercomputer.repository;

import com.example.servercomputer.entity.Order;
import com.example.servercomputer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByUser(User user);
}

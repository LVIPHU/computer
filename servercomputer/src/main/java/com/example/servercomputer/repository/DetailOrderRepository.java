package com.example.servercomputer.repository;

import com.example.servercomputer.entity.DetailOrder;
import com.example.servercomputer.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    List<DetailOrder> findOrderDetailsByOrder(Order order);
}

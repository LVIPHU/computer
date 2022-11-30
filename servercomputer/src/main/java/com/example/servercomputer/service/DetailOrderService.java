package com.example.servercomputer.service;

import com.example.servercomputer.dto.DetailOrderDTO;
import com.example.servercomputer.dto.OrderDetailResponseDTO;
import com.example.servercomputer.exception.AddDataFail;
import com.example.servercomputer.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface DetailOrderService {
    public List<DetailOrderDTO> retrieveOrderDetails();

    public Optional<DetailOrderDTO> getOrderDetail(Long detailId) throws ResourceNotFoundException;

    public DetailOrderDTO saveOrderDetail(DetailOrderDTO detailDTO) throws ResourceNotFoundException, AddDataFail;

    public Boolean deleteOrderDetail(Long detailId) throws ResourceNotFoundException;

    public DetailOrderDTO updateOrderDetail(Long id, DetailOrderDTO detailDTO) throws ResourceNotFoundException;

    public List<OrderDetailResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException;

    public DetailOrderDTO restoreQty(Long id) throws ResourceNotFoundException;

    public List<Object> getTopProduct();
}

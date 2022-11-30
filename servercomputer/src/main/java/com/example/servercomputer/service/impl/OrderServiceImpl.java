package com.example.servercomputer.service.impl;

import com.example.servercomputer.dto.OrderDTO;
import com.example.servercomputer.entity.Order;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.exception.*;
import com.example.servercomputer.repository.OrderRepository;
import com.example.servercomputer.repository.ProductRepository;
import com.example.servercomputer.repository.UserRepository;
import com.example.servercomputer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ProductRepository productrepository;

    @Override
    public List<OrderDTO> retrieveOrders() {
        List<Order> orders = orderRepository.findAll();

        return new OrderDTO().entityToDTO(orders);
    }

    @Override
    public Optional<OrderDTO> getOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: "+orderId));

        return Optional.of(new OrderDTO().entityToDTO(order));
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        User user =userRepository.findById(orderDTO.getId_user()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+orderDTO.getId_user()));
        Order order = new OrderDTO().dtoToEntity(orderDTO);
        order.setUser(user);
        order.setNgaydat(java.time.LocalDate.now());
        return new OrderDTO().entityToDTO(orderRepository.save(order));
    }

    @Override
    public Boolean deleteOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: " + orderId));
        this.orderRepository.delete(order);
        return true;
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO order) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));


        orderExist.setStatus(order.getStatus());
        orderExist.setTotal_price(order.getTotal_price());

        Order orderr = new Order();
        orderr = orderRepository.save(orderExist);
        return new OrderDTO().entityToDTO(orderr);
    }

    @Override
    public List<OrderDTO> findOrderByUser(Long userId) throws ResourceNotFoundException {
        Optional<User> userExist = userRepository.findById(userId);
        if(!userExist.isPresent()){
            throw new ResourceNotFoundException(""+ "No User");
        }
        User user = userExist.get();

        List<Order> list = null;
        list = orderRepository.getOrderByUser(user);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        orderDTOS = new OrderDTO().entityToDTO(list);
        return orderDTOS;
    }

    @Override
    public OrderDTO updateStatusOrder(Long orderId, String status) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));

        orderExist.setStatus(status);
        Order order = new Order();
        order = orderRepository.save(orderExist);

        return new OrderDTO().entityToDTO(order);
    }
}


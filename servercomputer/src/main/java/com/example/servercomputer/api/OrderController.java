package com.example.servercomputer.api;

import com.example.servercomputer.dto.ErrorCode;
import com.example.servercomputer.dto.OrderDTO;
import com.example.servercomputer.dto.ResponseDTO;
import com.example.servercomputer.dto.SuccessCode;
import com.example.servercomputer.exception.*;
import com.example.servercomputer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/orders", method = RequestMethod.DELETE)
public class OrderController {
    @Autowired
    private OrderService orderService;

    //find all order
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllOrder() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<OrderDTO> orderDTOS = orderService.retrieveOrders();
            List list = Collections.synchronizedList(new ArrayList<>(orderDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(orderDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_ORDER_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_ORDER_ERROR);
        }
        return ResponseEntity.ok(response);
    }
    //find order by order_id
    @GetMapping("/{order_id}")
    public ResponseEntity<ResponseDTO> findOrder(@PathVariable("order_id") Long orderId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<OrderDTO> orderDTO = orderService.getOrder(orderId);

            responseDTO.setData(orderDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_ORDER_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }
    //find order by user_id
    @GetMapping("/user/{user_id}")
    public ResponseEntity<ResponseDTO> findOrderByUser(@PathVariable("user_id") @NotBlank Long userId) throws ResourceNotFoundException {
        System.out.println(userId);
        ResponseDTO responseDTO = new ResponseDTO();
        List<OrderDTO> orderDTOS = orderService.findOrderByUser(userId);
        responseDTO.setData(orderDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    // create order
    @PostMapping("/add")
//    {
//        "status": "create",
//            "total_price": 1000,
//            "id_user": 1,
//            "address": "sss",
//            "phone": "1233345"
//    }
    public ResponseEntity<ResponseDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO dto = orderService.saveOrder(orderDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_ORDER_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ ErrorCode.ADD_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //update
    @PutMapping("/{order_id}")
//    {
//        "status": "completed",
//            "total_price": 1000
//    }
    public ResponseEntity<ResponseDTO> updateOrder(@PathVariable(value = "order_id") Long orderId,
                                                   @Valid @RequestBody OrderDTO orderDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO updateOrder = orderService.updateOrder(orderId, orderDTO);

            responseDTO.setData(updateOrder);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/order/{order_id}")
    public ResponseEntity<ResponseDTO> deleteOrder(@PathVariable(value = "order_id") Long orderId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = orderService.deleteOrder(orderId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //update status order
    @PutMapping("/status/{order_id}")
//    {
//        "status": "processing"
//    }
    public ResponseEntity<ResponseDTO> updateStatusOrder(@PathVariable(value = "order_id") Long orderId,
                                                         @RequestParam String status) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO updateOrder = orderService.updateStatusOrder(orderId, status);

            responseDTO.setData(updateOrder);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}

package com.example.servercomputer.dto;

import com.example.servercomputer.entity.DetailOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailOrderDTO {
    private Long id;
    private Integer detail_qty;
    private Float detail_price;
    private Long order_id;
    private Long product_id;

    public DetailOrderDTO entityToDTO(DetailOrder detailOrder) {
        DetailOrderDTO dto = new DetailOrderDTO();
        dto.setId(detailOrder.getId());
        dto.setDetail_qty(detailOrder.getDetail_qty());
        dto.setDetail_price(detailOrder.getDetail_price());
        dto.setOrder_id(detailOrder.getOrder().getId());
        dto.setProduct_id(detailOrder.getProduct().getId());
        return dto;
    }

    public DetailOrder dtoToEntity(DetailOrderDTO dto) {
        DetailOrder detail = new DetailOrder();
        detail.setId(dto.getId());
        detail.setDetail_qty(dto.getDetail_qty());
        detail.setDetail_price(dto.getDetail_price());
        return detail;
    }

    public List<DetailOrderDTO> entityToDTO(List<DetailOrder> detailOrders) {
        List<DetailOrderDTO> list = new ArrayList<>();
        detailOrders.forEach(x-> list.add(entityToDTO(x)));
        return list;
    }

    public List<DetailOrder> dtoToEntity(List<DetailOrderDTO> detailOrders) {
        List<DetailOrder> list = new ArrayList<>();
        detailOrders.forEach(x-> list.add(dtoToEntity(x)));
        return list;
    }
}


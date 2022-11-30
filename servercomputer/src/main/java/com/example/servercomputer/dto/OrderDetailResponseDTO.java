package com.example.servercomputer.dto;

import com.example.servercomputer.entity.DetailOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailResponseDTO {
    @NotNull
    private long detail_id;

    @NotNull
    private int detailQty;

    @NotNull
    private float detailPrice;
    private long order_id;
    private String name;
    //private List<ImageDTO> image;
    private String image;

    public OrderDetailResponseDTO convertToDto(DetailOrder detail) {
        OrderDetailResponseDTO deatailDTO = new OrderDetailResponseDTO();
        deatailDTO.setDetail_id(detail.getId());
        deatailDTO.setDetailQty(detail.getDetail_qty());
        deatailDTO.setDetailPrice(detail.getDetail_price());
        deatailDTO.setOrder_id(detail.getOrder().getId());
        //deatailDTO.setName(detail.getProduct().getProductName());
        deatailDTO.setName(detail.getProduct().getName());
        deatailDTO.setImage(detail.getProduct().getImage());
//        List<ImageDTO> listDto = new ArrayList<>();
//
//        if(detail.getProduct().getProductImages()!=null){
//            detail.getProduct().getProductImages().forEach(e -> {
//                listDto.add(new ImageDTO().convertToDto(e));
//            });
//        }
//        deatailDTO.setImage(listDto);

        return deatailDTO;
    }

    public DetailOrder convertToEti(OrderDetailResponseDTO deatailDTO) {
        DetailOrder detail = new DetailOrder();

        detail.setDetail_qty(deatailDTO.getDetailQty());
        detail.setDetail_price(deatailDTO.getDetailPrice());

        return detail;
    }


    public List<OrderDetailResponseDTO> toListDto(List<DetailOrder> listEntity) {
        List<OrderDetailResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}

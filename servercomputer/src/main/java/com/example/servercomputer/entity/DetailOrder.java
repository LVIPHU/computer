package com.example.servercomputer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="order_detail")
public class DetailOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "detail_qty")
    private Integer detail_qty;
    @Column(name = "detail_price")
    private Float detail_price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}

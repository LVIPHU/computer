package com.example.servercomputer.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Product name is mandatory")
	@Column(nullable = false)
	private String name;
	private String description;
	@Column(nullable = false)
	private Float price;
	private Integer quantity;
	@Lob
    @Column(columnDefinition="MEDIUMBLOB")
	private String image;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Category category;
	@OneToMany(fetch = FetchType.LAZY)
	private Set<DetailOrder> detailOrders;
}

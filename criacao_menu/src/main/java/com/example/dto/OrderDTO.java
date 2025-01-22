package com.example.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Integer id;
    private Integer menuId;
    private Integer quantity;
    private String deliveryAddress;
    private String contact;
    private String clientName;
}

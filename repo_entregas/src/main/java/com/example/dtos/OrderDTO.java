package com.example.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Integer menuId;
    private Integer quantity;
    private String deliveryAddress;
    private String contact;
    private String clientName;
}

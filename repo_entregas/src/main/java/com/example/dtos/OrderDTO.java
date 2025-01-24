package com.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

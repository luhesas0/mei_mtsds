package com.example.controller;

import com.example.dto.MenuRequestDTO;
import com.example.dto.MenuResponseDTO;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/generate")
    public ResponseEntity<MenuResponseDTO> createMenu(@RequestBody MenuRequestDTO request) {
        MenuResponseDTO response = menuService.createMenu(request);
        return ResponseEntity.ok(response);
    }
}

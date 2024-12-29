package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistoLoginDto {
    private Boolean statusLogin;
    private LocalDateTime dataLogin;
}

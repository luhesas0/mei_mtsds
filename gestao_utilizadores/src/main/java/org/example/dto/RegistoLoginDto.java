package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistoLoginDto {
    private Long idLogin;
    private Boolean statusLogin;
    private LocalDateTime dataLogin;

    // Getters e Setters
    public Long getLoginId(){
        return idLogin;
    }

    public void setLoginId(Long idLogin) {
        this.idLogin = idLogin;
    }

    public Boolean getLoginStatus(){
        return statusLogin;
    }

    public void setLoginStatus(Boolean statusLogin){
        this.statusLogin = statusLogin;
    }

    public LocalDateTime getLoginDate(){
        return dataLogin;
    }

    public void setLoginDate(LocalDateTime dataLogin){
        this.dataLogin = dataLogin;
    }
}

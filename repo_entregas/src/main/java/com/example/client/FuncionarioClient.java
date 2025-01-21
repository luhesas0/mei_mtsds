package com.example.client;


import com.example.config.RestTemplateConfig;
import com.example.dtos.FuncionarioDTO;
import com.example.exceptions.UtilizadoresUnexistingFuncionarioException;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class FuncionarioClient {

    @Value("${endpoints.gestao_utilizadores.url}")
    private String gestaoUtilizadoresUrl;

    private final RestTemplate restTemplate;

    public FuncionarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FuncionarioDTO getFuncionario(Integer funcionarioId) throws UtilizadoresUnexistingFuncionarioException {
        try {
            String url = gestaoUtilizadoresUrl + "/" + funcionarioId;
            ResponseEntity<?> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    FuncionarioDTO.class
            );
            return (FuncionarioDTO) response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Funcionário não encontrado");
        } catch (RuntimeException e) {
            throw new UtilizadoresUnexistingFuncionarioException();
        }
    }
}

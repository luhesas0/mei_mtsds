package com.example.messaging;

import com.example.client.FuncionarioClient;
import com.example.data.OrdemTrabalhoRepository;
import com.example.dtos.FuncionarioDTO;
import com.example.dtos.RequisicaoAceitacaoDTO;
import com.example.enums.OrderStatus;
import com.example.exceptions.InvalidOrdemTrabalho;
import com.example.models.OrdemTrabalho;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OTConsumer {

    private final OrdemTrabalhoRepository ordemTrabalhoRepository;

    private final FuncionarioClient funcionarioClient;

    public OTConsumer(OrdemTrabalhoRepository ordemTrabalhoRepository, FuncionarioClient funcionarioClient) {
        this.ordemTrabalhoRepository = ordemTrabalhoRepository;
        this.funcionarioClient = funcionarioClient;
    }

    @RabbitListener(queues = "ot-queue")
    public void acceptOT(RequisicaoAceitacaoDTO requisicao) {
        System.out.println("Mensagem recebida: " + requisicao);

        try {
            FuncionarioDTO funcionario = funcionarioClient.getFuncionario(requisicao.getFuncionarioId());

            if(funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }

            OrdemTrabalho ordemTrabalho = ordemTrabalhoRepository.findById(requisicao.getOrdemTrabalhoId())
                    .orElseThrow(() -> new InvalidOrdemTrabalho(requisicao.getOrdemTrabalhoId()));

            if (ordemTrabalho.getStatus() == OrderStatus.ACCEPTED) {
                System.out.println("Ordem de trabalho já aceita.");
                return;
            }

            if(requisicao.isAceite() && funcionario.getVehicleCapacity() >= requisicao.getCapacidadeVeiculo()) {
                ordemTrabalho.setStatus(OrderStatus.ACCEPTED);
                ordemTrabalho.setFuncionarioId(requisicao.getFuncionarioId());
            } else {
                ordemTrabalho.setStatus(OrderStatus.CANCELED);
            }
            ordemTrabalhoRepository.save(ordemTrabalho);

        } catch (Exception e) {
            System.out.println("Erro ao consumir message no rabbit: " + e.getMessage());
        }
    }
}

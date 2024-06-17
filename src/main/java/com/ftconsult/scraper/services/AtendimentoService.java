package com.ftconsult.scraper.services;

import com.ftconsult.scraper.models.Atendimento;
import com.ftconsult.scraper.repositories.AtendimentoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Log4j2
public class AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;

    public Optional<Atendimento> findByCodigoDoChamado(String codigoDoChamado) {
        log.info("Finding by codigo do chamado: " + codigoDoChamado);
        return atendimentoRepository.findByCodigoDoChamado(codigoDoChamado);
    }

    public void saveAll(Iterable<Atendimento> atendimentos) {
        log.info("Saving all atendimentos that do not exist in the database yet.");
        for (Atendimento atendimento : atendimentos) {
            Optional<Atendimento> atendimentoOptional = findByCodigoDoChamado(atendimento.getCodigoDoChamado());
            if (atendimentoOptional.isEmpty()) {
                log.info("Saving atendimento: " + atendimento);
                atendimentoRepository.save(atendimento);
            }
        }
    }

}

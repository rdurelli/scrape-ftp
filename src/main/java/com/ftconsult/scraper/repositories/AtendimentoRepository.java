package com.ftconsult.scraper.repositories;

import com.ftconsult.scraper.models.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    Optional<Atendimento> findByCodigoDoChamado(String codigoDoChamado);

}

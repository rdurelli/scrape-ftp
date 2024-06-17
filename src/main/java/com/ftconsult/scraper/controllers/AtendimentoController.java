package com.ftconsult.scraper.controllers;

import com.ftconsult.scraper.models.Atendimento;
import com.ftconsult.scraper.services.ScrapingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/api/scrape/ftconsult")
@Log4j2
public class AtendimentoController {

    @Autowired
    private ScrapingService scrapingService;

    @GetMapping("/atendimentos")
    public List<Atendimento> getAtendimentos() throws IOException {
        log.info("Requesting atendimentos");
        return scrapingService.scrapeAtendimentos();
    }
}

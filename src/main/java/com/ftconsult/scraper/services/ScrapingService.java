package com.ftconsult.scraper.services;

import com.ftconsult.scraper.models.Atendimento;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class ScrapingService {
    private final AtendimentoService atendimentoService;

    @Scheduled(fixedRate = 1800000) // 1800000 milliseconds = 30 minutes
    public void performTask() {
        log.info("Performing scheduled task");
        try {
            scrapeAtendimentos();
        } catch (IOException e) {
            log.error("Error scraping atendimentos: " + e.getMessage());
        }
    }

    public List<Atendimento> scrapeAtendimentos() throws IOException {
        log.info("Scraping atendimentos");
        String url = "https://ftconsult.desk.ms/PesquisaSatisfacao/exportar?t=powerbi&c=wrJZe8KWwqzDhMK1wp7Dl8OUw5LCosKSw43Cm8Kzw4XCl8OCwojCncKRwoNSwpZkwofCiMOKwqbDksOZw43DmcKawonCksOXw4rCrMKdwpjCmsKWwqfCp8OGwq3DhsKDwp3Cj1PDh8KbwqvDl8KnwoHDisOEwq_CgcKfwp9Yw4_CmcOTwqjDgsOWw53CksKlwpfCpMOXwoHCncKYwrBXwqTCnsKDw4nCnsOIw4bDkMKWwpbDk1TClsKQVcKlw4fDl8KXwrHClcKrwqvDlMKmw4bCn8OKw57DhcOKwppoU8KdwrxbWcKUY1d9w5vDlcKiw5fDgsKzwpnCpMOSwqfCosOXwpTCtMOHw5fCn8OUwpbCmsKbw4bCp8KHbcK8wobChsODZVh1w4TDlcKawofCnMKpwqjCp8ORw4bCpcOOw5vDhMKYwpLClFRzwr9VwoPDg8KPWMKxwoN_wqHDk8KZw5HCnMObw4XDh8OHwqh5wpnDhMOOwprCm8KmWW_Ck8KFw4vCosOXw5TDl1TClcOCwqtZw5PCmcKBw5DDhMKkw5bCkcKrwrHCkcKkw4bCpsOVwoTDiMOHwrJWwqDDicKBwp3CnMKawpzCosKaw4jDl1vDgsOew5_CsMKswoPCosKrw4nCmcOKw57DkljCm1LCn8Ksw4jCp8OTwqbDlsOQw5jCiGVYwqDDk8OGwqvCmMKbwqbCp1rCncOgW8OVw5PDiMKawprDmcKhW8KeVcOHw5rDhsKlw4_Co8KuwqTDmVrCkVXDhMOTw4jDlsKrwpvCl8OMw5nCqFlxamZqwpnCkVvDiMOQw4fCo8Khw4bCpMKaw4jCosOTwojCnWzCjVLCpsKnw4nCrcORwqLCg8KewpXCn2VYwpLDl8OKwq_CpllxV8KLwoXCkVvDisOZwoVuU8KvVGXChsKUw4XDk8KFcMKDwoNbZMKHwp_DisKlw4bDksOYw4tbcFPCscKDZVnCmsKmwpnCn8OVw5rCqcOUwoPCnVZhwpFiacKUZcKDwpLChcKfw4XCl8Krwq3DlcKnwodtwpPCmMKdwpJbwqzCmsOWw4jCq8KswqfCpldywoXCuFvCkcKDw5nCncKkw4jCpMKuw5TCosOUw4vDkMKow4bCo8KpWsKfWsKzVcKNwobDicOTwprCn8KdwoXCm1vCnsKYwpnCp8Khw4jDkcKlw4rCj8OGwpXCo8OXwpPCpcOMwqLCocOMw5fCmcOQwp7CrMKtw5HCrMKTwpbDkMORwpLDiMKrWF3ChcORwp7CqcKdwqDCoVrCncKXZcKHw5HDiMKmwpfDisKewprDmFXCm8KIwrZYwo1SfcKZw5nCmcK6wp_DlcONw5HDlXrCmcKWw5bDlMKoWXFZZ2jClcKZZsKVwpTCkGZhwoFjb8KeZMKVwqDClmjCg1xbwo3DkcKsw47CoMOQwqXDh8OLwqzCqcKgwoXCm1tpZ2lpZcKTwphmwpfCkcKFYFPDlsKmwpzChm3Cg8KTwpNpwptgaVrCkVrDicKYw47Dk8KGwqBbwoRTwo_Cg8KcwqbCpcKrwqfCmcOXw5Rbwp_Cg8K2Vl3Cg8KWwp7DkcKiw4bDnsOTwp_Dk8KRW3LCh2rClWXCk8KRwpTCnGZnaMKFwo1bwqTCmMKnwpbCmcOXw4rCp8OJw4rDkMKZwp_DlcKhW8KeVcK0wojCj1jDh8Kcwq7CsMOUwpnDmVXCm8KdwpDCiMKdwpfClcOMw4TCosKmwqXCmMKewqvChcKfW8KewoPCj1Z6w4XCm8Kow5HClMKxw5XDlcKqw4LCnMKIwqjDisKqw4bCl8OQw5bChsKgW8KEU8KPwoPCmsKnwqdZb1rCt8OOwpzDkMOGw5dWwq7CjVTCpcONwpXDhsOYw4RYwptSwoxawpFaw47Cl8OKw5PDkcOHW3BTw5PDlcKYwpnCqVlhWsOXw47CpsOKw5vDksKiwpbCg2xbwqXCoMOGw5jDjMKZw4LCjGh-w5TCqsOZwpTDjcOJw57Dh1tiU8OYw5XCnFlxWWJowpbCn2nClcKDwo9WwoTDgsKowp7CtsKYw4jCiMKdbMOe";
        Document document = Jsoup.connect(url).get();

        Elements rows = document.select("table tr");
        List<Atendimento> atendimentos = new ArrayList<>();

        log.info("Parsing atendimentos");
        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.size() == 12) { // Verifica se a linha tem 12 colunas
                Atendimento atendimento = new Atendimento(
                        cols.get(0).text(),
                        cols.get(1).text(),
                        cols.get(2).text(),
                        cols.get(3).text(),
                        cols.get(4).text(),
                        cols.get(5).text(),
                        cols.get(6).text(),
                        cols.get(7).text(),
                        cols.get(8).text(),
                        cols.get(9).text(),
                        cols.get(10).text(),
                        cols.get(11).text()
                );
                atendimentos.add(atendimento);
            }
        }
        log.info("Atendimentos parsed");
        atendimentoService.saveAll(atendimentos);
        return atendimentos;
    }
}

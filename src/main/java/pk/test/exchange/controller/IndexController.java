package pk.test.exchange.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pk.test.exchange.model.Currency;
import pk.test.exchange.model.History;
import pk.test.exchange.repository.CurrencyRepository;
import pk.test.exchange.repository.HistoryRepository;
import pk.test.exchange.security.PostgresUserDetails;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Controller
public class IndexController {

    private final CurrencyRepository currencyRepository;
    private final HistoryRepository historyRepository;

    public IndexController(CurrencyRepository currencyRepository, HistoryRepository historyRepository) {
        this.currencyRepository = currencyRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        Map<String, String> currencies = currencyRepository.findAll().stream().collect(toMap(
                Currency::getId, Currency::toSimpleString, (x, y) -> y, LinkedHashMap::new
        ));
        model.addAttribute("currencies", currencies);


        var userDetails = (PostgresUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<History> history = historyRepository.findAllByUserOrderByDateDesc(userDetails.toUser());
        model.addAttribute("history", history);

        return "index";
    }
}

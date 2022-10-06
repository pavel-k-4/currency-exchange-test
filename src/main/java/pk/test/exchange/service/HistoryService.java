package pk.test.exchange.service;


import com.fasterxml.jackson.core.io.BigDecimalParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.model.History;
import pk.test.exchange.repository.CurrencyRepository;
import pk.test.exchange.repository.HistoryRepository;
import pk.test.exchange.security.PostgresUserDetails;

import java.math.BigDecimal;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final CurrencyRepository currencyRepository;

    public HistoryService(HistoryRepository historyRepository, CurrencyRepository currencyRepository) {
        this.historyRepository = historyRepository;
        this.currencyRepository = currencyRepository;
    }

    public void write(ConvertDto dto, BigDecimal targetValue) {
        if (targetValue == null) {
            return;
        }
        var currentUser = ((PostgresUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toUser();
        historyRepository.save(new History(
                currentUser,
                currencyRepository.findById(dto.getInitialCurrency()).orElseThrow(IllegalStateException::new),
                currencyRepository.findById(dto.getTargetCurrency()).orElseThrow(IllegalStateException::new),
                BigDecimalParser.parse(dto.getInitialValue()),
                targetValue
        ));
    }
}

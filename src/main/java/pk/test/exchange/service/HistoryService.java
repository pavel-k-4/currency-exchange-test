package pk.test.exchange.service;


import com.fasterxml.jackson.core.io.BigDecimalParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.dto.HistoryEntriesDto;
import pk.test.exchange.model.History;
import pk.test.exchange.model.User;
import pk.test.exchange.repository.CurrencyRepository;
import pk.test.exchange.repository.HistoryRepository;
import pk.test.exchange.security.PostgresUserDetails;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
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
        var currentUser = getCurrentUser();
        historyRepository.save(new History(
                currentUser,
                currencyRepository.findById(dto.getInitialCurrency()).orElseThrow(IllegalStateException::new),
                currencyRepository.findById(dto.getTargetCurrency()).orElseThrow(IllegalStateException::new),
                BigDecimalParser.parse(dto.getInitialValue()),
                targetValue
        ));
    }

    public HistoryEntriesDto generateForCurrentUser() {
        List<History> history = historyRepository.findAllByUserOrderByDateDesc(getCurrentUser());
        return new HistoryEntriesDto(history);
    }

    public HistoryEntriesDto generateForCurrentUserAndDate(String d) {
        LocalDate date = LocalDate.parse(d, DateTimeFormatter.ISO_DATE);
        var currentUser =  getCurrentUser();
        var history = historyRepository.findAllByUserAndDateOrderByDateDesc(
                currentUser,
                date
        );
        return new HistoryEntriesDto(history);
    }

    private User getCurrentUser() {
        return ((PostgresUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toUser();
    }
}

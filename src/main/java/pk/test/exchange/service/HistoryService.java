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
import pk.test.exchange.util.BigDecimalUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class HistoryService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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


    public HistoryEntriesDto generateForCurrentUserAndDate(String d) {
        LocalDate date = LocalDate.parse(d, formatter);
        var currentUser =  getCurrentUser();
        var entries = historyRepository.findAllByUserAndDateOrderByDateDesc(
                currentUser,
                date
        );
        var entryDtos = entries.stream().map(entry ->
                new HistoryEntriesDto.HistoryEntryDto(
                        new HistoryEntriesDto.HistoryEntryDto.CurrencyDto(
                                entry.getInitialCurrency().getCharCode(),
                                entry.getInitialCurrency().getHint()
                        ),
                        new HistoryEntriesDto.HistoryEntryDto.CurrencyDto(
                                entry.getTargetCurrency().getCharCode(),
                                entry.getTargetCurrency().getHint()
                        ),
                        BigDecimalUtils.format(entry.getInitialValue()),
                        BigDecimalUtils.format(entry.getTargetValue()),
                        formatter.format(entry.getDate())
                )
        ).toList();
        return new HistoryEntriesDto(entryDtos);
    }

    private User getCurrentUser() {
        return ((PostgresUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toUser();
    }
}

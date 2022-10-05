package pk.test.exchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.model.Rate;
import pk.test.exchange.repository.RateRepository;
import pk.test.exchange.util.BigDecimalParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Optional;

@Service
public class ConvertService {

    private final static Logger log = LoggerFactory.getLogger(ConvertService.class);

    private final UpdateRateService updateRateService;
    private final RateRepository rateRepository;

    public ConvertService(UpdateRateService updateRateService, RateRepository rateRepository) {
        this.updateRateService = updateRateService;
        this.rateRepository = rateRepository;
    }

    @Nullable
    public BigDecimal convert(ConvertDto convertDto) {
        Optional<Rate> initial = rateRepository.findByCurrencyIdToday(convertDto.getInitialCurrency());
        Optional<Rate> target = rateRepository.findByCurrencyIdToday(convertDto.getTargetCurrency());

        if (initial.isEmpty() || target.isEmpty()) {
            log.info("updating the rates");
            updateRateService.update();
            initial = rateRepository.findByCurrencyIdToday(convertDto.getInitialCurrency());
            target = rateRepository.findByCurrencyIdToday(convertDto.getTargetCurrency());
        }

        if (initial.isEmpty() || target.isEmpty()) {
            log.error("could not find today's rates for {} and {} even after retry",
                    convertDto.getInitialCurrency(),
                    convertDto.getInitialCurrency());
            return null;
        }

        BigDecimal value;
        try {
            value = BigDecimalParser.parse(convertDto.getInitialValue());
        } catch (ParseException e) {
            log.warn("couldn't parse {}", convertDto.getInitialValue(), e);
            return null;
        }

        return value
                .multiply(initial.get().getValue())
                .divide(BigDecimal.valueOf(initial.get().getNominal()), 16, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(target.get().getNominal()))
                .divide(target.get().getValue(), 16, RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}

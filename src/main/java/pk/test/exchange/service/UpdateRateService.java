package pk.test.exchange.service;

import generated.ValCurs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pk.test.exchange.model.Currency;
import pk.test.exchange.model.Rate;
import pk.test.exchange.repository.CurrencyRepository;
import pk.test.exchange.repository.RateRepository;
import pk.test.exchange.util.BigDecimalUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Transactional
public class UpdateRateService {

    private static final Logger log = LoggerFactory.getLogger(UpdateRateService.class);
    @Value("${cbr.rates.endpoint}")
    private String url;
    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;
    private final RateRepository rateRepository;

    public UpdateRateService(RestTemplateBuilder restTemplateBuilder,
                             CurrencyRepository currencyRepository,
                             RateRepository rateRepository
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.currencyRepository = currencyRepository;
        this.rateRepository = rateRepository;
    }

    @PostConstruct
    public void prepare() {
        update();
    }

    public void update() {
        ValCurs response = restTemplate.getForObject(url, ValCurs.class);
        if (response == null) {
            throw new RestClientException("Object from " + url + " cannot be parsed, try rebuilding the app.");
        }
        LocalDate date;
        try {
            date = LocalDate.parse(response.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            log.error("Cannot parse date {}", response.getDate(), e);
            throw new RestClientException("Object from " + url + " cannot be parsed, try rebuilding the app.");
        }
        response.getValute().forEach(currencyRate -> {
            var currency = currencyRepository.findById(currencyRate.getID()).orElse(
                    currencyRepository.save(new Currency(
                            currencyRate.getID(),
                            currencyRate.getNumCode(),
                            currencyRate.getCharCode(),
                            currencyRate.getName()
                    ))
            );
            BigDecimal value;
            try {
                value = BigDecimalUtils.parse(currencyRate.getValue());
            } catch (ParseException e) {
                log.error("Cannot parse number {}", currencyRate.getValue(), e);
                throw new RestClientException("Object from " + url + " cannot be parsed, try rebuilding the app.");
            }
            var rate = new Rate(
                    currency,
                    currencyRate.getNominal(),
                    value,
                    date
            );
            rateRepository.save(rate);
        });
    }

}

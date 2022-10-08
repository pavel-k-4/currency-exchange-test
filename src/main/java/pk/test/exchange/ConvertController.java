package pk.test.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.service.ConvertService;
import pk.test.exchange.service.HistoryService;
import pk.test.exchange.util.BigDecimalUtils;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class ConvertController {

    private static final Logger log = LoggerFactory.getLogger(ConvertController.class);
    private final ConvertService convertService;
    private final HistoryService historyService;

    public ConvertController(ConvertService convertService, HistoryService historyService) {
        this.convertService = convertService;
        this.historyService = historyService;
    }

    @PostMapping(value = "/convert", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public String convert(@RequestBody @Valid ConvertDto convertDto) {
        var targetValue = convertService.convert(convertDto);
        historyService.write(convertDto, targetValue);
        log.info("Dto '{}' resulted in '{}'", convertDto, targetValue);
        return (targetValue != null) ? BigDecimalUtils.format(targetValue) : "";
    }
}

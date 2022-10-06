package pk.test.exchange;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.service.ConvertService;
import pk.test.exchange.service.HistoryService;

import javax.transaction.Transactional;

@RestController
public class ConvertController {

    private final ConvertService convertService;
    private final HistoryService historyService;

    public ConvertController(ConvertService convertService, HistoryService historyService) {
        this.convertService = convertService;
        this.historyService = historyService;
    }

    @PostMapping(value = "/convert", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public String convert(@RequestBody ConvertDto convertDto) {
        var targetValue = convertService.convert(convertDto);
        historyService.log(convertDto, targetValue);
        return (targetValue != null) ? targetValue.toString() : "";
    }
}

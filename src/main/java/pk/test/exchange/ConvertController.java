package pk.test.exchange;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.service.ConvertService;

@RestController
public class ConvertController {

    private final ConvertService convertService;

    public ConvertController(ConvertService convertService) {
        this.convertService = convertService;
    }

    @PostMapping(value = "/convert", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String convert(@RequestBody ConvertDto convertDto) {
        var targetValue = convertService.convert(convertDto);
        return (targetValue != null) ? targetValue.toString() : "";
    }
}

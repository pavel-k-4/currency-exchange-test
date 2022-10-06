package pk.test.exchange;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pk.test.exchange.dto.HistoryEntriesDto;
import pk.test.exchange.service.HistoryService;

@RestController
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(value = "/history", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HistoryEntriesDto dailyOperations(@RequestParam String date) {
        return historyService.generateForCurrentUserAndDate(date);
    }
}

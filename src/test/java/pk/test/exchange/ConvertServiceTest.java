package pk.test.exchange;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pk.test.exchange.dto.ConvertDto;
import pk.test.exchange.model.Rate;
import pk.test.exchange.repository.RateRepository;
import pk.test.exchange.service.ConvertService;
import pk.test.exchange.service.UpdateRateService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConvertServiceTest {
    @Mock
    public RateRepository rateRepository;

    @Mock
    public UpdateRateService updateRateService;

    @InjectMocks
    public ConvertService convertService;

    @Test
    public void shouldCallUpdateIfEmptyResults() {
        var dto = new ConvertDto();
        dto.setInitialCurrency("dollar");
        dto.setTargetCurrency("rub");
        dto.setInitialValue("1");

        when(rateRepository.findByCurrencyIdToday(dto.getInitialCurrency())).thenReturn(Optional.empty());
        when(rateRepository.findByCurrencyIdToday(dto.getTargetCurrency())).thenReturn(Optional.empty());

        assertThat(convertService.convert(dto)).isNull();
        verify(updateRateService).update();
    }

    @Test
    public void shouldDoASimpleConvertation() {
        var dto = new ConvertDto();
        dto.setInitialCurrency("dollar");
        dto.setTargetCurrency("rub");
        dto.setInitialValue("1");

        when(rateRepository.findByCurrencyIdToday(dto.getInitialCurrency())).thenReturn(Optional.of(
                new Rate(null, 1L, BigDecimal.valueOf(57), new Date()) //dollar
        ));
        when(rateRepository.findByCurrencyIdToday(dto.getTargetCurrency())).thenReturn(Optional.of(
                new Rate(null, 1L, BigDecimal.ONE, new Date()) //rub
        ));

        assertThat(convertService.convert(dto)).isEqualTo(new BigDecimal("57.00"));
    }

    @Test
    public void shouldDoAComplexConvertation() {
        var dto = new ConvertDto();
        dto.setInitialCurrency("dollar");
        dto.setTargetCurrency("DKK");
        dto.setInitialValue("1");

        when(rateRepository.findByCurrencyIdToday(dto.getInitialCurrency())).thenReturn(Optional.of(
                new Rate(null, 1L, new BigDecimal("57.1012"), new Date()) //dollar
        ));
        when(rateRepository.findByCurrencyIdToday(dto.getTargetCurrency())).thenReturn(Optional.of(
                new Rate(null, 10L, new BigDecimal("36.6002"), new Date()) //danish croon
        ));

        assertThat(convertService.convert(dto)).isEqualTo(new BigDecimal("15.60"));
    }
}

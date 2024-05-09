package es.inditex.tariff.infrastructure.adapter.in.web;

import es.inditex.tariff.application.PickingTariffQueryHandler;
import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import es.inditex.tariff.utils.TariffTestSampleData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffControllerTest {

    @Mock
    private PickingTariffQueryHandler pickingTariffQueryHandler;

    private TariffController tariffController;

    @BeforeEach
    void setUp() {
        tariffController = new TariffController(pickingTariffQueryHandler);
    }

    @Test
    void shouldReturnTariffWhenFound() throws PickingTariffQueryValidationException {
        when(pickingTariffQueryHandler.process(any(PickingTariffQuery.class))).thenReturn(Optional.of(TariffTestSampleData.TARIFF_01));
        ResponseEntity<PickTariffDto> result = tariffController.get(LocalDateTime.now(), 1L, 1L);

        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldReturnNotFoundWhenTariffIsNotFound() throws PickingTariffQueryValidationException {
        when(pickingTariffQueryHandler.process(any(PickingTariffQuery.class))).thenReturn(Optional.empty());

        ResponseEntity<PickTariffDto> result = tariffController.get(LocalDateTime.now(), 1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
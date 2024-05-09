package es.inditex.tariff.application;

import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import es.inditex.tariff.application.port.out.PickingTariffQueryPort;
import es.inditex.tariff.domain.Tariff;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PickingTariffQueryHandlerTest {

    @Mock
    private PickingTariffQueryPort queryPort;

    @Mock
    private Validator validator;

    private PickingTariffQueryHandler pickingTariffQueryHandler;

    @BeforeEach
    void setUp() {
        pickingTariffQueryHandler = new PickingTariffQueryHandler(queryPort, validator);
    }

    @Test
    void shouldReturnTariffWhenValidationPassesAndTariffIsFound() throws PickingTariffQueryValidationException {
        when(validator.validate(any(PickingTariffQuery.class))).thenReturn(Collections.emptySet());
        when(queryPort.lookupTariff(any(PickingTariffQuery.class))).thenReturn(Optional.of(mock(Tariff.class)));

        Optional<Tariff> result = pickingTariffQueryHandler.process(new PickingTariffQuery(Timestamp.valueOf("2022-06-01 00:00:00").toLocalDateTime(), 1L, 1L));

        assertTrue(result.isPresent());
    }

    @Test
    void shouldThrowExceptionWhenValidationFails() {
        Set<ConstraintViolation<PickingTariffQuery>> violations = Collections.singleton(null);
        when(validator.validate(any(PickingTariffQuery.class))).thenReturn(violations);

        assertThrows(PickingTariffQueryValidationException.class, () -> pickingTariffQueryHandler.process(new PickingTariffQuery(Timestamp.valueOf("2022-06-01 00:00:00").toLocalDateTime(), 1L, 1L)));
    }

    @Test
    void shouldReturnEmptyWhenValidationPassesAndTariffIsNotFound() throws PickingTariffQueryValidationException {
        when(validator.validate(any(PickingTariffQuery.class))).thenReturn(Collections.emptySet());
        when(queryPort.lookupTariff(any(PickingTariffQuery.class))).thenReturn(Optional.empty());

        Optional<Tariff> result = pickingTariffQueryHandler.process(new PickingTariffQuery(Timestamp.valueOf("2022-06-01 00:00:00").toLocalDateTime(), 1L, 1L));

        assertFalse(result.isPresent());
    }
}
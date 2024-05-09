package es.inditex.tariff.infrastructure.adapter.in.web;

import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffControllerAdviceTest {
    @Mock
    private PickingTariffQueryValidationException exception;

    private TariffControllerAdvice tariffControllerAdvice;

    @BeforeEach
    void setUp() {
        tariffControllerAdvice = new TariffControllerAdvice();
    }

    @Test
    void shouldReturnBadRequestWhenPickingTariffQueryValidationExceptionIsThrown() {
        when(exception.getMessage()).thenReturn("Validation error");

        ResponseEntity<String> result = tariffControllerAdvice.handlePickingTariffQueryValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Validation error", result.getBody());
    }
}
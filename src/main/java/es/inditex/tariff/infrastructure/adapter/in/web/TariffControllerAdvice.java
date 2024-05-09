package es.inditex.tariff.infrastructure.adapter.in.web;

import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TariffControllerAdvice {
    @ExceptionHandler(PickingTariffQueryValidationException.class)
    public ResponseEntity<String> handlePickingTariffQueryValidationException(PickingTariffQueryValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

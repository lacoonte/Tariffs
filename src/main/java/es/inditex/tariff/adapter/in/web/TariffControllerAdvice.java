package es.inditex.tariff.adapter.in.web;

import es.inditex.tariff.core.port.in.PickingTariffUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TariffControllerAdvice {
    @ExceptionHandler(PickingTariffUseCase.PickingTariffQueryValidationException.class)
    public ResponseEntity<String> handlePickingTariffQueryValidationException(PickingTariffUseCase.PickingTariffQueryValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

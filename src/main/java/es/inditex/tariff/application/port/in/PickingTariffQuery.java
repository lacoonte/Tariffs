package es.inditex.tariff.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PickingTariffQuery(
        @NotNull LocalDateTime date,
        @Positive long productId,
        @Positive long brandId
) {
}

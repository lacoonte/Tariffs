package es.inditex.tariff.core.port.in;

import es.inditex.tariff.core.domain.model.Tariff;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.time.LocalDateTime;
import java.util.Optional;

@PrimaryPort
public interface PickingTariffUseCase {
    Optional<Tariff> process(PickingTariffQuery query) throws PickingTariffQueryValidationException;

    record PickingTariffQuery(
            @NotNull LocalDateTime date,
            @Positive long productId,
            @Positive long brandId
    ) { }

    class PickingTariffQueryValidationException extends Exception {
        public PickingTariffQueryValidationException() {
            this(null, null);
        }

        public PickingTariffQueryValidationException(final String message, final Throwable cause) {
            super(message);
            if (cause != null) super.initCause(cause);
        }
    }

}

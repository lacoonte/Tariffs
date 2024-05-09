package es.inditex.tariff.application;

import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import es.inditex.tariff.application.port.out.PickingTariffQueryPort;
import es.inditex.tariff.domain.Tariff;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PickingTariffQueryHandler {
    private final PickingTariffQueryPort queryPort;
    private final Validator validator;

    public PickingTariffQueryHandler(PickingTariffQueryPort queryPort, Validator validator) {
        this.queryPort = queryPort;
        this.validator = validator;
    }

    public Optional<Tariff> process(PickingTariffQuery query) throws PickingTariffQueryValidationException {
        log.info("Processing tariff query: {}", query);
        if (!validator.validate(query).isEmpty()) {
            log.info("Validation failed for tariff query: {}", query);
            throw new PickingTariffQueryValidationException();
        }
        return queryPort.lookupTariff(query);
    }
}

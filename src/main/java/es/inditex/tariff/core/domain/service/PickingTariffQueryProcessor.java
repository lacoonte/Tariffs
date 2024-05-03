package es.inditex.tariff.core.domain.service;

import es.inditex.tariff.core.domain.model.Tariff;
import es.inditex.tariff.core.port.in.PickingTariffUseCase;
import es.inditex.tariff.core.port.out.PickingTariffQueryPort;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PickingTariffQueryProcessor implements PickingTariffUseCase {
    private final PickingTariffQueryPort queryPort;
    private final Validator validator;

    public PickingTariffQueryProcessor(PickingTariffQueryPort queryPort, Validator validator) {
        this.queryPort = queryPort;
        this.validator = validator;
    }

    public Optional<Tariff> process(PickingTariffQuery query) throws PickingTariffQueryValidationException {
        if (!validator.validate(query).isEmpty()) {
            throw new PickingTariffQueryValidationException();
        }
        return queryPort.lookupTariff(query);
    }
}

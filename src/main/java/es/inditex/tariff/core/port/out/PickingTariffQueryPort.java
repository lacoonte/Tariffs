package es.inditex.tariff.core.port.out;

import es.inditex.tariff.core.domain.model.Tariff;
import es.inditex.tariff.core.port.in.PickingTariffUseCase;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.Optional;

@SecondaryPort
public interface PickingTariffQueryPort {
    Optional<Tariff> lookupTariff(PickingTariffUseCase.PickingTariffQuery query);
}

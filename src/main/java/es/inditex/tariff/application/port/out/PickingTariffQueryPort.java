package es.inditex.tariff.application.port.out;

import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.domain.Tariff;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.Optional;

@SecondaryPort
public interface PickingTariffQueryPort {
    Optional<Tariff> lookupTariff(PickingTariffQuery query);
}

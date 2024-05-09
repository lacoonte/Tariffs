package es.inditex.tariff.infrastructure.adapter.in.web;

import es.inditex.tariff.application.PickingTariffQueryHandler;
import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.application.port.in.PickingTariffQueryValidationException;
import jakarta.validation.constraints.Positive;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@PrimaryAdapter
@RestController
public class TariffController {
    private final PickingTariffQueryHandler pickingTariffQueryHandler;

    public TariffController(PickingTariffQueryHandler pickingTariffQueryHandler) {
        this.pickingTariffQueryHandler = pickingTariffQueryHandler;
    }

    @GetMapping("/tariffs")
    public ResponseEntity<PickTariffDto> get(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime date,
            @RequestParam @Positive long productId,
            @RequestParam @Positive long brandId
    ) throws PickingTariffQueryValidationException {
        return pickingTariffQueryHandler.process(new PickingTariffQuery(date, productId, brandId))
                .map(tariff -> ResponseEntity.ok(PickTariffDto.fromDomain(tariff)))
                .orElse(ResponseEntity.notFound().build());
    }
}

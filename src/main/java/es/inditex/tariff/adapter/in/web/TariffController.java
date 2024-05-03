package es.inditex.tariff.adapter.in.web;

import es.inditex.tariff.core.port.in.PickingTariffUseCase;
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
    private final PickingTariffUseCase pickingTariffUseCase;

    public TariffController(PickingTariffUseCase pickingTariffUseCase) {
        this.pickingTariffUseCase = pickingTariffUseCase;
    }

    @GetMapping("/tariffs")
    public ResponseEntity<PickTariffDto> get(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime date,
            @RequestParam @Positive long productId,
            @RequestParam @Positive long brandId
    ) throws PickingTariffUseCase.PickingTariffQueryValidationException {
        return pickingTariffUseCase.process(new PickingTariffUseCase.PickingTariffQuery(date, productId, brandId))
                .map(tariff -> ResponseEntity.ok(PickTariffDto.fromDomain(tariff)))
                .orElse(ResponseEntity.notFound().build());
    }
}

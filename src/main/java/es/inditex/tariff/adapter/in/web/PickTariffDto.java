package es.inditex.tariff.adapter.in.web;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record PickTariffDto(
        long tariffList,
        long brandId,
        long productId,
        Schedule schedule,
        Price price) {
    record Schedule(LocalDateTime startDate, LocalDateTime endDate) { }
    record Price(String currency, BigInteger price) { }

    static PickTariffDto fromDomain(es.inditex.tariff.core.domain.model.Tariff tariff) {
        return new PickTariffDto(
                tariff.getId().id(),
                tariff.getBrandId().id(),
                tariff.getProductId().id(),
                new Schedule(tariff.getSchedule().startDate(), tariff.getSchedule().endDate()),
                new Price(tariff.getPrice().currency(), tariff.getPrice().amount().toBigInteger())
        );
    }
}

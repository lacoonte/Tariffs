package es.inditex.tariff.infrastructure.adapter.in.web;

import es.inditex.tariff.domain.Tariff;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PickTariffDto(
        long tariffList,
        long brandId,
        long productId,
        Schedule schedule,
        Price price) {
    static PickTariffDto fromDomain(Tariff tariff) {
        return new PickTariffDto(
                tariff.getId().id(),
                tariff.getBrandId(),
                tariff.getProductId(),
                new Schedule(tariff.getSchedule().startDate(), tariff.getSchedule().endDate()),
                new Price(tariff.getPrice().currency(), tariff.getPrice().amount())
        );
    }

    public record Schedule(LocalDateTime startDate, LocalDateTime endDate) {
    }

    public record Price(String currency, BigDecimal amount) {
    }
}

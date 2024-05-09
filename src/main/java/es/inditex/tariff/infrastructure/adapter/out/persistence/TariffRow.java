package es.inditex.tariff.infrastructure.adapter.out.persistence;

import es.inditex.tariff.domain.Price;
import es.inditex.tariff.domain.Schedule;
import es.inditex.tariff.domain.Tariff;
import es.inditex.tariff.domain.TariffId;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record TariffRow(long priceList, long brandId, Timestamp startDate, Timestamp endDate, long productId,
                 int priority, BigDecimal price, String currency) {
    Tariff toDomain() {
        return new Tariff(
                new TariffId(priceList()),
                brandId(),
                productId(),
                new Schedule(startDate().toLocalDateTime(), endDate().toLocalDateTime()),
                new Price(currency(), price())
        );
    }
}

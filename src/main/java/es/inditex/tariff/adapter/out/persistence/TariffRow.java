package es.inditex.tariff.adapter.out.persistence;

import java.math.BigDecimal;
import java.sql.Timestamp;

record TariffRow(long priceList, long brandId, Timestamp startDate, Timestamp endDate, long productId,
                 int priority, BigDecimal price, String currency) {
}

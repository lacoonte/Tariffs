package es.inditex.tariff.adapter.out.persistence;

import es.inditex.brand.BrandId;
import es.inditex.product.ProductId;
import es.inditex.tariff.core.domain.model.Price;
import es.inditex.tariff.core.domain.model.Schedule;
import es.inditex.tariff.core.domain.model.Tariff;
import es.inditex.tariff.core.domain.model.TariffId;

class TariffRowMapper {
    static Tariff toTariff(TariffRow tariffRow) {
        return new Tariff(
                new TariffId(tariffRow.priceList()),
                new BrandId(tariffRow.brandId()),
                new ProductId(tariffRow.productId()),
                new Schedule(tariffRow.startDate().toLocalDateTime(), tariffRow.endDate().toLocalDateTime()),
                new Price(tariffRow.currency(), tariffRow.price())
        );
    }
}

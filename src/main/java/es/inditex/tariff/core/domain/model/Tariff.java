package es.inditex.tariff.core.domain.model;

import es.inditex.brand.BrandId;
import es.inditex.product.ProductId;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
public class Tariff {
    @Identity
    private final TariffId id;
    private final BrandId brandId;
    private final ProductId productId;
    private final Schedule schedule;
    private final Price price;

    public Tariff(TariffId id, BrandId brandId, ProductId productId, Schedule schedule, Price price) {
        this.id = id;
        this.brandId = brandId;
        this.productId = productId;
        this.schedule = schedule;
        this.price = price;
    }

    public TariffId getId() {
        return id;
    }

    public BrandId getBrandId() {
        return brandId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Price getPrice() {
        return price;
    }
}

package es.inditex.tariff.domain;

import es.inditex.brand.BrandId;
import es.inditex.product.ProductId;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.Objects;

@AggregateRoot
public class Tariff {
    @Getter
    @Identity
    private final TariffId id;
    private final BrandId brandId;
    private final ProductId productId;
    @Getter
    private final Schedule schedule;
    @Getter
    private final Price price;

    public Tariff(TariffId id, long brandId, long productId, Schedule schedule, Price price) {
        this.id = id;
        this.brandId = new BrandId(brandId);
        this.productId = new ProductId(productId);
        this.schedule = schedule;
        this.price = price;
    }

    public long getBrandId() {
        return brandId.id();
    }

    public long getProductId() {
        return productId.id();
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", brandId=" + brandId.id() +
                ", productId=" + productId.id() +
                ", schedule=" + schedule +
                ", amount=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(id, tariff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

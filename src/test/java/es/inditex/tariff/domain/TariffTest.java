package es.inditex.tariff.domain;

import es.inditex.tariff.utils.TariffTestSampleData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryLocalVariable")
class TariffTest {
    @Test
    void shouldCreateTariffWithValidParameters() {
        TariffId id = new TariffId(1L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));

        Tariff tariff = new Tariff(id, brandId, productId, schedule, price);

        assertNotNull(tariff);
        assertEquals(id, tariff.getId());
        assertEquals(brandId, tariff.getBrandId());
        assertEquals(productId, tariff.getProductId());
        assertEquals(schedule, tariff.getSchedule());
        assertEquals(price, tariff.getPrice());
    }

    @Test
    void shouldReturnTrueWhenComparingTwoEqualIdTariffs() {
        TariffId id = new TariffId(1L);
        TariffId id2 = new TariffId(1L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));
        Price price2 = new Price("EUR", new BigDecimal("200.00"));
        long brandId2 = 2L;

        Tariff tariff1 = new Tariff(id, brandId, productId, schedule, price);
        Tariff tariff2 = new Tariff(id2, brandId2, productId, schedule, price2);

        assertEquals(tariff1, tariff2);
    }

    @Test
    void shouldReturnFalseWhenComparingTwoDifferentIdTariffs() {
        TariffId id1 = new TariffId(1L);
        TariffId id2 = new TariffId(2L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));

        Tariff tariff1 = new Tariff(id1, brandId, productId, schedule, price);
        Tariff tariff2 = new Tariff(id2, brandId, productId, schedule, price);

        assertNotEquals(tariff1, tariff2);
    }

    @Test
    void shouldReturnFalseWhenComparingTariffWithDifferentObject() {
        TariffId id = new TariffId(1L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));

        Tariff tariff = new Tariff(id, brandId, productId, schedule, price);

        assertNotEquals(tariff, new Object());
    }

    @Test
    void shouldReturnFalseWhenComparingWithNull() {
        TariffId id = new TariffId(1L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));

        Tariff tariff = new Tariff(id, brandId, productId, schedule, price);

        //noinspection SimplifiableAssertion,ConstantValue
        assertFalse(tariff.equals(null));
    }

    @Test
    void shouldReturnTrueWhenComparingSameInstance() {
        TariffId id = new TariffId(1L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));

        Tariff tariff = new Tariff(id, brandId, productId, schedule, price);
        Tariff sameTariff = tariff;

        assertEquals(sameTariff, tariff);
    }

    @Test
    void shouldReturnSameHashCodeWhenComparingTwoEqualIdTariffs() {
        TariffId id = new TariffId(1L);
        TariffId id2 = new TariffId(1L);
        long brandId = 1L;
        long productId = 1L;
        Schedule schedule = new Schedule(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Price price = new Price("EUR", new BigDecimal("100.00"));
        Price price2 = new Price("EUR", new BigDecimal("200.00"));
        long brandId2 = 2L;

        Tariff tariff1 = new Tariff(id, brandId, productId, schedule, price);
        Tariff tariff2 = new Tariff(id2, brandId2, productId, schedule, price2);

        assertEquals(tariff1.hashCode(), tariff2.hashCode());
    }

    @Test
    void shouldReturnStringWhenCallingToString() {
        assertEquals("Tariff{id=TariffId[id=1], brandId=1, productId=35455, schedule=Schedule[startDate=2020-06-14T00:00, endDate=2020-12-31T23:59:59], amount=Price[currency=EUR, amount=35.50]}", TariffTestSampleData.TARIFF_01.toString());
    }

}
package es.inditex.tariff.utils;

import es.inditex.tariff.domain.Price;
import es.inditex.tariff.domain.Schedule;
import es.inditex.tariff.domain.Tariff;
import es.inditex.tariff.domain.TariffId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TariffTestSampleData {
    // Row 1
    public static final Tariff TARIFF_01 = new Tariff(
            new TariffId(1),
            1,
            35455,
            new Schedule(LocalDateTime.parse("2020-06-14T00:00"), LocalDateTime.parse("2020-12-31T23:59:59")),
            new Price("EUR", new BigDecimal("35.50"))
    );

    // Row 2
    public static final Tariff TARIFF_02 = new Tariff(
            new TariffId(2),
            1,
            35455,
            new Schedule(LocalDateTime.parse("2020-06-14T15:00"), LocalDateTime.parse("2020-06-14T18:30")),
            new Price("EUR", new BigDecimal("25.45"))
    );

    // Row 3
    public static final Tariff TARIFF_03 = new Tariff(
            new TariffId(3),
            1,
            35455,
            new Schedule(LocalDateTime.parse("2020-06-15T00:00"), LocalDateTime.parse("2020-06-15T11:00")),
            new Price("EUR", new BigDecimal("30.50"))
    );

    // Row 4
    public static final Tariff TARIFF_04 = new Tariff(
            new TariffId(4),
            1,
            35455,
            new Schedule(LocalDateTime.parse("2020-06-15T16:00"), LocalDateTime.parse("2020-12-31T23:59:59")),
            new Price("EUR", new BigDecimal("38.95"))
    );

}

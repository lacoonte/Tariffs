package es.inditex.tariff.infrastructure.adapter.out.persistence;

import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.domain.Price;
import es.inditex.tariff.domain.Schedule;
import es.inditex.tariff.domain.Tariff;
import es.inditex.tariff.domain.TariffId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PickingTariffQueryRunnerIT {

    @Autowired
    private PickingTariffQueryRunner pickingTariffQueryRunner;

    @Test
    void shouldReturnEmptyWhenQueryingDateIsJustBeforeFirstTariff() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-13T23:59:59");
        PickingTariffQuery standardCase = new PickingTariffQuery(queryDate, 35455, 1);
        Optional<Tariff> result = pickingTariffQueryRunner.lookupTariff(standardCase);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnHighestPriorityTariffWhenQueryingDateIsTheTariffStartDate() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-14T15:00:00");
        PickingTariffQuery standardCase = new PickingTariffQuery(queryDate, 35455, 1);
        Optional<Tariff> result = pickingTariffQueryRunner.lookupTariff(standardCase);
        assertTrue(result.isPresent());
        Tariff expectedResult = new Tariff(new TariffId(2), 1, 35455, new Schedule(LocalDateTime.parse("2020-06-14T15:00"), LocalDateTime.parse("2020-06-14T18:30")), new Price("EUR", new BigDecimal("25.45")));
        assertEquals(expectedResult, result.get());
    }

    @Test
    void shouldReturnHighestPriorityTariffWhenQueryingDateIsTheTariffEndDate() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-14T18:30");
        PickingTariffQuery standardCase = new PickingTariffQuery(queryDate, 35455, 1);
        Optional<Tariff> result = pickingTariffQueryRunner.lookupTariff(standardCase);
        assertTrue(result.isPresent());
        Tariff expectedResult = new Tariff(new TariffId(2), 1, 35455, new Schedule(LocalDateTime.parse("2020-06-14T15:00"), LocalDateTime.parse("2020-06-14T18:30")), new Price("EUR", new BigDecimal("25.45")));
        assertEquals(expectedResult, result.get());
    }

    @Test
    void shouldReturnTheRightTariffWhenQueryingDateIsJustAfterAHigherPriorityTariff() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-14T18:30:01");
        PickingTariffQuery standardCase = new PickingTariffQuery(queryDate, 35455, 1);
        Optional<Tariff> result = pickingTariffQueryRunner.lookupTariff(standardCase);
        assertTrue(result.isPresent());
        Tariff expectedResult = new Tariff(new TariffId(1), 1, 35455, new Schedule(LocalDateTime.parse("2020-06-14T00:00"), LocalDateTime.parse("2020-12-31T23:59:59")), new Price("EUR", new BigDecimal("35.50")));
        assertEquals(expectedResult, result.get());
    }

    @Test
    void shouldReturnTheRightTariffIfAnotherTariffEndsTheSameDay() {
        LocalDateTime queryDate = LocalDateTime.parse("2020-06-15T16:00");
        PickingTariffQuery standardCase = new PickingTariffQuery(queryDate, 35455, 1);
        Optional<Tariff> result = pickingTariffQueryRunner.lookupTariff(standardCase);
        assertTrue(result.isPresent());
        Tariff expectedResult = new Tariff(new TariffId(4), 1, 35455, new Schedule(LocalDateTime.parse("2020-06-15T16:00"), LocalDateTime.parse("2020-12-31T23:59:59")), new Price("EUR", new BigDecimal("38.95")));
        assertEquals(expectedResult, result.get());
    }
}
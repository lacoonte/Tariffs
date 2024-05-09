package es.inditex.tariff.infrastructure.adapter.out.persistence;

import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.domain.Tariff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PickingTariffQueryRunnerTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PickingTariffQueryRunner pickingTariffQueryRunner;

    @Test
    void shouldReturnTariffWhenFound() {
        TariffRow tariffRow = new TariffRow(1L, 1L, Timestamp.valueOf("2022-01-01 00:00:00"), Timestamp.valueOf("2022-12-31 23:59:59"), 1L, 1, null, "EUR");
        //noinspection unchecked
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Timestamp.class), any(Long.class), any(Long.class))).thenReturn(tariffRow);

        Optional<Tariff> result = pickingTariffQueryRunner
                .lookupTariff(new PickingTariffQuery(Timestamp.valueOf("2022-06-01 00:00:00").toLocalDateTime(), 1L, 1L));

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        //noinspection unchecked
        when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Timestamp.class), any(Long.class), any(Long.class)))
                .thenThrow(EmptyResultDataAccessException.class);

        Optional<Tariff> result = pickingTariffQueryRunner
                .lookupTariff(new PickingTariffQuery(Timestamp.valueOf("2023-01-01 00:00:00").toLocalDateTime(), 1L, 1L));

        assertFalse(result.isPresent());
    }
}
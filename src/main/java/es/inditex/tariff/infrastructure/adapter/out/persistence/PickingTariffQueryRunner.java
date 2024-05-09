package es.inditex.tariff.infrastructure.adapter.out.persistence;

import es.inditex.tariff.application.port.in.PickingTariffQuery;
import es.inditex.tariff.application.port.out.PickingTariffQueryPort;
import es.inditex.tariff.domain.Tariff;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
@SecondaryAdapter
@Slf4j
public class PickingTariffQueryRunner implements PickingTariffQueryPort {
    private final JdbcTemplate jdbcTemplate;

    public PickingTariffQueryRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tariff> lookupTariff(PickingTariffQuery query) {
        String sql = "SELECT * FROM tariffs WHERE ? BETWEEN start_date AND end_date AND product_id = ? AND brand_id = ? ORDER BY priority DESC, start_date DESC LIMIT 1";
        try {
            TariffRow result = jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new TariffRow(
                            rs.getLong("tariff_list"),
                            rs.getLong("brand_id"),
                            rs.getTimestamp("start_date"),
                            rs.getTimestamp("end_date"),
                            rs.getLong("product_id"),
                            rs.getInt("priority"),
                            rs.getBigDecimal("price"),
                            rs.getString("curr")
                    ), Timestamp.valueOf(query.date()), query.productId(), query.brandId());
            log.info("Tariff found for query: {}", query);
            return Optional.ofNullable(result)
                    .map(TariffRow::toDomain);
        } catch (EmptyResultDataAccessException e) {
            log.info("No tariff found for query: {}", query);
            return Optional.empty();
        }
    }


}

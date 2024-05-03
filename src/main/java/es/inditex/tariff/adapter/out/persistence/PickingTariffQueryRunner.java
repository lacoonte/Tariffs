package es.inditex.tariff.adapter.out.persistence;

import es.inditex.tariff.core.domain.model.Tariff;
import es.inditex.tariff.core.port.in.PickingTariffUseCase;
import es.inditex.tariff.core.port.out.PickingTariffQueryPort;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
@SecondaryAdapter
public class PickingTariffQueryRunner implements PickingTariffQueryPort {
    private final JdbcTemplate jdbcTemplate;

    public PickingTariffQueryRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tariff> lookupTariff(PickingTariffUseCase.PickingTariffQuery query) {
        String sql = "SELECT * FROM tariffs WHERE ? BETWEEN start_date AND end_date AND product_id = ? AND brand_id = ? ORDER BY priority DESC, start_date DESC LIMIT 1";
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

        return Optional.ofNullable(result)
                .map(TariffRowMapper::toTariff);
    }


}

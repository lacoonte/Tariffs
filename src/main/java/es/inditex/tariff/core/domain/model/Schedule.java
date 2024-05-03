package es.inditex.tariff.core.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDateTime;

@ValueObject
public record Schedule(LocalDateTime startDate, LocalDateTime endDate) {
}

package es.inditex.tariff.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDateTime;

@ValueObject
public record Schedule(LocalDateTime startDate, LocalDateTime endDate) {
}

package es.inditex.tariff.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.math.BigDecimal;

@ValueObject
public record Price(String currency, BigDecimal amount) {
}

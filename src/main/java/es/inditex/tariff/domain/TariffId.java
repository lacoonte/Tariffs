package es.inditex.tariff.domain;

import org.jmolecules.ddd.types.Identifier;

public record TariffId(long id) implements Identifier {
}
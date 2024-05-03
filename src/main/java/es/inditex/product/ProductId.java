package es.inditex.product;

import jakarta.validation.constraints.Positive;
import org.jmolecules.ddd.types.Identifier;

public record ProductId(@Positive long id) implements Identifier {
}

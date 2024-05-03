package es.inditex.brand;

import jakarta.validation.constraints.Positive;
import org.jmolecules.ddd.types.Identifier;

public record BrandId(@Positive long id) implements Identifier {
}

package com.mutant.mutantapp.domain.model.mutant;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Dto que representa a un ser humano que puede ser mutante como no.
 * @author lauravanessap@gmail.com
 */
@Data
public class MutantDto {

    /**
     * Campo que asocia un adn o secuencia de bases nitrogenadas con un ser humano.
     * Además valida que la secuencia a mapear cumpla con el patrón exigido.
     */
    private List<@Pattern (regexp = "[A|T|C|G][A|T|C|G][A|T|C|G][A|T|C|G]+")  String> dna;

}

package com.mutant.mutantapp.infraestructure.drivenadapters.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Clase que hace referencia al objeto de base de datos Mutant, que se corresponde
 * con los humanos mutantes y no mutantes que han sido verificados con la aplicación.
 * @author  lauravanessap@gmail.com
 */
@Data
@Document
public class Mutant {

    @Id
    private String mutantId;

    private String[] dna;

    private Boolean isMutant;
}

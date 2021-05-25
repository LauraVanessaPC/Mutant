package com.mutant.mutantapp.domain.model.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Dto que representa a un objeto de estadísticas sobre los adn's evaluados con la aplicación:
 * número de mutantes verificados
 * número de humanos verificados
 * proporción o relación de mutantes a humanos que fueron verificados
 * @author lauravanessap@gmail.com
 */
@Data
@Builder
public class StatsDto {
    @JsonProperty("count_mutant_dna")
    private Long countMutant;
    @JsonProperty("count_human_dna")
    private Long countHuman;
    @JsonProperty("ratio")
    private Double ratio;
}

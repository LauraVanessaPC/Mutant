package com.mutant.mutantapp.domain.usecases.stats;

import com.mutant.mutantapp.domain.model.stats.StatsDto;
import org.springframework.stereotype.Service;

import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.Mutant;
import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.MutantRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

/**
 * Clase que permite generar las estadísticas sobre los humanos analizados
 * y cuya información reside en base de datos.
 * @author lauravanessap@gmail.com
 */
@Service
@RequiredArgsConstructor
public class StatsService {

    private final MutantRepository repository;

    /**
     * Trae todas las estadisticas de la base de datos
     */
    public Mono<StatsDto> getAllStats() {
        final Flux<Mutant> allData = repository.findAll();
        final Mono<Long> allRegistry = allData.count();
        final Mono<Long> allMutants = allData
            .filter(Mutant::getIsMutant)
            .count();
        final Mono<Long> allHumans = allData
            .filter(e -> !e.getIsMutant())
            .count();

        return Mono.zip(allRegistry, allMutants, allHumans)
            .flatMap(this::createStatsObject);
    }

    /**
     * Crea el objeto de estadísticas
     */
    private Mono<StatsDto> createStatsObject(Tuple3<Long, Long, Long> tuple) {
        final Double radio = tuple.getT2().doubleValue() / tuple.getT1().doubleValue();
        return Mono.just(StatsDto.builder()
            .ratio(radio)
            .countMutant(tuple.getT2())
            .countHuman(tuple.getT3()).build());
    }

}

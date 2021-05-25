package com.mutant.mutantapp.infraestructure.drivenadapters.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * Interface que implementa la interacción reactiva con base de datos MongoDB.
 * @author lauravanessap@gmail.com
 */
public interface MutantRepository extends ReactiveMongoRepository<Mutant, String > {
    Flux<Mutant> findByDna(String[] dna);
}

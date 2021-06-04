package com.mutant.mutantapp.domain.usecases.mutant;

import java.util.Optional;

import com.mutant.mutantapp.domain.model.mutant.MutantDto;
import org.springframework.stereotype.Service;

import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.Mutant;
import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.MutantRepository;
import com.mutant.mutantapp.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase que orquesta la lógica de negocio necesaria para suplir la funcionalidad brindada por esta
 * aplicación
 * @author lauravanessap@gmail.com
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class MutantService {

    private final ModelMapper modelMapper;

    private final MutantRepository repository;

    private final DnaProcessor dnaProcessor;

    /***
     * Lista todos los humanos; mutantes y no mutantes, almacenados en la BD.
     */
    public Flux<Mutant> listAllMutants(){
        return repository.findAll();
    }

    /**
     * Procesa el adn para validar si es mutante o no
     * ademas de almacenar en la base de datos.
     */
    public Mono<Boolean> processDna(final MutantDto mutantDto){
        log.trace(Constants.PROCESSING_DNA, mutantDto);
        final Mutant mutantEntity = modelMapper.map(mutantDto, Mutant.class);

        return Mono.justOrEmpty(mutantEntity)
            .flatMapMany(m -> repository.findByDna(m.getDna()))
            .next()
            .switchIfEmpty(Mono.just(mutantEntity))
            .filter(m -> Optional.ofNullable(m.getMutantId()).isPresent())
            .flatMap(e -> Mono.just(e.getIsMutant()))
            .switchIfEmpty(this.assessIfIsMutant(mutantEntity));
    }

    /**
     * En caso de no estar registrado en la base de datos se almacena el registro del
     * adn y se retorna la respuesta
     */
    private Mono<Boolean> assessIfIsMutant(Mutant mutant) {
        log.trace(Constants.STORED, mutant);
        return dnaProcessor.isMutant(mutant)
                        .flatMap(isMutantFlag -> {
                            System.out.println("Mutant : " + isMutantFlag);
                            mutant.setIsMutant(isMutantFlag);
                            return repository.save(mutant);
                        }).flatMap(e -> Mono.just(e.getIsMutant()));
    }

}

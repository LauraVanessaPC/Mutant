package com.mutant.mutantapp.infraestructure.entrypoints.presentation.controller.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutant.mutantapp.domain.model.mutant.MutantDto;
import com.mutant.mutantapp.domain.usecases.mutant.MutantService;
import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.Mutant;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase controladora que expone los puntos de entrada a la aplicación.
 * @author lauravanessap@gmail.com
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MutantController {

    private final MutantService mutantService;

    /**
     * Devuelve al cliente todos los humanos analizados en la API.
     * @return
     */
    @GetMapping("/list")
    public Flux<Mutant> listAllMutantStored(){
        return mutantService.listAllMutants();
    }

    /**
     * Devuelve al cliente si el adn del humano pasado a la API, es mutante o no.
     * @param mutant
     * @return
     */
    @PostMapping("/mutant")
    public Mono<ResponseEntity<Boolean>> isMutant(@RequestBody @Validated final MutantDto mutant){
        return mutantService.processDna(mutant)
            .filter(e -> e)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}

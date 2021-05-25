package com.mutant.mutantapp.infraestructure.entrypoints.presentation.controller.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutant.mutantapp.domain.model.stats.StatsDto;
import com.mutant.mutantapp.domain.usecases.stats.StatsService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Clase controladora que expone el punto de entrada para que sean devueltas las
 * cifras estadísticas de los adn's de humanos analizados a través de esta API.
 * @author lauravanessap@gmail.com
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/stats")
    public Mono<ResponseEntity<StatsDto>> statsMutant(){
        return statsService.getAllStats()
            .map(ResponseEntity::ok);
    }
}

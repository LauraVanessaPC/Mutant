package com.mutant.mutantapp.domain.usecases.mutant;

import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.Mutant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class DnaProcessorTest {

    String[] dnaPositive= {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    String[] dnaNegative= {"ATGCGA","CAGTGC", "TTATTT","AGACGG", "GCGTCA", "TCACTG"};
    String[] dnaNagative2 = {"GTGCCC","CAGTGT","TTATGT","AGAAGT","CCCCTA","TCACTG"};

    @Autowired
    DnaProcessor dnaProcessor;

    @BeforeEach
    void setUp() {
        dnaProcessor = new DnaProcessor ();
    }

    @Test
    public void testMutantOk(){

        Mutant mutant = new Mutant ();
        mutant.setDna (dnaPositive);

        Mono<Boolean> isMutant = dnaProcessor.isMutant (mutant);

        StepVerifier.create(isMutant).expectNext (Boolean.TRUE).verifyComplete ();


    }

    @Test
    public void testNoMutant(){

        Mutant mutant =  new Mutant();
        mutant.setDna (dnaNegative);

        Mutant mutant2 =  new Mutant();
        mutant2.setDna (dnaNagative2);

        Mono<Boolean> isMutant = dnaProcessor.isMutant (mutant);
        Mono<Boolean> isMutant2 = dnaProcessor.isMutant (mutant2);

        StepVerifier.create(isMutant).expectNext (Boolean.FALSE).verifyComplete ();
        StepVerifier.create(isMutant2).expectNext (Boolean.FALSE).verifyComplete ();
    }


}

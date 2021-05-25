package com.mutant.mutantapp.infraestructure.entrypoints.presentation.controller.advice;

import com.mutant.mutantapp.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

/**
 * Clase controladora que gestiona las excepciones lanzadas al interior de la aplicación.
 * @author lauravanessap@gmail.com
 */
@Log4j2
@RestControllerAdvice
public class ErrorApplicationAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<String> handleValidationExceptions(
            WebExchangeBindException ex) {
        log.error (Constants.VALIDATION_EXCEPTION_DNA, ex.getBindingResult ());
        return Mono.just ("Invalid sequence");
    }
}

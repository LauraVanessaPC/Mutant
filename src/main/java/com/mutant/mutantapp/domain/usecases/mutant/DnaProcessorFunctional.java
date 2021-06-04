package com.mutant.mutantapp.domain.usecases.mutant;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Clase que procesa la secuencia del adn para determinar si un humano es mutante o no, mediante programación funcional.
 * @author  lauravanessap@gmail.com
 */
@Service
@Log4j2
public class DnaProcessorFunctional {

    private static final int TARGET_TO_BE_MUTANT = 2;
    private static final int CONSECUTIVE_CONSTANTS = 4;

    public Mono<Boolean> isMutant(String ... dna){
        final int width = Arrays.stream (dna)
                .mapToInt (String::length)
                .max ()
                .orElse (0);
        int totalOfCoincidences = 0;

        System.out.println("-----------------------------------------HORIZONTAL");
        totalOfCoincidences += getTotals(dna, width, this::getImplementationHorizontal);
        System.out.println("-----------------------------------------VERTICAL");
        totalOfCoincidences += getTotals(dna, width, this::getImplementationVertical);
        System.out.println("-----------------------------------------DIAGONAL BACK SLASH");
        totalOfCoincidences += getTotals(dna, width, this::getImplementationDiagonalBackSlash);
        System.out.println("-----------------------------------------DIAGONAL SLASH");
        totalOfCoincidences += getTotals(dna, width, this::getImplementationDiagonalSlash);

        System.out.println("-----------------------------------------RESULTADOS");
        System.out.println("Total: " + totalOfCoincidences);
        return totalOfCoincidences >= TARGET_TO_BE_MUTANT ? Mono.just(true) : Mono.just(false);
    }

    private Stream<String> getImplementationHorizontal(String[] dna, Integer integer) {
        return Arrays.stream(dna);
    }

    private Stream<String> getImplementationVertical(String[] dna, Integer width) {
        return transposeDnaVertical(dna, width).stream();
    }

    private Stream<String> getImplementationDiagonalBackSlash(String[] dna, Integer width) {
        return transposeDnaBackSlashDiagonal(dna, width).stream();
    }

    private Stream<String> getImplementationDiagonalSlash(String[] dna, Integer width) {
        return Optional.of(invertDna(dna))
                .map(e -> transposeDnaBackSlashDiagonal(e, width))
                .get()
                .stream();
    }

    /**
     * Trae los totales de los distintos recorridos
     */
    private int getTotals(String[] arn,
                          Integer with,
                          BiFunction<String[], Integer, Stream<String>> callback){
        return callback.apply(arn, with)
                .mapToInt(this::countChain)
                .sum();
    }

    /**
     *Invierte la matriz con el Dna
     */
    private String[] invertDna(String[] dna){
        return Arrays.stream(dna)
                .map(chain -> {
                    StringBuilder input = new StringBuilder(chain);
                    return input.reverse().toString();
                }).toArray(String[]::new);
    }

    /**
     *Recorrer back slash o diagonal derecha
     */
    private List<String> transposeDnaBackSlashDiagonal(String[] dna, int width) {
        List<String> diagonalBackSlash = new ArrayList<> ();
        for (int i = 1 - width; i < dna.length; i++){
            StringBuilder newChain = new StringBuilder(width);
            for (int x = -min(0, i), y = max(0, i); x < width && y < dna.length; x++, y++){
                newChain.append(dna[y].charAt(x));
            }
            diagonalBackSlash.add(newChain.toString());
        }
        return diagonalBackSlash;
    }

    /**
     * Transponemos el dna verticalmente
     */
    private List<String> transposeDnaVertical(String[] dna, int width){
        List<String> verticalTransposeDna = new ArrayList<>();
        Flux.range(0, width)
                .map(col -> getVerticalString(width, col, dna))
                .map(verticalTransposeDna::add)
                .subscribe();
        return verticalTransposeDna;
    }

    /**
     * Trae los Substring cuando son verticales
     */
    private String getVerticalString(int max, Integer col, String[] dna) {
        StringBuilder newChain = new StringBuilder(max);
        return Flux.range(0,  dna.length)
                .map(row -> newChain.append(dna[row].charAt(col)).toString())
                .last()
                .block();
    }

    private Integer countChain(String innerChain){
        final Set<Character> unrepeatedString = new HashSet<>();
        innerChain.chars().forEach(e -> unrepeatedString.add((char) e));
        return unrepeatedString
                .stream()
                .mapToInt( e -> getOccurrences(innerChain, e))
                .sum();
    }

    /**
     *Trae las ocurrencias
     */
    private int getOccurrences(String innerChain, Character e) {
        final String findStr = e.toString().repeat(CONSECUTIVE_CONSTANTS);
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){
            lastIndex = innerChain.indexOf(findStr,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += findStr.length();
            }
        }
        System.out.println("innerChain: " + innerChain + " findChain: " + findStr + " occurrences: " + count);
        return count;
    }
}


package com.mutant.mutantapp.domain.usecases.mutant;

import org.springframework.stereotype.Service;

import com.mutant.mutantapp.infraestructure.drivenadapters.mongodb.Mutant;
import reactor.core.publisher.Mono;

/**
 * Clase que procesa la secuencia del adn para determinar si un humano es mutante o no.
 * @author  lauravanessap@gmail.com
 */
@Service
public class DnaProcessor {

    private int numberOfChains;

    /**
     * Método que determina si un ser humano es o no mutante.
     * @param mutant representa el ser humano a verificar
     * @return
     */
    synchronized Mono<Boolean> isMutant(final Mutant mutant){
        numberOfChains = 0;
        int limitIndex = mutant.getDna().length -4;
        
        for (int i = 0; i < mutant.getDna().length && numberOfChains <= 1; i++) {
            for (int j = 0; j < mutant.getDna().length && numberOfChains <= 1; j++) {
                Character characterX = mutant.getDna()[i].charAt (j);
                if(j <= limitIndex) {
                    findHorizontalChain (i, j, characterX, mutant);
                }
                if(i <= limitIndex){
                    if(numberOfChains <= 1){
                        findVerticalChain (i, j, characterX, mutant);
                    }
                    if(1 >= 4 - j && numberOfChains <= 1){
                        findLeftDiagonalChain (i, j, characterX, mutant);
                    }
                    if(mutant.getDna().length >= 4 + j && numberOfChains <= 1){
                        findRightDiagonalChain (i, j, characterX, mutant);
                    }
                }
            }
        }

        if(numberOfChains > 1)
            return Mono.just(true);
        else
            return Mono.just(false);

    }

    /**
     * Método que permite encontrar si hay una cadena horizontal formada por el caracter characterX, desde la posición:
     * fila rowI y columna columnJ.
     * @param rowI
     * @param columnJ
     * @param characterX
     * @param mutant
     *
     */
    void findHorizontalChain(int rowI, int columnJ, Character characterX, Mutant mutant){
        int repetitions = 1;
        boolean derecha = false;
        int row = rowI;
        int column = columnJ;
        do {
            if (column < mutant.getDna().length -1 &&
                Character.toString (mutant.getDna()[row].charAt (column + 1)).equals (characterX.toString ())) {
                derecha = true;
                repetitions += 1;
                column += 1;
            } else if(repetitions >= 4){
                numberOfChains += 1;
                derecha = false;
            } else{
                derecha = false;
            }
        } while (derecha);

    }

    /**
     * Método que permite encontrar si hay una cadena vertical formada por el caracter characterX, desde la posición:
     * fila rowI y columna columnJ.
     * @param rowI
     * @param columnJ
     * @param characterX
     * @param mutant
     */

    void findVerticalChain(int rowI, int columnJ, Character characterX, Mutant mutant){
        int repetitions = 1;
        boolean vertical = false;
        int row = rowI;
        int column = columnJ;
        do{
            if (row < mutant.getDna().length - 1
                && Character.toString (mutant.getDna()[row+1].charAt (column)).equals (characterX.toString ())) {
                vertical = true;
                repetitions += 1;
                row += 1;
            } else if(repetitions >= 4){
                numberOfChains += 1;
                vertical = false;
            } else{
                vertical = false;
            }
        } while (vertical);

        return;
    }

    /**
     * Método que permite encontrar si hay una cadena diagonal izquierda formada por el caracter characterX, desde la posición:
     * fila rowI y columna columnJ.
     * @param rowI
     * @param columnJ
     * @param characterX
     * @param mutant
     */
    void findLeftDiagonalChain(int rowI, int columnJ, Character characterX, Mutant mutant){
        int repetitions = 1;
        boolean leftDiagonal = false;
        int row = rowI;
        int column = columnJ;
        do{
            if (row < mutant.getDna().length - 1 &&
                column > 0 &&
                Character.toString (mutant.getDna()[row+1].charAt (column-1)).equals (characterX.toString ())) {
                leftDiagonal = true;
                repetitions += 1;
                row += 1;
                column -= 1;
            } else if(repetitions >= 4){
                numberOfChains += 1;
                leftDiagonal = false;
            } else{
                leftDiagonal = false;
            }
        } while (leftDiagonal);

        return;

    }

    /**
     * Método que permite encontrar si hay una cadena diagonal derecha formada por el caracter characterX, desde la posición:
     * fila rowI y columna columnJ.
     * @param rowI
     * @param columnJ
     * @param characterX
     * @param mutant
     */
    void findRightDiagonalChain(int rowI, int columnJ, Character characterX, Mutant mutant){
        int repetitions = 1;
        boolean rightDiagonal = false;
        int row = rowI;
        int column = columnJ;
        do{
            if (row < mutant.getDna().length - 1 &&
                column < mutant.getDna().length -1 &&
                Character.toString (mutant.getDna()[row+1].charAt (column+1)).equals (characterX.toString ())) {
                rightDiagonal = true;
                repetitions += 1;
                row += 1;
                column += 1;
            } else if(repetitions >= 4){
                numberOfChains += 1;
                rightDiagonal = false;
            } else{
                rightDiagonal = false;
            }
        } while (rightDiagonal);

        return;
    }



}

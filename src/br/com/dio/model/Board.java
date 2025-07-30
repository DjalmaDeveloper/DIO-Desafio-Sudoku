package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatusEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces; // Lista de listas de espaços (spaces)

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){ // Pegando a Lista 'spaces', usamos flatMap para pegar a lista interna e verificamos se todas as posições NÃO são fixas E NÃO é nula
            return NON_STARTED;
        } // Retorne o status NON_STARTED (NÃO_INICIADO)

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE; // Se algum espaço for nulo, ele está INCOMPLETO; SENÃO, está COMPLETO
    }

    public boolean hasErrors(){
        if(getStatus() == NON_STARTED){ // SE o status for NÃO_INICIADO, retorne FALSO
            return false;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected())); // SE algum espaço, cuja propriedade atual, não for nulo (preenchida) E se o valor NÃO for IGUAL ao expected (esperado)
    }

    public boolean changeValue(final int col, final int row, final int value){ // Lista externa representa as colunas (vertical), enquanto as listas internas representam as linhas (horizontal); o valor é o número que será acrescentado
        Space space = spaces.get(col).get(row); // Pegando a posição do espaço (coluna[col], linha[row)
        if(space.isFixed()){ // SE o espaço for FIXO
            return false;
        }

        space.setActual(value); // Alterando o valor do espaço
        return true; // Retornando condição verdadeira
    }

    public boolean clearValue(final int col, final int row){
        Space space = spaces.get(col).get(row);
        if(space.isFixed()){
            return false;
        }

        space.clearSpace(); // Espaço limpo
        return true; // Returnando condição verdadeira
    }

    public void reset(){ // Método para resetar o jogo
        spaces.forEach(c -> c.forEach(Space::clearSpace)); // Limpando todas as linhas e colunas do Tabuleiro (Board), percorrendo cada coluna (c)
    }

    public boolean gameIsFinished(){ // Verifica se o jogo acabou
        return !hasErrors() && getStatus() == COMPLETE; // Retornar SE NÃO há erros E se o status está como completo (COMPLETE)
        // return !hasErrors() && getStatus().equals(COMPLETE); SEGUNDA OPÇÃO DE CÓDIGO
    }
}

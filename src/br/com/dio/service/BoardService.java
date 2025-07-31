package br.com.dio.service;

import br.com.dio.model.Board;
import br.com.dio.model.GameStatusEnum;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService { // Começando a aprender a quebrar o software em camadas, começando pela pasta de Services

    private final static int BOARD_LIMIT = 9;

    private final Board board;

    public BoardService(final Map<String, String> gameConfig) { // Não será uma instância de board, mas sim uma instância do Map
        this.board = new Board(initBoard(gameConfig)); // A instância receberá um metodo que retorna o 'gameConfig'
    }

    public List<List<Space>> getSpaces(){
        return this.board.getSpaces();
    }

    public void reset(){
        this.board.reset();
    }

    public boolean hasErrors(){
        return this.board.hasErrors();
    }

    public GameStatusEnum getStatus(){
        return this.board.getStatus();
    }

    public boolean gameIsFinished(){
        return this.board.gameIsFinished();
    }

    private List<List<Space>> initBoard(Map<String, String> gameConfig) {
        List<List<Space>> spaces = new ArrayList<>();
        for(int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_LIMIT; j++){
                var positionConfig = gameConfig.get("%s,%s".formatted(i, j)); // Pegando posição fornecida pelo usuário - Trocando 'posisions' por 'gameConfig'
                var expected = Integer.parseInt(positionConfig.split(",")[0]); // Pegando o número do valor para a posição
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]); // Pegando o valor que indica se o espaço é fixo ou não
                var currentSpace = new Space(expected, fixed); // Objeto criado (Espaço[Space])
                spaces.get(i).add(currentSpace);
            }
        }

        return spaces; // Retornando o que a Função prometeu retornar (List<List<Space>>)
    }
}

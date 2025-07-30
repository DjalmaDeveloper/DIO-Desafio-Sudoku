package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner sc = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) { // Passamos no 'args', do metodo 'main', os valores para montar o sudoku
        final Map<String, String> positions = Stream.of(args) // Criando um Stream sobre os 'args'
                .collect(toMap(
                        k -> k.split(";")[0], // Separando os dois métodos por ponto-virgula(;), como foi feito nos 'args' acrescentados
                        v -> v.split(";")[1]
                ));
        int option = -1; // Iniciando valor inicial do menu de options
        while (true){ // Usando um while para criar o menu de opções (options)
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = sc.nextInt(); // Recebendo a escolha de opção do usuário

            switch (option){ // Switch das opções do jogo
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }
    // Métodos de cada opção do jogo
    private static void startGame(Map<String, String> positions) {
        if(nonNull(board)){ // SE o board não estiver nulo
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for(int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_LIMIT; j++){
                String positionConfig = positions.get("%s, %s".formatted(i, j)); // Pegando posição fornecida pelo usuário
                int expected = Integer.parseInt(positionConfig.split(",")[0]); // Pegando o número do valor para a posição
                boolean fixed = Boolean.parseBoolean(positionConfig.split(",")[1]); // Pegando o valor que indica se o espaço é fixo ou não
                Space currentSpace = new Space(expected, fixed); // Objeto criado (Espaço[Space])
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");
    }

    private static void inputNumber() {
        if(isNull(board)){ // SE o board estiver nulo
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido");
        int col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será inserido");
        int row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);
        int value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void removeNumber() {
        if(isNull(board)){ // SE o board estiver nulo
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido");
        int col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será inserido");
        int row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);
        if(!board.clearValue(col, row)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void showCurrentGame() {
    }

    private static void showGameStatus() {
    }

    private static void clearGame() {
    }

    private static void finishGame() {
    }

    private static int runUntilGetValidNumber(final int min, final int max){ // Metodo que repete a entrada do número até que insira um número válido
        int current = sc.nextInt();
        while(current < min || current > max){ // Enquanto o numero for menor que o mínimo OU maior que o máximo
            System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = sc.nextInt();
        }
        return current; // Retorna o número válido
    }

}

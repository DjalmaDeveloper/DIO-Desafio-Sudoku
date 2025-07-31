package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.*;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
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
                var positionConfig = positions.get("%s,%s".formatted(i, j)); // Pegando posição fornecida pelo usuário
                var expected = Integer.parseInt(positionConfig.split(",")[0]); // Pegando o número do valor para a posição
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]); // Pegando o valor que indica se o espaço é fixo ou não
                var currentSpace = new Space(expected, fixed); // Objeto criado (Espaço[Space])
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

        System.out.println("Informe a coluna em que o número será removido");
        int col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será removido");
        int row = runUntilGetValidNumber(0, 8);
        if(!board.clearValue(col, row)){
            System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
        }
    }

    private static void showCurrentGame() {
        if(isNull(board)){ // SE o board estiver nulo
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        Object[] args = new Object[81]; // Criando um Array de Objects com 81 posições
        int argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) { // Percorrendo a lista externa
            for (var col : board.getSpaces()){ // Percorrendo as listas internas (Colunas)
                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual()); // Verificando SE o espaço é Nulo, adiciona um espaço branco, SENÃO adiciona o valor contido
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args); // Imprimindo o Board na tela
    }

    private static void showGameStatus() {
        if(isNull(board)){ // SE o board estiver nulo
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status %s\n", board.getStatus().getLabel()); // Exibindo o Status Atual do jogo
        if(board.hasErrors()){ // Verificando se o jogo contém erros
            System.out.println("O jogo contém erros");
        }
        else{
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if(isNull(board)){ // SE o board estiver nulo
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?"); // Pergunta de confirmação
        String confirm = sc.next(); // Entrada da resposta
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){ // Repete a entrada de resposta enquanto o usuário NÃO colocar 'sim' ou 'não'
            System.out.println("Informe 'sim' ou 'não'");
            confirm = sc.next();
        }
        if(confirm.equalsIgnoreCase("sim")){ // Se responder 'sim', o tabuleiro reseta
            board.reset();
        }
    }

    private static void finishGame() {
        if(isNull(board)){ // SE o board estiver nulo
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        if(board.gameIsFinished()){ // Verifica SE o tabuleiro está terminado
            System.out.println("Parabéns! Você concluiu o jogo");
            showCurrentGame();
            board = null;
        }
        else if (board.hasErrors()){ // Verifica se o tabuleiro contém erros
            System.out.println("Seu jogo contém erros! Verifique seu board e ajuste-o");
        }
        else{ // Outra condição, caso não haja erros, mas tenha algum espaço pendente
            System.out.println("Você ainda precisa preencher algum espaço");
        }
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

package br.com.dio;

import br.com.dio.ui.custom.screen.MainScreen;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        final Map<String, String> gameConfig = Stream.of(args) // Criando um Stream sobre os 'args'
                .collect(toMap(
                        k -> k.split(";")[0], // Separando os dois métodos por ponto-virgula(;), como foi feito nos 'args' acrescentados
                        v -> v.split(";")[1]));
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }

}

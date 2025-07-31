package br.com.dio.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton { // Classe para o botão gráfico

    public CheckGameStatusButton(final ActionListener actionListener){
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }
}

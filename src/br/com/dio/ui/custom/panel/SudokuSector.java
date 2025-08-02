package br.com.dio.ui.custom.panel;

import br.com.dio.ui.custom.input.NumberText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

import static java.awt.Color.black;

public class SudokuSector extends JPanel {

    public SudokuSector(final List<NumberText> textFields){
        var dimension = new Dimension(170, 170); // Criando uma dimensão de largura e altura
        this.setSize(dimension); // Usando a dimension para definir tamanho
        this.setPreferredSize(dimension); // Usando a dimension para definir tamanho preferido
        this.setBorder(new LineBorder(black, 2, true)); // Definindo borda com linha preta, 2 de espessura e cantos arrendodados
        this.setVisible(true); // Tornar o componente visível
        textFields.forEach(this::add); // Adicionando os campos de texto para serem exibidos
    }
}

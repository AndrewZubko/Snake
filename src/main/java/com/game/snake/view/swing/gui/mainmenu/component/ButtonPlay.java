package com.game.snake.view.swing.gui.mainmenu.component;

import com.game.snake.controller.Controller;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * @author Koliadin Nikita
 * @version 1.14
 */
public class ButtonPlay implements MainMenuComponent {

    private final JButton jButtonPlay = new JButton();

    private final Container container;
    private final GridBagConstraints gridBagConstraints;

    @Getter @Setter private String text = String.valueOf("PLAY");

    @Getter @Setter private int buttonWidth = 150;
    @Getter @Setter private int buttonHeight = 25;

    public ButtonPlay(@NonNull final Container container,
                      @NonNull final GridBagConstraints gridBagConstraints) {
        this.container = container;
        this.gridBagConstraints = gridBagConstraints;
    }

    @Override
    public void init() {
        jButtonPlay.setText(text);
        setJButtonSize();
        gridBagConstraints.gridy++;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        container.add(jButtonPlay, gridBagConstraints);
    }

    @Override
    public void setAction() {
        jButtonPlay.addActionListener(e -> Controller.getInstance().startGame());
    }

    private void setJButtonSize() {
        jButtonPlay.setPreferredSize(new Dimension(
                buttonWidth,
                buttonHeight
        ));
        jButtonPlay.setMinimumSize(new Dimension(
                buttonWidth,
                buttonHeight
        ));
    }
}

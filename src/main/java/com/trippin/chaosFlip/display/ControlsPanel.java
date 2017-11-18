package com.trippin.chaosFlip.display;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControlsPanel
    extends JPanel
    implements ActionListener {

    private final JFrame parent;
    private final JButton stopButton;

    private static final long serialVersionUID = 1L;

    ControlsPanel(JFrame parent) {

        this.parent = parent;

        setMinimumSize(new Dimension(100, 20));

        stopButton = new JButton("stop");
        stopButton.addActionListener(this);
        add(stopButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == stopButton) {
            stop();
        }
    }

    private void stop() {

        MainMenu mainMenu = new MainMenu(parent);
        Container parentContainer = parent.getContentPane();
        parentContainer.removeAll();
        parentContainer.add(mainMenu);
        parentContainer.validate();
    }
}

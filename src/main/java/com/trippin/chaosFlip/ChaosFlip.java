package com.trippin.chaosFlip;

import javax.swing.JFrame;

import com.trippin.chaosFlip.display.MainMenu;

public class ChaosFlip extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String args[]) {

        ChaosFlip chaosFlip = new ChaosFlip();
        chaosFlip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chaosFlip.setSize(640, 960);
        chaosFlip.setVisible(true);
    }

    public ChaosFlip() {
        add(new MainMenu(this));
    }
}

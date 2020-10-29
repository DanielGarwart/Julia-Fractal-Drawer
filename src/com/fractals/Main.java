package com.fractals;
import javax.swing.*;
import java.awt.*;

public class Main {
    private static final int width = 800;
    private static final int height = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FractalPanel fractalPanel = new FractalPanel(width, height);
            new FractalWindow(fractalPanel); // reserves for further functionality in FractalWindow class

            Point p = fractalPanel.getMousePosition();
            if(p != null) { // this handles the potential exception getMousePosition could throw
                fractalPanel.lastXPos = p.x;
                fractalPanel.lastYPos = p.y;
            }

            fractalPanel.setFocusable(true);
            fractalPanel.requestFocusInWindow();
        });
    }
}
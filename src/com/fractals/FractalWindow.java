package com.fractals;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FractalWindow {
    private JFrame frame;
    private JMenuBar statusBar;
    private FractalPanel fractalPanel;


    private void createStatusBar() {
        this.statusBar = new JMenuBar();
        JMenu fractalMenu = new JMenu("Options");

        // Configure functionality for individual options

        JMenuItem chooseConstant = new JMenuItem("Choose Julia Constant");
        chooseConstant.addActionListener((ActionEvent e) -> {
            int width = 200, height = 310;

            JFrame whatToSelect = new JFrame();
            whatToSelect.setSize(width, height);
            whatToSelect.setLocationRelativeTo(this.frame);
            whatToSelect.setResizable(false);

            JLabel annotation = new JLabel("Select a Julia Constant");
            annotation.setBounds(0, 0, 200, 30);
            annotation.setHorizontalAlignment(JLabel.CENTER);

            // Fractal "Names"; indices for now
            final String[] fractalNames = new String[]{
                    "Julia 0", "Julia 1", "Julia 2", "Julia 3", "Julia 4",
                    "Julia 5", "Julia 6", "Julia 7", "Julia 8", "Julia 9"
            };

            JList<String> selectionList = new JList<>(fractalNames);
            selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            selectionList.addListSelectionListener((ListSelectionEvent selectionEvent) -> {
                fractalPanel.fractal.setConstants(
                        Fractal.constantsX[selectionList.getSelectedIndex()],
                        Fractal.constantsY[selectionList.getSelectedIndex()]
                );

                fractalPanel.repaint();
            });
            selectionList.setBounds(0, 30, 200, 200);

            JButton exit = new JButton("Exit!");
            exit.addActionListener((ActionEvent exitEvent) -> {
                whatToSelect.setVisible(false);
                whatToSelect.dispose();
            });
            exit.setBounds(0, 230, 200, 40);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setSize(width, height);

            panel.add(annotation);
            panel.add(selectionList);
            panel.add(exit);

            whatToSelect.add(panel);
            whatToSelect.setVisible(true);
        });

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(e -> this.saveFile());

        JMenuItem close = new JMenuItem("Close Window");
        close.addActionListener(e -> this.closeWindow());

        fractalMenu.add(chooseConstant);
        fractalMenu.add(save);
        fractalMenu.add(close);
        statusBar.add(fractalMenu);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this.frame, "Made by the G", "The Programmer", JOptionPane.INFORMATION_MESSAGE));
        statusBar.add(about);
    }

    private void saveFile() {
        String imageName = JOptionPane.showInputDialog(frame, "Specify a Filename:");
        if(imageName == null) return;
        fractalPanel.saveImage(imageName);
    }

    private void closeWindow() {
        frame.setVisible(false);
        frame.dispose();
    }

    public FractalWindow(FractalPanel panel) {
        this.fractalPanel = panel;
        frame = new JFrame();
        this.createStatusBar();

        frame.setSize(panel.width, panel.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fractal Drawer");
        frame.setResizable(false);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(this.statusBar);
        frame.setVisible(true);
    }
}
package com.fractals;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class FractalPanel extends JPanel implements KeyListener, MouseWheelListener, MouseMotionListener {
    private static double zoom = 1;
    public int lastXPos, lastYPos;
    private double moveX, moveY;
    public final int width;
    public final int height;
    protected Fractal fractal;

    public FractalPanel(int width, int height) {
        this.width = width;
        this.height = height;
        JLabel currMode = new JLabel("Equation : z^2 + c");
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        this.add(currMode, BorderLayout.PAGE_START);

        this.addKeyListener(this);
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);

        moveX = moveY = 0;
        this.fractal = new Fractal(width, height);
    }

    public void saveImage(String fileName)  {
        try {
            ImageIO.write(fractal.getFractalImage(zoom, moveX, moveY), "PNG", new File(fileName + ".png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(fractal.getFractalImage(zoom, moveX, moveY), 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_PLUS:
                zoom *= 1.5;
                break;
            case KeyEvent.VK_MINUS:
                zoom /= 1.5;
                break;
            case KeyEvent.VK_LEFT:
                moveX -= 0.005;
                break;
            case KeyEvent.VK_RIGHT:
                moveX += 0.005;
            case KeyEvent.VK_UP:
                moveY -= 0.005;
                break;
            case KeyEvent.VK_DOWN:
                moveY += 0.005;
                break;
            case KeyEvent.VK_ESCAPE:
                //closeApplication();
                break;
            default:
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double wheelRotation = e.getPreciseWheelRotation();

        if(wheelRotation < 0) {
            zoom *= 1.5;
        }
        else if(wheelRotation > 0) {
            zoom /= 1.5;
        }
        repaint();
    }

    @Override public void mouseMoved(MouseEvent e) {}

    @Override public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        moveX -= (x - lastXPos)*0.001;
        moveY -= (y - lastYPos)*0.001;
        lastXPos = x; lastYPos = y;
        repaint();
    }
}

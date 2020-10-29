package com.fractals;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fractal {
    private int width;
    private int height;
    private double cX;
    private double cY;
    private BufferedImage image;

    public static final double[] constantsX = {
            0.4, 0.285, 0.285, 0.45, -0.70176,
            -0.835, -0.8, -0.7269, 0.0, -0.7
    };

    public static final double[] constantsY = {
            0.6, 0.0, 0.01, 0.1428, -0.3842,
            -0.2321, 0.156, 0.1889, -0.8, 0.27015
    };

    public Fractal(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        cX = -0.7;
        cY = 0.27015;
    }

    public BufferedImage getFractalImage(double zoom, double moveX, double moveY) {
        double zx, zy;

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                zx = 1.5 * (x - width / 2.0) / (0.5 * zoom * width) + moveX; // (-2.5, 1)
                zy = (y - height / 2.0) / (0.5 * zoom * height) + moveY; // (−1, 1)

                int maxIter = 300;
                float i = maxIter;
                while (zx * zx + zy * zy < 4 && i > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    i--;
                }

                int color = Color.HSBtoRGB((maxIter / i) % 1, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, color);
            }
        }

        return image;
    }

    public void setConstants(double cX, double cY) {
        this.cX = cX;
        this.cY = cY;
    }
}

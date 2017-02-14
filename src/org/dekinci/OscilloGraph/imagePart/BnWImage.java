package org.dekinci.oscillograph.imagepart;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class's only purpose is to convert colored image into black and white image
 *
 */

class BnWImage {
    private BufferedImage convertingImage;

    /**
     * Constructor gets colored image and creates it's copy
     *
     * @param coloredImage the image to convert
     */
    BnWImage(BufferedImage coloredImage) {
        BufferedImage converted = new BufferedImage(coloredImage.getWidth(), coloredImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = converted.createGraphics();
        g.drawImage(coloredImage, 0, 0, null);
        g.dispose();
        convertingImage = converted;
    }

    /**
     *
     * @return converted image
     */
    public BufferedImage convertImage() {
        converter(calculateAverageBrightness());
        return convertingImage;
    }

    /**
     *
     * @param brightnessCoefficient to manipulate the brightness manually
     * @return converted image
     */
    public BufferedImage convertImage(double brightnessCoefficient) {
        converter(calculateAverageBrightness() - brightnessCoefficient);
        return convertingImage;
    }

    /**
     * Calculates brightness of the whole picture
     * uses average squares method
     *
     * @return picture's brightness
     */
    private double calculateAverageBrightness() {
        double averageBrightness = 0;
        for (int iterWidth = 0; iterWidth < convertingImage.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < convertingImage.getHeight(); iterHeight++)
                averageBrightness += Math.pow(calculatePixelBrightness(iterWidth, iterHeight), 2);

        averageBrightness /= (convertingImage.getHeight() * convertingImage.getWidth());
        return Math.sqrt(averageBrightness);
    }

    /**
     * runs throw and across the image, changing every pixel's color
     * @param finalBrightness
     */
    private void converter(double finalBrightness) {
        for (int iterWidth = 0; iterWidth < convertingImage.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < convertingImage.getHeight(); iterHeight++) {
                convertPixel(iterWidth, iterHeight, finalBrightness);
            }
    }


    private final int BLACK = 0x000000;
    private final int WHITE = 0xFFFFFF;
    /**
     * Converts pixel, situated in coordinates to the black or white color,
     * based on if it brighter or darker than the brightness of the picture
     *
     * @param positionWidth X coordinate of the pixel
     * @param positionHeight Y coordinate of the pixel
     * @param finalBrightness brightness of the picture, might be changed with coefficient
     */
    private void convertPixel(int positionWidth, int positionHeight, double finalBrightness) {
        if (calculatePixelBrightness(positionWidth, positionHeight) - finalBrightness < 0)
            convertingImage.setRGB(positionWidth, positionHeight, BLACK);
        else
            convertingImage.setRGB(positionWidth, positionHeight, WHITE);
    }

    /**
     * Calculate the brightness of the pixel, based on formula
     * @param positionWidth X position of the pixel
     * @param positionHeight Y position of the pixel
     * @return calculated brightness
     */
    private double calculatePixelBrightness(int positionWidth, int positionHeight) {
        Color clr = new Color(convertingImage.getRGB(positionWidth, positionHeight));
        return  0.3 * clr.getRed()
                + 0.59 * clr.getGreen()
                + 0.11 * clr.getBlue();
    }
}

package org.dekinci.OscilloGraph.imagePart;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class's only purpose is to convert colored image into black and white image
 *
 */

class BnWImage {
    private BufferedImage convertingImage;

    /**
     * Constructor gets colored image, than changes every pixel color to black or white,
     * according to brightness of the whole picture
     *
     * @param coloredImage the image to convert
     * @param brightnessCoefficient allows to change brightness of the result manually
     */
    BnWImage(BufferedImage coloredImage, double brightnessCoefficient) {
        convertingImage = coloredImage;
        double finalBrightness = calculateAverageBrightness() - brightnessCoefficient;

        for (int iterWidth = 0; iterWidth < convertingImage.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < convertingImage.getHeight(); iterHeight++) {
                convertPixel(iterWidth, iterHeight, finalBrightness);
            }
    }

    /**
     * Constructor gets colored image, than changes every pixel color to black or white,
     * according to brightness of the whole picture
     *
     * @param coloredImage the image to convert
     */
    BnWImage(BufferedImage coloredImage) {
        convertingImage = coloredImage;
        double finalBrightness = calculateAverageBrightness();

        for (int iterWidth = 0; iterWidth < convertingImage.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < convertingImage.getHeight(); iterHeight++) {
                convertPixel(iterWidth, iterHeight, finalBrightness);
            }
    }

    /**
     * Calulates the brightness of the whole picture
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
     * Converts pixel, situated in coordinates to the black or white color,
     * based on if it brighter or darker than the brightness of the picture
     *
     * @param positionWidth X coordinate of the pixel
     * @param positionHeight Y coordinate of the pixel
     * @param finalBrightness brightness of the picture, might be changed with coefficient
     */
    private void convertPixel(int positionWidth, int positionHeight, double finalBrightness) {
        if (calculatePixelBrightness(positionWidth, positionHeight) - finalBrightness < 0)
            convertingImage.setRGB(positionWidth, positionHeight, new Color(0, 0, 0).getRGB());
        else
            convertingImage.setRGB(positionWidth, positionHeight, new Color(255, 255, 255).getRGB());
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

    /**
     * redraw prevents wrong
     * @return the BnW picture
     */
    BufferedImage getBnWImage() {
        redraw();
        return convertingImage;
    }

    /**
     * To prevent bug with wrong colors
     */
    private void redraw() {
        BufferedImage converted = new BufferedImage(convertingImage.getWidth(), convertingImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = converted.createGraphics();
        g.drawImage(convertingImage, 0, 0, convertingImage.getWidth(), convertingImage.getHeight(), null);
        g.dispose();
        convertingImage = converted;
    }
}

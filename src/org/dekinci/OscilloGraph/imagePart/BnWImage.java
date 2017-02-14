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

        double finalBrightness = calculateAverageBrightness() + brightnessCoefficient;

        for (int iterWidth = 0; iterWidth < convertingImage.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < convertingImage.getHeight(); iterHeight++) {
                convertPixel(iterWidth, iterHeight, finalBrightness);
            }
        redraw();
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
        redraw();
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
            for (int iterHeight = 0; iterHeight < convertingImage.getHeight(); iterHeight++) {
                Color pixelColor = new Color(convertingImage.getRGB(iterWidth, iterHeight));
                double brightness = 0.3 * pixelColor.getRed()
                                + 0.59 * pixelColor.getGreen()
                                + 0.11 * pixelColor.getBlue();

                averageBrightness += brightness * brightness;            }
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
        Color pixelColor = new Color(convertingImage.getRGB(positionWidth, positionHeight));
        double brightness = 0.3 * pixelColor.getRed()
                        + 0.59 * pixelColor.getGreen()
                        + 0.11 * pixelColor.getBlue();

        if (brightness - finalBrightness < 0)
            convertingImage.setRGB(positionWidth, positionHeight, new Color(0, 0, 0).getRGB());
        else
            convertingImage.setRGB(positionWidth, positionHeight, new Color(255, 255, 255).getRGB());
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

    /**
     *
     * @return the BnW picture
     */
    BufferedImage getBnWImage() {
        return convertingImage;
    }
}

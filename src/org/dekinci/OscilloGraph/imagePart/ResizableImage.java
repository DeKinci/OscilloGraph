package org.dekinci.OscilloGraph.imagePart;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class only purpose is to DOWNSCALE the image to the convenient size
 * for displaying on the oscilloscope
 */

class ResizableImage {
    private BufferedImage resizingImage;

    /**
     * recreates an image with new size
     * saves the proportions of the image
     *
     * uses calculateWidth to save the pro
     *
     * @param importedImage an image to resize
     * @param scaledHeight desired height of the image
     */
    ResizableImage(BufferedImage importedImage, int scaledHeight) {
        resizingImage = importedImage;

        int scaledWidth = calulateWidth(scaledHeight);
        BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();

        g.drawImage(importedImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        this.resizingImage = scaled;
    }

    /**
     * recreates an image with new size
     * doesn't saves proportions of the image
     *
     * @param importedImage an image to resize
     * @param scaledHeight desired height of the image
     * @param scaledWidth desired width of the image
     */
    public ResizableImage(BufferedImage importedImage, int scaledHeight, int scaledWidth) {
        resizingImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizingImage.createGraphics();

        g.drawImage(importedImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
    }

    /**
     * calculates the proportions of the image
     * and then multiplies them on the known height of the resized image
     *
     * @param scaledHeight desired height
     * @return width based on height and keeping proportionals
     */
    private int calulateWidth (int scaledHeight) {
        return resizingImage.getWidth() * scaledHeight / resizingImage.getHeight(); // Wi / Hi = W / H
    }

    /**
     *
     * @return the buffered resized image
     */
    BufferedImage getResized() {
        return resizingImage;
    }
}

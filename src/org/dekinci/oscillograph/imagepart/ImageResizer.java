package org.dekinci.oscillograph.imagepart;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class only purpose is to DOWNSCALE the image to the convenient size
 * for displaying on the oscilloscope
 */

class ImageResizer {
    /**
     * creates image's copy with new size
     * saves the proportions of the image
     *
     * @param sourceImage an image to resize
     * @param scaledHeight desired height of the image
     */
    public BufferedImage resizeImage(BufferedImage sourceImage, int scaledHeight) {
        return resizer(sourceImage,
                sourceImage.getWidth() * scaledHeight / sourceImage.getHeight(), //scaled width calculations
                scaledHeight);
    }

    /**
     * creates image's copy with new size
     * doesn't saves proportions of the image
     *
     * @param sourceImage an image to resize
     * @param scaledHeight desired height of the image
     * @param scaledWidth desired width of the image
     */
    public BufferedImage resizeImage(BufferedImage sourceImage, int scaledHeight, int scaledWidth) {
        return resizer(sourceImage, scaledWidth, scaledHeight);
    }

    /**
     * creates image's copy with new size
     *
     * @param sourceImage an image to resize
     * @param scaledWidth desired width of the image
     * @param scaledHeight desired height of the image
     */
    private BufferedImage resizer(BufferedImage sourceImage, int scaledWidth, int scaledHeight) {
        BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(sourceImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaled;
    }
}

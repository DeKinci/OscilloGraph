package org.dekinci.oscillograph.imagepart;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class only purpose is to DOWNSCALE the image to the convenient size
 * for displaying on the oscilloscope
 */

class ResizableImage {
    private BufferedImage scalingImage;

    /**
     * creates image's copy with new size
     * saves the proportions of the image
     *
     * @param imageToResize an image to resize
     * @param scaledHeight desired height of the image
     */
    public BufferedImage resizeImage(BufferedImage imageToResize, int scaledHeight) {
        resizer(imageToResize,
                imageToResize.getWidth() * scaledHeight / imageToResize.getHeight(), //scaled width calculations
                scaledHeight);
        return scalingImage;
    }

    /**
     * creates image's copy with new size
     * doesn't saves proportions of the image
     *
     * @param imageToResize an image to resize
     * @param scaledHeight desired height of the image
     * @param scaledWidth desired width of the image
     */
    public BufferedImage resizeImage(BufferedImage imageToResize, int scaledHeight, int scaledWidth) {
        resizer(imageToResize, scaledWidth, scaledHeight);
        return scalingImage;
    }

    /**
     * creates image's copy with new size
     *
     * @param imageToResize an image to resize
     * @param scaledWidth desired width of the image
     * @param scaledHeight desired height of the image
     */
    private void resizer(BufferedImage imageToResize, int scaledWidth, int scaledHeight) {
        BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(imageToResize, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        scalingImage = scaled;
    }
}

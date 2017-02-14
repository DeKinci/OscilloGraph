package org.dekinci.oscillograph.imagepart;

import java.awt.*;
import java.awt.image.*;

/**
 * Easily edit the image to display it on oscilloscope
 */

public class EditableImage {
    private BufferedImage processingImage;

    /**
     * Creates a buffered image to edit
     * @param nonBufferedImage image to edit
     */
    public EditableImage(Image nonBufferedImage) {
        processingImage = new BufferedImage(nonBufferedImage.getWidth(null),
                                                nonBufferedImage.getHeight(null),
                                                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = processingImage.createGraphics();
        g.drawImage(nonBufferedImage, 0, 0, null);
        g.dispose();
    }

    /**
     * Uses BnWImage class to create black and white image
     * @param brightnessCoefficient to manipulate the brightness of the picture
     */
    public void convertToBnW(double brightnessCoefficient) {
        processingImage = new BnWImage(processingImage).convertImage(brightnessCoefficient);
    }

    /**
     * Uses BnWImage class to create black and white image
     */
    public void convertToBnW() {
        processingImage = new BnWImage(processingImage).convertImage();
    }

    /**
     * Uses ResizableImage class to downscale the image
     * @param scaledHeight desired height of the image
     */
    public void resize(int scaledHeight) {
        processingImage = new ResizableImage().resizeImage(processingImage, scaledHeight);
    }

    /**
     * @return buffered edited image
     */
    public BufferedImage getEditedImage() {
        return processingImage;
    }
}

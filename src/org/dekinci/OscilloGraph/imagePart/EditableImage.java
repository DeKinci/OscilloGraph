package org.dekinci.OscilloGraph.imagePart;

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
                                                BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = processingImage.createGraphics();
        bGr.drawImage(nonBufferedImage, 0, 0, null);
        bGr.dispose();
    }

    /**
     * Uses BnWImage class to create black and white image
     * @param brightnessCoefficient to manipulate with the brightness of the picture
     */
    public void convertToBnW(double brightnessCoefficient) {
        processingImage = new BnWImage(processingImage, brightnessCoefficient).getBnWImage();
    }

    /**
     * Uses BnWImage class to create black and white image
     */
    public void convertToBnW() {
        processingImage = new BnWImage(processingImage).getBnWImage();
    }

    /**
     * Uses ResizableImage class to downscale the image
     * @param scaledHeight desired height of the image
     */
    public void resize(int scaledHeight) {
        processingImage = new ResizableImage(processingImage, scaledHeight).getResized();
    }

    /**
     * @return buffered edited image
     */
    public BufferedImage getEditedImage() {
        return processingImage;
    }
}

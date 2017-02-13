package org.dekinci.OscilloGraph.comPart;

import java.awt.image.BufferedImage;

/**
 * Creates an image (type image biffer) you can transfer to arduino
 */

public class TransferableImage {
    private BufferedImage transferingImage;
    private ArduinoConnection connection;

    public TransferableImage(BufferedImage imageToTransfer) {
        transferingImage = imageToTransfer;
        connection = new ArduinoConnection();

        transferImage();
    }

    public void transferImage() {
        transferData();

        for (int iterWidth = 0; iterWidth < transferingImage.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < transferingImage.getHeight(); iterHeight++)
                transferPixel(iterWidth, iterHeight);
    }

    private void transferData() {
        connection.writeByte((byte) 0xC1);
        connection.writeInt(transferingImage.getHeight());
        connection.writeInt(transferingImage.getWidth());
    }

    private void transferPixel(int positionWidth, int positionHeight) {
        if (transferingImage.getRGB(positionWidth, positionHeight) == 0)
            connection.writeInt(1);               //The black color will be displayed
        else
            connection.writeInt(0);               //The white color will be background
    }
}

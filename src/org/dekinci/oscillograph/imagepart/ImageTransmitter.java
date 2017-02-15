package org.dekinci.oscillograph.imagepart;

import org.dekinci.oscillograph.compart.ArduinoConnection;

import java.awt.image.BufferedImage;

/**
 * Creates an image (type BufferedImage) you can transfer to arduino
 */

class ImageTransmitter {
    private ArduinoConnection connection;

    void transmitImage(BufferedImage imageToTransmit) {
        connection = new ArduinoConnection();
        transferData(imageToTransmit);

        for (int iterWidth = 0; iterWidth < imageToTransmit.getWidth(); iterWidth++)
            for (int iterHeight = 0; iterHeight < imageToTransmit.getHeight(); iterHeight++)
                transferPixel(imageToTransmit, iterWidth, iterHeight);
    }

    private void transferData(BufferedImage imageToTransmit) {
        connection.writeByte((byte) 0xC1);
        connection.writeInt(imageToTransmit.getHeight());
        connection.writeInt(imageToTransmit.getWidth());
    }

    private void transferPixel(BufferedImage imageToTransmit, int positionWidth, int positionHeight) {
        if (imageToTransmit.getRGB(positionWidth, positionHeight) == 0)
            connection.writeInt(1);               //The black color will be displayed
        else
            connection.writeInt(0);               //The white color will be background
    }
}

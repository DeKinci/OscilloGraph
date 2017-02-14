package org.dekinci.oscillograph.compart;

import jssc.*;

import java.util.Scanner;

/**
 * A connection with Arduino board usding COM port
 */

class ArduinoConnection {
    private SerialPort port;

    /**
     * Setting port up and testing it
     */
    ArduinoConnection() {
        port = new SerialPort(portChooser());

        portSetup();
        connectionTest();
    }

    /**
     * @return port used by connection
     */
    public SerialPort getPort() {
        return port;
    }

    /**
     * Looking for available COM ports and choosing one:
     * if no found - Exception
     * if 1 found - using it
     * if many found - choosing which to use
     *
     * @return port name
     */
    private String portChooser() {
        String[] ports = SerialPortList.getPortNames(); //looking for available ports

        if (ports.length == 0)
            throw new RuntimeException("No COM port found. " +
                                    "Please, connect the arduino and try again");

        else if (ports.length == 1) {
            System.out.println("Found only port " + ports[0] + ", using it");
            return ports[0];
        }

        else {
            System.out.println("Some ports found, please, choose which to use:");
            for (int portsOutputIterations = 0; portsOutputIterations < ports.length; portsOutputIterations++)
                System.out.println((portsOutputIterations + 1) + ") " + ports[portsOutputIterations]);

            while(true) {
                int answer = new Scanner(System.in).nextInt() - 1;

                if (answer >= 0 && answer < ports.length) {
                    System.out.println("Using " + ports[answer] + " to communicate");
                    return ports[answer];
                }

                System.out.println("Mistakes were made, please reenter the port chosen");
            }
        }
    }

    /**
     * Opening port and setting its params
     */
    private void portSetup() {
        setClosePortHook();

        try {
            port.openPort();
            port.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

        } catch (SerialPortException e) {
            throw new RuntimeException("Port setup error");
        }
    }

    /**
     * Sending test byte,
     * if no or wrong respond exception
     */
    private void connectionTest() {
        try {
            port.writeByte((byte) 0xF1);
        } catch (SerialPortException e) {
            throw new RuntimeException("Error port access");
        }

        addReceiveHandler();
        timeoutTimer(100, 5000);
    }

    /**
     * Adding listener to handle respond
     */
    private void addReceiveHandler() {
        try {
            port.setEventsMask(SerialPort.MASK_RXCHAR);
            port.addEventListener(event -> {
                try {
                    if(event.isRXCHAR() && event.getEventValue() == 1)
                        check(port.readBytes(1));
                } catch (SerialPortException e) {
                    throw new RuntimeException("Port read error");
                }
            });
        } catch (SerialPortException e) {
            throw new RuntimeException("Handler adding error");
        }
    }

    /**
     * if not false, the timer for responding timeout is on;
     */
    private boolean timerSwitch;

    /**
     * If timer ends, and no respond - exception
     * @param delay - how often to check the flag
     * @param timeout - waiting for connection
     */
    private void timeoutTimer(int delay, int timeout) {
        timerSwitch = true;
        while (timerSwitch && timeout >= 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread sleep error");
            }
            timeout -= delay;
        }

        if (timerSwitch)
            throw new RuntimeException("Connection timeout");
    }

    /**
     * when reply recieved check, if it from arduino or not
     * @param buffer - reply bytes
     */
    private void check(byte[] buffer) {
        timerSwitch = false;

        if (buffer == new byte[] { (byte) 0xA1 } )
            System.out.println("Port is opened and configured");
        else {
            try {
                port.closePort();
            } catch (SerialPortException e) {
                throw new RuntimeException("Port close error");
            }
            throw new RuntimeException("Error connecting the port, state " + Integer.toHexString(Byte.toUnsignedInt(buffer[0])));
        }
    }

    /**
     * just sending a byte through the port
     * @param X - send it
     */
    void writeByte(Byte X) {
        try {
            port.writeByte(X);
        } catch (SerialPortException e) {
            throw new RuntimeException("Error writing byte");
        }
    }

    /**
     * just sending an integer through the port
     * @param X - send it
     */
    void writeInt(int X) {
        try {
            port.writeInt(X);
        } catch (SerialPortException e) {
            throw new RuntimeException("Error writing integer");
        }
    }

    /**
     * Don't forgot to close it!!!
     */
    private void closePort() {
        try {
            if (port != null && port.isOpened()) {
                System.out.println("Closing port...");
                port.closePort();
            }
        } catch (SerialPortException e) {
            throw new RuntimeException("Error closing port");
        }
    }

    /**
     * No need to remember to close the port when transfer is finished!!!!!1!11!1!1!
     * This is sooo freaking awesome!!
     */
    private void setClosePortHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closePort));
    }
}

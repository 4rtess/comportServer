package com.artess.comportServer.serialport;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialPortController {
    private SerialPort serialPort;


    public SerialPortController(String portName) {

            serialPort = new SerialPort(portName);
            try {
                serialPort.openPort();
                serialPort.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE,
                        false,
                        false
                );
                serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
                serialPort.addEventListener(new EventListener());
            } catch (SerialPortException e) {
                System.out.println("Can't set params");
                e.printStackTrace();
            }
    }

    public void writeString(String data) {
        try{
            serialPort.writeString(data);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void closeSerialPort() {
        try {
            serialPort.closePort();
            System.out.println("Port closed");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    private class EventListener implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String data = serialPort.readString(event.getEventValue());
                    System.out.println(data);
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

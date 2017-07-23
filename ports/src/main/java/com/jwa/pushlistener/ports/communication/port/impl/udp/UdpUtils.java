package com.jwa.pushlistener.ports.communication.port.impl.udp;

import com.jwa.pushlistener.messagemodel.MessageModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class UdpUtils {
    public static final int DATAGRAM_BUFFER_SIZE = 1024; // 1 KiB
    public static final int MAX_SIZE_DATAGRAM_PAYLOAD = 65507; // source: http://openbook.rheinwerk-verlag.de/java7/1507_11_011.html#dodtp497f87ed-dd23-48d4-80c7-7e11b3ec99d6

    public static byte[] serialize(MessageModel msg) throws IOException {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(msg);
        oo.close();
        return bStream.toByteArray();
    }

    public static MessageModel deserialize(byte[] data) throws IOException {
        try {
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));
            MessageModel msg = (MessageModel) iStream.readObject();
            iStream.close();
            return msg;
        } catch (ClassNotFoundException e) {
            throw new IOException("...", e);
        }
    }

    public static void close(DatagramSocket datagramSocket) {
        if (datagramSocket != null && !datagramSocket.isClosed()) {
            datagramSocket.close();
        }
    }

    public static void send(MessageModel msg, SocketAddress recipient, DatagramSocket datagramSocket) throws IOException {
        byte[] byteMessage = serialize(msg);
        if (byteMessage.length > UdpUtils.MAX_SIZE_DATAGRAM_PAYLOAD) {
            throw new IOException("Message too large, exceeds " + UdpUtils.MAX_SIZE_DATAGRAM_PAYLOAD + " bytes");
        }
        DatagramPacket datagramPacket = new DatagramPacket(byteMessage, byteMessage.length, recipient);
        datagramSocket.send(datagramPacket);
    }
}

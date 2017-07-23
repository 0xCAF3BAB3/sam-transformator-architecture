package com.jwa.pushlistener.ports.communication.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.ReceiverHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpServer implements Runnable {
    private final int NUMBER_OF_CONCURRENT_PROCESSED_DATAGRAMS = 30;

    private final int port;
    private ReceiverHandler handler;
    private AsynchronousSenderCallback callback;

    private ExecutorService executorService;
    private DatagramSocket datagramSocket;

    public UdpServer(int port, ReceiverHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public UdpServer(int port, AsynchronousSenderCallback callback) {
        this.port = port;
        this.callback = callback;
    }

    public void start() throws CommunicationException {
        Thread t = new Thread(this, "UdpServer_port_" + port);
        this.executorService = Executors.newFixedThreadPool(NUMBER_OF_CONCURRENT_PROCESSED_DATAGRAMS, runnable -> new Thread(runnable, "UdpRequestWorkers"));
        t.start();
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            return;
        }

        try {
            while (true) {
                byte[] buffer = new byte[UdpUtils.DATAGRAM_BUFFER_SIZE];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                executorService.execute(new UdpRequestWorker(datagramPacket));
            }
        } catch (SocketException e) {
            // gets thrown when socket is closed --> ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() throws CommunicationException {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
        UdpUtils.close(datagramSocket);
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    private class UdpRequestWorker implements Runnable {
        private final DatagramPacket datagramPacket;

        private UdpRequestWorker(DatagramPacket datagramPacket) {
            this.datagramPacket = datagramPacket;
        }

        @Override
        public void run() {
            try {
                MessageModel request = UdpUtils.deserialize(datagramPacket.getData());
                if (handler != null) {
                    Optional<MessageModel> response = handler.handle(request);
                    SocketAddress recipient = datagramPacket.getSocketAddress();
                    UdpUtils.send(response.or(new UdpAckMessage()), recipient, datagramSocket);
                } else {
                    callback.process(request);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

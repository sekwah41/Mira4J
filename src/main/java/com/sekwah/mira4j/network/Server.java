package com.sekwah.mira4j.network;

import com.sekwah.mira4j.config.ServerConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.BindException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.sekwah.mira4j.Mira4J.LOGGER;

public class Server implements Runnable {

    private static Server instance;
    private final int port;
    private final String address;

    private final List<ConnectionManager> managers = Collections.synchronizedList(new ArrayList<ConnectionManager>());
    private final Thread tickThread = new Thread(this);

    public static Server getInstance() {
        return instance;
    }

    public Server(ServerConfig serverConfig) {
        instance = this;

        this.address = serverConfig.address;
        this.port = serverConfig.port;
    }

    public void start() throws InterruptedException, BindException {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            tickThread.setDaemon(true); // tell the vm not to wait for this thread to close
            tickThread.start();

            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<DatagramChannel>() {
                        @Override
                        public void initChannel(final DatagramChannel ch) throws Exception {
                            ConnectionManager manager = new ConnectionManager();

                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                new IncomingPacketHandler(manager),
                                new OutgoingPacketHandler()
                            );

                            managers.add(manager);
                            p.addLast(manager);
                        }
                    });

            LOGGER.info("waiting for message on {}:{}", address, port);
            b.bind(address, port).sync().channel().closeFuture().await();
        } finally {
            LOGGER.info("Server Closing");
        }
    }

    public void tick() {
        synchronized(managers) {
            Iterator<ConnectionManager> iterator = managers.iterator();
            while(iterator.hasNext()) {
                ConnectionManager manager = iterator.next();

                // Mira4J.LOGGER.info("hasClient: {} {}", manager, manager.hasClient());
                if(manager.hasClient()) {
                    try {
                        manager.tick();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    continue;
                }

                iterator.remove();
                manager.disconnect();
            }
        }
    }

    @Override
    public void run() {
        long last = System.currentTimeMillis();
        try {
            while(true) {
                if(Thread.interrupted())
                    break;

                long now = System.currentTimeMillis();

                // Tell the player that the server is overloaded ???
                if(now + 20L > last) {
                    last += 20L;

                    tick();
                } else {
                    Thread.sleep(1);
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

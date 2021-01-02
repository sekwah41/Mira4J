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

import static com.sekwah.mira4j.Mira4J.LOGGER;

public class Server {

    private static Server instance;
    private final int port;
    private final String address;

    // Milliseconds / tickRate
    private long tickDelay = 1000 / 30;

    //private final Thread tickThread = new Thread(new ServerTicker(this));

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

            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<DatagramChannel>() {
                        @Override
                        public void initChannel(final DatagramChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new IncomingPacketHandler(),
                                    new OutgoingPacketHandler()
                            );
                        }
                    });

            LOGGER.info("waiting for message on {}:{}", address, port);
            b.bind(address, port).sync().channel().closeFuture().await();
        } finally {
            LOGGER.info("Server Closing");
        }
    }

    /**
     * Not used for now. Though useful for later
     * tickThread.setDaemon(true); // tell the vm not to wait for this thread to close
     * tickThread.start();
     */
    public class ServerTicker implements Runnable {

        private Server server;

        public ServerTicker(Server server) {
            this.server = server;
        }

        public void tick() {

        }

        @Override
        public void run() {
            long last = System.currentTimeMillis();
            try {
                while(true) {
                    if(Thread.interrupted())
                        break;

                    last += tickDelay;

                    System.out.println("TICK");

                    tick();

                    // Tell the player that the server is overloaded ???
                    long delay = last - System.currentTimeMillis();
                    if(delay < 0) {
                        LOGGER.info("Server overloaded.");
                    }
                    Thread.sleep(delay);
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

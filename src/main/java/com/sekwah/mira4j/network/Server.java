package com.sekwah.mira4j.network;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.config.ServerConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetAddress;

import static com.sekwah.mira4j.Mira4J.LOGGER;

public class Server {

    private static Server instance;
    private final int port;
    private final String address;

    public static Server getInstance() {
        return instance;
    }

    public Server(ServerConfig serverConfig) {
        instance = this;

        this.address = serverConfig.address;
        this.port = serverConfig.port;
    }

    public void start() throws Exception {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(final NioDatagramChannel ch) throws Exception {

                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IncomingPacketHandler());
                        }
                    });


            LOGGER.info("waiting for message on {}:{}", address, port );
            b.bind(address,port).sync().channel().closeFuture().await();

        } finally {
            LOGGER.info("Server Closing");
        }
    }
}

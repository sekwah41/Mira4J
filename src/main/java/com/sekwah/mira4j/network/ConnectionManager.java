package com.sekwah.mira4j.network;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramChannel;

import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionManager extends SimpleChannelInboundHandler<Packet> {

    private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();

    public DatagramChannel channel;

    private boolean hasRemote;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = (DatagramChannel)ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        System.out.println("SOME READ THINGY");
    }

    public boolean hasClient() {
        return channel != null && (!hasRemote || channel.isConnected()) && channel.isOpen();
    }

    public void tick() {
        updateQueue();

        if (channel != null) {
            channel.flush();
        }
    }

    private void sendPacketBack(Packet packet) {
        if (channel.eventLoop().inEventLoop()) {
            ChannelFuture channelfuture = this.channel.writeAndFlush(packet);
            channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
            channel.eventLoop().execute(() -> {
                ChannelFuture channelfuture = this.channel.writeAndFlush(packet);
                channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            });
        }
    }

    public void updateQueue() {
        if (!hasClient()) return;

        synchronized (packetQueue) {
            Packet packet = null;
            while ((packet = packetQueue.poll()) != null) {
                sendPacketBack(packet);
            }
        }
    }

    public void close() {
        if (channel.isOpen()) {
            channel.close();
            disconnect();
        }
    }

    public void sendPacket(Packet packet) {
        if (hasClient()) {
            updateQueue();
            sendPacketBack(packet);
        } else {
            packetQueue.add(packet);
        }
    }

    public void disconnect() {
        hasRemote = false;
        if(this.channel == null) return;

        ChannelFuture channelfuture = this.channel.disconnect();
        channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public void connect(InetSocketAddress addr) {
        if(!hasRemote) {
            hasRemote = true;
            ChannelFuture channelfuture = this.channel.connect(addr);
            channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }

}

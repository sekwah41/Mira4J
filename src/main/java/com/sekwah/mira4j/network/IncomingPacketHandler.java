package com.sekwah.mira4j.network;

import com.sekwah.mira4j.Mira4J;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetAddress;
import java.util.Arrays;

public class IncomingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    public IncomingPacketHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        final int readableBytes = buf.readableBytes();
        final byte[] packetBuffer = new byte[readableBytes];
        buf.readBytes(packetBuffer);
        Mira4J.LOGGER.info("Recieved Packet {}", Arrays.toString(packetBuffer));
        Mira4J.LOGGER.info("Packet type {} {}", packetBuffer[0], Packets.PacketType.fromId(packetBuffer[0]).toString());
    }
}

package com.sekwah.mira4j.network;

import java.util.Arrays;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.data.PacketType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class IncomingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final ConnectionManager manager;

    public IncomingPacketHandler(ConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        manager.connect(msg.sender());

        buf.markReaderIndex();
        final int readableBytes = buf.readableBytes();
        final byte[] packetBuffer = new byte[readableBytes];
        buf.readBytes(packetBuffer);
        buf.resetReaderIndex();

        Mira4J.LOGGER.info("Recieved Packet {}", Arrays.toString(packetBuffer));
        Mira4J.LOGGER.info("Packet type {} {}", packetBuffer[0], PacketType.fromId(packetBuffer[0]).toString());

        decode(ctx, buf);
    }

    private void decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        PacketType packetType = PacketType.fromId(buf.readUnsignedByte());

        Packet<?> packet = Packets.getPacketFromType(packetType);
        if (packet == null) return;
        packet.readData(PacketBuf.wrap(buf));

        ctx.fireChannelRead(packet);
    }
}

package com.sekwah.mira4j.network;

import com.sekwah.mira4j.network.Packets.PacketType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutgoingPacketHandler extends ChannelOutboundHandlerAdapter {
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof Packet)) return;
        Packet<?> packet = (Packet<?>)msg;
        
        ByteBuf buf = ctx.alloc().ioBuffer();
        PacketType type = Packets.getPacketType(packet.getClass());
        buf.writeByte(type.getId());
        packet.writeData(PacketBuf.wrap(buf));
        ctx.write(buf, promise);
        
        
        /*
        buf.markReaderIndex();
        final int readableBytes = buf.readableBytes();
        final byte[] packetBuffer = new byte[readableBytes];
        buf.readBytes(packetBuffer);
        buf.resetReaderIndex();
        
        Mira4J.LOGGER.info("Sending packet {} {}", type, Arrays.toString(packetBuffer));
        */
    }
}

package com.sekwah.mira4j.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutgoingPacketHandler extends ChannelOutboundHandlerAdapter {
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof Packet)) return;
        Packet<?> packet = (Packet<?>)msg;
        
        ByteBuf buffer = ctx.alloc().ioBuffer();
        packet.writeData(buffer);
        ctx.write(buffer, promise);
    }
}

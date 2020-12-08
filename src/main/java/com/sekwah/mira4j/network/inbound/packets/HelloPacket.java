package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.MessageType;
import com.sekwah.mira4j.network.Packet;

import io.netty.buffer.ByteBuf;

public class HelloPacket implements Packet<ClientListener> {
    private String username;
    private MessageType type;
    
    @Override
    public void readData(ByteBuf reader) {
        // [PacketType], [MessageType??], [??], [??], [??], [??], [??], [??], [UsernameLength], [Username]
        
        type = MessageType.fromId(reader.readUnsignedByte()); // probably?
        reader.skipBytes(6);
        int length = reader.readUnsignedByte();
        
        byte[] username_buffer = new byte[length];
        reader.readBytes(username_buffer);
        username = new String(username_buffer);
    }

    @Override
    public void writeData(ByteBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onHelloPacket(this);
    }

    public String getUsername() {
        return username;
    }
    
    public MessageType getMessageType() {
        return type;
    }
}

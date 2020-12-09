package com.sekwah.mira4j.network;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketBuf {
    private ByteBuf buffer;
    private PacketBuf(ByteBuf buffer) {
        this.buffer = buffer;
    }
    
    public void markReaderIndex() {
        buffer.markReaderIndex();
    }
    
    public void resetReaderIndex() {
        buffer.resetReaderIndex();
    }
    
    public void markWriterIndex() {
        buffer.markWriterIndex();
    }
    
    public void resetWriterIndex() {
        buffer.resetWriterIndex();
    }
    
    public void skipBytes(int length) {
        buffer.skipBytes(length);
    }
    
    public int readableBytes() {
        return buffer.readableBytes();
    }
    
    public void release() {
        buffer.release();
    }
    
    public boolean readBoolean() {
        return buffer.readBoolean();
    }
    
    public byte readByte() {
        return buffer.readByte();
    }
    
    public short readShort() {
        return buffer.readShortLE();
    }
    
    public short readShortBE() {
        return buffer.readShort();
    }
    
    public int readInt() {
        return buffer.readIntLE();
    }
    
    public int readIntBE() {
        return buffer.readInt();
    }
    
    public short readUnsignedByte() {
        return buffer.readUnsignedByte();
    }
    
    public int readUnsignedShort() {
        return buffer.readUnsignedShortLE();
    }
    
    public int readUnsignedShortBE() {
        return buffer.readUnsignedShort();
    }
    
    public long readUnsignedInt() {
        return buffer.readUnsignedIntLE();
    }
    
    public long readUnsignedIntBE() {
        return buffer.readUnsignedInt();
    }
    
    // packed stuff is LEB128. Could we wrong. double check them
    public int readUnsignedPacketInt() {
        int result = 0;
        int shift = 0;
        while (true) {
            int tmp = readUnsignedByte();
            result |= (tmp & 0x7f) << shift;
            if((result & 0x80) == 0) break;
            shift += 7;
        }
        
        return result;
    }
    
    public int readPacketInt() {
        int result = 0;
        int shift = 0;
        int tmp = 0;
        
        do {
            tmp = readUnsignedByte();
            result |= (tmp & 0x7f) << shift;
            shift += 7;
        } while ((tmp & 0x80) != 0);
        
        int size = 32;
        
        if ((shift < size) && ((tmp & 0x40) != 0)) {
            result |= (~0 << shift);
        }
        
        return result;
    }
    
    public String readString() {
        int size = readUnsignedPacketInt();
        byte[] bytes = new byte[size];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return bytes;
    }
    
    public void writeBoolean(boolean value) {
        buffer.writeBoolean(value);
    }
    
    public void writeByte(int value) {
        buffer.writeByte(value);
    }
    
    public void writeShort(int value) {
        buffer.writeShortLE(value);
    }
    
    public void writeShortBE(int value) {
        buffer.writeShort(value);
    }
    
    public void writeInt(int value) {
        buffer.writeIntLE(value);
    }
    
    public void writeIntBE(int value) {
        buffer.writeInt(value);
    }
    
    public void writeUnsignedPacketInt(int value) {
        do {
            int tmp = value & 0x7f;
            value >>= 7;
            if (value != 0) {
                tmp |= 0x80;
            }
            
            buffer.writeByte(tmp);
        } while (value != 0);
    }
    
    public void writePacketInt(int value) {
        boolean more = true;
        boolean negative = (value < 0);

        while (more) {
            int tmp = value & 0x7f;
            value >>= 7;
            
            if (negative) {
                value |= (~0 << (25));
            }
            
            int sign = tmp & 0x80;
            if ((value == 0 && (sign == 0)) || (value == -1 && (sign != 0))) {
                more = false;
            } else {
                tmp |= 0x80;
            }
            
            buffer.writeByte(tmp);
        }
    }
    
    public void writeString(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeUnsignedPacketInt(bytes.length);
        buffer.writeBytes(bytes);
    }
    
    public void writeBytes(byte[] bytes) {
        buffer.writeBytes(bytes);
    }
    
    public static PacketBuf wrap(ByteBuf buffer) {
        return new PacketBuf(buffer);
    }
    
    public static PacketBuf wrap(byte[] bytes) {
        return new PacketBuf(Unpooled.wrappedBuffer(bytes));
    }
}

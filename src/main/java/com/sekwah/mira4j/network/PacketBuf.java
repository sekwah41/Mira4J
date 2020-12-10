package com.sekwah.mira4j.network;

import java.nio.charset.StandardCharsets;

import com.sekwah.mira4j.game.GameOptionsData;
import com.sekwah.mira4j.network.Packets.Maps;

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
    
    public float readFloat() {
        return buffer.readFloatLE();
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
    
    public GameOptionsData readGameOptionsData() {
        GameOptionsData data = new GameOptionsData();
        
        // TODO: Make sure that we read all the packet bytes!
        /* int length = */ readUnsignedPacketInt();
        try {
            data.version = readByte();
            data.maxPlayers = readByte();
            data.keywords = readUnsignedInt();
            data.maps = Maps.fromId(readByte());
            data.playerSpeedModifier = readFloat();
            data.crewmateLightModifier = readFloat();
            data.impostorLightModifier = readFloat();
            data.killCooldown = readFloat();
            data.numCommonTasks = readByte();
            data.numLongTasks = readByte();
            data.numShortTasks = readByte();
            data.numEmergencyMeetings = readUnsignedInt();
            data.numImpostors = readByte();
            data.killDistance = readByte();
            data.discussionTime = readUnsignedInt();
            data.votingTime = readUnsignedInt();
            data.isDefaults = readBoolean();
            data.emergencyCooldown = readByte();
            data.confirmEjects = readBoolean();
            data.visualTasks = readBoolean();
            data.anonymousVotes = readBoolean();
            data.taskBarUpdates = readByte();
        } catch(IndexOutOfBoundsException e) {
        }
        
        return data;
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
    
    public void writeUnsignedShortBE(long value) {
        buffer.writeShort((int)value);
    }
    
    public void writeInt(int value) {
        buffer.writeIntLE(value);
    }
    
    public void writeUnsignedInt(long value) {
        buffer.writeIntLE((int)value);
    }
    
    public void writeIntBE(int value) {
        buffer.writeInt(value);
    }
    
    public void writeFloat(float value) {
        buffer.writeFloatLE(value);
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
    
    public void writeGameOptionsData(GameOptionsData data) {
        PacketBuf buf = PacketBuf.wrap(new byte[2048]);
        buf.writeByte(data.version);
        buf.writeByte(data.maxPlayers);
        buf.writeUnsignedInt(data.keywords);
        buf.writeByte(data.maps.getId());
        buf.writeFloat(data.playerSpeedModifier);
        buf.writeFloat(data.crewmateLightModifier);
        buf.writeFloat(data.impostorLightModifier);
        buf.writeFloat(data.killCooldown);
        buf.writeByte(data.numCommonTasks);
        buf.writeByte(data.numLongTasks);
        buf.writeByte(data.numShortTasks);
        buf.writeUnsignedInt(data.numEmergencyMeetings);
        buf.writeByte(data.numImpostors);
        buf.writeByte(data.killDistance);
        buf.writeUnsignedInt(data.discussionTime);
        buf.writeUnsignedInt(data.votingTime);
        buf.writeBoolean(data.isDefaults);
        buf.writeByte(data.emergencyCooldown);
        buf.writeBoolean(data.confirmEjects);
        buf.writeBoolean(data.visualTasks);
        buf.writeBoolean(data.anonymousVotes);
        buf.writeByte(data.taskBarUpdates);
        
        byte[] bytes = buf.readBytes(buf.readableBytes());
        writeUnsignedPacketInt(bytes.length);
        writeBytes(bytes);
    }
    
    public static PacketBuf wrap(ByteBuf buffer) {
        return new PacketBuf(buffer);
    }
    
    public static PacketBuf wrap(byte[] bytes) {
        return new PacketBuf(Unpooled.wrappedBuffer(bytes));
    }

    /**
     * You need to release the buffer returned from this method
     */
    public static PacketBuf create(int size) {
        return new PacketBuf(Unpooled.buffer(size));
    }
}

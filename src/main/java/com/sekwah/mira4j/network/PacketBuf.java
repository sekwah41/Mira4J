package com.sekwah.mira4j.network;

import com.sekwah.mira4j.game.GameOptionsData;
import com.sekwah.mira4j.math.Vector2;
import com.sekwah.mira4j.network.data.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class PacketBuf {
    private LinkedList<ByteBuf> messages = new LinkedList<>();
    private ByteBuf buffer;
    private PacketBuf(ByteBuf buffer) {
        this.buffer = buffer;
    }

    public int readerIndex() {
        return buffer.readerIndex();
    }

    public int writerIndex() {
        return buffer.writerIndex();
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

    public void writerIndex(int writerIndex) {
        buffer.writerIndex(writerIndex);
    }

    public void readerIndex(int readerIndex) {
        buffer.readerIndex(readerIndex);
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

    /**
     * The packet buf returned from this method must call {@link #release()}
     */
    @Nonnull
    public PacketBuf readMessage() {
        if(readableBytes() < 3) return wrap(new byte[0]); // No header
        int length = readUnsignedShort();
        @SuppressWarnings("unused")
        int typeId = readUnsignedByte(); // Unused!?
        byte[] bytes = readBytes(length);
        return PacketBuf.wrap(bytes);
    }

    /**
     * The packet buf returned from this method must call {@link #release()}
     * @apiNote First byte will be the type id
     */
    @Nonnull
    public PacketBuf readMessageKeepId() {
        if(readableBytes() < 3) return wrap(new byte[0]); // No header
        int length = readUnsignedShort();
        if(readableBytes() < length + 1) {
            length = readableBytes() - 1;
        }

        byte[] bytes = readBytes(length + 1);
        return PacketBuf.wrap(bytes);
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

    public Vector2 readVector2() {
        float x = (readUnsignedShort() / 65535.0f);
        float y = (readUnsignedShort() / 65535.0f);
        return new Vector2(x * 80.0f - 40.0f, y * 80.0f - 40.0f);
    }

    // packed stuff is LEB128. Could we wrong. double check them
    public int readUnsignedPackedInt() {
        int result = 0;
        int shift = 0;
        while (true) {
            int tmp = readUnsignedByte();
            result |= (tmp & 0x7f) << shift;
            if((tmp & 0x80) == 0) break;
            shift += 7;
        }

        return result;
    }

    public int readPackedInt() {
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
        int size = readUnsignedPackedInt();
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
        /* int length = */ readUnsignedPackedInt();
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

    public void writeUnsignedByte(int value) {
        buffer.writeByte(value);
    }

    public void writeShort(int value) {
        buffer.writeShortLE(value);
    }

    public void writeUnsignedShort(int value) {
        buffer.writeShortLE(value);
    }

    public void writeShortBE(int value) {
        buffer.writeShort(value);
    }

    public void writeUnsignedShortBE(int value) {
        buffer.writeShort(value);
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

    public void writeVector2(Vector2 vector) {
        if(vector == null) vector = Vector2.ZERO;

        int x = (int)(((vector.x + 40.0f) / 80.0f) * 65535);
        int y = (int)(((vector.y + 40.0f) / 80.0f) * 65535);

        if(x < 0) x = 0;
        if(x > 65535) x = 65535;
        if(y < 0) y = 0;
        if(y > 65535) y = 65535;
        writeUnsignedShort(x);
        writeUnsignedShort(y);
    }

    public void writeUnsignedPackedInt(int value) {
        do {
            int tmp = value & 0x7f;
            value >>>= 7;
            if (value != 0) {
                tmp |= 0x80;
            }

            buffer.writeByte(tmp);
        } while (value != 0);
    }

    public void writePackedInt(int value) {
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
        writeUnsignedPackedInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    public void writeBytes(byte[] bytes) {
        buffer.writeBytes(bytes);
    }

    public void writeGameOptionsData(GameOptionsData data) {
        PacketBuf buf = PacketBuf.create(4096);

        try {
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
            writeUnsignedPackedInt(bytes.length);
            writeBytes(bytes);
        } finally {
            buf.release();
        }
    }

    public static PacketBuf wrap(ByteBuf buffer) {
        return new PacketBuf(buffer);
    }

    public static PacketBuf wrap(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        return new PacketBuf(buf);
    }

    /**
     * You need to release the buffer returned from this method
     */
    public static PacketBuf create(int size) {
        return new PacketBuf(Unpooled.buffer(size));
    }

    public void startMessage(int id) {
        messages.add(buffer);
        buffer = Unpooled.buffer(65536);
        buffer.writeByte(id);
    }

    public void endMessage() {
        byte[] bytes = readBytes(buffer.readableBytes());
        buffer.release(); // Release the buffer

        buffer = messages.pollLast();
        writeShort(bytes.length - 1); // length - 1 for Id
        writeBytes(bytes);
    }
}

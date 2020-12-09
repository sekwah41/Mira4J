package com.sekwah.mira4j.network;

public interface Packet<T> {
    void readData(PacketBuf reader);
    void writeData(PacketBuf writer);
    void forwardPacket(T listener);
    
    /**
     * Convert the value from {{@link #writeData(PacketBuf)} to a byte array
     */
    default byte[] toBytes() {
        PacketBuf buf = PacketBuf.create(65536);
        writeData(buf);
        byte[] array = buf.readBytes(buf.readableBytes());
        
        // Release the buffer
        buf.release();
        return array;
    }
}

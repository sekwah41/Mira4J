package com.sekwah.mira4j.network.packets.server;

import java.util.ArrayList;
import java.util.List;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.decoder.HazelDecoder;

public class SReliablePacket implements Packet<SClientListener> {
    private int nonce;
    private List<SHazelMessage> messages;

    public SReliablePacket() {

    }

    public SReliablePacket(int nonce, SHazelMessage... args) {
        this.nonce = nonce;
        messages = new ArrayList<>();

        for(SHazelMessage msg : args) {
            messages.add(msg);
        }
    }

    @Override
    public void readData(PacketBuf reader) {
        nonce = reader.readUnsignedShortBE();
        byte[] data = reader.readBytes(reader.readableBytes());

        PacketBuf wrap = PacketBuf.wrap(data);
        messages = new ArrayList<>();
        int max_tries = 10;
        while(wrap.readableBytes() > 0 && (max_tries-- > 0)) {
            SHazelMessage msg = HazelDecoder.decode(wrap);
            if(msg == null) continue;
            messages.add(msg);
        }
    }

    @Override
    public void writeData(PacketBuf writer) {
        writer.writeUnsignedShortBE(nonce);
        for(SHazelMessage msg : messages) {
            msg.writeData(writer);
        }
    }

    @Override
    public void forwardPacket(SClientListener listener) {
        listener.onReliablePacket(this);
    }

    public int getNonce() {
        return nonce;
    }

    public List<SHazelMessage> getMessages() {
        return messages;
    }
}

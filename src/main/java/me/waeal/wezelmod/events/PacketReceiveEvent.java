package me.waeal.wezelmod.events;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketReceiveEvent extends Event {
    private final Packet packet;
    private final ChannelHandlerContext ctx;

    public PacketReceiveEvent(Packet packet, ChannelHandlerContext ctx) {
        this.packet = packet;
        this.ctx = ctx;
    }

    public Packet getPacket() {
        return packet;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}

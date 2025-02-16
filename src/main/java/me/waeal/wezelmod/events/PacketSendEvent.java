package me.waeal.wezelmod.events;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketSendEvent extends Event {
    private final Packet packet;
    private final ChannelHandlerContext ctx;
    private final ChannelPromise promise;

    public PacketSendEvent(Packet packet, ChannelHandlerContext ctx, ChannelPromise promise) {
        this.packet = packet;
        this.ctx = ctx;
        this.promise = promise;
    }

    public Packet getPacket() {
        return packet;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public ChannelPromise getPromise() {
        return promise;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}

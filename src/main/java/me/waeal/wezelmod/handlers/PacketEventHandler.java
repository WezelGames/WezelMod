package me.waeal.wezelmod.handlers;

import io.netty.channel.*;
import java.net.SocketAddress;
import me.waeal.wezelmod.events.PacketReceiveEvent;
import me.waeal.wezelmod.events.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@ChannelHandler.Sharable
public class PacketEventHandler extends SimpleChannelInboundHandler<Packet> implements ChannelOutboundHandler {
    public PacketEventHandler() {
        super(false);
    }

    @SubscribeEvent
    public void connect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ChannelPipeline pipeline = event.manager.channel().pipeline();
        pipeline.addBefore("packet_handler", this.getClass().getName(), this);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg){
        PacketReceiveEvent event = new PacketReceiveEvent(msg, ctx);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled())
            ctx.fireChannelRead(msg);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
        ctx.bind(localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.disconnect(promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.close(promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.deregister(promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) {
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if (msg instanceof Packet) {
            PacketSendEvent event = new PacketSendEvent((Packet) msg, ctx, promise);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
                return;
        }
        ctx.write(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}

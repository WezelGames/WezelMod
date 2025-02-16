package me.waeal.wezelmod.listeners;

import java.util.HashSet;
import java.util.Random;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.events.PacketReceiveEvent;
import me.waeal.wezelmod.events.PacketSendEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PickPingListener {
    private final Random random = new Random();
    private final HashSet<BlockPos> positions = new HashSet<>();

    @SubscribeEvent
    public void onSend(PacketSendEvent event) {
        if (!Main.settings.pickPing)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (!(event.getPacket() instanceof C07PacketPlayerDigging
                && isPick(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem())))
            return;

        positions.add(((C07PacketPlayerDigging) event.getPacket()).getPosition());
    }

    @SubscribeEvent
    public void onReceive(PacketReceiveEvent event) {
        if (!Main.settings.pickPing)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (!(event.getPacket() instanceof S23PacketBlockChange)
                || ((S23PacketBlockChange) event.getPacket()).getBlockState().getBlock() == Block.getBlockById(0)
                || !positions.contains(((S23PacketBlockChange) event.getPacket()).getBlockPosition()))
            return;

        positions.remove(((S23PacketBlockChange) event.getPacket()).getBlockPosition());
        event.setCanceled(true);
        new Thread(() -> {
            try {
                Thread.sleep(Main.settings.pickDelay +
                                     (Main.settings.pickDelay > 25?
                                             random.nextInt(50) - 25
                                             :0));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                event.getCtx().fireChannelRead(event.getPacket());
            }
        }).start();
    }

    private boolean isPick(ItemStack item) {
        return item != null &&
                (Item.getIdFromItem(item.getItem()) == 257
                || Item.getIdFromItem(item.getItem()) == 274
                || Item.getIdFromItem(item.getItem()) == 270
                || Item.getIdFromItem(item.getItem()) == 285
                || Item.getIdFromItem(item.getItem()) == 278);
    }
}

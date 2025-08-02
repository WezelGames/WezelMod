package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class FreeMouseKeybindingListener {
    private boolean freeMouse;
    public float yaw;
    public float pitch;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!Main.freeMouseBind.isPressed())
            return;

        yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        pitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

        Mouse.setGrabbed(freeMouse);
        freeMouse = !freeMouse;
        if (ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/shmouselock") == 0)
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Couldn't lock mouse rotation " + EnumChatFormatting.RESET + "(Tell Waeal he's a lazy fuck and should make his own rotation lock)"));
    }

//    @SubscribeEvent
//    public void onRender(TickEvent.RenderTickEvent event) {
//        if (!freeMouse)
//            return;
//
//        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
//        if (player.rotationYaw == yaw && player.rotationPitch == pitch)
//            return;
//
//        Minecraft.getMinecraft().theWorld.playSound(player.posX, player.posY, player.posZ, "note.pling", 100, 10, false);
//        if (Main.settings.macroToggle)
//            ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/wezelmacro disable");
//    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (freeMouse && event.gui instanceof GuiIngameMenu)
            event.gui = null;
        if (freeMouse && event.gui == null)
            Mouse.setGrabbed(false);
    }
}

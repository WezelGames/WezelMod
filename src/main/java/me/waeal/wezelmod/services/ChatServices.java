package me.waeal.wezelmod.services;

import me.waeal.wezelmod.mixin.mixins.AccessorGuiNewChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.springframework.stereotype.Service;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.List;

@Service
public class ChatServices {
    public void copyChat() {
        GuiNewChat guiChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        AccessorGuiNewChat gui = (AccessorGuiNewChat) guiChat;
        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY();
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());

        int i = scaledresolution.getScaleFactor();
        float f = Minecraft.getMinecraft().gameSettings.chatScale;
        int j = mouseX / i - 3;
        int k = mouseY / i - 27;
        j = MathHelper.floor_float((float)j / f);
        k = MathHelper.floor_float((float)k / f);

        if (j >= 0 && k >= 0) {
            int l = Math.min(guiChat.getLineCount(), gui.getDrawnChatLines().size());

            if (j <= MathHelper.floor_float((float) guiChat.getChatWidth() / f) && k < Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * l + l) {
                int i1 = k / Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;

                if (i1 >= 0 && i1 < gui.getDrawnChatLines().size()) {
                    ChatLine chatline = findChatLine(gui.getChatLines(), MathHelper.floor_float(guiChat.getChatWidth() / guiChat.getChatScale()), gui.getScrollPos() + i1);
                    if (chatline == null)
                        return;

                    try {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(chatline.getChatComponent().getUnformattedText()), null);
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "WezelMod" + EnumChatFormatting.RESET + " > Copied Chat"));
                    } catch (IllegalStateException e) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "WezelMod" + EnumChatFormatting.RESET + " > Couldn't Copy Chat, some other software is probably blocking your clipboard."));
                    }
                }
            }
        }
    }

    private ChatLine findChatLine(List<ChatLine> list, int j, int index) {
        int lineCounter = 0;
        for (ChatLine line : list) {
            lineCounter += GuiUtilRenderComponents.splitText(line.getChatComponent(), j, Minecraft.getMinecraft().fontRendererObj, false, false).size();
            if (lineCounter > index)
                return line;
        }
        return null;
    }

    public void displayUserInfo(String username) {

    }
}

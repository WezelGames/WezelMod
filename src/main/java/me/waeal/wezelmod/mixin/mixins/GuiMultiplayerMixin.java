package me.waeal.wezelmod.mixin.mixins;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.WezelServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GuiMultiplayer.class)
public class GuiMultiplayerMixin extends GuiScreen {

    @Inject(method = "initGui", at = @At("RETURN"))
    private void addCustomButton(CallbackInfo ci) {
        String name = "Proxy Disabled";
        if (Main.settings.proxyEnabled)
            name = Main.settings.proxyIP;

        this.buttonList.add(new GuiButton(99, this.width - 120, 8, 100, 20, name) {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                if (!this.visible)
                    return;

                int color = Main.settings.proxyEnabled ? 0x00FF00 : 0xFF0000;
                super.drawButton(mc, mouseX, mouseY);
                this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
            }
        });
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    private void onButtonClick(GuiButton button, CallbackInfo ci) throws IOException {
        if (button.id == 99)
            WezelServices.openSettingsGui();
    }
}
package com.trials.modsquad.gui;

import com.trials.modsquad.ModSquad;
import com.trials.modsquad.block.tile.TileCharger;
import com.trials.modsquad.block.container.ContainerCharger;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.PowerBar;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;

public class GUICharger extends GuiContainer {

    private ITeslaHolder charger;
    private PowerBar p;
    private Field x;
    private Field y;

    public GUICharger(InventoryPlayer player, TileCharger charger) {
        super(new ContainerCharger(player, charger));
        this.charger = charger.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, EnumFacing.DOWN);
        p = new PowerBar(this, ((width -xSize)/2) + 8, ((height-ySize)/2)+15, PowerBar.BackgroundType.LIGHT);
        try {
            x = PowerBar.class.getDeclaredField("x");
            y = PowerBar.class.getDeclaredField("y");
            x.setAccessible(true);
            y.setAccessible(true);
        } catch (NoSuchFieldException e) {}
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Charger", 8, 6, 4210751);
        String power = TeslaUtils.getDisplayableTeslaCount(charger.getStoredPower());
        String max = TeslaUtils.getDisplayableTeslaCount(charger.getCapacity());
        fontRendererObj.drawString(power+" / "+max, 56, 70, 4210751);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        ResourceLocation l;
        mc.renderEngine.getTexture(l=new ResourceLocation(ModSquad.MODID, "textures/gui/container/fgen.png"));
        mc.renderEngine.bindTexture(l);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        drawTexturedModalRect((width - xSize)/2, (height-ySize)/2, 0, 0, xSize, ySize);
        try {
            x.setInt(p, ((width - xSize)/2) + 8);
            y.setInt(p, ((height-ySize)/2)+15);
        } catch (IllegalAccessException e) { }
        p.draw(charger);
    }

}

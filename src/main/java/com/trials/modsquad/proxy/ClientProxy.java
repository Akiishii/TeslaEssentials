package com.trials.modsquad.proxy;

import com.trials.modsquad.block.ModBlocks;
import com.trials.modsquad.item.ModItems;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.ServerWorldEventHandler;

@SuppressWarnings("unused")
public class ClientProxy implements CommonProxy {

    @Override
    public void preInit() {
        // Register tile that want data
    }

    @Override
    public void init() {
        ModBlocks.registerRenders();
        ModItems.registerRenders();
    }

    @Override
    public void postInit() {

    }
}

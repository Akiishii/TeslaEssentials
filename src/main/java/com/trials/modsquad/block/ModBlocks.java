package com.trials.modsquad.block;

import com.trials.modsquad.Ref;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    //Ores
    public static Block oreCopper;
    public static Block oreTin;
    public static Block oreSilver;
    public static Block oreOil;
    public static Block oreOsmium;
    public static Block oreTitanium;
    public static Block oreChromium;
    public static Block oreNickel;
    public static Block oreLead;

    Enum ores = new Ref.OreReference;

    public static void init() {
        oreCopper = new ModOre();
    }

    public static void register() {

    }

    public static void registerBlock(Block block) {
        GameRegistry.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

    public static void registerRenders() {

    }

    public static void registerRender(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
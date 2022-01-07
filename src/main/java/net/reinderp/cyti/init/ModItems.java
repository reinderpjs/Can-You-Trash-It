package net.reinderp.cyti.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import net.reinderp.cyti.util.Identifiers;

public class ModItems {

    public static final BlockItem TRASHCAN_ITEM = new BlockItem(ModBlocks.TRASHCAN_ITEM, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final BlockItem TRASHCAN_FLUID = new BlockItem(ModBlocks.TRASHCAN_FLUID, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final BlockItem TRASHCAN_ENERGY = new BlockItem(ModBlocks.TRASHCAN_ENERGY, new FabricItemSettings().group(ItemGroup.DECORATIONS));

    public static void Initialize() {
        Registry.register(Registry.ITEM, Identifiers.TRASHCAN_ITEM, TRASHCAN_ITEM);
        Registry.register(Registry.ITEM, Identifiers.TRASHCAN_FLUID, TRASHCAN_FLUID);
        Registry.register(Registry.ITEM, Identifiers.TRASHCAN_ENERGY, TRASHCAN_ENERGY);
    }
}

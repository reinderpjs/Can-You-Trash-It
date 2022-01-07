package net.reinderp.cyti.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import net.reinderp.cyti.common.blocks.EnergyTrashcanBlock;
import net.reinderp.cyti.common.blocks.FluidTrashcanBlock;
import net.reinderp.cyti.common.blocks.ItemTrashcanBlock;
import net.reinderp.cyti.util.Identifiers;

public class ModBlocks {

    public static final ItemTrashcanBlock TRASHCAN_ITEM = new ItemTrashcanBlock(FabricBlockSettings.of(Material.METAL).strength(1.5F, 4.8F).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final FluidTrashcanBlock TRASHCAN_FLUID = new FluidTrashcanBlock(FabricBlockSettings.of(Material.METAL).strength(1.5F, 4.8F).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final EnergyTrashcanBlock TRASHCAN_ENERGY = new EnergyTrashcanBlock(FabricBlockSettings.of(Material.METAL).strength(1.5F, 4.8F).sounds(BlockSoundGroup.METAL).nonOpaque());

    public static void Initialize() {
        Registry.register(Registry.BLOCK, Identifiers.TRASHCAN_ITEM, TRASHCAN_ITEM);
        Registry.register(Registry.BLOCK, Identifiers.TRASHCAN_FLUID, TRASHCAN_FLUID);
        Registry.register(Registry.BLOCK, Identifiers.TRASHCAN_ENERGY, TRASHCAN_ENERGY);
    }
}
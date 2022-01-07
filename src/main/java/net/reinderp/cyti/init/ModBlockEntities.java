package net.reinderp.cyti.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import net.reinderp.cyti.common.blockentities.EnergyTrashcanBlockEntity;
import net.reinderp.cyti.common.blockentities.FluidTrashcanBlockEntity;
import net.reinderp.cyti.common.blockentities.ItemTrashcanBlockEntity;
import net.reinderp.cyti.util.Identifiers;

public class ModBlockEntities {

    public static final BlockEntityType<? extends ItemTrashcanBlockEntity> ITEM_TRASHCAN_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(ItemTrashcanBlockEntity::new, ModBlocks.TRASHCAN_ITEM).build(null);
    public static final BlockEntityType<? extends FluidTrashcanBlockEntity> FLUID_TRASHCAN_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(FluidTrashcanBlockEntity::new, ModBlocks.TRASHCAN_FLUID).build(null);
    public static final BlockEntityType<? extends EnergyTrashcanBlockEntity> ENERGY_TRASHCAN_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(EnergyTrashcanBlockEntity::new, ModBlocks.TRASHCAN_ENERGY).build(null);

    public static void Initialize() {
        Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                Identifiers.TRASHCAN_ITEM,
                ITEM_TRASHCAN_BLOCK_ENTITY);

        Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                Identifiers.TRASHCAN_FLUID,
                FLUID_TRASHCAN_BLOCK_ENTITY);

        Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                Identifiers.TRASHCAN_ENERGY,
                ENERGY_TRASHCAN_BLOCK_ENTITY);
    }
}

package net.reinderp.cyti.render.guis.trashcan_energy.slots;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleBatteryItem;
import team.reborn.energy.impl.SimpleItemEnergyStorageImpl;

public class EnergyTrashSlot extends Slot {
    public EnergyTrashSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return EnergyStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlot(0))) != null;
    }
}

package net.reinderp.cyti.render.guis.trashcan_fluid.slots;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.reinderp.cyti.CYTIMod;
import net.reinderp.cyti.compat.TechRebornCompat;
import org.apache.logging.log4j.Level;

public class FluidTrashSlot extends Slot {
    public FluidTrashSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.getItem() instanceof BucketItem) {
            return true;
        }
        else if (FabricLoader.getInstance().isModLoaded("techreborn") && TechRebornCompat.instanceOfCell(stack.getItem())) {
            return true;
        }
        else if (FluidStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlot(0))) != null) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack insertStack(ItemStack stack) {
        CYTIMod.LOGGER.log(Level.INFO, "NO");
        return super.insertStack(stack);
    }
}

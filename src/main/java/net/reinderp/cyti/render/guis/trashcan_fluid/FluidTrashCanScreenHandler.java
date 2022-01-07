package net.reinderp.cyti.render.guis.trashcan_fluid;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.reinderp.cyti.config.TrashConfig;
import net.reinderp.cyti.init.ModScreenHandlers;
import net.reinderp.cyti.render.guis.trashcan_fluid.slots.FluidTrashSlot;

public class FluidTrashCanScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public FluidTrashCanScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public FluidTrashCanScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.FLUID_TRASHCAN_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int m;
        int l;

        this.addSlot(new FluidTrashSlot(inventory, 0, 80, 36));

        for (m = 0; m < 3; m++) {
            for (l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; m++) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (TrashConfig.getConfig().trashSettings.shiftClearingBucket && originalStack.getItem() instanceof BucketItem && originalStack.getItem() != Items.BUCKET) {
                this.slots.get(index).setStack(new ItemStack(Items.BUCKET, originalStack.getCount()));
                return ItemStack.EMPTY;
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                this.slots.get(index).setStack(BucketItem.getEmptiedStack(originalStack, player));
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }


}

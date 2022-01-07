package net.reinderp.cyti.compat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techreborn.items.DynamicCellItem;

public class TechRebornCompat {

    public static boolean instanceOfCell(Item item) {
        return item instanceof DynamicCellItem;
    }

    public static ItemStack getEmptyStack(ItemStack stack) {
        return DynamicCellItem.getEmptyCell(stack.getCount());
    }
}

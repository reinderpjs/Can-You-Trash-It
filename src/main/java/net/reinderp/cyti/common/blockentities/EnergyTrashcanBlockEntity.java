package net.reinderp.cyti.common.blockentities;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.reinderp.cyti.CYTIMod;
import net.reinderp.cyti.config.TrashConfig;
import net.reinderp.cyti.init.ModBlockEntities;
import net.reinderp.cyti.render.guis.trashcan_energy.EnergyTrashcanScreenHandler;
import net.reinderp.cyti.util.ImplementedInventory;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleBatteryItem;
import team.reborn.energy.api.base.SimpleSidedEnergyContainer;


public class EnergyTrashcanBlockEntity extends BlockEntity implements BlockEntityTicker<EnergyTrashcanBlockEntity>, NamedScreenHandlerFactory, ImplementedInventory {

    protected DefaultedList<ItemStack> inventory;

    private final SimpleSidedEnergyContainer energyContainer = new SimpleSidedEnergyContainer() {
        @Override
        public long getCapacity() {
            return 10000;
        }

        @Override
        public long getMaxInsert(@Nullable Direction side) {
            return TrashConfig.getConfig().trashSettings.energyTrashcanInput;
        }

        @Override
        public long getMaxExtract(@Nullable Direction side) {
            return 0;
        }
    };

    public EnergyTrashcanBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENERGY_TRASHCAN_BLOCK_ENTITY, pos, state);

        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, EnergyTrashcanBlockEntity blockEntity) {
        if (!world.isClient && energyContainer.amount > 0) {
            energyContainer.amount = 0;
        }

        ItemStack itemStack = this.inventory.get(0);
        if (!itemStack.isEmpty()) {
            if (EnergyStorage.ITEM.find(itemStack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0))) != null) {
                 try (Transaction outerTransaction = Transaction.openOuter()) {
                     EnergyStorage energyStorage = EnergyStorage.ITEM.find(itemStack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0)));

                     energyStorage.extract(TrashConfig.getConfig().trashSettings.energyTrashcanInput, outerTransaction);
                     outerTransaction.commit();
                 }
            }
        }
    }

    public EnergyStorage getEnergyContainer(@Nullable Direction side) {
        return energyContainer.getSideStorage(side);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EnergyTrashcanScreenHandler(syncId, inv, this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }
}

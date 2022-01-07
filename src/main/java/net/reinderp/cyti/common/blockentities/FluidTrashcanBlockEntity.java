package net.reinderp.cyti.common.blockentities;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.reinderp.cyti.compat.TechRebornCompat;
import net.reinderp.cyti.config.TrashConfig;
import net.reinderp.cyti.init.ModBlockEntities;
import net.reinderp.cyti.render.guis.trashcan_fluid.FluidTrashCanScreenHandler;
import net.reinderp.cyti.util.fluid.FluidContainerProvider;
import net.reinderp.cyti.util.ImplementedInventory;
import net.reinderp.cyti.util.fluid.FluidContainer;
import net.reinderp.cyti.util.fluid.FluidValue;
import net.reinderp.cyti.util.fluid.VoidFluidContainer;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class FluidTrashcanBlockEntity extends BlockEntity implements BlockEntityTicker<FluidTrashcanBlockEntity>, FluidContainerProvider, NamedScreenHandlerFactory, ImplementedInventory {

    protected DefaultedList<ItemStack> inventory;

    private VoidFluidContainer fluidContainer = new VoidFluidContainer("FluidTrashcanFluidContainer", FluidValue.BUCKET);

    public FluidTrashcanBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_TRASHCAN_BLOCK_ENTITY, pos, state);

        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    @Override
    public FluidContainer getFluidContainer(BlockState var1, WorldAccess var2, BlockPos var3) {
        return this.fluidContainer;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FluidTrashCanScreenHandler(syncId, inv, this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, FluidTrashcanBlockEntity blockEntity) {
        ItemStack itemStack = this.inventory.get(0);
        if (!itemStack.isEmpty()) {
            if (itemStack.getItem() instanceof BucketItem) {
                this.inventory.set(0, new ItemStack(Items.BUCKET, itemStack.getCount()));
            }
            else if (TechRebornCompat.instanceOfCell(itemStack.getItem())) {
                this.inventory.set(0, TechRebornCompat.getEmptyStack(itemStack));
            }
            else if (FluidStorage.ITEM.find(itemStack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0))) != null) {
                try (Transaction outerTransaction = Transaction.openOuter()) {
                    Storage<FluidVariant> fluidStorage = FluidStorage.ITEM.find(itemStack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0)));
                    Iterator<StorageView<FluidVariant>> storageViewIterator = fluidStorage.iterator(outerTransaction);

                    if (storageViewIterator.hasNext()) {
                        StorageView<FluidVariant> fluidStorageSpecs = storageViewIterator.next();

                        if (!fluidStorageSpecs.isResourceBlank()) {
                            fluidStorage.extract(fluidStorageSpecs.getResource(), Math.min(TrashConfig.getConfig().trashSettings.fluidTrashcanInput, fluidStorageSpecs.getAmount()), outerTransaction);
                        }
                    }
                    outerTransaction.commit();
                }
            }
        }
    }
}

package net.reinderp.cyti.util.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public class FluidContainer extends SnapshotParticipant<FluidInstance> implements SingleSlotStorage<FluidVariant> {

    private final String name;
    @NotNull
    private FluidInstance fluidInstance = new FluidInstance();
    private final FluidValue capacity;

    public FluidContainer(String name, FluidValue capacity) {
        super();
        this.name = name;
        this.capacity = capacity;
    }

    public FluidInstance getFluidInstance() {
        return fluidInstance;
    }

    public Fluid getFluid() {
        return getFluidInstance().getFluid();
    }

    public FluidValue getFluidValueCapacity() {
        return capacity;
    }

    public FluidValue getFluidAmount() {
        return getFluidInstance().getAmount();
    }

    public FluidValue getFreeSpace() {
        return getFluidValueCapacity().subtract(getFluidAmount());
    }

    public boolean canFit(Fluid fluid, FluidValue amount) {
        return (isEmpty() || getFluid() == fluid && getFreeSpace().equalOrMoreThan(amount));
    }

    public boolean isFull() {
        return !getFluidInstance().isEmpty() && getFluidInstance().getAmount().equalOrMoreThan(getFluidValueCapacity());
    }

    public boolean isEmpty() {
        return getFluidInstance().isEmpty();
    }

    public final NbtCompound write(NbtCompound nbt) {
        NbtCompound fluidContainerData = fluidInstance.write();
        nbt.put(name, fluidContainerData);
        return nbt;
    }

    public final FluidContainer read(NbtCompound nbt) {
        if (nbt.contains(name)) {
            setFluid(Fluids.EMPTY);

            NbtCompound fluidContainerData = nbt.getCompound(name);
            fluidInstance = new FluidInstance(fluidContainerData);
        }
        return this;
    }

    public void setFluid(@NotNull Fluid f) {
        Validate.notNull(f);
        fluidInstance.setFluid(f);
    }

    public void setFluid(@NotNull FluidInstance instance) {
        fluidInstance = instance;
    }

    public void setFluidAmount(FluidValue amount) {
        if (!fluidInstance.isEmptyFluid()) {
            fluidInstance.setAmount(amount);
        }
    }

    public void setFluidInstance(@NotNull FluidInstance fluidInstance) {
        this.fluidInstance = fluidInstance;
    }

    public void onInsert() {

    }

    @Override
    public long insert(FluidVariant insertedVariant, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(insertedVariant, maxAmount);
        FluidVariant currentVariant = getResource();

        if (currentVariant.equals(insertedVariant) || currentVariant.isBlank()) {
            long insertedAmount = Math.min(maxAmount, getCapacity() - getAmount());

            if (insertedAmount > 0) {
                updateSnapshots(transaction);

                //in case
                if (currentVariant.isBlank()) fluidInstance.setAmount(FluidValue.EMPTY);

                fluidInstance.setFluid(insertedVariant.getFluid());
                fluidInstance.setAmount(FluidValue.fromRaw(insertedAmount).add(getFluidAmount()));
                fluidInstance.setTag(insertedVariant.getNbt());
            }

            return insertedAmount;
        }

        return 0;
    }

    @Override
    public long extract(FluidVariant extractedVariant, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(extractedVariant, maxAmount);
        FluidVariant currentVariant = getResource();

        if (extractedVariant.equals(currentVariant)) {
            long extractedAmount = Math.min(maxAmount, getAmount());

            if (extractedAmount > 0) {
                updateSnapshots(transaction);

                fluidInstance.subtractAmount(FluidValue.fromRaw(extractedAmount));
            }

            return extractedAmount;
        }

        return 0;
    }

    @Override
    public boolean isResourceBlank() {
        return getResource().isBlank();
    }

    @Override
    public FluidVariant getResource() {
        return fluidInstance.getVariant();
    }

    @Override
    public long getAmount() {
        return fluidInstance.getAmount().getRawValue();
    }

    @Override
    public long getCapacity() {
        return getFluidValueCapacity().getRawValue();
    }

    @Override
    protected FluidInstance createSnapshot() {
        return fluidInstance.copy();
    }

    @Override
    protected void readSnapshot(FluidInstance snapshot) {
        setFluidInstance(snapshot);
    }
}

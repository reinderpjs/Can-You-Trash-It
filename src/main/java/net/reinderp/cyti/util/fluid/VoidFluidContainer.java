package net.reinderp.cyti.util.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import java.util.Collections;
import java.util.Iterator;

public class VoidFluidContainer extends FluidContainer {

    public VoidFluidContainer(String name, FluidValue capacity) {
        super(name, capacity);
    }

    @Override
    public long insert(FluidVariant insertedVariant, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(insertedVariant, maxAmount);

        return maxAmount;
    }

    @Override
    public Iterator<StorageView<FluidVariant>> iterator() {
        return Collections.emptyIterator();
    }
}

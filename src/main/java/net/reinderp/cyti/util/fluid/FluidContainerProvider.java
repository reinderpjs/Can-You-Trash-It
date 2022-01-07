package net.reinderp.cyti.util.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public interface FluidContainerProvider {
    public FluidContainer getFluidContainer(BlockState var1, WorldAccess var2, BlockPos var3);
}

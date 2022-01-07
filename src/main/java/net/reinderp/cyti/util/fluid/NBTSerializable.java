package net.reinderp.cyti.util.fluid;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public interface NBTSerializable {

    @NotNull
    NbtCompound write();

    void read(@NotNull NbtCompound tag);
}

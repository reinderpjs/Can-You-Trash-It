package net.reinderp.cyti.util.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public class FluidInstance implements NBTSerializable {
    public static final String FLUID_KEY = "Fluid";
    public static final String AMOUNT_KEY = "Amount";
    public static final String TAG_KEY = "Tag";

    public static final FluidInstance EMPTY = new FluidInstance(Fluids.EMPTY, FluidValue.EMPTY);

    protected Fluid fluid;
    protected FluidValue amount;
    protected NbtCompound tag;

    public FluidInstance(Fluid fluid, FluidValue amount) {
        this.fluid = fluid;
        this.amount = amount;
    }

    public FluidInstance(Fluid fluid) {
        this(fluid, FluidValue.EMPTY);
    }

    public FluidInstance() {
        this(Fluids.EMPTY);
    }

    public FluidInstance(NbtCompound tag) {
        this();
        read(tag);
    }

    public Fluid getFluid() {
        return fluid;
    }

    public FluidValue getAmount() {
        return amount;
    }

    public NbtCompound getTag() {
        return tag;
    }

    public FluidVariant getVariant() {
        if (isEmpty()) return FluidVariant.blank();
        else return FluidVariant.of(fluid, tag);
    }

    public FluidInstance setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public FluidInstance setAmount(FluidValue amount) {
        this.amount = amount;
        return this;
    }

    public FluidInstance subtractAmount(FluidValue amount) {
        this.amount = this.amount.subtract(amount);
        return this;
    }

    public FluidInstance addAmount(FluidValue amount) {
        this.amount = this.amount.add(amount);
        return this;
    }

    public void setTag(NbtCompound tag) {
        this.tag = tag;
    }

    public boolean isEmpty() {
        return isEmptyFluid() || this.getAmount().isEmpty();
    }

    public boolean isEmptyFluid() {
        return this.getFluid() == Fluids.EMPTY;
    }

    public FluidInstance copy() {
        return new FluidInstance().setFluid(fluid).setAmount(amount);
    }

    @Override
    public @NotNull NbtCompound write() {
        NbtCompound tag = new NbtCompound();
        tag.putString(FLUID_KEY, Registry.FLUID.getId(fluid).toString());
        tag.putLong(AMOUNT_KEY, amount.getRawValue());
        if (this.tag != null && !this.tag.isEmpty()) {
            tag.put(TAG_KEY, this.tag);
        }
        return tag;
    }

    @Override
    public void read(@NotNull NbtCompound tag) {
        fluid = Registry.FLUID.get(new Identifier(tag.getString(FLUID_KEY)));
        amount = FluidValue.fromRaw(tag.getLong(AMOUNT_KEY));
        if (tag.contains(TAG_KEY)) {
            this.tag = tag.getCompound(TAG_KEY);
        }
    }
}

package net.reinderp.cyti.util.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

public final class FluidValue {

    public static final FluidValue EMPTY = new FluidValue(0);
    public static final FluidValue BUCKET_QUARTER = new FluidValue(FluidConstants.BUCKET / 4);
    public static final FluidValue BUCKET = new FluidValue(FluidConstants.BUCKET);

    private final long rawValue;

    private static FluidValue fromMilliBuckets(long milliBuckets) {
        return new FluidValue(milliBuckets * 81);
    }

    private FluidValue(long rawValue) {
        this.rawValue = rawValue;
    }

    public FluidValue multiply(long value) {
        return fromRaw(rawValue * value);
    }

    public FluidValue fraction(long divider) {
        return fromRaw(rawValue / divider);
    }

    public FluidValue add(FluidValue fluidValue) {
        return fromRaw(rawValue + fluidValue.rawValue);
    }

    public FluidValue subtract(FluidValue fluidValue) {
        return fromRaw(rawValue - fluidValue.rawValue);
    }


    public FluidValue min(FluidValue fluidValue) {
        return fromRaw(Math.min(rawValue, fluidValue.rawValue));
    }

    public boolean isEmpty() {
        return rawValue == 0;
    }

    public boolean moreThan(FluidValue value) {
        return rawValue > value.rawValue;
    }

    public boolean equalOrMoreThan(FluidValue value) {
        return rawValue >= value.rawValue;
    }

    public boolean lessThan(FluidValue value) {
        return rawValue < value.rawValue;
    }

    public boolean lessThanOrEqual(FluidValue value) {
        return rawValue <= value.rawValue;
    }

    //TODO move away from using this
    @Deprecated
    public long getRawValue() {
        return rawValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FluidValue that = (FluidValue) o;
        return rawValue == that.rawValue;
    }

    @Deprecated
    public static FluidValue fromRaw(long rawValue) {
        if (rawValue < 0) {
            rawValue = 0;
        }
        return new FluidValue(rawValue);
    }
}

package net.reinderp.cyti.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.reinderp.cyti.common.blockentities.EnergyTrashcanBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class EnergyTrashcanBlock extends BlockWithEntity {
    public EnergyTrashcanBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(5, 15, 5, 11, 16, 11),
                Block.createCuboidShape(1, 0, 1, 15, 13, 15),
                Block.createCuboidShape(0, 13, 0, 16, 15, 16)
        ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return Stream.of(
                Block.createCuboidShape(5, 15, 5, 11, 16, 11),
                Block.createCuboidShape(1, 0, 1, 15, 13, 15),
                Block.createCuboidShape(0, 13, 0, 16, 15, 16)
        ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyTrashcanBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if(blockEntity instanceof BlockEntityTicker) {
                ((BlockEntityTicker)blockEntity).tick(world1, pos, state1, blockEntity);
            }
        };
    }
}

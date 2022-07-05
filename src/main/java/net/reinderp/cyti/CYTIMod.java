package net.reinderp.cyti;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.reinderp.cyti.common.blockentities.EnergyTrashcanBlockEntity;
import net.reinderp.cyti.common.blockentities.FluidTrashcanBlockEntity;
import net.reinderp.cyti.init.ModBlockEntities;
import net.reinderp.cyti.init.ModBlocks;
import net.reinderp.cyti.init.ModItems;
import net.reinderp.cyti.init.ModScreenHandlers;
import net.reinderp.cyti.util.Identifiers;
import net.reinderp.cyti.config.TrashConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.reborn.energy.api.EnergyStorage;

public class CYTIMod implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger(Identifiers.MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.Initialize();
		ModItems.Initialize();
		ModBlockEntities.Initialize();
		ModScreenHandlers.Initialize();
		AutoConfig.register(TrashConfig.class, Toml4jConfigSerializer::new);

		FluidStorage.SIDED.registerFallback((world, pos, state, blockEntity, direction) -> {
			if (blockEntity instanceof FluidTrashcanBlockEntity trashcan) {
				return trashcan.getFluidContainer(state, world, pos);
			}

			return null;
		});

		EnergyStorage.SIDED.registerFallback((world, pos, state, blockEntity, direction) -> {
			if (blockEntity instanceof EnergyTrashcanBlockEntity trashcan) {
				return trashcan.getEnergyContainer(direction);
			}

			return null;
		});

		

		LOGGER.info("The trashcans has been loaded!");
	}
}

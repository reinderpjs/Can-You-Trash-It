package net.reinderp.cyti.init;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;
import net.reinderp.cyti.render.guis.trashcan_energy.EnergyTrashcanScreen;
import net.reinderp.cyti.render.guis.trashcan_energy.EnergyTrashcanScreenHandler;
import net.reinderp.cyti.render.guis.trashcan_fluid.FluidTrashCanScreenHandler;
import net.reinderp.cyti.render.guis.trashcan_fluid.FluidTrashcanScreen;
import net.reinderp.cyti.render.guis.trashcan_item.ItemTrashcanScreen;
import net.reinderp.cyti.render.guis.trashcan_item.ItemTrashcanScreenHandler;
import net.reinderp.cyti.util.Identifiers;

public class ModScreenHandlers {

    public static final ScreenHandlerType<ItemTrashcanScreenHandler> ITEM_TRASHCAN_SCREEN_HANDLER;
    public static final ScreenHandlerType<FluidTrashCanScreenHandler> FLUID_TRASHCAN_SCREEN_HANDLER;
    public static final ScreenHandlerType<EnergyTrashcanScreenHandler> ENERGY_TRASHCAN_SCREEN_HANDLER;

    static {
        ITEM_TRASHCAN_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, Identifiers.TRASHCAN_ITEM,
                new ScreenHandlerType<>(ItemTrashcanScreenHandler::new));
        FLUID_TRASHCAN_SCREEN_HANDLER =  Registry.register(Registry.SCREEN_HANDLER, Identifiers.TRASHCAN_FLUID,
                new ScreenHandlerType<>(FluidTrashCanScreenHandler::new));
        ENERGY_TRASHCAN_SCREEN_HANDLER =  Registry.register(Registry.SCREEN_HANDLER, Identifiers.TRASHCAN_ENERGY,
                new ScreenHandlerType<>(EnergyTrashcanScreenHandler::new));
    }

    public static void ClientInitialize() {
        HandledScreens.register(ITEM_TRASHCAN_SCREEN_HANDLER, ItemTrashcanScreen::new);
        HandledScreens.register(FLUID_TRASHCAN_SCREEN_HANDLER, FluidTrashcanScreen::new);
        HandledScreens.register(ENERGY_TRASHCAN_SCREEN_HANDLER, EnergyTrashcanScreen::new);
    }
}

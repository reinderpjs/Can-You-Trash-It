package net.reinderp.cyti.init;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
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
        ITEM_TRASHCAN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Identifiers.TRASHCAN_ITEM,
                ItemTrashcanScreenHandler::new);
        FLUID_TRASHCAN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Identifiers.TRASHCAN_FLUID,
                FluidTrashCanScreenHandler::new);
        ENERGY_TRASHCAN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Identifiers.TRASHCAN_ENERGY,
                EnergyTrashcanScreenHandler::new);
    }

    public static void ClientInitialize() {
        ScreenRegistry.register(ITEM_TRASHCAN_SCREEN_HANDLER, ItemTrashcanScreen::new);
        ScreenRegistry.register(FLUID_TRASHCAN_SCREEN_HANDLER, FluidTrashcanScreen::new);
        ScreenRegistry.register(ENERGY_TRASHCAN_SCREEN_HANDLER, EnergyTrashcanScreen::new);
    }
}

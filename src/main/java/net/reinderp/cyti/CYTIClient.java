package net.reinderp.cyti;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.reinderp.cyti.init.ModScreenHandlers;

@Environment(EnvType.CLIENT)
public class CYTIClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModScreenHandlers.ClientInitialize();

    }
}

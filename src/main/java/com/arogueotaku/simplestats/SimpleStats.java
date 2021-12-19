package com.arogueotaku.simplestats;

import com.arogueotaku.simplestats.handlers.NetworkSyncHandler;
import com.arogueotaku.simplestats.handlers.RenderStatsHandler;
import com.arogueotaku.simplestats.screens.SimpleStatsConfigScreen;
import com.arogueotaku.simplestats.utils.ConfigUtil;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(SimpleStats.MOD_ID)
public class SimpleStats
{
    public static final String MOD_ID = "simplestats";
    public SimpleStats() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigUtil.SPEC, "simplestats-client.toml");
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, screen)-> new SimpleStatsConfigScreen()));
        NetworkSyncHandler.init();
        MinecraftForge.EVENT_BUS.addListener(NetworkSyncHandler::handleLogin);
        MinecraftForge.EVENT_BUS.addListener(NetworkSyncHandler::handleLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(RenderStatsHandler::handle);
    }

}

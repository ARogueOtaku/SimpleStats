package com.arogueotaku.simplestats.handlers;

import com.arogueotaku.simplestats.utils.ConfigUtil;
import com.arogueotaku.simplestats.utils.MathUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.HashMap;

public class RenderStatsHandler {
    private static final HashMap<String, String> statsMap = new HashMap<>();

    private static void updateStatsMap(Minecraft minecraft) {
        Vec3 playerPos = minecraft.player.position();
        FoodData playerFoodData = minecraft.player.getFoodData();
        String fps = "FPS: " + minecraft.fpsString.substring(0, minecraft.fpsString.indexOf("fps")-1);
        String xPos = "X: " + MathUtil.roundString(playerPos.x, 2);
        String yPos = "Y: " + MathUtil.roundString(playerPos.y, 2);
        String zPos = "Z: " + MathUtil.roundString(playerPos.z, 2);
        String food = "Food: " + MathUtil.roundString(playerFoodData.getFoodLevel(), 1);
        String exhaustion = "Exhaustion: " + MathUtil.roundString(playerFoodData.getExhaustionLevel(), 1);
        String saturation = "Saturation: " + MathUtil.roundString(playerFoodData.getSaturationLevel(), 1);
        String latency = "Latency: " + minecraft.player.connection.getPlayerInfo(minecraft.player.getUUID()).getLatency() + " ms";
        String biome = Util.makeDescriptionId("Biome: ", minecraft.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(minecraft.level.getBiome(new BlockPos(playerPos)))).replace(".minecraft.","");
        statsMap.put("fps", fps);
        statsMap.put("x", xPos);
        statsMap.put("y", yPos);
        statsMap.put("z", zPos);
        statsMap.put("food", food);
        statsMap.put("exhaustion", exhaustion);
        statsMap.put("saturation", saturation);
        statsMap.put("latency", latency);
        statsMap.put("biome", biome);
    }

    public static void handle(RenderGameOverlayEvent.Text event) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean positive = false, negative = false;
        int offset = 0;
        for(MobEffectInstance effectInstance: minecraft.player.getActiveEffects()) {
            MobEffect effect = effectInstance.getEffect();
            if(effect.isBeneficial()) positive = true;
            else negative = true;
            if(negative) break;
        }
        if(!minecraft.options.renderDebug && event.getType() == RenderGameOverlayEvent.ElementType.TEXT && ConfigUtil.showSimpleStats.get()) {
            updateStatsMap(minecraft);
            if(ConfigUtil.showCoordinates.get()) {
                event.getLeft().add(statsMap.get("x"));
                event.getLeft().add(statsMap.get("y"));
                event.getLeft().add(statsMap.get("z"));
            }
            if(ConfigUtil.showCoordinates.get() && ConfigUtil.showFPS.get()) {
                event.getLeft().add("");
            }
            if(ConfigUtil.showFPS.get()) {
                event.getLeft().add(statsMap.get("fps"));
            }
            if(negative) offset = 6;
            else if(positive) offset = 3;
            for(int i = 0; i < offset; i++) event.getRight().add("");
            if(ConfigUtil.showFood.get()) {
                event.getRight().add(statsMap.get("food"));
                if(NetworkSyncHandler.networkSync) {
                    event.getRight().add(statsMap.get("saturation"));
                    event.getRight().add(statsMap.get("exhaustion"));
                }
            }
            if(ConfigUtil.showFood.get() && ConfigUtil.showPing.get()) {
                event.getRight().add("");
            }
            if(ConfigUtil.showPing.get()) {
                event.getRight().add(statsMap.get("latency"));
            }
            if(ConfigUtil.showWorld.get() && (ConfigUtil.showFood.get() || ConfigUtil.showPing.get())) {
                event.getRight().add("");
            }
            if(ConfigUtil.showWorld.get()) {
                event.getRight().add(statsMap.get("biome"));
            }
        }
    }
}

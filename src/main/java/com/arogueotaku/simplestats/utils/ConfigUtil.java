package com.arogueotaku.simplestats.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigUtil {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.BooleanValue showSimpleStats;
    public static ForgeConfigSpec.BooleanValue showCoordinates;
    public static ForgeConfigSpec.BooleanValue showFPS;
    public static ForgeConfigSpec.BooleanValue showFood;
    public static ForgeConfigSpec.BooleanValue showPing;
    public static ForgeConfigSpec.BooleanValue showWorld;
    static  {
        BUILDER.push("Test Config File");
        showSimpleStats = BUILDER.comment("Whether SimpleStats should be shown. Default is true").define("ShowSimpleStats", true);
        showCoordinates = BUILDER.comment("Whether the XYZ Coordinates of the player should be shown. Default is true").define("Show Coordinates", true);
        showFPS = BUILDER.comment("Whether the Client FPS should be shown. Default is true").define("Show FPS", true);
        showFood = BUILDER.comment("Whether the Player's Food Stats should be shown. Default is true").define("Show Food", true);
        showPing = BUILDER.comment("Whether the Network Latency should be shown. Default is true").define("Show Ping", true);
        showWorld = BUILDER.comment("Whether the World Data should be shown. Default is true").define("Show World", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

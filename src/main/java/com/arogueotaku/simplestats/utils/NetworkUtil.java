package com.arogueotaku.simplestats.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class NetworkUtil {
    public static Player getClientPlayer(NetworkEvent.Context ctx) {
        return ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER ? ctx.getSender() : Minecraft.getInstance().player;
    }
}

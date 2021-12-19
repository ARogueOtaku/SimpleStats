package com.arogueotaku.simplestats.utils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SaturationMessage {

    private float saturation;
    public SaturationMessage(float saturation) {
        this.saturation = saturation;
    }

    public static void encodeSaturation(SaturationMessage packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.saturation);
    }

    public static SaturationMessage decodeSaturation(FriendlyByteBuf buffer) {
        return new SaturationMessage(buffer.readFloat());
    }

    public static void handleServerSaturation(SaturationMessage saturationMessage, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            NetworkUtil.getClientPlayer(ctx.get()).getFoodData().setSaturation(saturationMessage.saturation);
        });
        ctx.get().setPacketHandled(true);
    }
}

package com.arogueotaku.simplestats.utils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExhaustionMessage {

    private float exhaustion;
    public ExhaustionMessage(float exhaustion) {
        this.exhaustion = exhaustion;
    }

    public static void encodeExhaustion(ExhaustionMessage packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.exhaustion);
    }

    public static ExhaustionMessage decodeExhaustion(FriendlyByteBuf buffer) {
        return new ExhaustionMessage(buffer.readFloat());
    }

    public static void handleServerExhaustion(ExhaustionMessage exhaustionMessage, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            NetworkUtil.getClientPlayer(ctx.get()).getFoodData().setExhaustion(exhaustionMessage.exhaustion);
        });
        ctx.get().setPacketHandled(true);
    }
}

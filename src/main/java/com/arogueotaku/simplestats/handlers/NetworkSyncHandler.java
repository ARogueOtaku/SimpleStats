package com.arogueotaku.simplestats.handlers;

import com.arogueotaku.simplestats.SimpleStats;
import com.arogueotaku.simplestats.utils.ExhaustionMessage;
import com.arogueotaku.simplestats.utils.SaturationMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.UUID;

public class NetworkSyncHandler {

    private static final SimpleChannel SIMPLE_CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SimpleStats.MOD_ID,"sync")).clientAcceptedVersions(s->true).serverAcceptedVersions(s->true).networkProtocolVersion(()->"1").simpleChannel();
    private static final HashMap<UUID, Float> prevExhaustions = new HashMap<>();
    private static final HashMap<UUID, Float> prevSaturations = new HashMap<>();
    public static boolean networkSync = false;

    public static void init() {
        SIMPLE_CHANNEL.registerMessage(1, ExhaustionMessage.class, ExhaustionMessage::encodeExhaustion, ExhaustionMessage::decodeExhaustion, ExhaustionMessage::handleServerExhaustion);
        SIMPLE_CHANNEL.registerMessage(2, SaturationMessage.class, SaturationMessage::encodeSaturation, SaturationMessage::decodeSaturation, SaturationMessage::handleServerSaturation);
    }

    public static void handleLivingUpdate(LivingEvent.LivingUpdateEvent event) {

        if(!(event.getEntity() instanceof ServerPlayer)) return;
        NetworkSyncHandler.networkSync = true;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        Float prevExhaustion = prevExhaustions.get(player.getUUID());
        Float prevSaturation = prevSaturations.get(player.getUUID());
        float currentExhaustion = player.getFoodData().getExhaustionLevel();
        float currentSaturation = player.getFoodData().getSaturationLevel();
        if(prevExhaustion == null || prevExhaustion != currentExhaustion) {
            ExhaustionMessage exhaustionMessage = new ExhaustionMessage(currentExhaustion);
            SIMPLE_CHANNEL.sendTo(exhaustionMessage, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
        if(prevSaturation == null || prevSaturation != currentSaturation) {
            SaturationMessage saturationMessage = new SaturationMessage(currentSaturation);
            SIMPLE_CHANNEL.sendTo(saturationMessage, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void handleLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if(!(event.getPlayer() instanceof ServerPlayer)) return;
        prevExhaustions.remove(event.getPlayer().getUUID());
        prevSaturations.remove(event.getPlayer().getUUID());
    }

}

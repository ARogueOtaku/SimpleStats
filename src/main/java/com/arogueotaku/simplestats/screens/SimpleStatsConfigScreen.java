package com.arogueotaku.simplestats.screens;

import com.arogueotaku.simplestats.monsters.MonsterToggleOption;
import com.arogueotaku.simplestats.utils.ConfigUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public final class SimpleStatsConfigScreen extends Screen {

    private OptionsList optionsList;
    private Button doneButton;
    private MonsterToggleOption showAll;
    private MonsterToggleOption showCoords;
    private MonsterToggleOption showFps;
    private MonsterToggleOption showFood;
    private MonsterToggleOption showPing;
    private MonsterToggleOption showWorld;

    public SimpleStatsConfigScreen() {
        super(new TranslatableComponent("Simple Stats"));
    }
    @Override
    protected void init() {
        this.optionsList = new OptionsList(this.minecraft, this.width, this.height, 24, this.height - 32, 25);
        this.doneButton = new Button((this.width-200)/2,this.height-26, 200, 20, new TranslatableComponent("Done"), button->this.onClose());
        this.showAll = new MonsterToggleOption("Show All Stats", () -> ConfigUtil.showSimpleStats.get(), (newVal)->{
            ConfigUtil.showSimpleStats.set(newVal);
            updateConfigAvailability(newVal);
        });
        this.showCoords = new MonsterToggleOption("Show Player Coordinates", () -> ConfigUtil.showCoordinates.get(), (newVal)->ConfigUtil.showCoordinates.set(newVal));
        this.showFps = new MonsterToggleOption("Show Client FPS", () -> ConfigUtil.showFPS.get(), (newVal)->ConfigUtil.showFPS.set(newVal));
        this.showFood = new MonsterToggleOption("Show Food Stats", () -> ConfigUtil.showFood.get(), (newVal)->ConfigUtil.showFood.set(newVal));
        this.showPing = new MonsterToggleOption("Show Network Latency", () -> ConfigUtil.showPing.get(), (newVal)->ConfigUtil.showPing.set(newVal));
        this.showWorld = new MonsterToggleOption("Show World Data", () -> ConfigUtil.showWorld.get(), (newVal)->ConfigUtil.showWorld.set(newVal));
        this.optionsList.addBig(showAll);
        this.optionsList.addBig(showCoords);
        this.optionsList.addBig(showFps);
        this.optionsList.addBig(showFood);
        this.optionsList.addBig(showPing);
        this.optionsList.addBig(showWorld);
        updateConfigAvailability(ConfigUtil.showSimpleStats.get());
        this.addWidget(this.optionsList);
        this.addRenderableWidget(this.doneButton);
    }
    @Override
    public void render(PoseStack poseStack, int x, int y, float ticks) {
        this.renderBackground(poseStack);
        this.optionsList.render(poseStack, x, y, ticks);
        Screen.drawCenteredString(poseStack, this.font, this.title, this.width/2, 8, 16777215);
        super.render(poseStack, x, y, ticks);
    }

    public void updateConfigAvailability(boolean available) {
        showCoords.getAttachedButton().active = available;
        showFps.getAttachedButton().active = available;
        showFood.getAttachedButton().active = available;
        showPing.getAttachedButton().active = available;
        showWorld.getAttachedButton().active = available;
    }
}

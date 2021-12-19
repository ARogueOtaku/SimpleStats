package com.arogueotaku.simplestats.monsters;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MonsterToggleButton extends Button {

    private Supplier<Boolean> isOn;
    private String label;

    public MonsterToggleButton(int x, int y, int width, int height, String displayString, Consumer<MonsterToggleButton> onClick, Supplier<Boolean> isOn) {
        super(x, y, width, height,new TranslatableComponent(displayString), b->onClick.accept((MonsterToggleButton) b));
        this.isOn = isOn;
        this.label = displayString;
        update();
    }

    private void update() {
        setMessage(new TranslatableComponent(this.label + (isOn.get()?": ON" : ": OFF")));
    }

    @Override
    public void onPress() {
        super.onPress();
        update();
    }
}

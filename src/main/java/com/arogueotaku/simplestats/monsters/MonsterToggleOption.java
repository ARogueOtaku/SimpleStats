package com.arogueotaku.simplestats.monsters;

import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MonsterToggleOption extends Option {

    private final Supplier<Boolean> getValue;
    private final Consumer<Boolean> setValue;
    private MonsterToggleButton attachedButton;

    public MonsterToggleOption(String displayString, Supplier<Boolean> getValue, Consumer<Boolean> setValue) {
        super(displayString);
        this.getValue = getValue;
        this.setValue = setValue;
        this.attachedButton = null;
    }

    public MonsterToggleButton getAttachedButton() {
        return attachedButton;
    }

    @Override
    public AbstractWidget createButton(Options options, int x, int y, int width) {
        this.attachedButton = new MonsterToggleButton(x, y, width, 20, getCaption().getString(), b->this.setValue.accept(!this.getValue.get()), this.getValue);
        return this.attachedButton;
    }
}

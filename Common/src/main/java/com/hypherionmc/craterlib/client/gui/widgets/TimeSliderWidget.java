package com.hypherionmc.craterlib.client.gui.widgets;

import com.hypherionmc.craterlib.core.abstraction.text.AbstractComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

/**
 * @author HypherionSA
 * A custom slider widget used for Time. Mostly used by the Hyper Lighting Smoke Machine
 */
public class TimeSliderWidget extends AbstractSliderButton {

    private final double maxValue;
    private final ISliderChanged sliderChanged;

    public TimeSliderWidget(int x, int y, int width, int height, Component text, double value, double maxValue, ISliderChanged sliderChanged) {
        super(x, y, width, height, text, value / maxValue);
        this.maxValue = maxValue;
        this.sliderChanged = sliderChanged;
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(getDisplayString());
    }

    @Override
    protected void applyValue() {
        this.sliderChanged.onSliderChange(this);
    }

    private Component getDisplayString() {
        long seconds = Math.round(this.value * this.maxValue / 20);
        long minutes = Math.round(seconds / 60);
        if (this.value * this.maxValue >= 1200) {
            String appendString = (minutes == 1) ? "Minute" : "Minutes";
            String doSeconds = ((seconds - (minutes * 60)) > 0) ? ", " + (seconds - (minutes * 60)) + " Seconds" : "";
            return AbstractComponent.literal(minutes + " " + appendString + doSeconds);
        } else {
            return AbstractComponent.literal(seconds + " Seconds");
        }
    }

    public double getValue() {
        return this.value;
    }

    public interface ISliderChanged {
        void onSliderChange(TimeSliderWidget slider);
    }
}

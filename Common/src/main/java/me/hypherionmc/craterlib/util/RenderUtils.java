package me.hypherionmc.craterlib.util;

import com.mojang.math.Vector4f;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.awt.*;

public class RenderUtils {

    public static Vector4f colorIntToRGBA(int color) {
        float a = 1.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;

        return new Vector4f(r, g, b, a);
    }

    public static Component getFluidAmount(long amount, long capacity) {
        amount = amount / 81;
        capacity = capacity / 81;
        String text = "" + (int) (((float) amount / capacity) * 100);
        return amount > 0 ? Component.literal(ChatFormatting.AQUA + text + "%") : Component.literal(text + "%");
    }

    public static Component getTimeDisplayString(double value) {
        long seconds = Math.round((value / 20));
        long minutes = Math.round(seconds / 60);
        if (seconds >= 60) {
            String appendString = (minutes == 1) ? "Minute" : "Minutes";
            String doSeconds = ((seconds - (minutes * 60)) > 0) ? ", " + (seconds - (minutes * 60)) + " Seconds" : "";
            return Component.literal(minutes + " " + appendString + doSeconds);
        } else {
            return Component.literal(seconds + " Seconds");
        }
    }

    public static class ARGB32 {
        public static int alpha(int pPackedColor) {
            return pPackedColor >>> 24;
        }

        public static int red(int pPackedColor) {
            return pPackedColor >> 16 & 255;
        }

        public static int green(int pPackedColor) {
            return pPackedColor >> 8 & 255;
        }

        public static int blue(int pPackedColor) {
            return pPackedColor & 255;
        }
    }

    public static int renderColorFromDye(DyeColor color) {
        return color.getMaterialColor().col | 0xFF000000;
    }

    public static int alphaColorFromDye(DyeColor color, float alpha) {
        float[] colors = color.getTextureDiffuseColors();
        return new Color(colors[0], colors[1], colors[2], alpha).getRGB();
    }
}

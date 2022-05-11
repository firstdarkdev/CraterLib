package me.hypherionmc.craterlib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OptifineUtils {

    private static boolean hasOptifine = false;

    public static void checkOptifine() {
        try {
            Class ofConfigClass = Class.forName("net.optifine.Config");
            hasOptifine = true;
        } catch (ClassNotFoundException e) {
            // Optifine is probably not present. Ignore the error
            hasOptifine = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        hasOptifine = false;
    }

    public static boolean isRenderRegions() {
        try {
            Class ofConfigClass = Class.forName("net.optifine.Config");
            Method rrField = ofConfigClass.getMethod("isRenderRegions");
            return (boolean) rrField.invoke(null);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            // Optifine is probably not present. Ignore the error
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hasOptifine() {
        return hasOptifine;
    }

}

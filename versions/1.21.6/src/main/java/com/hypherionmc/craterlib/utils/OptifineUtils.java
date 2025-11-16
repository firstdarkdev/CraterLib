package com.hypherionmc.craterlib.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author HypherionSA
 * Utility class for Optifine compatibility
 */
public class OptifineUtils {

    private static final boolean hasOptifine = checkOptifine();

    private static boolean checkOptifine() {
        try {
            Class<?> ofConfigClass = Class.forName("net.optifine.Config");
            return true;
        } catch (ClassNotFoundException e) {
            // Optifine is probably not present. Ignore the error
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isRenderRegions() {
        try {
            Class<?> ofConfigClass = Class.forName("net.optifine.Config");
            Method rrField = ofConfigClass.getMethod("isRenderRegions");
            return (boolean) rrField.invoke(null);
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
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
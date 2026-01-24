package com.hypherionmc.craterlib.client.gui.config;

import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.client.gui.config.widgets.ClothConfigButtonEntry;
import com.hypherionmc.craterlib.core.config.AbstractConfig;
import com.hypherionmc.craterlib.core.config.annotations.HideFromScreen;
import com.hypherionmc.craterlib.core.config.annotations.SubConfig;
import com.hypherionmc.craterlib.core.config.annotations.Tooltip;
import me.hypherionmc.moonconfig.core.conversion.SpecComment;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HypherionSA
 * A Helper Class to convert {@link AbstractConfig}s into Cloth Config Screens
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@ApiStatus.Internal
public class ClothConfigScreenBuilder {

    /**
     * Build a new Cloth Config screen, from an {@link AbstractConfig}
     *
     * @param config The {@link AbstractConfig} the config screen is for
     * @param parent The parent {@link Screen} this screen will return to when closed
     * @return A fully usable Cloth Config screen
     */
    public static Screen buildConfigScreen(AbstractConfig config, @Nullable Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(getTranslationKey(config, config, null));

        readConfigFields(config, config, builder);

        builder.setSavingRunnable(() -> safeSaveConfig(config));
        return builder.build();
    }

    /**
     * Build a new sub-screen for config entries marked with {@link SubConfig}
     *
     * @param config The {@link AbstractConfig} the config screen is for
     * @param clazz The object or class that the screen is being built for
     * @param parent The parent {@link Screen} this screen will return to when closed
     * @return A fully usable Cloth Config screen
     */
    private static Screen buildSubScreen(AbstractConfig config, Object clazz, @Nullable Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(getTranslationKey(config, clazz, null));

        readConfigFields(config, clazz, builder);

        builder.setSavingRunnable(() -> safeSaveConfig(config));
        return builder.build();
    }

    /**
     * Build a new screen, that allows editing lists of complex objects, like a list of Classes
     *
     * @param config The {@link AbstractConfig} the config screen is for
     * @param list The list of objects this screen will be responsible for
     * @param field The field this list belongs to in the main config
     * @param invoker The object used to invoke the field, when setting the new value
     * @param parent The parent {@link Screen} this screen will return to when closed
     * @param edited Was this list edited
     * @return A fully usable Cloth Config screen
     */
    private static Screen buildListScreen(AbstractConfig config, List list, Field field, Object invoker, @Nullable Screen parent, boolean edited) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(getTranslationKey(config, invoker, field.getName()));

        ConfigCategory category = builder.getOrCreateCategory(Component.literal("Entries"));

        // Handle Existing items in the list
        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);

            int finalI = i;
            // Add a button to open the edit screen, as well as a delete button
            category.addEntry(
                    new ClothConfigButtonEntry(
                            Component.translatable("cl.buttons.entry", (i + 1)),
                            Component.translatable("cl.buttons.edit"),
                            button -> Minecraft.getInstance().setScreen(
                                    buildSubScreen(config, item, builder.build())
                            ),
                            button -> {
                                list.remove(finalI);
                                saveFieldValue(list, field, invoker);
                                Minecraft.getInstance().setScreen(buildListScreen(config, list, field, invoker, parent, true));
                            },
                            edited
                    )
            );
        }

        // Add a button to add new list entries
        Type listType = field.getGenericType();
        if (listType instanceof ParameterizedType paramType) {
            Class<?> elementType = (Class<?>) paramType.getActualTypeArguments()[0];

            category.addEntry(
                    new ClothConfigButtonEntry(
                            Component.literal(""),
                            Component.translatable("cl.buttons.add_entry"),
                            button -> {
                                try {
                                    Object newItem = elementType.getDeclaredConstructor().newInstance();
                                    list.add(newItem);
                                    saveFieldValue(list, field, invoker);
                                    Minecraft.getInstance().setScreen(buildListScreen(config, list, field, invoker, parent, true));
                                } catch (Exception e) {
                                    CraterLoader.LOGGER.error("Failed to create new list entry", e);
                                }
                            }
                    )
            );
        }

        builder.setSavingRunnable(() -> safeSaveConfig(config));
        return builder.build();
    }

    /**
     * A helper method to convert an {@link AbstractConfig} into the corresponding cloth config gui fields
     *
     * @param baseConfig The {@link AbstractConfig} to convert
     * @param config The base class that is being processed
     * @param builder The {@link ClothConfigScreenBuilder} we are currently working with
     */
    private static void readConfigFields(AbstractConfig baseConfig, Object config, ConfigBuilder builder) {
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory configCategory = builder.getOrCreateCategory(Component.literal("General"));

        for (Field field : config.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                // We ignore static, transient fields or fields marked with @HideFromScreen
                final int fieldModifiers = field.getModifiers();
                if (Modifier.isStatic(fieldModifiers) || Modifier.isTransient(fieldModifiers) || field.isAnnotationPresent(HideFromScreen.class))
                    continue;

                Object val = field.get(config);

                // Field is marked as sub-config, so we add a button field for it
                if (field.isAnnotationPresent(SubConfig.class)) {
                    if (val != null) {
                        configCategory.addEntry(
                                new ClothConfigButtonEntry(
                                        Component.translatable("cl.config." + baseConfig.getClass().getSimpleName().toLowerCase() + "." + field.getName().toLowerCase()),
                                        Component.translatable("cl.buttons.edit"),
                                        button -> Minecraft.getInstance().setScreen(
                                                buildSubScreen(baseConfig, val, builder.build())
                                        )
                                )
                        );
                        continue;
                    }
                }

                // Boolean Values
                if (val instanceof Boolean bool) {
                    configCategory.addEntry(entryBuilder.startBooleanToggle(getTranslationKey(baseConfig, config, field.getName()), bool)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(bool).build());
                }

                // Enum Values
                if (val instanceof Enum<?> enumValue) {
                    Class<Enum> enumClass = (Class<Enum>)enumValue.getDeclaringClass();
                    configCategory.addEntry(entryBuilder.startEnumSelector(
                                    getTranslationKey(baseConfig, config, field.getName()),
                                    enumClass,
                                    enumValue)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(enumValue)
                            .build());
                }

                // Int Values
                if (val instanceof Integer intt) {
                    configCategory.addEntry(entryBuilder.startIntField(getTranslationKey(baseConfig, config, field.getName()), intt)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(intt).build());
                }

                // Long Values
                if (val instanceof Long longt) {
                    configCategory.addEntry(entryBuilder.startLongField(getTranslationKey(baseConfig, config, field.getName()), longt)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(longt).build());
                }

                // Float Values
                if (val instanceof Float floatt) {
                    configCategory.addEntry(entryBuilder.startFloatField(getTranslationKey(baseConfig, config, field.getName()), floatt)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(floatt).build());
                }

                // Double Values
                if (val instanceof Double doublet) {
                    configCategory.addEntry(entryBuilder.startDoubleField(getTranslationKey(baseConfig, config, field.getName()), doublet)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(doublet).build());
                }

                // String Values
                if (val instanceof String string) {
                    configCategory.addEntry(entryBuilder.startStrField(getTranslationKey(baseConfig, config, field.getName()), string)
                            .setTooltip(getToolTip(field))
                            .setSaveConsumer(newValue -> saveFieldValue(newValue, field, config))
                            .setDefaultValue(string).build());
                }

                // Lists
                if (val instanceof List<?> list) {
                    Type listType = field.getGenericType();
                    if (listType instanceof ParameterizedType paramType) {
                        Type elementType = paramType.getActualTypeArguments()[0];

                        // String List
                        if (elementType.equals(String.class)) {
                            configCategory.addEntry(entryBuilder.startStrList(getTranslationKey(baseConfig, config, field.getName()), (List<String>) list)
                                    .setTooltip(getToolTip(field))
                                    .setSaveConsumer(newValue -> saveFieldValue(new ArrayList<>(newValue), field, config))
                                    .setDefaultValue((List<String>) list).build());

                            // Int List
                        } else if (elementType.equals(Integer.class)) {
                            configCategory.addEntry(entryBuilder.startIntList(getTranslationKey(baseConfig, config, field.getName()), (List<Integer>) list)
                                    .setTooltip(getToolTip(field))
                                    .setSaveConsumer(newValue -> saveFieldValue(new ArrayList<>(newValue), field, config))
                                    .setDefaultValue((List<Integer>) list).build());

                            // Long List
                        } else if (elementType.equals(Long.class)) {
                            configCategory.addEntry(entryBuilder.startLongList(getTranslationKey(baseConfig, config, field.getName()), (List<Long>) list)
                                    .setTooltip(getToolTip(field))
                                    .setSaveConsumer(newValue -> saveFieldValue(new ArrayList<>(newValue), field, config))
                                    .setDefaultValue((List<Long>) list).build());

                            // Float List
                        } else if (elementType.equals(Float.class)) {
                            configCategory.addEntry(entryBuilder.startFloatList(getTranslationKey(baseConfig, config, field.getName()), (List<Float>) list)
                                    .setTooltip(getToolTip(field))
                                    .setSaveConsumer(newValue -> saveFieldValue(new ArrayList<>(newValue), field, config))
                                    .setDefaultValue((List<Float>) list).build());

                            // Double List
                        } else if (elementType.equals(Double.class)) {
                            configCategory.addEntry(entryBuilder.startDoubleList(getTranslationKey(baseConfig, config, field.getName()), (List<Double>) list)
                                    .setTooltip(getToolTip(field))
                                    .setSaveConsumer(newValue -> saveFieldValue(new ArrayList<>(newValue), field, config))
                                    .setDefaultValue((List<Double>) list).build());
                        } else {
                            // List of complex objects
                            configCategory.addEntry(
                                    new ClothConfigButtonEntry(
                                            getTranslationKey(baseConfig, config, field.getName()),
                                            Component.translatable("cl.buttons.edit"),
                                            button -> Minecraft.getInstance().setScreen(
                                                    buildListScreen(baseConfig, list, field, config, builder.build(), false)
                                            )
                                    )
                            );
                        }
                    }
                }

            } catch (Exception e) {
                CraterLoader.LOGGER.error("Failed to process config file {}", baseConfig.getConfigName(), e);
            }
        }
    }

    /**
     * Helper method to supply tooltips to config fields.
     * If the field has an {@link SpecComment}, we use that, otherwise we use the {@link Tooltip} annotation
     * or generate one from the field name
     *
     * @param field The field that is being processed
     * @return A {@link Component} that can be used for the tooltip
     */
    private static Component getToolTip(Field field) {
        Component component = Component.empty();

        if (field.isAnnotationPresent(SpecComment.class)) {
            SpecComment comment = field.getAnnotation(SpecComment.class);
            component = Component.literal(comment.value());
        }

        if (field.isAnnotationPresent(Tooltip.class)) {
            Tooltip tooltip = field.getAnnotation(Tooltip.class);
            Component c = Component.empty();

            for (String comment : tooltip.value()) {
                c.getSiblings().add(Component.literal(comment));
            }

            component = c;
        }

        return component;
    }

    /**
     * A helper method to build translation keys for config screens, fields etc
     *
     * @param baseConfig The {@link AbstractConfig} being processed
     * @param currentConfig The field being processed
     * @param fieldName The raw name of the field
     * @return A {@link Component} with the new translation key
     */
    private static Component getTranslationKey(AbstractConfig baseConfig, Object currentConfig, String fieldName) {
        String baseKey = "cl.config." + baseConfig.getClass().getSimpleName().toLowerCase();

        if (currentConfig != baseConfig) {
            baseKey += "." + currentConfig.getClass().getSimpleName().toLowerCase();
        }

        if (fieldName != null) {
            baseKey += "." + fieldName.toLowerCase();
        }

        return Component.translatable(baseKey);
    }

    /**
     * Helper method to write changes values back to the config using reflection
     *
     * @param value The new value of the field
     * @param field The field that needs to be updated
     * @param config The object used to invoke the field for updating
     */
    private static void saveFieldValue(Object value, Field field, Object config) {
        try {
            if (value instanceof List && !field.getType().equals(List.class)) {
                List newList = (List)field.getType().getDeclaredConstructor().newInstance();
                newList.addAll((List)value);
                field.set(config, newList);
            } else {
                field.set(config, value);
            }
        } catch (Exception e) {
            CraterLoader.LOGGER.error("Failed to write config field {}", field.getName(), e);
        }
    }

    /**
     * Safety method to prevent config screens from corrupting configs. In some edge cases, the config gui
     * can generate invalid values, that cause the config saving to fail, and then save an empty file.
     * This method makes a backup of the config before writing to it, and restores the backup if it fails
     *
     * @param config The {@link AbstractConfig} being saved
     */
    private static void safeSaveConfig(AbstractConfig config) {
        File configPath = config.getConfigPath();
        Path backupPath = configPath.toPath().resolveSibling(configPath.getName() + ".bak");

        try {
            Files.copy(configPath.toPath(), backupPath, StandardCopyOption.REPLACE_EXISTING);
            config.saveConfig(config);
            Files.deleteIfExists(backupPath);
        } catch (Exception e) {
            Minecraft.getInstance().getToasts().addToast(
                    new SystemToast(
                            SystemToast.SystemToastId.PACK_LOAD_FAILURE,
                            Component.literal("Failed To Save Config"),
                            Component.literal("Restoring Backup Copy. Check log for details"))
            );
            CraterLoader.LOGGER.error("Failed to save config, restoring backup", e);
            try {
                Files.copy(backupPath, configPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                config.configReloaded();
            } catch (Exception restoreError) {
                CraterLoader.LOGGER.error("Failed to restore config backup", restoreError);
            }
        }
    }

}

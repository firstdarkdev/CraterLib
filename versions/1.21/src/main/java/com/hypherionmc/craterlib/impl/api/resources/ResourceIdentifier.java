package com.hypherionmc.craterlib.impl.api.resources;

import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import net.minecraft.resources.ResourceLocation;

public class ResourceIdentifier implements CraterIdentifier {

    private final ResourceLocation internal;

    public ResourceIdentifier(String namespace, String path) {
        this.internal = ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public ResourceIdentifier(String path) {
        this.internal = ResourceLocation.parse(path);
    }

    public String getNamespace() {
        return internal.getNamespace();
    }

    public String getPath() {
        return internal.getPath();
    }

    public String getString() {
        return internal.toString();
    }

    public static ResourceIdentifier fromMojang(ResourceLocation location) {
        return new ResourceIdentifier(location.getNamespace(), location.getPath());
    }

    public ResourceLocation unwrapInternal() {
        return internal;
    }
}

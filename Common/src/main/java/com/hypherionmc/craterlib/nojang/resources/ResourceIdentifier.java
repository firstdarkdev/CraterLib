package com.hypherionmc.craterlib.nojang.resources;

import net.minecraft.resources.ResourceLocation;

public class ResourceIdentifier {

    private final ResourceLocation internal;

    public ResourceIdentifier(String namespace, String path) {
        this.internal = new ResourceLocation(namespace, path);
    }

    public ResourceIdentifier(String path) {
        this.internal = new ResourceLocation(path);
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

    public ResourceLocation toMojang() {
        return internal;
    }
}

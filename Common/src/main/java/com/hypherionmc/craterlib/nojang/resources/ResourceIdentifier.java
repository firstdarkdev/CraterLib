package com.hypherionmc.craterlib.nojang.resources;

import net.minecraft.resources.Identifier;

public class ResourceIdentifier {

    private final Identifier internal;

    public ResourceIdentifier(String namespace, String path) {
        this.internal = Identifier.fromNamespaceAndPath(namespace, path);
    }

    public ResourceIdentifier(String path) {
        this.internal = Identifier.parse(path);
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

    public static ResourceIdentifier fromMojang(Identifier location) {
        return new ResourceIdentifier(location.getNamespace(), location.getPath());
    }

    public Identifier toMojang() {
        return internal;
    }
}

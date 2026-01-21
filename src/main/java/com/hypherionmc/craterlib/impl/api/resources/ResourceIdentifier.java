package com.hypherionmc.craterlib.impl.api.resources;

import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import net.minecraft.resources.Identifier;

public class ResourceIdentifier implements CraterIdentifier {

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

    public Identifier unwrapInternal() {
        return internal;
    }
}

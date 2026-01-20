package com.hypherionmc.craterlib.nojang.resources;

// TODO: Implement this properly
public class ResourceIdentifier {

    private final String namespace, path;

    public ResourceIdentifier(String namespace, String path) {
        this.path = path;
        this.namespace = namespace;
    }

    public ResourceIdentifier(String path) {
        this.path = path;
        this.namespace = "hytale";
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    public String getString() {
        return namespace + ":" + path;
    }
}

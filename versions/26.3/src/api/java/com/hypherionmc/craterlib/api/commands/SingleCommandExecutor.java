package com.hypherionmc.craterlib.api.commands;

@FunctionalInterface
public interface SingleCommandExecutor<S> {
    int run(S stack);
}
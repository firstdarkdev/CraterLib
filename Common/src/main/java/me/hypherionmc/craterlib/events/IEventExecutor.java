package me.hypherionmc.craterlib.events;

/**
 * @author HypherionSA
 * @date 21/06/2022
 */
@FunctionalInterface
public interface IEventExecutor<T extends Event> {
    void execute(T event);
}

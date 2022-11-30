package me.hypherionmc.craterlib.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HypherionSA
 * @date 21/06/2022
 * Event Bus handler for CraterLib Events
 */
public class CraterEventBus {

    /** List of registered events **/
    private static final ConcurrentHashMap<Class<? extends Event>, List<IEventExecutor<?>>> map = new ConcurrentHashMap<>();

    /**
     * Register an event to be processed
     * @param clazz a Class implementing @link{Event}
     * @param handler - The callback for when the Event is Fired
     * @param <T>
     */
    public static  <T extends Event> void register(Class<T> clazz, IEventExecutor<T> handler) {
        if (!map.containsKey(clazz)) map.put(clazz, new ArrayList<>());
        map.get(clazz).add(handler);
    }

    /**
     * Used internally to fire events
     * @param event The type of event that will be fired
     * @return True or False based on if the event is cancelled or not
     */
    public static boolean post(Event event) {
        Class<? extends Event> clazz = event.getClass();
        if (map.containsKey(clazz)) {
            List<IEventExecutor<?>> handlers = map.get(clazz);
            for (IEventExecutor handler : handlers) {
                handler.execute(event);
                if (event.isCancelled()) {
                    return false;
                }
            }
        }
        return true;
    }
}

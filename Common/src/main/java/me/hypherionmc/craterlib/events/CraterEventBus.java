package me.hypherionmc.craterlib.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HypherionSA
 * @date 21/06/2022
 */
public class CraterEventBus {
    private static final ConcurrentHashMap<Class<? extends Event>, List<IEventExecutor<?>>> map = new ConcurrentHashMap();

    public static  <T extends Event> void register(Class<T> clazz, IEventExecutor<T> handler) {
        if (!map.containsKey(clazz)) map.put(clazz, new ArrayList<>());
        map.get(clazz).add(handler);
    }

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

package com.hypherionmc.craterlib.core.event;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.event.annot.CraterEventListener;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;

public final class CraterEventBus {

    public static final CraterEventBus INSTANCE = new CraterEventBus();
    private static final Logger LOGGER = CraterConstants.LOG;
    private final Map<Class<? extends CraterEvent>, List<ListenerContainer>> events = new HashMap<>();

    public void postEvent(CraterEvent event) {
        if (eventsRegisteredForType(event.getClass())) {
            List<ListenerContainer> l = new ArrayList<>(events.get(event.getClass()));
            l.sort((o1, o2) -> Integer.compare(o2.priority, o1.priority));

            for (ListenerContainer c : l) {
                c.notifyListener(event);
            }
        }
    }

    public void registerEventListener(Class<?> clazz) {
        this.registerListenerMethods(this.getEventMethodsOf(clazz));
    }

    public void registerEventListener(Object object) {
        this.registerListenerMethods(this.getEventMethodsOf(object));
    }

    private void registerListenerMethods(List<EventMethod> methods) {
        for (EventMethod m : methods) {
            Consumer<CraterEvent> listener = (event) -> {
                try {
                    m.method.invoke(m.parentObject, event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            ListenerContainer container = new ListenerContainer(m.eventType, listener, m.priority);
            container.listenerParentClassName = m.parentClass.getName();
            container.listenerMethodName = m.method.getName();
            this.registerListener(container);
        }
    }

    private List<EventMethod> getEventMethodsOf(Object objectOrClass) {
        List<EventMethod> l = new ArrayList<>();
        try {
            if (objectOrClass != null) {
                boolean isClass = (objectOrClass instanceof Class<?>);
                Class<?> c = isClass ? (Class<?>) objectOrClass : objectOrClass.getClass();
                for (Method m : c.getMethods()) {
                    if (isClass && Modifier.isStatic(m.getModifiers())) {
                        EventMethod em = EventMethod.tryCreateFrom(new AnalyzedMethod(m, c));
                        if ((em != null) && this.hasEventAnnotation(em)) l.add(em);
                    }
                    if (!isClass && !Modifier.isStatic(m.getModifiers())) {
                        EventMethod em = EventMethod.tryCreateFrom(new AnalyzedMethod(m, objectOrClass));
                        if ((em != null) && this.hasEventAnnotation(em)) l.add(em);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    private boolean hasEventAnnotation(EventMethod m) {
        for (Annotation a : m.annotations) {
            if (a instanceof CraterEventListener) return true;
        }
        return false;
    }

    public void registerListener(Consumer<CraterEvent> listener, Class<? extends CraterEvent> eventType) {
        this.registerListener(listener, eventType, 0);
    }

    public void registerListener(Consumer<CraterEvent> listener, Class<? extends CraterEvent> eventType, int priority) {
        this.registerListener(new ListenerContainer(eventType, listener, priority));
    }

    private void registerListener(ListenerContainer listenerContainer) {
        try {
            if (!eventsRegisteredForType(listenerContainer.eventType)) {
                events.put(listenerContainer.eventType, new ArrayList<>());
            }
            events.get(listenerContainer.eventType).add(listenerContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean eventsRegisteredForType(Class<? extends CraterEvent> eventType) {
        if (eventType == null) {
            return false;
        }
        return this.events.containsKey(eventType);
    }

    protected final static class ListenerContainer {

        private final Consumer<CraterEvent> listener;
        private final Class<? extends CraterEvent> eventType;
        private final int priority;
        private String listenerParentClassName = "[unknown]";
        private String listenerMethodName = "[unknown]";

        private ListenerContainer(Class<? extends CraterEvent> eventType, Consumer<CraterEvent> listener, int priority) {
            this.eventType = eventType;
            this.listener = listener;
            this.priority = priority;
        }

        private void notifyListener(CraterEvent event) {
            try {
                this.listener.accept(event);
            } catch (Exception e) {
                LOGGER.error("##################################");
                LOGGER.error("Failed to notify event listener!");
                LOGGER.error("Event Type: " + this.eventType.getName());
                LOGGER.error("Listener Parent Class Name: " + this.listenerParentClassName);
                LOGGER.error("Listener Method Name In Parent Class: " + this.listenerMethodName);
                LOGGER.error("##################################");
                e.printStackTrace();
            }
        }
    }

    protected static class AnalyzedMethod {

        protected Method method;
        protected Object parentObject;
        protected Class<?> parentClass;
        protected boolean isStatic;
        protected List<Annotation> annotations = new ArrayList<>();

        protected AnalyzedMethod() {
        }

        protected AnalyzedMethod(Method method, Object parentObjectOrClass) {
            this.method = method;
            this.parentObject = parentObjectOrClass;
            this.parentClass = this.tryGetParentClass();
            this.isStatic = Modifier.isStatic(method.getModifiers());
            collectMethodAnnotations(this.isStatic ? null : this.parentObject.getClass(), this.method, this.annotations);
        }

        protected static void collectMethodAnnotations(Class<?> c, Method m, List<Annotation> addToList) {
            try {
                addToList.addAll(Arrays.asList(m.getAnnotations()));
                if (!Modifier.isStatic(m.getModifiers()) && (c != null)) {
                    Class<?> sc = c.getSuperclass();
                    if (sc != null) {
                        try {
                            Method sm = sc.getMethod(m.getName(), m.getParameterTypes());
                            collectMethodAnnotations(sc, sm, addToList);
                        } catch (Exception ignored) {
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }

        protected Class<?> tryGetParentClass() {
            if (this.parentObject instanceof Class<?>) {
                return (Class<?>) this.parentObject;
            }
            return this.parentObject.getClass();
        }

    }

    protected static class EventMethod extends AnalyzedMethod {

        protected final int priority;
        protected final Class<? extends CraterEvent> eventType;

        protected EventMethod(AnalyzedMethod method) {

            super();
            this.method = method.method;
            this.parentObject = method.parentObject;
            this.parentClass = method.parentClass;
            this.isStatic = method.isStatic;
            this.annotations = method.annotations;

            this.priority = this.tryGetPriority();
            this.eventType = this.tryGetEventType();

        }

        protected static EventMethod tryCreateFrom(AnalyzedMethod method) {
            EventMethod em = new EventMethod(method);
            return (em.eventType != null) ? em : null;
        }

        protected Class<? extends CraterEvent> tryGetEventType() {
            try {
                if (this.method != null) {
                    Class<?>[] params = this.method.getParameterTypes();
                    if (params.length > 0) {
                        Class<?> firstParam = params[0];
                        if (CraterEvent.class.isAssignableFrom(firstParam)) {
                            return (Class<? extends CraterEvent>) firstParam;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected int tryGetPriority() {
            try {
                for (Annotation a : this.annotations) {
                    if (a instanceof CraterEventListener craterEventListener) {
                        return craterEventListener.priority();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

    }

}
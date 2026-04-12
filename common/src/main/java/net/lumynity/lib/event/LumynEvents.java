package net.lumynity.lib.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class LumynEvents {

    private final HashMap<Class<? extends Event>, List<Consumer<? extends Event>>> eventConsumerMap = new HashMap<>();

    public <T extends Event> void register(Class<T> e, Consumer<T> consumer) {
        if (!eventConsumerMap.containsKey(e)) {
            eventConsumerMap.put(e, new ArrayList<>());
        }
        eventConsumerMap.get(e).add(consumer);
    }

    @SuppressWarnings("unchecked")
    public void raise(Event event) {
        if (!eventConsumerMap.containsKey(event.getClass())) {
            return;
        }
        for (Consumer<? extends Event> consumer: eventConsumerMap.get(event.getClass())) {
            ((Consumer<Event>) consumer).accept(event);
        }
    }

}

package cosmetics.adapters;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber s) {
        subscribers.add(s);
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    public void notifySubscribers(String eventType, Object data) {
        for (Subscriber s : subscribers) {
            s.update(eventType, data);
        }
    }
}


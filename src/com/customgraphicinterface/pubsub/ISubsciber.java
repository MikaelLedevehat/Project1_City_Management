package com.customgraphicinterface.pubsub;

public interface ISubsciber {
    default public void subscribe(String channel){
        EventManager.getInstance().subscribe(channel, this);
    }

    default public void unsubscribe(String channel){
        EventManager.getInstance().unsubscribe(channel, this);
    }

    public void onEventRecieved(String eventType, Object... payload);
}

package com.customgraphicinterface.pubsub;

public interface ISubsciber {
    public void onEventRecieved(String eventType, Object... payload);
}

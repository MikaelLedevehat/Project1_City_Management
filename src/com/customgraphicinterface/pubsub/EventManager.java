package com.customgraphicinterface.pubsub;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventManager {

    private static EventManager _em;
    private Map<String, Set<ISubsciber>> _channels;

    public static EventManager getInstance(){
        if(_em == null){
            synchronized (EventManager.class)
            {
                if(_em == null){
                    _em = new EventManager();
                }
            }
        }

        return _em;
    }

    private EventManager(){
        _channels = new ConcurrentHashMap<String, Set<ISubsciber>>();
    }

    public boolean subscribe (String channelName, ISubsciber sub){
        if(channelName == null) throw new NullPointerException("Channel name can't be null!");
        if(sub == null) throw new NullPointerException("Subscriber can't be null!");

        Set<ISubsciber> channel = _channels.get(channelName);

		if (channel == null)
		{
			synchronized (_channels)
			{
				if ((channel = _channels.get(channelName)) == null)
				{
					channel = new CopyOnWriteArraySet<ISubsciber>();
					_channels.put(channelName, channel);
				}
			}
		}
		return channel.add(sub);
    }

    public boolean unsubscribe(String channelName, ISubsciber sub){
        if(channelName == null) throw new NullPointerException("Channel name can't be null!");
        if(sub == null) throw new NullPointerException("Subscriber can't be null!");

        Set<ISubsciber> channel = _channels.get(channelName);

        if (channel == null) return false;

		return channel.remove(sub);
    }

    public Set<ISubsciber> getChannel(String chanelName){
        return _channels.get(chanelName);
    }

    public void notifyChanel(String channelName, Object... payload){
        Set<ISubsciber> subs = EventManager.getInstance().getChannel(channelName);
        if(subs == null) return;
        for (ISubsciber e : EventManager.getInstance().getChannel(channelName)) {
			e.onEventRecieved(channelName, payload);
		}
    }

    public boolean deleteChannel(String channelName){
        if(channelName == null) throw new NullPointerException("Channel name can't be null!");
        
        Set<ISubsciber> channel = _channels.get(channelName);

        if (channel == null) return false;

        for(ISubsciber e : channel) {
            this.unsubscribe(channelName, e);
        }

        return true;
    }
}

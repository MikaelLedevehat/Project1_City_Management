package com.customgraphicinterface.pubsub;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventManager {

    public class ChannelAlreadyCreatedException extends RuntimeException{
        public ChannelAlreadyCreatedException(String errorMessage, Throwable err) {
            super(errorMessage, err);
        }
        public ChannelAlreadyCreatedException(String errorMessage) {
            super(errorMessage);
        }
    }

    public class Channel{
        public final Set<ISubsciber> subs;
        public final String name;

        private Channel(Set<ISubsciber> s, String n){
            subs = s;
            name = n;
        }

        public void sendNotification(Object... payload){
            if(subs == null) return;
            for (ISubsciber e : subs) {
                e.onEventRecieved(name, payload);
            }
        }
    }

    private static EventManager _em;
    private Map<String, Channel> _channels;

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
        _channels = new ConcurrentHashMap<String, Channel>();
    }

    public Channel createChannel(String channelName){
        Channel channel = _channels.get(channelName);

		if (channel == null)
		{
			synchronized (_channels)
			{
                channel = _channels.get(channelName);
				if (channel == null)
				{
					Set<ISubsciber> s = new CopyOnWriteArraySet<ISubsciber>();
                    channel = new Channel(s, channelName);
					_channels.put(channelName, channel);
                    return channel;
				}
                else throw new ChannelAlreadyCreatedException(channelName);
			}
		}else throw new ChannelAlreadyCreatedException(channelName);
    }

    public void subscribe (String channelName, ISubsciber sub){
        if(sub == null) throw new NullPointerException("Subscriber can't be null!");
        Channel channel = getChannel(channelName);
		channel.subs.add(sub);
    }

    public void unsubscribe(String channelName, ISubsciber sub){
        
        if(sub == null) throw new NullPointerException("Subscriber can't be null!");
        Channel channel = getChannel(channelName);
		channel.subs.remove(sub);
    }

    protected Channel getChannel(String channelName){
        if(channelName == null) throw new NullPointerException("Channel name can't be null!");
        Channel channel = _channels.get(channelName);
        if (channel == null) throw new NullPointerException("the channel '"+ channelName +"' does not exist");
        return channel;
    }

    public boolean deleteChannel(String channelName){
        if(channelName == null) throw new NullPointerException("Channel name can't be null!");
        
        Channel channel = _channels.get(channelName);

        if (channel == null) return false;

        for(ISubsciber e : channel.subs) {
            this.unsubscribe(channelName, e);
        }

        _channels.remove(channelName);

        return true;
    }
}

/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

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

    public class PrivateChannelException extends RuntimeException{
        public PrivateChannelException(String errorMessage, Throwable err) {
            super(errorMessage, err);
        }
        public PrivateChannelException(String errorMessage) {
            super(errorMessage);
        }
    }

    public class Channel{
        public final Set<ISubsciber> subs;
        public final String name;
        public final boolean isPublic;

        private Channel(Set<ISubsciber> s, String n, boolean p){
            subs = s;
            name = n;
            isPublic = p;
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

    public Channel accessChannel(String name){
        Channel c = getChannel(name);
        if(c.isPublic == false) throw new PrivateChannelException("The channel '" + name + "' is private!");
        else return c;
    }

    public Channel createChannel(String channelName, boolean isPublic){
        Channel channel = _channels.get(channelName);

		if (channel == null)
		{
			synchronized (_channels)
			{
                channel = _channels.get(channelName);
				if (channel == null)
				{
					Set<ISubsciber> s = new CopyOnWriteArraySet<ISubsciber>();
                    channel = new Channel(s, channelName,isPublic);
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
		if(channel != null) channel.subs.remove(sub);
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

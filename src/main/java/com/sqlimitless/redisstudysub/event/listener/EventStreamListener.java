package com.sqlimitless.redisstudysub.event.listener;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventStreamListener implements StreamListener<String, MapRecord<String,String,String>> {

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map map = message.getValue();
        System.out.println("map = " + map);
    }
}

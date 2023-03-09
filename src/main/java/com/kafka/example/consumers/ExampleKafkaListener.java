package com.kafka.example.consumers;

import com.kafka.example.domain.AttackEvent;
import com.kafka.example.domain.AttackEventType;
import com.kafka.example.domain.UserAttacksEvent;
import com.kafka.example.cache.CacheMock;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExampleKafkaListener {

    private CacheMock cacheMock = null;//This cache has a TTL of X based on configuration
    private Set<AttackEventType> requiredEventsForStitch = Set.of(AttackEventType.values());

    @KafkaListener(topics = {"topic1", "topic2"})
    public void listenAttacksTypeOne(AttackEvent event) {
        if (triggerPublish(event)) {
            List<AttackEvent> attackEvents = cacheMock.get(event.getUserID());
            UserAttacksEvent mergedEvent = mergeEvents(attackEvents);
            publishMergedAttackEvent(mergedEvent);
            cacheMock.clear(event.getUserID());
            return;
        }
        cacheMock.save(event.getUserID(), event);
    }

    private boolean triggerPublish(AttackEvent event) {
        Set<AttackEventType> requiredEvents = requiredEventsForStitch.stream()
                                                                     .filter(type -> !type.equals(event.getType()))
                                                                     .collect(Collectors.toSet());
        return cacheMock.userEventsContainAllTypes(event.getUserID(), requiredEvents);
    }

    private UserAttacksEvent mergeEvents(List<AttackEvent> events) {
        //implementation
        return null;
    }

    private void publishMergedAttackEvent(UserAttacksEvent mergedEvent) {
        //Implementation
        return;
    }

}

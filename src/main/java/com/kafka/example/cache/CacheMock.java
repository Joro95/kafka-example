package com.kafka.example.cache;

import com.kafka.example.domain.AttackEvent;
import com.kafka.example.domain.AttackEventType;

import java.util.List;
import java.util.Set;

public interface CacheMock {
    void save(String userID, AttackEvent event);

    List<AttackEvent> get(String userID);

    void clear(String userID);

    boolean userEventsContainAllTypes(String userId, Set<AttackEventType> type);
}

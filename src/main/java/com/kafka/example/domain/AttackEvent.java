package com.kafka.example.domain;

public interface AttackEvent {

    String getUserID();//Probably a UUID
    AttackEventType getType();
}

package com.jakartawebs.learn.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * 
 * @author zakyalvan
 */
public interface NotificationRepository extends JpaRepository<Notification, Long>, QueryDslPredicateExecutor<Notification> {

}

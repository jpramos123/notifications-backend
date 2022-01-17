package com.redhat.cloud.notifications.db.repositories;

import com.redhat.cloud.notifications.models.EventType;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

// TODO: Move this class to notifications-engine.

@ApplicationScoped
public class EventTypeRepository {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    // Note: This method uses a stateless session
    public Uni<EventType> getEventType(String bundleName, String applicationName, String eventTypeName) {
        final String query = "FROM EventType WHERE name = :eventTypeName AND application.name = :applicationName AND application.bundle.name = :bundleName";
        return sessionFactory.withStatelessSession(statelessSession -> {
            return statelessSession.createQuery(query, EventType.class)
                    .setParameter("bundleName", bundleName)
                    .setParameter("applicationName", applicationName)
                    .setParameter("eventTypeName", eventTypeName)
                    .getSingleResult();
        });
    }
}

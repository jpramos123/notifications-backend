package com.redhat.cloud.notifications.db.repositories;

import com.redhat.cloud.notifications.models.Event;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

// TODO: Move this class to notifications-engine.

@ApplicationScoped
public class EventRepository {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    // Note: This method uses a stateless session
    public Uni<Event> create(Event event) {
        event.prePersist(); // This method must be called manually while using a StatelessSession.
        return sessionFactory.withStatelessSession(statelessSession -> {
            return statelessSession.insert(event)
                    .replaceWith(event);
        });
    }
}

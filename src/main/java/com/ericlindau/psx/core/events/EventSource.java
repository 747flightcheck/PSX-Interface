package com.ericlindau.psx.core.events;

import net.java.games.input.Event;

public interface EventSource extends Iterable<Event> {
  Event next();
}

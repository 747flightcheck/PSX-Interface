package com.ericlindau.psx.core.sources;

import net.java.games.input.Event;

public interface EventSource extends Iterable<Event> {
  Event next();
}

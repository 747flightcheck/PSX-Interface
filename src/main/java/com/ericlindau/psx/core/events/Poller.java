package com.ericlindau.psx.core.events;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class Poller implements EventSource {
  private final Controller[] controllers;
  private final EventQueue[] queues;

  public Poller(Controller[] controllers) {
    this.controllers = controllers;
    this.queues = new EventQueue[controllers.length];
    for (int i = 0; i < controllers.length; i++) {
      this.queues[i] = controllers[i].getEventQueue();
    }
  }

  @Override
  public Event next() {
    return null;
  }

  @Override
  @Nonnull
  public Iterator<Event> iterator() {
    return new Iterator<Event>() {
      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public Event next() {
        Event container = new Event();
        while (true) {
          for (Controller c : controllers) {
            c.poll();
          }
          for (EventQueue queue : queues) {
            if (queue.getNextEvent(container)) {
              return container;
            }
          }
        }
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}

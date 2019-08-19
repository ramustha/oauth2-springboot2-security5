package com.ramusthastudio.authserver.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

@Component
public class IdGenerators implements IdGenerator {
  private final AtomicLong topBits = new AtomicLong();
  private final AtomicLong bottomBits = new AtomicLong();

  @Override
  public UUID generateId() {
    long lowerBits = bottomBits.getAndSet(System.currentTimeMillis());
    return lowerBits == 0L ? new UUID(topBits.incrementAndGet(), lowerBits) : new UUID(topBits.get(), lowerBits);
  }
}

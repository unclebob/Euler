package sieve;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsEqual.equalTo;

public class SieveTest {
  @Test
  public void testSieve() throws Exception {
    assertThat(Sieve.primesUpTo(1), empty());
    assertThat(Sieve.primesUpTo(2), contains(2));
    assertThat(Sieve.primesUpTo(10), contains(2, 3, 5, 7));
    assertThat(Sieve.primesUpTo(1000).size(), equalTo(168));
    assertThat(Sieve.primesUpTo(1000000).size(), equalTo(78498));
  }
}

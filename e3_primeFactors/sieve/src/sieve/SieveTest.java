package sieve;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class SieveTest {
  @Test
  public void testSieve() throws Exception {
    assertThat(primesUpTo(1), empty());
    assertThat(primesUpTo(2), contains(2));
    assertThat(primesUpTo(10), contains(2,3,5,7));
    assertThat(primesUpTo(1000).size(), equalTo(168));
    for (int i=0; i<1000; i++)
      assertThat(primesUpTo(1000000).size(), equalTo(78498));
  }

  private List<Integer> primesUpTo(int n) {
    if (n<2)
      return new ArrayList<Integer>();
    return sieve(n);
  }

  private List<Integer> sieve(int n) {
    List<Integer> primes = new ArrayList<>();
    boolean[] composites = new boolean[n+1];
    for (int candidate=2; candidate<=n; candidate++) {
      if (!composites[candidate]) {
        primes.add(candidate);
        for (int multiple=2*candidate; multiple <= n; multiple += candidate)
          composites[multiple] = true;
      }
    }
    return primes;
  }
}

package sieve;

import java.util.ArrayList;
import java.util.List;

public class Sieve {
  public static List<Integer> primesUpTo(int n) {
    if (n < 2)
      return new ArrayList<>();
    return sieve(n);
  }

  private static List<Integer> sieve(int n) {
    List<Integer> primes = new ArrayList<>();
    boolean[] composites = new boolean[n + 1];
    for (int candidate = 2; candidate <= n; candidate++) {
      if (!composites[candidate]) {
        primes.add(candidate);
        for (int multiple = 2 * candidate; multiple <= n; multiple += candidate)
          composites[multiple] = true;
      }
    }
    return primes;
  }
}

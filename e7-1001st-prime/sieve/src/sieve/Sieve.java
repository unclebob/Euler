package sieve;

import java.util.ArrayList;
import java.util.List;

public class Sieve {
  private static int[] composites;

  public static List<Long> primesUpTo(long n) {
    if (n < 2)
      return new ArrayList<>();
    return sieve(n);
  }

  private static List<Long> sieve(long n) {
    List<Long> primes = new ArrayList<>();
    int nwords = (int)(n/32)+1;
    composites = new int[nwords];
    for (long candidate = 2; candidate <= n; candidate++) {
      if (!isComposite(candidate)) {
        primes.add(candidate);
        for (long multiple = 2 * candidate; multiple <= n; multiple += candidate)
          setComposite(multiple);
      }
    }
    return primes;
  }

  private static void setComposite(long candidate) {
    int wordIndex = (int) (candidate/32);
    int bitIndex = (int)(candidate%32);
    int bit = (1 << bitIndex);
    composites[wordIndex] |= bit;
  }

  private static boolean isComposite(long candidate) {
    int wordIndex = (int) (candidate/32);
    int theWord = composites[wordIndex];
    int bitIndex = (int)(candidate%32);
    int bit = (1 << bitIndex);
    int result = theWord & bit;
    return result != 0;
  }
}

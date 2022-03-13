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

  public static List<Long> sieve(long n) {
    List<Long> primes = new ArrayList<>();
    int nwords = (int) (n / 32) + 1;
    composites = new int[nwords];
    int count = 0;
    int period = 2 * 3 * 5 * 7 * 11 * 13 * 17 * 19 *23;
    long multipleLimit = period*32L;
    for (long candidate = 2; candidate <= n; candidate += (candidate == 2 ? 1 : 2)) {
      if (!isComposite(candidate)) {
        if (++count >= 999999999) {
          System.out.println("n= " + count + " prime = " + candidate);
        }
        //primes.add(candidate);
        if (count > 1000000001)
          return primes;
        for (long multiple = 2 * candidate; multiple <= n; multiple += candidate) {
          if (multiple == multipleLimit) {
            System.arraycopy(composites, 0, composites, period, nwords - period);
            break;
          } else
            setComposite(multiple);
        }
      }
    }
    return primes;
  }

  private static void setComposite(long candidate) {
    int wordIndex = (int) (candidate / 32);
    int bitIndex = (int) (candidate % 32);
    int bit = (1 << bitIndex);
    composites[wordIndex] |= bit;
  }

  private static boolean isComposite(long candidate) {
    int wordIndex = (int) (candidate / 32);
    int theWord = composites[wordIndex];
    int bitIndex = (int) (candidate % 32);
    int bit = (1 << bitIndex);
    int result = theWord & bit;
    return result != 0;
  }
}

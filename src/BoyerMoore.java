import java.math.*;
import java.util.*;

import parcs.*;

public class BoyerMoore implements AM{

     static int NO_OF_CHARS = 256;

    //A utility function to get maximum of two integers
     static int max (int a, int b) { return (a > b)? a: b; }

     //The preprocessing function for Boyer Moore's
     //bad character heuristic
     static void badCharHeuristic( char []str, int size,int badchar[])
     {

      // Initialize all occurrences as -1
      for (int i = 0; i < NO_OF_CHARS; i++)
           badchar[i] = -1;

      // Fill the actual value of last occurrence
      // of a character (indices of table are ascii and values are index of occurrence)
      for (int i = 0; i < size; i++)
           badchar[(int) str[i]] = i;
     }

     /* A pattern searching function that uses Bad
     Character Heuristic of Boyer Moore Algorithm */
     static int search( char txt[],  char pat[])
     {
      int m = pat.length;
      int n = txt.length;

      int badchar[] = new int[NO_OF_CHARS];

      /* Fill the bad character array by calling
         the preprocessing function badCharHeuristic()
         for given pattern */
      badCharHeuristic(pat, m, badchar);

      int s = 0;  // s is shift of the pattern with
                  // respect to text
       //there are n-m+1 potential alignments
      int count = 0;
      while(s <= (n - m))
      {
          int j = m-1;

          /* Keep reducing index j of pattern while
             characters of pattern and text are
             matching at this shift s */
          while(j >= 0 && pat[j] == txt[s+j])
              j--;

          /* If the pattern is present at current
             shift, then index j will become -1 after
             the above loop */
          if (j < 0)
          {
              System.out.println("Patterns occur at shift = " + s);
              count++;
              /* Shift the pattern so that the next
                 character in text aligns with the last
                 occurrence of it in pattern.
                 The condition s+m < n is necessary for
                 the case when pattern occurs at the end
                 of text */
              //txt[s+m] is character after the pattern in text
              s += (s+m < n)? m-badchar[txt[s+m]] : 1;

          }

          else
              /* Shift the pattern so that the bad character
                 in text aligns with the last occurrence of
                 it in pattern. The max function is used to
                 make sure that we get a positive shift.
                 We may get a negative shift if the last
                 occurrence  of bad character in pattern
                 is on the right side of the current
                 character. */
              s += max(1, j - badchar[txt[s+j]]);
      }
      System.out.println("Patterns occurs " + count + "times.");
      return count;
     }

     /* Driver program to test above function */
    public  void run(AMInfo info) {

         char txt[] = "cxzcxzc xzczxzxc zxc zxc".toCharArray();
         char pat[] = "zxc".toCharArray();
         search(txt, pat);
    }
}

// public class BoyerMoore implements AM{
//     public void run(AMInfo info)
//     {
//         long la, lb, ln, ll, lr;
//         // Reading data
//         la = info.parent.readLong();
//         lb = info.parent.readLong();
//         ln = info.parent.readLong();
//         ll = info.parent.readLong();
//         lr = info.parent.readLong();
//         lr = info.parent.read
//
//         System.out.println("The ShanksAlgorithm class have read data from the parent server");
//         System.out.println("alpha = " + la);
//         System.out.println("beta = " + lb);
//         System.out.println("n = " + ln);
//         System.out.println("left bound = " + ll);
//         System.out.println("right bound = " + lr);
//         // Converting to BigInteger
//         BigInteger a = BigInteger.valueOf(la);
//         BigInteger b = BigInteger.valueOf(lb);
//         BigInteger n = BigInteger.valueOf(ln);
//         BigInteger l = BigInteger.valueOf(ll);
//         BigInteger r = BigInteger.valueOf(lr);
//
//         HashMap<BigInteger, BigInteger> mp = new HashMap<>();
//
//         BigInteger maxRoot = BigInteger.valueOf(10000000);
//         BigInteger m = n.sqrt();
//         m = m.min(maxRoot);
//
//         // Making map - the first part
//         BigInteger x;
//         BigInteger gamma = BigInteger.valueOf(1);
//         for(BigInteger i = BigInteger.valueOf(1); i.compareTo(m) == -1; i = i.add(BigInteger.valueOf(1)))
//         {
//             mp.put(gamma, i);
//             gamma = gamma.multiply(a).mod(n);
//         }
//
//         // Main algorithm - the second part
//         BigInteger solutionX = null;
//         BigInteger step = a.modInverse(n).modPow(m, n);
//
//         BigInteger j  = BigInteger.valueOf(1);
//
//         for(x = l, gamma = a.modInverse(n).modPow(l, n); x.compareTo(r) <= 0;
//             x = x.add(m), gamma = gamma.multiply(step).mod(n))
//         {
//             //j = j.add(BigInteger.valueOf(1));
//
//             if(mp.containsKey(gamma.multiply(b).mod(n)))
//             {
//                 solutionX = x.add(mp.get(gamma.multiply(b).mod(n)));
//                 break;
//             }
//         }
//         //System.out.println(j);
//         // Returning results
//         if(solutionX != null)
//         {
//             solutionX = solutionX.subtract(j);
//             System.out.println("The ShanksAlgorithm class return the result: x = " + solutionX);
//             info.parent.write(solutionX.longValue());
//         }
//         else
//         {
//             System.out.println("The ShanksAlgorithm class doesn't find any solution");
//             info.parent.write(-1L);
//         }
//     }
// }
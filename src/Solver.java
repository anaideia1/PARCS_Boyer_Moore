import java.io.*;
import java.math.*;
import java.util.*;

import parcs.*;

public class Solver implements AM
{
    public static void main(String[] args)
    {
        System.out.println("The Solver class start method main");

        task curtask = new task();
        curtask.addJarFile("Solver.jar");
        curtask.addJarFile("BoyerMoore.jar");

        System.out.println("The Solver class method main adder jar files");

        (new Solver()).run(new AMInfo(curtask, (channel)null));

        System.out.println("The Solver class method main finish work");
        curtask.end();
    }

    public void run(AMInfo info)
    {
        long la, lb, ln;
        int lp;

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("test_1.txt")));

//             lp = Integer.parseInt(in.readLine());
//             la = Long.parseLong(in.readLine());
//             lb = Long.parseLong(in.readLine());
//             ln = Long.parseLong(in.readLine());
        }
        catch (IOException e)
        {
            System.out.print("Reading input data error\n");
            e.printStackTrace();
            return;
        }

//         System.out.println("The Solver class have read data from the parent server");
//         System.out.println("alpha = " + la);
//         System.out.println("beta = " + lb);
//         System.out.println("n = " + ln);
        // Time counting
        long tStart = System.nanoTime();

//         long res = solve(info, lp, la, lb, ln);
        long res = solve(info, 2);

        long tEnd = System.nanoTime();
        // Printing results
        if(res == -1)
        {
            System.out.println("There is no solution for: ");
        }
        else
        {
            System.out.println("x = " + res);
        }
        System.out.println("Working time on " + 2 + " processes: " + ((tEnd - tStart) / 1000000) + "ms");
    }

    static public long solve(AMInfo info, int nThreads)
    {
        List<BigInteger> left = new ArrayList<>();
        List<BigInteger> right = new ArrayList<>();
        List<Long> solution = new ArrayList<>();

//         BigInteger n = BigInteger.valueOf(ln);
        BigInteger n = BigInteger.ONE;
        // Dividing the line of mod to intervals
//         for(int i = 0; i < nThreads; i++)
//         {
//             BigInteger tl = n.multiply(BigInteger.valueOf(i)).divide(BigInteger.valueOf(nThreads));
//             BigInteger tr = n.multiply(BigInteger.valueOf(i).add(BigInteger.valueOf(1))).divide(BigInteger.valueOf(nThreads)).subtract(BigInteger.valueOf(1));
//
//             left.add(tl);
//             right.add(tr);
//         }

        List <point> points = new ArrayList<point>();
        List <channel> channels = new ArrayList<channel>();
        // Connection to points
        for(int i = 0; i < nThreads; i++)
        {
            BigInteger tl = n.multiply(BigInteger.valueOf(i)).divide(BigInteger.valueOf(nThreads));
            BigInteger tr = n.multiply(BigInteger.valueOf(i).add(BigInteger.valueOf(1))).divide(BigInteger.valueOf(nThreads)).subtract(BigInteger.valueOf(1));

            String text = "cxzxcxzc xzczxzxc zxc zxcz";
            String pattern = "zxc";

            System.out.println(i);
            points.add(info.createPoint());
            System.out.println(points);
            channels.add(points.get(i).createChannel());
            points.get(i).execute("BoyerMoore");
            channels.get(i).write(text);
            channels.get(i).write(pattern);
        }

        // Mapping results
        for(int i = 0; i < nThreads; i++)
        {
            solution.add(channels.get(i).readLong());
        }
        // Finding the solution
        long result = -1;
        for (Long aLong : solution) {
            if (aLong != -1) {
                result = aLong;
            }
        }

        return result;
    }
}
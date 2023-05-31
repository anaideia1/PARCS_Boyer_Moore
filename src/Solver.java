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
        int n=2;
        String text, pattern;

        try
        {
            BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("test_2.txt")));
            text = in.readLine();
            pattern = "zxc";
        }
        catch (IOException e)
        {
            System.out.print("Reading input data error\n");
            e.printStackTrace();
            return;
        }

        long tStart = System.nanoTime();
        long res = solve(info, n, text, pattern);

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

    static public long solve(AMInfo info, int nThreads, String text, String pattern)
    {
        List<BigInteger> left = new ArrayList<>();
        List<BigInteger> right = new ArrayList<>();
        List<Integer> solution = new ArrayList<>();

        List <point> points = new ArrayList<point>();
        List <channel> channels = new ArrayList<channel>();
        
        // Connection to points
        Integer step = text.length() / nThreads;
        Integer remainder = text.length() % nThreads;
        for(int i = 0; i < nThreads; i++)
        {
            Integer overlap = ((i < nThreads - 1) ? pattern.length() - 1 : remainder);
            String subText = text.substring(i * step, (i + 1) * step + overlap);

            System.out.println(i);
            points.add(info.createPoint());
            System.out.println(points);
            channels.add(points.get(i).createChannel());
            points.get(i).execute("BoyerMoore");
            channels.get(i).write(subText);
            channels.get(i).write(pattern);
        }

        // Mapping results
        for(int i = 0; i < nThreads; i++)
        {
            solution.add(channels.get(i).readInt());
        }
        // Finding the solution
        long result = 0;
        for (int a: solution) {
            result += a;
        }
        System.out.println("The BoyerMoore daemons returns the summarized result: x = " + result);
        return result;
    }
}
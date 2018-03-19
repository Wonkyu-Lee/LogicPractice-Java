import java.util.*;

public class WeightedJobScheduling {
    public static class Job {
        public final int start;
        public final int end;
        public final int benefit;

        Job(int start, int end, int benefit) {
            this.start = start;
            this.end = end;
            this.benefit = benefit;
        }

        boolean overlap(Job other) {
            return (start < other.start && other.start < end) || (start < other.end && other.end < end);
        }

        @Override
        public String toString() {
            return String.format("[%d~%d, %d]", start, end, benefit);
        }
    }

    /**
     *   range: (1,3) (2,5) (4,6) (6,7) (5,8) (7,9)
     * benefit:   5     6     5     4    11     2
     *      dp:   5     6    10    14    17    16
     *       j:                                 .
     *       i:                                 .
     */
    static int maxBenefit(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparing(job->job.end));
        int[] dp = new int[jobs.length];
        for (int i = 0; i < dp.length; ++i) {
            dp[i] = jobs[i].benefit;
        }

        for (int i = 1; i < dp.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (jobs[i].overlap(jobs[j]))
                    continue;
                dp[i] = Math.max(dp[i], dp[j] + jobs[i].benefit);
            }
        }

        int maxValue = Integer.MIN_VALUE;
        for (int i = 1; i < dp.length; ++i) {
            maxValue = Math.max(maxValue, dp[i]);
        }

        return maxValue;
    }

    static Collection<Job> maxBenefitJobs(Job[] jobs) {
        Arrays.sort(jobs, Comparator.comparing(job->job.end));

        int[] dp = new int[jobs.length];
        int[] prev = new int[jobs.length];
        for (int i = 0; i < dp.length; ++i) {
            dp[i] = jobs[i].benefit;
            prev[i] = -1;
        }

        for (int i = 1; i < dp.length; ++i) {
            int indexOfMaxBenefit = -1;
            for (int j = 0; j < i; ++j) {
                if (jobs[i].overlap(jobs[j]))
                    continue;

                int newBenefit = dp[j] + jobs[i].benefit;
                if (dp[i] < newBenefit) {
                    dp[i] = newBenefit;
                    prev[i] = j;
                }
            }
        }

        int maxValue = Integer.MIN_VALUE;
        int indexOfMaxValue = -1;
        for (int i = 1; i < dp.length; ++i) {
            if (dp[i] > maxValue) {
                maxValue = dp[i];
                indexOfMaxValue = i;
            }
        }

        LinkedList<Job> result = new LinkedList<>();
        int current = indexOfMaxValue;
        while (current != -1) {
            result.addFirst(jobs[current]);
            current = prev[current];
        }

        return result;
    }

    public static void main(String[] args) {
        Job[] jobs = {
                new Job(1, 3, 5),
                new Job(2, 5, 6),
                new Job(4, 6, 5),
                new Job(6, 7, 4),
                new Job(5, 8, 11),
                new Job(7, 9, 2)
        };

        Collection<Job> result = maxBenefitJobs(jobs);
        for (Job job : result) {
            System.out.print(job);
        }
    }

}

package toplikedquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/29
 * @version: V1.0
 * @slogan:
 * @description :目标和
 */
public class Problem_0494_TargetSum {

    public int findTargetSumWays1(int[] arr, int s) {
        return process1(arr, 0, s);
    }

    /**
     * 暴力递归  从index... 往后的位置上随机组合得到目标target的方案数有多少
     */
    private int process1(int[] nums, int index, int rest) {
        if (index == nums.length) {
            // 到达结尾时 目标值刚好为0 就找到一种方案
            return rest == 0 ? 1 : 0;
        }

        // index位置+时
        int p1 = process1(nums, index + 1, rest - nums[index]);
        // index位置-时
        int p2 = process1(nums, index + 1, rest + nums[index]);
        return p1 + p2;
    }


    public static int dp(int[] arr, int s) {
        int N = arr.length;
        int allSum = 0;
        for (int i = 0; i < N; i++) {
            allSum += arr[i];
        }

        if (Math.abs(s) > allSum) {
            return 0;
        }


        // f(index, target)  =  dp[i][j + sum]  不错在 dp[i][-2]
        int[][] dp = new int[N + 1][2 * allSum + 1];
        // dp[N][...] = 0
        dp[N][0 + allSum] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = -allSum; rest <= allSum; rest++) {
                // index rest   (index+1 rest + arr[index])
                //              (index+1 rest - arr[index])

                dp[index][allSum + rest] = 0;
                if (allSum + rest + arr[index] <= 2 * allSum) {
                    dp[index][allSum + rest] += dp[index + 1][allSum + rest + arr[index]];
                }
                if (allSum + rest - arr[index] >= 0) {
                    dp[index][allSum + rest] += dp[index + 1][allSum + rest - arr[index]];
                }
            }
        }

        // {1} s=100 显然会越界
        return dp[0][allSum + s];
    }





    /**
     * 动态规划版本  注意取值范围！！！
     *
     * 1 <= nums.length <= 20
     * 0 <= nums[i] <= 1000
     * 0 <= sum(nums[i]) <= 1000
     * -1000 <= target <= 1000
     */
    public static int findTargetSumWays2(int[] nums, int target) {
        int N = nums.length;

        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        return process2(nums,   0, target, map);
    }


    /**
     * 傻缓存
     */
    private static int process2(int[] nums, int index, int target, HashMap<Integer, HashMap<Integer, Integer>> map) {
        if (map.containsKey(index) && map.get(index).containsKey(target)) {
            return map.get(index).get(target);
        }

        // 可能时index没有算过 也可能时target没有算过
        if (index == nums.length) {
            int p = target == 0 ? 1 : 0;
            HashMap<Integer, Integer> targetMap = map.getOrDefault(index, new HashMap<>());
            if (targetMap.containsKey(target)) {
                return targetMap.get(target);
            }
            targetMap.put(target, p);
            map.put(index, targetMap);
            return p;
        }


        // index位置+时
        int p1 = process2(nums, index + 1, target - nums[index], map);
        // index位置-时
        int p2 = process2(nums, index + 1, target + nums[index], map);

        int p = p1 + p2;
        // 可能时index没有算过 也可能时target没有算过
        HashMap<Integer, Integer> targetMap = map.getOrDefault(index, new HashMap<>());
        if (targetMap.containsKey(target)) {
            return targetMap.get(target);
        }
        targetMap.put(target, p);
        map.put(index, targetMap);
        return p;
    }



    // 老师给的解法一

    public static int findTargetSumWays3(int[] arr, int s) {
        return process3(arr, 0, s, new HashMap<>());
    }

    public static int process3(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            return dp.get(index).get(rest);
        }
        int ans = 0;
        if (index == arr.length) {
            ans = rest == 0 ? 1 : 0;
        } else {
            ans = process3(arr, index + 1, rest - arr[index], dp) + process3(arr, index + 1, rest + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }


    // 老师给的解法二  这是一个错误的答案

    // 优化点一 :
    // 你可以认为arr中都是非负数
    // 因为即便是arr中有负数，比如[3,-4,2]
    // 因为你能在每个数前面用+或者-号
    // 所以[3,-4,2]其实和[3,4,2]达成一样的效果
    // 那么我们就全把arr变成非负数，不会影响结果的
    // 优化点二 :
    // 如果arr都是非负数，并且所有数的累加和是sum
    // 那么如果target<sum，很明显没有任何方法可以达到target，可以直接返回0
    // 优化点三 :
    // arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
    // 所以，如果所有数的累加和是sum，
    // 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
    // 优化点四 :
    // 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
    // 其中一个方案是 : +1 -2 +3 -4 +5 = 3
    // 该方案中取了正的集合为P = {1，3，5}
    // 该方案中取了负的集合为N = {2，4}
    // 所以任何一种方案，都一定有 sum(P) - sum(N) = target
    // 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
    // sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
    // 2 * sum(P) = target + 数组所有数的累加和
    // sum(P) = (target + 数组所有数的累加和) / 2
    // 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
    // 那么就一定对应一种target的方式
    // 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
    // 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
    // 优化点五 :
    // 二维动态规划的空间压缩技巧
    public static int findTargetSumWays4(int[] arr, int s) {
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        return sum < s || ((s & 1) ^ (sum & 1)) != 0 ? 0 : subset(arr, (s + sum) >> 1);
    }

    public static int subset(int[] nums, int s) {
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for (int n : nums) {
            for (int i = s; i >= n; i--) {
                dp[i] += dp[i - n];
            }
        }
        return dp[s];
    }
    public static void main(String[] args) {
        int[] nums = {1};
        System.out.println(dp(nums, 2));
    }
}

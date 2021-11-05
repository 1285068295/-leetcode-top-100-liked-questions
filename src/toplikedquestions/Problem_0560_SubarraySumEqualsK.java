package toplikedquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/30
 * @version: V1.0
 * @slogan:
 * @description :和为 K 的子数组  进阶班数组三连问题以及 438 树上累加和问题
 * 【解题思路】   记住枚举所有的以arr[i] 结尾的子数组！！！
 *  先把 0 ~ i arr[0]~arr[i] 任意位置的累加和求出来记作 all
 *  我们去枚举所有的以arr[i]结尾的子数组累加和为k
 *  要求arr[x+1] ~ arr[i] 累加和k 所以arr[0]~arr[x]上累加和 all-k
 *  因为  arr[0] ... arr[x] arr[x+1] ... arr[i] 总的累加和all
 *  可以用map记录 arr[0] ~ arr[x] 累加和 all-k  map的value为 累加和为all-k的次数
 *   最后累加所有的次数即可
 */
public class Problem_0560_SubarraySumEqualsK {

    public static int subarraySum(int[] nums, int k) {

        // key - 累加和为all-k  value 累加和为all-k出现的次数
        HashMap<Integer, Integer> map = new HashMap<>();

        // 预置 防止错过以arr[0]开头的子数组累加和为k  {1} k=1
        map.put(0, 1);

        // 收集答案
        int ans = 0;
        // 0...i 位置的累加和
        int all = 0;
        for (int i = 0; i < nums.length; i++) {
            all += nums[i];

            // 收集答案  必须要先收集答案 再加入map中
            if (map.containsKey(all - k)) {
                ans += map.get(all - k);
            }


            if (map.containsKey(all)) {
                map.put(all, map.get(all) + 1);
            } else {
                map.put(all, 1);
            }


        }
        return ans;
    }

    public static void main(String[] args) {

        int[] arr = {1,1,1};
        System.out.println(subarraySum(arr, 2));

    }


}

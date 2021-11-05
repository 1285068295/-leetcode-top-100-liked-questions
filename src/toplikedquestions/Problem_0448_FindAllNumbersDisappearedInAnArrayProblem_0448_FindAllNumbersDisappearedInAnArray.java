package toplikedquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/29
 * @version: V1.0
 * @slogan:
 * @description :找到所有数组中消失的数字  参考41题  268题
 *
 * 解题思路  在索引为i的位置放上  i+1 遍历完成后 那些i位置上的数字不是i+1就说明 是缺失的数字
 *
 * 这道题的技巧是 当i位置上不是 i+1 时  把位置的值arr[i] 交换到 arr[i] -1 的位置（一定不会越界的）
 *
 */
public class Problem_0448_FindAllNumbersDisappearedInAnArrayProblem_0448_FindAllNumbersDisappearedInAnArray {

    public static List<Integer> findDisappearedNumbers(int[] nums) {

        List<Integer> ans = new ArrayList<>();

        // 5 4 5 1 5
        // 0 1 2 3 4

        for (int i = 0; i < nums.length; i++) {
            // 长度为 n 范围为 1 ~ n+1
            // 把 i 位置的nums[i] 交换到num[i]-1
            while (nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        // i位置上不是i+1的位置收集答案
        for (int i = 0; i < nums.length; i++) {
            // 长度为 n 范围为 1 ~ n+1
            // 把 i 位置的nums[i] 交换到num[i]-1
            if (nums[i] != i + 1) {
                ans.add(i + 1);
            }
        }
        return ans;
    }
    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {4,3,2,7,8,2,3,1};
        findDisappearedNumbers(arr);
    }

}

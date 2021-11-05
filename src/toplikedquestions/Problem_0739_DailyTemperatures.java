package toplikedquestions;

import java.util.ArrayDeque;

/**
 * @author ：Lisp
 * @date： 2021/10/31
 * @version: V1.0
 * @slogan:
 * @description :每日温度
 *
 * 单调栈搞  距离当前位置右侧最近的比自己大的数的位置
 *
 * 从下到上是递增的 可以找到最近的比自己小的数
 * 如 10 8 6 4 14  8压栈的时候 比栈顶的10小 则10右侧最近的比自己小的数就是8
 *
 * 比自己小时直接压栈  大于的时候结算栈顶的数据  可以找到最近的比自己大的数
 * 如 10 8 6 4 14  8压栈的时候 比栈顶的10小 则10右侧最近的比自己小的数就是8
 *
 *
 *
 */
public class Problem_0739_DailyTemperatures {

    public static int[] dailyTemperatures(int[] temperatures) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int N = temperatures.length;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            // 比自己小时直接压栈  大于的时候结算栈顶的数据  3 2 1 4
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int preIndex = stack.poll();
                ans[preIndex] = i - preIndex;
            }
            stack.push(i);
        }
        return ans;
    }

    public static void main(String[] args) {

        int[] arr1 = {3, 2, 1, 4};
        int[] arr2 = {89, 62, 70, 58, 47, 47, 46, 76, 100, 70};
//        dailyTemperatures(arr1);
        dailyTemperatures(arr2);
    }


}

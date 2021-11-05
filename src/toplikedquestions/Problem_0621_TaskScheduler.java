package toplikedquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/30
 * @version: V1.0
 * @slogan:
 * @description :任务调度器
 *
 * 【解题思路】  很有意思的一道题
 *  整体思路如下
 *  1 先找到出现次数最多的任务  如  ['A', 'B', 'A', 'B', 'A', 'B','C', 'D']  A-3次   B-3次  设 free = 3
 *  2 把出现次数最多的任务分组如下    AB _  _   AB _  _ AB   因为free为3 每个A之间间隔为 3
 *  3 剩下的任务依次的插入到空格中  AB C  _   AB D  _ AB
 *    如果空格不够了 继续想里面插如下 AB C XRDS   AB D XRDS  AB  注意填空格时 要交替依次填 如下顺序填写
 *    AB 1 3 5 ... AB 2 4 6...  AB
 *
 *    总时间为两种情况 1 剩余空格时 总计时间= 总任务数 + 空格数
 *                   2 空格全部被填满了  总时间 = 总任务书
 *
 *  类似CUP流水线调度任务！！！
 *
 */
public class Problem_0621_TaskScheduler {


    public static int leastInterval(char[] tasks, int free) {
        // 统计词频 找到最大词频
        int maxTime = 0;
        int[] times = new int[256];
        for (int i = 0; i < tasks.length; i++) {
            times[tasks[i]]++;
            maxTime = Math.max(maxTime, times[tasks[i]]);
        }


        // 词频最多的单词的个数 以便确定出空格的数量
        int count = 0;
        for (int i = 0; i < times.length; i++) {
            if (maxTime == times[i]) {
                count++;
            }
        }

        // 计算空格的数量  maxTime=2  count=2 free=4  AB_ _ _ AB
        int midFree = (maxTime - 1) * (free - count + 1);
        // 总的剩余任务数量
        int AllRest = tasks.length - (maxTime * count);
        return tasks.length + (AllRest > midFree ? 0 : midFree - AllRest);

    }

    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B','B'    };
        System.out.println(leastInterval(tasks, 2));
    }

}

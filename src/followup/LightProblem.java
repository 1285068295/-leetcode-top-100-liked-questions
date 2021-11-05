package followup;

/**
 * @author ：Lisp
 * @date： 2021/11/2
 * @version: V1.0
 * @slogan:
 * @description :【字节跳动面试题】
 *
 * 给定一个数组arr，长度为N，arr中的值不是0就是1
 * arr[i]表示第i栈灯的状态，0代表灭灯，1代表亮灯
 * 每一栈灯都有开关，但是按下i号灯的开关，会同时改变i-1、i、i+1栈灯的状态
 * 问题一：
 * 如果N栈灯排成一条直线,请问最少按下多少次开关,能让灯都亮起来
 * 排成一条直线说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关只能影响0和1位置的灯
 * N-1号灯的开关只能影响N-2和N-1位置的灯
 *
 * 问题二：
 * 如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来
 * 排成一个圈说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关能影响N-1、0和1位置的灯
 * N-1号灯的开关能影响N-2、N-1和0位置的灯
 *
 */
public class LightProblem {

    /**
     * 无环改灯问题的暴力版本
     */
    public static int noLoopRight(int[] arr) {

        if (arr == null ||arr.length == 0){
            return 0;
        }

        if (arr.length == 1) {
            // 只有一个灯  灯是灭的1次  灯是开的0次
            return arr[0] ^ 1;
        }

        if (arr.length == 2) {
            // 两个等的装状态不一致 无法完成同时亮
            return arr[0] != arr[1] ? Integer.MAX_VALUE : arr[0] ^ 1;
        }

        return f1(arr, 0);

    }

    /**
     * 暴力递归  因为arr数组每次都在改变 所以不能改动态规划
     * 从i位置决策  i位置为开时，i位置为关时，去i+1位置上决策  返回最少的开关调整的方案数
     *
     * 	arr[0...i-1]的灯，不需要管
     * 	arr[i......]的灯上，按开关
     * 	1) i位置的开关，不改变，走一个分支
     * 	2) i位置的开关，改变，走一个分支
     */
    private static int f1(int[] arr, int i) {

        if (i == arr.length){
            // 到达末尾位置时 检查是否灯全亮 全亮 不需要再改变灯
            return valid(arr) ? 0 : Integer.MAX_VALUE;
        }

        // 决定，在i位置，不改变开关  p1也有可能为Integer.MAX_VALUE
        int p1 = f1(arr, i + 1);
        // 决定，在i位置，改变开关
        change1(arr, i);
        int p2 = f1(arr, i + 1);

        // 这里有点难理解  自己画一下递归的过程图
        // 深度优先遍历 需要还原现场
        /**
         *   f1(0) -->  f1(1) --> f1(2) --> f1(3) -->  f1(4) -->  ...
         *                                            |- change(arr) valid
         *                                            |- 返回到上一级的f1(3) 时 要先把arr状态还原了。
         *
         *                                  |- change(arr)  f1(4)
         *                                                    |- 返回到上一级的f1(2) 时 要先把arr状态还原了。
         *
         *                                  |- 返回到上一级的f1(2) 时 要先把arr状态还原了。
         */
        change1(arr, i);

        p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
        return Math.min(p1, p2);
    }

    /**
     * 改变i位置的灯 同时影响 i-1 i+1位置
     */
    private static void change1(int[] arr, int i) {
        // 灯的状态为0变为1 灯的状态为1变为0  使用异或运算
        if (i == 0) {
            // 0位置的灯只能影响1位置的灯
            arr[0] ^= 1;
            arr[1] ^= 1;
        } else if (i == arr.length - 1) {
            // 末尾的灯只能影响其前一个位置的灯
            arr[i - 1] ^= 1;
            arr[i] ^= 1;
        } else {
            arr[i - 1] ^= 1;
            arr[i] ^= 1;
            arr[i + 1] ^= 1;
        }
    }

    /**
     * 检查灯是否全亮
     */
    private static boolean valid(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                return false;
            }
        }
        return true;
    }





    /**
     * 无环改灯问题的优良递归版本
     *
     * 【思路】
     *  递归的过程是从左向右来尝试的  当尝试到 i 位置时 i位置的灯可以影响到 i-1  i+1
     *  而处理完成 i 位置的灯时 再去递归处理 i+1 位置的灯时   i+1 位置的灯只能影响  i i+2 位置灯
     *  而i-1 位置灯再也不可能改变了
     *  所以再遍历arr数组时 只需要记录  前前位置灯状态  前一个灯位置状态  当前灯位置
     *  根据前前灯的状态来调整前一个灯的状态来进行递归过程
     *
     *  注意  0位置的灯要保持亮有两种方案  情况1 直接改变0位置的开关
     *                                 情况2 通过改变1位置的开关来影响0位置的灯
     *       除此之位再没有其他的方案了。
     *       对于0位置除外的位置   prepre   pre  cur  遍历的cur位置时  如果prepre位置为关闭的 只能调整pre位置的开关来使得prepre亮
     *       因为下次递归时  prepre=pre  pre=cur  cur=cur+1 再也不可能影响到prepre位置的灯了
     *
     */
    public static int noLoopMinStep1(int[] arr) {

        if (arr == null ||arr.length == 0){
            return 0;
        }
        if (arr.length == 1) {
            // 只有一个灯  灯是灭的1次  灯是开的0次
            return arr[0] ^ 1;
        }
        if (arr.length == 2) {
            // 两个等的装状态不一致 无法完成同时亮
            return arr[0] != arr[1] ? Integer.MAX_VALUE : arr[0] ^ 1;
        }

        // 超过两盏灯的情况下
        // 0位置的灯是特殊情况 有种方案
        // 方案1 不改变0位置的灯
        int p1 = f2(arr, arr[0], arr[1], 2);
        // 方案2 改变0位置的灯开关 即使0位置的灯由1变为了0 依然可以通过改变1 位置的开关影响0 使得0变亮
        int p2 = f2(arr, arr[0] ^ 1, arr[1] ^ 1, 2);
        if (p2 != Integer.MAX_VALUE) {
            p2++;
        }
        return Math.min(p1, p2);
    }


    /**
     * 这种递归的方式 不再需要改变原数组的信息
     * @param arr
     * @param prepre 当前遍历位置的前前灯的状态
     * @param pre    前一个位置的状态，这个是要改变的！
     * @param index  来到的位置，不能在这个位置上做改变！
     * @return
     */
    private static int f2(int[] arr, int prepre, int pre, int index) {
        if (index == arr.length) {
            // 注意pre是最后一盏灯
            //  ... 0        0   ... 1        1
            //  ... prepre pre   ... prepre  pre
            return prepre != pre ? Integer.MAX_VALUE : (pre ^ 1);
        }

        if (prepre == 1) {
            // 前前灯是亮的  踩坑地方 第二个参数不是index
            return f2(arr, pre, arr[index], index + 1);
        } else {
            // 必须调整pre位置的灯使得 prepre的灯保持亮
            // 在后续的递归中 pre状态变为prepre状态  cur状态变为pre状态

            // pre这个灯，一定要变，pre前灯的状态，改变！ 前前 -> 1
            // 前前（有效） pre^1 (cur) (arr[index] ^ 1)
            pre ^= 1;
            int cur = arr[index] ^ 1;
            int next = f2(arr, pre, cur, index + 1);
            return next == Integer.MAX_VALUE ? next : (next + 1);
        }
    }



    /**
     *  无环改灯问题的迭代版本
     *  处理两种情况
     *  1 0位置的灯开关打开
     *  2 0位置的灯开关不改变
     */
    public static int noLoopMinStep2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        // 注意小方法的抽取 提高代码复用
        // 0位置的灯开关不调整
        int p1 = traceNoLoop(arr, arr[0], arr[1]);
        // 0位置的灯的开关调整
        int p2 = traceNoLoop(arr, arr[0] ^ 1, arr[1] ^ 1);
        p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
        return Math.min(p1, p2);
    }

    public static int traceNoLoop(int[] arr, int prepre, int pre) {
        int i = 2;
        int op = 0;
        while (i != arr.length) {
            if (prepre == 0) {
                op++;
                prepre = pre ^ 1;
                pre = arr[i++] ^ 1;
            } else {
                prepre = pre;
                pre = arr[i++];
            }
        }
        return (prepre != pre) ? Integer.MAX_VALUE : (op + (pre ^ 1));
    }



    // *******************************一下是第二问  有环问题的讨论***********************************

    /**
     * 有环问题的处理思路参考无环问题处理思路
     * 任意的中间位置 遍历到 i 位置的灯时   由i-2 位置灯的状态来决定i-1位置的灯是否要进行开启
     * 而对于0位置灯 以及N-1 位置灯有一下集中状态
     *  对于2 3 4 5 ... N-2位置灯 只能由3 4 5 6 ... N-1位置的灯来影响
     *
     *  0 / 1
     *  prepre   pre  cur  当来到cur位置时 在pre位置做决定  prepre位置为1 pre只能是0  prepre位置是0 pre只能是1
     *  因为再往下递归处理下一个位置的灯时 再也不可能影响到prepre位置的灯了
     *  就是说除了 0 和 N-1位置的灯之外 其余位置必须照顾前前位置的灯状态来做决定
     *
     *  0位置的灯状态可以通过N-1位置灯的状态调整过来  1位置有两种决策 开或者关   来到2位置决策时  必须照顾1位置的状态
     *  就是说根据1位置的状态来决定2位置的灯的开关状态 只有一条路径
     *
     *           x  √ x
     *        x  √  x √
     *
     *           x  √ x √ ...
     *        √  √  x √ x ...
     *  N-1   0  1  2 3 4 5
     *
     */



    // 有环改灯问题的暴力版本
    public static int loopRight(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        return f2(arr, 0);
    }

    public static int f2(int[] arr, int i) {
        if (i == arr.length) {
            return valid(arr) ? 0 : Integer.MAX_VALUE;
        }
        // i位置的灯 不做改变
        int p1 = f2(arr, i + 1);
        // 调整i位置的灯开关
        change2(arr, i);
        int p2 = f2(arr, i + 1);

        // 参考f1递归的还原过程恢复现场  深度优先遍历过程
        change2(arr, i);
        p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
        return Math.min(p1, p2);
    }

    /**
     * 老师写的代码 方法确实时精简呀！！！
     */
    public static void change2(int[] arr, int i) {
        // 因为有环存在  首尾位置两侧的灯状态都要改变
        arr[lastIndex(i, arr.length)] ^= 1;
        arr[i] ^= 1;
        arr[nextIndex(i, arr.length)] ^= 1;
    }

    public static int lastIndex(int i, int N) {
        return i == 0 ? (N - 1) : (i - 1);
    }

    public static int nextIndex(int i, int N) {
        return i == N - 1 ? 0 : (i + 1);
    }






    // 有环改灯问题的递归版本
    public static int loopMinStep1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        if (arr.length == 3) {
            return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }

        // 2 位置必须照顾1位置的灯状态 只有一个选择  参见最上面的注释
        // 0不变，1不变
        int p1 = process2(arr, 3, arr[1], arr[2], arr[arr.length - 1], arr[0]);
        // 0改变，1不变
        int p2 = process2(arr, 3, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
        // 0不变，1改变
        int p3 = process2(arr, 3, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
        // 0改变，1改变
        int p4 = process2(arr, 3, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
        p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
        p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
        p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
        return Math.min(Math.min(p1, p2), Math.min(p3, p4));
    }

    // 当我调用process2，一定要保证，传入的index >= 3
    // 当前来到index位置，不在index上做决定
    // prepre，前前灯的状态，
    // pre，前灯的状态，这是要做决定的灯
    // endStatus, N-1号灯的状态   因为0位置的灯会影响N-1位置的灯状态 所以不能直接取数组中的值的作为N-1位置的灯状态
    // firstStatus, 0号灯的状态   同理0位置的灯也会收到
    // 让所有灯都亮，最少要按几次开关
    public static int process2(int[] arr, int index, int prepre, int pre, int endStatus, int firstStatus) {
        if (index == arr.length) {
            // N-1上做决定
            return (endStatus != firstStatus || endStatus != prepre) ? Integer.MAX_VALUE : (endStatus ^ 1);
        }
        // N-2 i
        if (index < arr.length - 1) {
            // 彻底平凡的位置
            if (prepre == 0) {
                // 需要注意 precess2 这个方法会改变arr数组的状态！！！  第4个参数
                int next = process2(arr, index + 1, pre ^ 1, arr[index] ^ 1, endStatus, firstStatus);
                return next == Integer.MAX_VALUE ? next : (next + 1);
            } else {
                return process2(arr, index + 1, pre, arr[index], endStatus, firstStatus);
            }
        } else {
            // 目前在N-2号灯上做决定
            // N-2 (前)   arr[N-1]（来到的灯的状态）？？？endStatus
            if (prepre == 0) {
                // 需要特殊处理的原因是因为  第4个参数不能直接取arr[index] 而应该传入 endStatus ^ 1  因为最开始时 0位置的灯改变状态会影响最后N-1位置的灯
                //    N-2  N-1   0
                //        index
                int next = process2(arr, index + 1, pre ^ 1, endStatus ^ 1, endStatus ^ 1, firstStatus);
                return next == Integer.MAX_VALUE ? next : (next + 1);
            } else {
                return process2(arr, index + 1, pre, endStatus, endStatus, firstStatus);
            }
        }
    }





    // 有环改灯问题的迭代版本
    public static int loopMinStep2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        if (arr.length == 3) {
            return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        // 0不变，1不变
        int p1 = traceLoop(arr, arr[1], arr[2], arr[arr.length - 1], arr[0]);
        // 0改变，1不变
        int p2 = traceLoop(arr, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
        // 0不变，1改变
        int p3 = traceLoop(arr, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
        // 0改变，1改变
        int p4 = traceLoop(arr, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
        p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
        p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
        p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
        return Math.min(Math.min(p1, p2), Math.min(p3, p4));
    }


    public static int traceLoop(int[] arr, int preStatus, int curStatus, int endStatus, int firstStatus) {
        int i = 3;
        int op = 0;
        while (i < arr.length - 1) {
            if (preStatus == 0) {
                op++;
                preStatus = curStatus ^ 1;
                curStatus = (arr[i++] ^ 1);
            } else {
                preStatus = curStatus;
                curStatus = arr[i++];
            }
        }
        if (preStatus == 0) {
            op++;
            preStatus = curStatus ^ 1;
            endStatus ^= 1;
            curStatus = endStatus;
        } else {
            preStatus = curStatus;
            curStatus = endStatus;
        }
        return (endStatus != firstStatus || endStatus != preStatus) ? Integer.MAX_VALUE : (op + (endStatus ^ 1));
    }



    /**
     * for test
     * 生成长度为len的随机数组，值只有0和1两种值
     */
    public static int[] randomArray(int len) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 2);
        }
        return arr;
    }


    /**
     * for test
     */
    public static void main(String[] args) {

//        int[] t ={0,1,0,0,1};
//        int m = noLoopMinStep1(t);
        int[] t1 = {0, 1, 0, 0, 1};
        int m1 = noLoopRight(t1);

        System.out.println("如果没有任何Oops打印，说明所有方法都正确");
        System.out.println("test begin");
        int testTime = 20000;
        int lenMax = 12;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * lenMax);
            int[] arr = randomArray(len);
            int ans1 = noLoopRight(arr);
            int ans2 = noLoopMinStep1(arr);
            int ans3 = noLoopMinStep2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("1 Oops!");
            }
        }
//        for (int i = 0; i < testTime; i++) {
//            int len = (int) (Math.random() * lenMax);
//            int[] arr = randomArray(len);
//            int ans1 = loopRight(arr);
//            int ans2 = loopMinStep1(arr);
//            int ans3 = loopMinStep2(arr);
//            if (ans1 != ans2 || ans1 != ans3) {
//                System.out.println("2 Oops!");
//                System.out.println(ans1);
//                System.out.println(ans2);
//                System.out.println(ans3);
//            }
//        }
//        System.out.println("test end");
//
//        int len = 100000000;
//        System.out.println("性能测试");
//        System.out.println("数组大小：" + len);
//        int[] arr = randomArray(len);
//        long start = 0;
//        long end = 0;
//        start = System.currentTimeMillis();
//        noLoopMinStep2(arr);
//        end = System.currentTimeMillis();
//        System.out.println("noLoopMinStep2 run time: " + (end - start) + "(ms)");
//
//        start = System.currentTimeMillis();
//        loopMinStep2(arr);
//        end = System.currentTimeMillis();
//        System.out.println("loopMinStep2 run time: " + (end - start) + "(ms)");
    }


}

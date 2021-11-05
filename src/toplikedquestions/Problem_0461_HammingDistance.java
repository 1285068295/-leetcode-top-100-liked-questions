package toplikedquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/29
 * @version: V1.0
 * @slogan:
 * @description : 汉明距离
 *
 */
public class Problem_0461_HammingDistance {

    public static int hammingDistance(int x, int y) {

        int count = 0;
        for (int i = 0; i < 32; i++) {
            int xLastOne = ((x & 1) == 1) ? 1 : 0;
            int yLastOne = ((y & 1) == 1) ? 1 : 0;
            if (xLastOne != yLastOne) {
                count++;
            }
            x = x >> 1;
            y = y >> 1;
        }
        return count;
    }


    public static void main(String[] args) {
        System.out.println(hammingDistance(0b0001,0b0100));
    }

}

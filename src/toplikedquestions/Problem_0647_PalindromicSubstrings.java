package toplikedquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/31
 * @version: V1.0
 * @slogan:
 * @description :回文子串
 *
 * 最长回文子串 用manacher算法
 *
 * 【思路一】
 *  利用动态规划  计算任意的子串i ~ j 位置上 是否为回文串
 *
 * 【思路二】
 *  利用manacher算法 计算得到字符串所有的字符为为中心的回文半径
 *  则每个字符为中心时可以形成的回文数量就是回文半径的长度
 *
 *
 *
 */
public class Problem_0647_PalindromicSubstrings {


    /**
     * 思路二
     */
    public int countSubstrings(String s) {
        if (s == null|| "".equals(s)){
            return 0;
        }
        // 添加辅助字符 构建回文串
        char[] str = manachrString(s).toCharArray();

        int N = str.length;
        // i ~ j 位置上字符串是否为回文串
        boolean[][] dp = new boolean[N][N];

        // 写对角线的每个字符一定是回文串
        int ans = N;
        dp[N - 1][N - 1] = true;

        for (int i = 0; i < N - 1; i++) {
            // 斜对角线
            dp[i][i] = true;
            // 紧挨着的斜对角线的第二条线
            dp[i][i + 1] = str[i] == str[i + 1];

            // 统计答案
            ans += dp[i][i + 1] ? 1 : 0;
        }


        // 从斜对角线向上推
        for (int j = 2; j < N; j++) {
            int row = 0;
            int col = j;
            while (row < N && col < N) {
                dp[row][col] = str[row] == str[col] && dp[row + 1][col - 1];
                // 统计答案
                ans += dp[row][col] ? 1 : 0;
                row++;
                col++;
            }
        }
        return ans;
    }

    /**
     * 拼接辅助字符 构建回文字符串
     * 位的是 方便计算长度为奇数字符串的回文串  如 aba
     *
     * aba  ->  #a#b#a#
     */
    private String manachrString(String s) {
        StringBuffer sb = new StringBuffer("#");
        char[] str = s.toCharArray();
        for (char c : str) {
            sb.append(c).append("#");
        }
        return sb.toString();
    }








    /**
     * 思路一
     */
    public int countSubstrings1(String s) {
        char[] str = s.toCharArray();
        int N = str.length;
        // i ~ j 位置上字符串是否为回文串
        boolean[][] dp = new boolean[N][N];

        // 写对角线的每个字符一定是回文串
        int ans = N;
        dp[N - 1][N - 1] = true;

        for (int i = 0; i < N - 1; i++) {
            // 斜对角线
            dp[i][i] = true;
            // 紧挨着的斜对角线的第二条线
            dp[i][i + 1] = str[i] == str[i + 1];

            // 统计答案
            ans += dp[i][i + 1] ? 1 : 0;
        }


        // 从斜对角线向上推
        for (int j = 2; j < N; j++) {
            int row = 0;
            int col = j;
            while (row < N && col < N) {
                dp[row][col] = str[row] == str[col] && dp[row + 1][col - 1];
                // 统计答案
                ans += dp[row][col] ? 1 : 0;
                row++;
                col++;
            }
        }
        return ans;
    }





}

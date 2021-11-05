package toplikedquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/30
 * @version: V1.0
 * @slogan:
 * @description :二叉树的直径  参考 top interview 124
 */
public class Problem_0543_DiameterOfBinaryTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    /**
     * 需要两个信息
     * 1 可以不经过当前点的情况下 最大路径
     * 2 必须经过当前点的情况下 向下扎多深
     */
    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }
    }

    public int diameterOfBinaryTree(TreeNode root) {
        return process(root).maxDistance - 1;
    }

    public Info process(TreeNode node) {
        if (node == null) {
            return new Info(1,1);
        }

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        // 必须从当前点出发的情况下 最大可以向下扎多深
        // 从左右子树的最大扎如深度决策出较大的一个值  + 当前值
        int height = Math.max(lInfo.height, rInfo.height) + 1;

        int maxDistance = lInfo.height + rInfo.height - 1;

        maxDistance = Math.max(maxDistance, Math.max(lInfo.maxDistance, rInfo.maxDistance));

        return new Info(maxDistance, height);

    }

}

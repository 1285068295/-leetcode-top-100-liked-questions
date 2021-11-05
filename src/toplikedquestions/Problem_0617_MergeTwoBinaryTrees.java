package toplikedquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/30
 * @version: V1.0
 * @slogan:
 * @description :合并二叉树
 * 【解题思路】
 *  直接用递归搞
 *
 */
public class Problem_0617_MergeTwoBinaryTrees {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }
        TreeNode node = new TreeNode();

        TreeNode left = mergeTrees(root1 == null ? null : root1.left, root2 == null ? null : root2.left);
        TreeNode right = mergeTrees(root1 == null ? null : root1.right, root2 == null ? null : root2.right);

        node.val += root1 == null ? 0 : root1.val;
        node.val += root2 == null ? 0 : root2.val;

        node.left = left;
        node.right = right;
        return node;
    }


}

package toplikedquestions;

import javax.swing.tree.TreeNode;

/**
 * @author ：Lisp
 * @date： 2021/10/30
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0538ConvertBstToGreaterRree {


    public static class TreeNode {
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

    /** 全局的累加和变量  不要使用static 测试用例连续的测试时 会一直累加！！！ */
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }



    public static void main(String[] args) {


        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(1);
        root.right = new TreeNode(6);

        root.left.left = new TreeNode(0);

        root.left.right = new TreeNode(2);
        root.left.right.right = new TreeNode(3);


        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(8);


//        convertBST(root);

    }



}

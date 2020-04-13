package easy.Tree;

/**
 * @Time : 2020年2月20日15:03:20
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

/**
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 * 示例 :
 * 给定二叉树
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree
 */
public class diameterOfBinaryTree543 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :38.8 MB, 在所有 Java 提交中击败了
     * 题目要求的最长直径，要么是当前结点左右的两条最长路径（到叶子结点）连接在一起，要么就是左右子树中的最大直径
     * 所以要求当前结点的最大直径，得先知道其左右子树的信息。于是需要从底往上递归
     */
    public static int result;
    public int diameterOfBinaryTree(TreeNode root) {
        result = 0;
        depth(root);
        return result;
    }

    /**
     *计算每个结点的深度（到叶子结点最长路径包含的结点数）
     * 在计算过程中 不断地将result变量设置为左右子树种最大直径，然后与当前节点左右最长路径长度和进行比较，更新为当前树的最长路径。
     */
    private int depth(TreeNode x){
        if(x == null) return 0;
        int leftDepth = depth(x.left);
        int rightDepth = depth(x.right);
        // leftDepth - 1：左子树高度-1，即可得到左最长的边的长度，同理rightDepth - 1得到右子树最长的边。分别通过当前根结点相连接，边长+2
        result = Math.max(result, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}

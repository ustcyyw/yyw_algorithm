package easy.Tree;

/**
 * @Time : 2020年2月29日14:18:01
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

/**
 * 给定一个二叉搜索树，同时给定最小边界L 和最大边界 R。通过修剪二叉搜索树，使得所有节点的值在[L, R]中 (R>=L) 。
 * 你可能需要改变树的根节点，所以结果应当返回修剪好的二叉搜索树的新的根节点。
 *
 * 示例 1:
 * 输入:
 *     1
 *    / \
 *   0   2
 *   L = 1
 *   R = 2
 * 输出:
 *     1
 *       \
 *        2
 *
 * 示例 2:
 * 输入:
 *     3
 *    / \
 *   0   4
 *    \
 *     2
 *    /
 *   1
 *   L = 1
 *   R = 3
 * 输出:
 *       3
 *      /
 *    2
 *   /
 *  1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/trim-a-binary-search-tree
 */
public class trimBST {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.5 MB, 在所有 Java 提交中击败了9.09%的用户
     * 返回以当前结点为根的已修剪好的数的根结点（可能根结点变化了）。
     */
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if(root == null) return null;
        if(root.val < L) return trimBST(root.right, L, R);
        if(root.val > R) return trimBST(root.left, L, R);
        root.left = trimBST(root.left, L, R); // 涉及到改变树结构的，肯定要改变链接
        root.right = trimBST(root.right, L, R); // 通过这两个链接更新，当前结点的左右子树都是修剪过的了。
        return root;
    }
}

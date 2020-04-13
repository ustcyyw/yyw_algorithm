package easy.Tree;

/**
 * @Time : 2020年2月29日11:23:53
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

/**
 * 给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为 2 或 0。
 * 如果一个节点有两个子节点的话，那么这个节点的值不大于它的子节点的值。 
 * 给出这样的一个二叉树，你需要输出所有节点中的第二小的值。如果第二小的值不存在的话，输出 -1 。
 *
 * 示例 1:
 * 输入:
 *     2
 *    / \
 *   2   5
 *      / \
 *     5   7
 * 输出: 5
 * 说明: 最小的值是 2 ，第二小的值是 5 。
 *
 * 示例 2:
 * 输入:
 *     2
 *    / \
 *   2   2
 * 输出: -1
 * 说明: 最小的值是 2, 但是不存在第二小的值。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/second-minimum-node-in-a-binary-tree
 */
public class findSecondMinimumValue {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了10%的用户
     */
    public int findSecondMinimumValue(TreeNode root) {
        if(root == null)
            return -1;
        return find(root, root.val);
    }

    /**
     * 按照题目描述，这个二叉树是个最小堆，最上面的元素就是最小的元素。用rootValue表示最小值
     * 找到和rootValue值不相同的最小值，与rootValue不相同的最小值其实就是第二小的值。
     */
    private int find(TreeNode x, int rootValue){
        if(x.val != rootValue) // 如果当前结点不等于根结点至，那么当x值为以x为根的最小的非rootValue的值
            return x.val;
        // 这之下都是 当前结点值为根结点值的情况
        if(x.left == null) // 递归到叶子结点 且其值为根结点值，说明没有找到第二小的值，返回失败标志-1。
            return -1;
        int leftMin = find(x.left, rootValue);
        int rightMin = find(x.right, rootValue);
        if(leftMin == -1)
            return rightMin;
        if(rightMin == -1)
            return leftMin;
        return Math.min(leftMin, rightMin);
    }
}

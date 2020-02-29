package medium.Tree;

/**
 * @Time : 2020年2月29日14:27:55
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。
 * 说明：
 * 你可以假设 k 总是有效的，1 ≤ k ≤ 二叉搜索树元素个数。
 *
 * 示例 1:
 * 输入: root = [3,1,4,null,2], k = 1
 *    3
 *   / \
 *  1   4
 *   \
 *    2
 * 输出: 1
 *
 * 示例 2:
 * 输入: root = [5,3,6,2,4,null,null,1], k = 3
 *        5
 *       / \
 *      3   6
 *     / \
 *    2   4
 *   /
 *  1
 * 输出: 3
 * 进阶：
 * 如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化 kthSmallest 函数？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst
 */
public class kthSmallest {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了77.95%的用户
     * 内存消耗 :41.1 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    private List<Integer> items;
    public int kthSmallest(TreeNode root, int k) {
        items = new ArrayList<>();
        midTraversal(root, k);
        return items.get(k - 1);
    }

    private void midTraversal(TreeNode x, int k){
        if(x == null) return;
        if(items.size() == k) return;
        midTraversal(x.left, k);
        items.add(x.val);
        midTraversal(x.right, k);
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了77.95%的用户
     * 内存消耗 :41.1 MB, 在所有 Java 提交中击败了5.02%的用户
     * 中序遍历的迭代实现
     */
    public int kthSmallest2(TreeNode root, int k) {
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null){
            while(node != null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            count++;
            if(count == k)
                return node.val;
            node = node.right;
        }
        return -1;
    }

    /**
     * 同方法一 中序遍历
     * 其实并不需要一个列表，只要记录遍历到第几个就行，并且记录下该结点值就行。
     *
     *执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.2 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    private int count;
    private int res;
    public int kthSmallest3(TreeNode root, int k) {
        count = 0;
        midTraversal3(root, k);
        return res;
    }

    private void midTraversal3(TreeNode x, int k){
        if(x == null) return;
        if(count == k) return;
        midTraversal(x.left, k);
        count++;
        if(count == k){
            res = x.val;
            return;
        }
        midTraversal(x.right, k);
    }
}

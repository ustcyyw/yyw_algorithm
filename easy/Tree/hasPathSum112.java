package easy.Tree;

/**
 * @Time : 2020年2月21日20:44:39
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;
import javafx.util.Pair;

import java.util.Stack;

/**
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 * 说明: 叶子节点是指没有子节点的节点。
 * 示例: 
 * 给定如下二叉树，以及目标和 sum = 22，
 * 5
 * / \
 * 4   8
 * /   / \
 * 11  13  4
 * /  \      \
 * 7    2      1
 * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum
 */
public class hasPathSum112 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.3 MB, 在所有 Java 提交中击败了8.60%的用户
     */

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        return pathSum(root, sum, 0);
    }

    private boolean pathSum(TreeNode root, int sum, int curSum) {
        curSum += root.val;
        boolean result;
        if (root.left == null && root.right == null) // 没有左右子结点，才是叶子结点，此时才判断路径上值之和与目标sum是否相等
            result = sum == curSum;
        else if (root.left != null && root.right != null) // 左右子结点都有，左右子树中有一条路径添加上目前路径可以得到正确结果即可
            result = pathSum(root.left, sum, curSum) || pathSum(root.right, sum, curSum);
        else if (root.left != null) // 只有左子树，路径继续往左走
            result = pathSum(root.left, sum, curSum);
        else
            result = pathSum(root.right, sum, curSum);
        return result;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了6.90%的用户
     * 内存消耗 :39.2 MB, 在所有 Java 提交中击败了13.39%的用户
     */
    public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null) return false;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, root.val));
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> curPair = stack.pop();
            TreeNode curNode = curPair.getKey();
            int curSum = curPair.getValue();
            if (curNode.left == null && curNode.right == null) {
                if (curSum == sum) return true;
            }
            if (curNode.left != null)
                stack.add(new Pair<>(curNode.left, curSum + curNode.left.val));
            if (curNode.right != null)
                stack.add(new Pair<>(curNode.right, curSum + curNode.right.val));
        }
        return false;
    }

    /**
     * 官方解答 这个递归与方法1不太一样
     */
    public boolean hasPathSum3(TreeNode root, int sum) {
        if (root == null)
            return false;

        sum -= root.val; // 函数内部的sum指的是 剩下的结点还需要加和到多少
        if ((root.left == null) && (root.right == null))
            return sum == 0;
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
    }
}

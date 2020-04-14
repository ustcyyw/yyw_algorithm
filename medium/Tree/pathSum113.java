package medium.Tree;

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年4月14日19:50:11
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22，
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 * 返回:
 *
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum-ii
 */
public class pathSum113 {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.98%的用户
     * 内存消耗 :40 MB, 在所有 Java 提交中击败了5.26%的用户
     */
    List<List<Integer>> res;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        res = new ArrayList<>();
        if(root == null)
            return res;
        backTrack(root, sum, 0, new ArrayList<>());
        return res;
    }

    private void backTrack(TreeNode x, int sum, int curSum, List<Integer> vals){
        vals.add(x.val);
        curSum += x.val;
        if(x.left == null && x.right == null){
            if(curSum == sum){
                res.add(new ArrayList<>(vals));
            }
            vals.remove(vals.size() - 1);
            return;
        }
        if(x.left != null)
            backTrack(x.left, sum, curSum, vals);
        if(x.right != null)
            backTrack(x.right, sum, curSum, vals);
        vals.remove(vals.size() - 1);
    }
}

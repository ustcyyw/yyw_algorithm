package easy.Tree;

/**
 * @Time : 2020年3月21日17:18:21
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 输入:
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 *
 * 输出: ["1->2->5", "1->3"]
 *
 * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-paths
 */
public class binaryTreePaths {
    /**
     * 执行用时 :7 ms, 在所有 Java 提交中击败了44.53%的用户
     * 内存消耗 :38.1 MB, 在所有 Java 提交中击败了5.33%的用户
     */
    private List<String> res;
    public List<String> binaryTreePaths(TreeNode root) {
        res = new ArrayList<>();
        if(root == null) return res;
        traversal(root, new StringBuilder()); // traversal2(root, new ArrayList<>());
        return res;
    }

    private void traversal(TreeNode x, StringBuilder sb){
        if(x.left == null && x.right == null)
            res.add(sb.toString() + x.val);
        else {
            String temp = x.val + "->";
            sb.append(temp);
            if(x.left != null)
                traversal(x.left, sb);
            if(x.right != null)
                traversal(x.right, sb);
            sb.delete(sb.length() - temp.length(), sb.length());
        }
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了73.73%的用户
     * 内存消耗 :38.5 MB, 在所有 Java 提交中击败了5.33%的用户
     */
    private void traversal2(TreeNode x, List<Integer> vals){
        if(x.left == null && x.right == null){
            StringBuilder sb = new StringBuilder();
            for(int i : vals)
                sb.append(i).append("->");
            sb.append(x.val);
            res.add(sb.toString());
        }
        else {
            vals.add(x.val);
            if(x.left != null)
                traversal2(x.left, vals);
            if(x.right != null)
                traversal2(x.right, vals);
            vals.remove(vals.size() - 1);
        }
    }
}

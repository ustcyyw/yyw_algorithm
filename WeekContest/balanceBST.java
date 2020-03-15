package WeekContest;

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年3月15日10:49:37
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给你一棵二叉搜索树，请你返回一棵 平衡后 的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。
 * 如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是 平衡的 。
 * 如果有多种构造方法，请你返回任意一种。
 *
 * 示例：
 * 输入：root = [1,null,2,null,3,null,4,null,null]
 * 输出：[2,1,3,null,null,null,4]
 * 解释：这不是唯一的正确答案，[3,1,4,null,2,null,null] 也是一个可行的构造方案。
 *  
 *
 * 提示：
 * 树节点的数目在 1 到 10^4 之间。
 * 树节点的值互不相同，且在 1 到 10^5 之间。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/balance-a-binary-search-tree
 */
public class balanceBST {
    /**
     * 难度 medium  3ms
     */
    public TreeNode balanceBST(TreeNode root) {
        if(root == null)
            return null;
        List<Integer> vals = new ArrayList<>();
        traversal(root, vals);
        return build(vals, 0, vals.size() - 1);
    }

    private void traversal(TreeNode x, List<Integer> vals){
        if(x == null) return;
        traversal(x.left, vals);
        vals.add(x.val);
        traversal(x.right, vals);
    }

    private TreeNode build(List<Integer> vals, int lo, int hi){
        if(lo > hi) return null;
        if(lo == hi) return new TreeNode(vals.get(hi));
        int mid = (hi - lo) / 2 + lo;
        TreeNode x = new TreeNode(vals.get(mid));
        x.left = build(vals, lo, mid - 1);
        x.right = build(vals, mid + 1, hi);
        return x;
    }
}

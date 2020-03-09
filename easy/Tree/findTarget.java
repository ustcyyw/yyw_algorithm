package easy.Tree;

/**
 * @Time : 2020年3月1日19:37:15
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 *
 * 案例 1:
 * 输入:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 * Target = 9
 * 输出: True
 *  
 * 案例 2:
 * 输入:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 * Target = 28
 * 输出: False
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst
 */
public class findTarget {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了93.15%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了51.69%的用户
     */
    private List<Integer> nums;
    public boolean findTarget(TreeNode root, int k) {
        nums = new ArrayList<>();
        midTraversal(root);
        int i = 0, j = nums.size() - 1;
        while(i < j){
            int sum = nums.get(i) + nums.get(j);
            if(sum < k) i++;
            else if(sum > k) j--;
            else return true;
        }
        return false;
    }

    private void midTraversal(TreeNode x){
        if(x == null)
            return;
        midTraversal(x.left);
        nums.add(x.val);
        midTraversal(x.right);
    }
}

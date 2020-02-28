package easy.Tree;

import BaseClass.TreeNode;

import java.util.Stack;

/**
 * @Time : 2020年2月20日16:26:14
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
 * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，
 * 否则不为 NULL 的节点将直接作为新二叉树的节点。
 * 示例 1:
 * 输入:
 * 	Tree 1                     Tree 2
 *           1                         2
 *          / \                       / \
 *         3   2                     1   3
 *        /                           \   \
 *       5                             4   7
 * 输出:
 * 合并后的树:
 * 	     3
 * 	    / \
 * 	   4   5
 * 	  / \   \
 * 	 5   4   7
 * 注意: 合并必须从两个树的根节点开始。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-binary-trees。
 */
public class mergeTrees {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.1 MB, 在所有 Java 提交中击败了11.18%的用户
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return null;
        if(t1 == null) // 其中一个结点为null 直接返回另外一个结点即可完成合并
            return t2;
        if(t2 == null)
            return t1;
        t1.left = mergeTrees(t1.left, t2.left); // 因为会涉及到t1.left为null的情况，这时递归调用会返回t2.left，这个时候主树需要更新结点的链接
        t1.right = mergeTrees(t1.right, t2.right);
        t1.val = t1.val + t2.val;
        return t1;
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了9.69%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了11.01%的用户
     */
    public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
        if(t1 == null) return t2;
        if(t2 == null) return t1;
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(t1);
        s2.push(t2);
        while(!s1.empty()){ // s1 s2出入栈是同步的 所以条件写一个就行
            TreeNode cur1 = s1.pop(); // 栈顶元素即为当前要合并的
            TreeNode cur2 = s2.pop();
            if(cur1.left != null && cur2.left != null){ // 当前节点的左子结点 有四种情况，但是均为null的不用处理。
                s1.push(cur1.left);
                s2.push(cur2.left);
            } else if(cur1.left == null) { // cur1.left == null 的两种情况处理方式使用这行代码均正确。
                cur1.left = cur2.left;
            }
            if(cur1.right != null && cur2.right != null){
                s1.push(cur1.right);
                s2.push(cur2.right);
            } else if(cur1.right == null){
                cur1.right = cur2.right;
            }
            cur1.val = cur1.val + cur2.val;
        }
        return t1;
    }
}

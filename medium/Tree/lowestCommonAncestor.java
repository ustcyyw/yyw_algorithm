package medium.Tree;

import BaseClass.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2020年2月29日16:44:37
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大
 * （一个节点也可以是它自己的祖先）。”
 *
 * 例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]
 * 示例 1:
 * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * 输出: 3
 * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
 *
 * 示例 2:
 * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * 输出: 5
 * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
 *
 * 说明:
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉树中。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree
 */
public class lowestCommonAncestor {
    /**
     * 执行用时 :8 ms, 在所有 Java 提交中击败了99.70%的用户
     * 内存消耗 :42 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    private TreeNode res;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        res = null;
        contains(root, p, q);
        return res;
    }

    /**
     * 返回值为当前结点为根的树种是否含有p，q中的一个结点
     * leftCon 左子树是否含有其中一个结点；rightCon 右子树是否含有其中一个结点
     * 条件1234的意义为当前结点是p，q的其中之一，其子树之一包含另外一个结点。那么本结点也是公共祖先
     * 条件5的意义是 左右子树分别含有q，p其中之一。此时当然当前的树包含了二者之一，返回true，并且已经找到最下方的公共祖先了。
     */
    private boolean contains(TreeNode x, TreeNode p, TreeNode q){
        if(x == null) return false;
        boolean leftCon = contains(x.left, p, q);
        boolean rightCon = contains(x.right, p, q);
        if((x == p && rightCon) || (x == q && leftCon) || (x == p && leftCon)|| (x == q && rightCon) ||(leftCon && rightCon)) {
            res = x;
            return true;
        }
        return x == p || x == q || leftCon || rightCon;
    }

    /**
     * 执行用时 :11 ms, 在所有 Java 提交中击败了35.86%的用户
     * 内存消耗 :42.3 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    Map<TreeNode, Integer> map;

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        map = new HashMap<>();
        return helper(root, p, q);
    }

    private TreeNode helper(TreeNode root, TreeNode p, TreeNode q){
        if (contains(root.left, p.val, q.val) == 2)
            return helper(root.left, p, q);
        else if (contains(root.right, p.val, q.val) == 2)
            return helper(root.right, p, q);
        return root;
    }

    // -1表示都没找到, 0表示找到p，1表示找到q，2表示都找到
    private int contains(TreeNode x, int p, int q) {
        if (x == null) return -1;
        if (map.containsKey(x))
            return map.get(x);
        int temp;
        if (x.val == p) {
            if (contains(x.left, p, q) == 1 || contains(x.right, p, q) == 1)
                temp = 2;
            else temp = 0;
        } else if (x.val == q) {
            if (contains(x.left, p, q) == 0 || contains(x.right, p, q) == 0)
                temp = 2;
            else temp = 1;
        } else {
            int left = contains(x.left, p, q), right = contains(x.right, p, q);
            if (left == 2 || right == 2 || (left == 0 && right == 1) || (left == 1 && right == 0))
                temp = 2;
            else if (left == 0 || right == 0)
                temp = 0;
            else if (left == 1 || right == 1)
                temp = 1;
            else temp = -1;
        }
        map.put(x, temp);
        return temp;
    }
}

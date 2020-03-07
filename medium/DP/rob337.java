package medium.DP;

import BaseClass.TreeNode;

/**
 * @Time : 2020年2月27日10:48:53
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。
 * 这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。
 * 一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 *
 * 示例 1:
 * 输入: [3,2,3,null,3,null,1]
 *      3
 *     / \
 *    2   3
 *     \   \
 *      3   1
 * 输出: 7
 * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
 *
 * 示例 2:
 * 输入: [3,4,5,1,3,null,1]
 *      3
 *     / \
 *    4   5
 *   / \   \
 *  1   3   1
 * 输出: 9
 * 解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/house-robber-iii
 */
public class rob337 {
    /**
     * 执行用时 :521 ms, 在所有 Java 提交中击败了36.24%的用户
     * 内存消耗 :38.9 MB, 在所有 Java 提交中击败了40.19%的用户
     * 有重复计算的递归
     */
    public int rob(TreeNode root) {
        if(root == null) return 0;
        if(root.left == null && root.right == null) return root.val;
        if(root.left == null){
            return Math.max(rob(root.right), root.val + rob(root.right.left) + rob(root.right.right));
        }
        if(root.right == null){
            return Math.max(rob(root.left), root.val + rob(root.left.left) + rob(root.left.right));
        }
        return Math.max(rob(root.left) + rob(root.right),
                root.val + rob(root.left.left) + rob(root.left.right) + rob(root.right.left) + rob(root.right.right));
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.72%的用户
     * 内存消耗 :38.8 MB, 在所有 Java 提交中击败了42.99%的用户
     * 带记忆搜索的递归 相当于DP。val为负的结点，其值代表的是从根结点到此结点能偷的最多的钱
     * 考虑要不要偷第i当前结点层时：
     *      如果选择偷，那么其可以偷到的钱为i层当前结点的值及i+2层的所有子节点能偷到的最多的钱
     *      如果选择不偷，那么其偷到的钱为i+1层它两个子节点能偷的钱之和。
     *      要得到偷的最多的钱，当然二者取最大。
     * 本题主要是边界条件要处理好，空结点的问题要考虑完备。
     */
    public int rob2(TreeNode root){
        if(root == null) return 0;
        if(root.val < 0) return -root.val;
        if(root.left == null && root.right == null) {
            root.val = -root.val;
            return -root.val;
        }
        int tempVal;
        if(root.left == null || root.right == null){
            TreeNode nextLayer = root.left == null ? root.right : root.left;
            tempVal = Math.max(rob(nextLayer), root.val + rob(nextLayer.left) + rob(nextLayer.right));
        }
        else {
            tempVal = Math.max(rob(root.left) + rob(root.right),
                    root.val + rob(root.left.left) + rob(root.left.right) + rob(root.right.left) + rob(root.right.right));
        }
        root.val = -tempVal;
        return tempVal;
    }
}

package easy.Tree;

/**
 * @Time : 2020年2月21日21:58:25
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import BaseClass.TreeNode;

/**
 * 给定一个二叉树，它的每个结点都存放着一个整数值。
 * 找出路径和等于给定数值的路径总数。
 * 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * 二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。
 * <p>
 * 示例：
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 * 10
 * /  \
 * 5   -3
 * / \    \
 * 3   2   11
 * / \   \
 * 3  -2   1
 * <p>
 * 返回 3。和等于 8 的路径有:
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3.  -3 -> 11
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum-iii
 */
public class pathSum437 {
    /**
     * 评论区大佬的解法
     * 3~4秒就解决了
     */
    private int count;
    public int pathSum(TreeNode root, int sum) {
        count = 0;
        backTrack(root, sum, new int[1000], 0);
        return count;
    }

    public void backTrack(TreeNode x, int sum, int[] pathItem, int curIndex){
        if(x == null) return;

        if(x.val == sum) count++;
        for(int i = curIndex - 1, temp = x.val; i >= 0; i--){
            temp += pathItem[i];
            if(temp == sum)
                count++;
        }

        pathItem[curIndex] = x.val;
        backTrack(x.left, sum, pathItem, curIndex + 1);
        backTrack(x.right, sum, pathItem, curIndex + 1);
    }
}

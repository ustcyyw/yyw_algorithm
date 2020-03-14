package medium.Tree;

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年3月4日12:17:28
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */
public class generateTrees95 {
    /**
     * 官方标答：使用递归，选定i为根，则其左子树在[1,2,...i - 1]生成，右子树在[i+1,i+2,...n]生成
     * 以i为根一共能生产的BST 为其左子树可能的数量乘右子树可能的数量。
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.4 MB, 在所有 Java 提交中击败了5.07%的用户
     */
    public List<TreeNode> generateTrees(int n) {
        if(n == 0)
            return new ArrayList<>();
        return sonTrees(1, n);
    }

    private List<TreeNode> sonTrees(int lo, int hi) { // [lo,hi]范围内所有的BST
        List<TreeNode> res = new ArrayList<>();
        if(lo > hi)
            res.add(null);
        else if(lo == hi)
            res.add(new TreeNode(lo));
        else {
            // 当前区间内，所有能生产的BST，每一个值都可能为根，然后要考虑其左边区间所有子树与右边区间所有子树的可能
            for(int i = lo; i <= hi; i++){
                List<TreeNode> leftSon = sonTrees(lo, i - 1);
                List<TreeNode> rightSon = sonTrees(i + 1, hi);
                for(TreeNode left : leftSon){
                    for(TreeNode right : rightSon){
                        TreeNode curRoot = new TreeNode(i);
                        curRoot.left = left;
                        curRoot.right = right;
                        res.add(curRoot);
                    }
                }
            }
        }
        return res;
    }
}

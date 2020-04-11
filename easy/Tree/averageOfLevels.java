package easy.Tree;

/**
 * @Time : 2020年2月29日12:21:03
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;
import javafx.util.Pair;

import java.util.*;

/**
 * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组.
 *
 * 示例 1:
 * 输入:
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 输出: [3, 14.5, 11]
 * 解释:
 * 第0层的平均值是 3,  第1层是 14.5, 第2层是 11. 因此返回 [3, 14.5, 11].
 * 注意：
 * 节点值的范围在32位有符号整数范围内。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/average-of-levels-in-binary-tree
 */
public class averageOfLevels {
    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了11.23%的用户
     * 内存消耗 :42.4 MB, 在所有 Java 提交中击败了26.78%的用户
     * bfs能保证按层遍历。
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root, 0));
        int curLayer = 0;
        long sum = 0;
        int count = 0;
        while (!queue.isEmpty()){
            Pair<TreeNode, Integer> pair = queue.remove();
            TreeNode node = pair.getKey();
            if(pair.getValue() == curLayer){
                count++;
                sum += node.val;
            }
            else{
                res.add(sum * 1.0 / count);
                curLayer++;
                sum = node.val;
                count = 1;
            }
            if(node.left != null)
                queue.add(new Pair<>(node.left, curLayer + 1));
            if(node.right != null)
                queue.add(new Pair<>(node.right, curLayer + 1));
        }
        res.add(sum * 1.0 / count);
        return res;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了85.00%的用户
     * 内存消耗 :42.7 MB, 在所有 Java 提交中击败了22.01%的用户
     */
    private List<Long> sum;
    private List<Integer> count;
    public List<Double> averageOfLevels2(TreeNode root) {
        sum = new ArrayList<>();
        count = new ArrayList<>();
        List<Double> res = new ArrayList<>();
        preTraversal(root, 0);
        int n = sum.size();
        for(int i = 0; i < n; i++)
            res.add(sum.get(i) * 1.0 / count.get(i));
        return res;
    }

    public void preTraversal(TreeNode x, int layer){
        if(x == null) return;
        if(layer < sum.size()){ // 由于使用的是前序遍历，所以可以保证每层的第一个数据是依次进入列表的
            sum.set(layer, sum.get(layer) + x.val);
            count.set(layer, count.get(layer) + 1);
        }
        else{
            sum.add((long)x.val);
            count.add(1);
        }
        preTraversal(x.left, layer + 1); // 这里先遍历左子树还是右子树无所谓
        preTraversal(x.right, layer + 1);
    }
}

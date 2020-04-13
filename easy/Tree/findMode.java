package easy.Tree;

/**
 * @Time : 2020年3月2日12:56:10
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.*;

/**
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 * 假定 BST 有如下定义：
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 * 例如：
 * 给定 BST [1,null,2,2],
 * 1
 * \
 * 2
 * /
 * 2
 * 返回[2].
 * <p>
 * 提示：如果众数超过1个，不需考虑输出顺序
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-mode-in-binary-search-tree
 */
public class findMode {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.25%的用户
     */
    private List<Integer> items;
    private int maxCount;
    private int curCount;
    private TreeNode pre;

    public int[] findMode(TreeNode root) {
        if (root == null)
            return new int[0];
        items = new ArrayList<>();
        // 这里设置为1是因为 在递归中 BST中最左边的结点被跳过了，作为初状态 该结点值出现一次，记录的出现最多次数一开始也为1
        maxCount = 1;
        curCount = 1;
        midTraversal(root);
        // 最右端结点的处理，从递归中看，最后一个结点与前一个结点相等只更新了curCount的值；不相等则只考虑到倒数第二个结点。
        if(curCount > maxCount)
            return new int[]{pre.val};
        if(curCount == maxCount)
            items.add(pre.val);
        int[] res = new int[items.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = items.get(i);
        return res;
    }

    private void midTraversal(TreeNode x) {
        if (x == null) return;
        midTraversal(x.left);
        if(pre != null){
            if(x.val != pre.val){ // 说明上一个值的结点数量已经统计完成 要看出现次数的情况
                if(curCount > maxCount){ // 出现次数更多，清空之前的出现少的数，更新最大出现次数
                    maxCount = curCount;
                    items.clear();
                    items.add(pre.val);
                }
                else if(curCount == maxCount){ // 不止一个众数
                    items.add(pre.val);
                }
                curCount = 1; // 当前值与上一个结点值不同，重置计数
            }
            else curCount++; // 当前值与上一个结点值相同，当前值的出现次数增加。
        }
        pre = x;
        midTraversal(x.right);
    }

    private Map<Integer, Integer> map;
    private int count, max;
    public int[] findMode2(TreeNode root) {
        if (root == null)
            return new int[0];
        map = new HashMap<>();
        count =  0;
        max = 0;
        midTraversal2(root);
        int[] res = new int[count];
        for(int key : map.keySet()){
            if(map.get(key) == max)
                res[--count] = key;
        }
        return res;
    }

    private void midTraversal2(TreeNode x){
        if(x == null)
            return;
        midTraversal2(x.left);
        int temp = map.getOrDefault(x.val, 0) + 1;
        map.put(x.val, temp);
        if(temp > max){
            max = temp;
            count = 1;
        } else if(temp == max){
            count++;
        }
        midTraversal2(x.right);
    }
}

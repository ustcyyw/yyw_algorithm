# 113. 路径总和 II

### 原题
给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
说明: 叶子节点是指没有子节点的节点。

示例:
给定如下二叉树，以及目标和 sum = 22，

```
          	  5
             / \
            4   8
           /   / \
          11  13  4
         /  \    / \
        7    2  5   1
```

返回:

[
   [5,4,11,2],
   [5,8,4,5]
]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/path-sum-ii)：https://leetcode-cn.com/problems/path-sum-ii

### 解法：回溯

```java
List<List<Integer>> res;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        res = new ArrayList<>();
        if(root == null)
            return res;
        backTrack(root, sum, 0, new ArrayList<>());
        return res;
    }

    private void backTrack(TreeNode x, int sum, int curSum, List<Integer> vals){
        vals.add(x.val);
        curSum += x.val;
        if(x.left == null && x.right == null){
            if(curSum == sum){
                res.add(new ArrayList<>(vals));
            }
            vals.remove(vals.size() - 1);
            return;
        }
        if(x.left != null)
            backTrack(x.left, sum, curSum, vals);
        if(x.right != null)
            backTrack(x.right, sum, curSum, vals);
        vals.remove(vals.size() - 1);
    }
```

思路分析：

* 首先可以看看 [112.路径总和 I](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/Tree/hasPathSum112.md)。这个题很类似，题目中没有规定结点值为正，所以在从根走到叶子结点的过程中，如果路径和已经大于指定的`sum`，也要继续往下走，因为后续的结点值可能为负。本题要求所有从根到叶子结点路径和为`sum`的路径，所以需要进行dfs遍历每一条到叶子结点的路径。
* 要求每一条满足条件的路径，需要记录路径上的所有值，这显然就类似于求排列组合那样，需要进行回溯。所以考虑如何定义递归函数，关于路径和的判断参考前面112题链接的题解。递归函数定义为`void backTrack(TreeNode x, int sum, int curSum, List<Integer> vals)`
    * 参数`sum`即为题目要求的路径和。参数`curSum`表示到达当前结点的父节点的路径和，所以`curSum += root.val;`就是从根结点到当前结点的路径和。最后一个参数List用于存放路径上各个结点的值。
    * 首先当前结点值放入`vals`中。然后根据参数的定义，需要更新``curSum += root.val`。
    * 如果当前结点是叶子结点，进行判断`if(curSum == sum)`，如果成立，则这是一个符合条件的路径，要将其放入结果集`res.add(new ArrayList<>(vals));`，一定要new一个对象。然后返回之前要进行回溯，将当前结点踢出`vals`，`vals.remove(vals.size() - 1);`
    * 接下来如果左右子树存在，分别进行递归。
    * 最后别忘了还得回溯`vals.remove(vals.size() - 1);`
* 主函数需要判断根结点是否为`null`，如果是返回空的List即可。如果不是就调用`backTrack(root, sum, 0, new ArrayList<>());`第三个参数为0，因为此时还没有经过任何结点，路径和为0。
* 时间复杂度为$O(n)$，因为遍历了所有结果。除去函数调用栈的开销，空间复杂度就正比于满足条件的路径数和路径上的结点数量和。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了99.98%的用户
* 内存消耗 :40 MB, 在所有 Java 提交中击败了5.26%的用户
# 104.二叉树的最大深度

### 原题
给定一个二叉树，找出其最大深度。
二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
说明: 叶子节点是指没有子节点的节点。
示例：
给定二叉树 [3,9,20,null,null,15,7]，
    3
   / \
  9  20
      /  \
    15   7
返回它的最大深度 3 。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree)：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree

----

### 两种解法

##### 1.递归（我的解法）

```java
public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
```

思路分析：

* 一棵树的最大深度，等于其左右子树二者中的最大深度加上根结点带来的深度1。递归的关系。
* 当一个结点为`null`时，其深度为0。这就是递归的结束条件。
* 遍历了树中所有节点，所以时间复杂度为$O(n)$，函数调用栈与树高成正比，在最糟糕的情况下，树是完全不平衡的，例如每个结点只剩下左子结点，保持调用栈的存储将是 $O(n)$。但在最好的情况下（树是完全平衡的），空间复杂度将是$O(log(n))$。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39 MB, 在所有 Java 提交中击败了12.47%的用户

##### 2.迭代（官方解法）

```java
public int maxDepth2(TreeNode root) {
        if(root == null) return 0;
        int result = 0;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 1));
        while(!stack.empty()){
            Pair<TreeNode, Integer> curPair = stack.pop();
            TreeNode curNode = curPair.getKey();
            int curDepth = curPair.getValue();
            if(curNode != null){
                result = Math.max(result, curDepth);
                stack.push(new Pair<>(curNode.left, curDepth + 1)); // 从添加顺序看，是先走右子树到底部
                stack.push(new Pair<>(curNode.right, curDepth + 1));
            }
        }
        return result;
    }
```

思路分析：

* 可以将递归转换为等价的迭代。这是一个深度优先的搜索，因为我们要计算的是深度，所以需要记录每个结点到达根结点的路径上的节点数（对于根节点，就是1）。使用java内置的`Pair<TreeNode, Integer>`。
* 其余安装DFS的标准写法即可，维持一个栈，每次从栈中取出一个元素，将元素中保持的结点取出，如果非空就更新` result = Math.max(result, curDepth);`。然后将左右子节点与子节点到达根的路径上的节点数（`curDepth + 1`）放入栈中。
* 如果根结点为`null`，但是还是将`curDepth + 1`一起放入栈中，这不会影响结果，因为判断为`null`的结点不会更新`result`。
* 时间复杂度为$O(n)$,空间负责度，取决于栈中元素。


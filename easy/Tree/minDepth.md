# 111.二叉树的最小深度

### 原题
给定一个二叉树，找出其最小深度。
最小深度是从**根节点到最近叶子节点**的最短路径上的节点数量。
说明: 叶子节点是指没有子节点的节点。

示例:
给定二叉树 [3,9,20,null,null,15,7],

```
	3
   / \
  9  20
    /  \
   15   7
```

返回它的最小深度  2.

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/minimum-depth-of-binary-tree)：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree

### 两种解法

##### 1.递归（我的第一解）

```java
public int minDepth(TreeNode root) {
        if (root == null) return 0;
        return min(root);
    }

    private int min(TreeNode root){
        if(root.left == null && root.right == null)
            return 1;
        if(root.left  == null)
            return min(root.right) + 1;
        if(root.right == null)
            return min(root.left) + 1;
        return Math.min(min(root.left), min(root.right)) + 1;
    }
```

思路分析：

* 首先处理根结点是`null`这种特殊情况，深度为0。另外根据定义，叶子结点的深度为1。
* 注意深度的定义，如果认为一棵树的最小深度是其左子树的最小深度与右子树最小深度中较小者就会出现错误。因为一个根结点他可能没有左子结点，这时无法定义其左子树的最小深度（为0？显然也不是🤐）。
* 所以递归结构要好好思考下：
    * 如果当前结点是叶子结点，则其深度依据定义为1,`if(root.left == null && root.right == null) return 1;`
    * 当前结点没有左子结点，则其最小深度为右子树最小深度+1。
    * 当前结点没有右子结点，则其最小深度为左子树最小深度+1。
    * 当前结点左右子结点双全，则其最小深度是其左子树的最小深度与右子树最小深度中较小者。
* 时间复杂度，由于每个结点只访问了一次，所以$O(n)$。空间复杂度，就是函数调用栈的空间，其$O(h)$,h为树的高度。对于平衡二叉树为logn，完全不平衡二叉树则是n。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.6 MB, 在所有 Java 提交中击败了5.06%的用户

##### 2.迭代（我的第二解）

```java
public int minDepth2(TreeNode root) {
        if(root == null) return 0;
        int result = Integer.MAX_VALUE;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.add(new Pair<>(root, 1));
        while(!stack.isEmpty()){
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int depth = pair.getValue();
            if(node.left != null)
                stack.push(new Pair<>(node.left, depth + 1));
            if(node.right != null)
                stack.push(new Pair<>(node.right, depth + 1));
            if(node.left == null && node.right == null)
                result = Math.min(result, depth);
        }
        return result;
    }
```

思路分析：

* 迭代用自己的栈来替代函数调用栈，但是当前结点的深度需要一层层向下传递，所以使用`Pair<TreeNode, Integer>`。
* 遍历结点的过程就是DFS，模板写法。最坏的情况，栈中的元素个数为N/2，所以空间复杂度$O(N)$，时间复杂度同方法1，遍历了一次所有结点$O(N)$。

代码解释：

* 第3行，初始化`int result = Integer.MAX_VALUE;`方便在第14行，满足叶子结点条件时更新结果为这个叶子结点的深度与前面记录的最小深度中更小的一个。

运行结果：
* 执行用时 :5 ms, 在所有 Java 提交中击败了33.05%的用户
* 内存消耗 :40.9 MB, 在所有 Java 提交中击败了5.06%的用户
# 538. 把二叉搜索树转换为累加树
### 原题
给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。

例如：
输入: 二叉搜索树:
              5
            /   \
           2     13
输出: 转换为累加树:
             18
            /   \
          20     13

来源：力扣（LeetCode）
[链接]((https://leetcode-cn.com/problems/convert-bst-to-greater-tree/)
)：https://leetcode-cn.com/problems/convert-bst-to-greater-tree

### 两种解法

##### 1.递归 反方向的中序遍历

```java
	private int sum;
    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        convert(root);
        return root;
    }
    private void convert(TreeNode x){
        if(x == null) return;
        convert(x.right);
        sum = sum + x.val;
        x.val = sum;
        convert(x.left);
    }
```

思路分析：

* 在这棵二叉搜索树中，要将每个结点的值加上比它大的所有元素的和。那么树中最小结点（也就是最左边路径的叶子结点）就要加上除它以外的所有结点的和，所以要得到改变后它的值，得先遍历其它结点加和。
* 树中最大结点则不需要改变。树中倒数第二大的结点需要先知道最大元素的值。我们得先知道大的结点之和才能改变小的结点的值。
* 对于二叉搜索树，正常的[中序遍历](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/inorderTraversal.md)得到的升序排列的结点集合。不过我们稍微"颠倒"一下中序遍历的顺序不就好了吗？改成先遍历右子树，再查看根结点，最后遍历左子树，这样就是降序排列了。
* 在搜索过程中不断加和，就可以得到比当前结点值大的结点值之和，然后就可以完成更新了。
* 遍历了N个结点，所以显然时间复杂度为$O(N)$。空间复杂度，就是函数调用栈的空间，其$O(h)$,h为树的高度。对于平衡二叉树为logn，完全不平衡二叉树则是n。

代码解释：

* 10-11行，中序遍历中查看当前结点时，加和即得到题目所求，再更新到当前结点值。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了99.68%的用户
* 内存消耗 :40.4 MB, 在所有 Java 提交中击败了47.51%的用户

##### 2.迭代 反方向的中序遍历

```java
	public TreeNode convertBST2(TreeNode root) {
        int sum = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null){
            while (node != null){
                stack.push(node);
                node = node.right;
            }
            node = stack.pop();
            sum = node.val + sum;
            node.val = sum;
            node = node.left;
        }
        return root;
    }
```

思路分析：

* 方法一的迭代版本。程序结构与正常中序遍历很类似，唯一不同的是我们要先遍历右子树。所以：
    * 内循环中，是不断将右结点放入栈中`stack.push(node);node = node.right;`。
    * 当某结点出栈后，下一次要将其左子结点为根的树最右边路径入栈`node = node.left;`

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了16.67%的用户
* 内存消耗 :41 MB, 在所有 Java 提交中击败了36.50%的用户
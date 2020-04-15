# 110. 平衡二叉树

### 原题
给定一个二叉树，判断它是否是高度平衡的二叉树。
本题中，一棵高度平衡二叉树定义为：
一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。

示例 1:
给定二叉树 [3,9,20,null,null,15,7]
```
	3
   / \
  9  20
    /  \
   15   7
```
返回 true 。

示例 2:
给定二叉树 [1,2,2,3,3,null,null,4,4]
```
  	   1
 	  / \
	 2   2
	/ \
   3   3
  / \
 4   4
```
返回 false 。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/balanced-binary-tree)：https://leetcode-cn.com/problems/balanced-binary-tree

### 三种方法

##### １.计算每棵树的左右子树高度（两层递归）

```java
public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (Math.abs(height(root.left) - height(root.right)) < 2) {
            return isBalanced(root.left) && isBalanced(root.right);
        } else return false;
    }

    private int height(TreeNode x) {
        if (x == null) return 0;
        return Math.max(height(x.left), height(x.right)) + 1;
    }
```

思路分析：

* 要判断树是否平衡，就需要保证这棵树的每一个子树都是高度平衡的。
    * 如果某一个结点的左右子树高度差大于等于2，那么以这个结点为根的树肯定高度不平衡；
    * 如果一个结点的左右子树高度小于2`Math.abs(height(root.left) - height(root.right)) < 2`，就需要保证其左右子树都是高度平衡的才能断定这个结点为根的树高度平衡，`isBalanced(root.left)`为真且`isBalanced(root.right)`为真才能断定树高度平衡。
* 上述思路需要使用辅助函数`int height(TreeNode x)`来计算树高：
    * 如果`x == null`不存在树，高度为0
    * 否则以`x`为根的树高按定义等于左右子树中更高的那棵的高度+1`return Math.max(height(x.left), height(x.right)) + 1;`
* 可以看到，对每个节点进行遍历的时候，还需要进行一次递归得到该结点的左右子树，这样的做法其实是两层递归。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了17.82%的用户
* 内存消耗 :41.9 MB, 在所有 Java 提交中击败了5.01%的用户

##### 2.迭代计算每棵树的左右子树高度

```java
public boolean isBalanced2(TreeNode root) {
        if (root == null) return true;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode curNode = stack.pop();
            if (curNode != null) {
                TreeNode left = curNode.left;
                TreeNode right = curNode.right;
                if (Math.abs(height(left) - height(right)) > 1)
                    return false;
                stack.push(left);
                stack.push(right);
            }
        }
        return true;
    }
private int height(TreeNode x) {
        if (x == null) return 0;
        return Math.max(height(x.left), height(x.right)) + 1;
    }
```

思路分析：

* 方法二总体思路与方法1一致，都是要遍历每个结点保证每个结点的左右子树高度差不超过1。所以用栈进行dfs或者用队列进行bfs都是可行的。
* 计算某个结点为根的树高的辅助函数同方法1。
* 这里代码使用了栈来迭代模拟方法一的递归。对于栈中弹出的某个结点，如果其非空：
    * 计算其左右子树的高度，如果高度差大于1，那么可以肯定这棵树不平衡，直接返回`false`。
    * 然后将其左右子结点放入栈中。
* 同方法1，这里本质也是两层递归。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了17.82%的用户
* 内存消耗 :41.6 MB, 在所有 Java 提交中击败了6.35%的用户

##### 3.计算树高时用-1标识树的平衡性

```java
public boolean isBalanced3(TreeNode root) {
        return depth(root) != -1;
    }
    private int depth(TreeNode x) {
        if (x == null) return 0;
        int leftDepth = depth(x.left);
        if (leftDepth == -1) return -1; // 左子树深度为-1 说明左子树不平衡，当前子树也不平衡，返回标识-1
        int rightDepth = depth(x.right);
        if (rightDepth == -1) return -1;
        return Math.abs(leftDepth - rightDepth) < 2 ? Math.max(leftDepth, rightDepth) + 1 : -1;
    }
```

思路分析：

* 改进方法1与2中的两层递归，在计算树高的递归过程中，都是需要先计算左右子树的高度，这个时候就可以判断某个树是否高度平衡，如果不平衡，可以将这个信息返回。
* 定义`int depth(TreeNode x)`的返回值：如果以x为根的树平衡，则返回该树的高度，否则返回-1表示树不平衡。
    * 如果`x == null`不存在树，高度为0
    * 调用`int leftDepth = depth(x.left);`，如果这个调用返回值为-1，说明其左子树不平衡，那么以x为根的树也不平衡，直接返回-1。对右子树的处理同样。
    * 如果左右子树都平衡，判断左右子树的高度差是否小于2，
        * 如果是返回` Math.max(leftDepth, rightDepth) + 1`
        * 否则这个树还是不平衡，返回-1。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了99.84%的用户

* 内存消耗 :39.7 MB, 在所有 Java 提交中击败了23.53%的用户
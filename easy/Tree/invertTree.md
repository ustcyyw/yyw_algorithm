# 226. 翻转二叉树

### 原题
翻转一棵二叉树。

示例：
输入：

```
	 4
   /   \
  2     7
 / \   / \
1   3 6   9
```

输出：

```
	 4
   /   \
  7     2
 / \   / \
9   6 3   1
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/invert-binary-tree)：https://leetcode-cn.com/problems/invert-binary-tree

### 两种解法

##### 1.递归

```java
public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.right = left;
        root.left = right;
        return root;
    }
```

思路分析：

* 首先观察题目给的示例，翻转一棵树就是将其左右子结点交换位置，且对其左右子树也进行相同的操作，自上而下这样操作。如果自下而上就是，某个结点的左右子树分别完成翻转，然后再将左右子树交换，这就是一个很简单的递归。
* 递归函数就按照上述逻辑：
    * 递归结束的条件为`root == null`
    * 如果当前结点不为`null`，那么先将其左右子树进行翻转，然后交换左右子树。
    * 返回值为完成了翻转后的当前结点。
* 时间复杂度为$O(n)$，因为遍历了每一个结点。空间复杂度为树高成正比。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.3 MB, 在所有 Java 提交中击败了5.04%的用户

##### 2.迭代

```java
	public TreeNode invertTreeBFS(TreeNode root){
        if(root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode curNode = queue.remove();
            TreeNode left = curNode.left;
            TreeNode right = curNode.right;
            curNode.left = right;
            curNode.right = left;
            if(left != null) queue.add(left);
            if(right != null) queue.add(right);
        }
        return root;
    }

    public TreeNode invertTreeDFS(TreeNode root){
        if(root == null) return null;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            TreeNode curNode = stack.pop();
            TreeNode left = curNode.left;
            TreeNode right = curNode.right;
            curNode.left = right;
            curNode.right = left;
            if(left != null) stack.push(left);
            if(right != null) stack.push(right);
        }
        return root;
    }
```

思路分析：

* 按照方法一的思路：翻转一棵树就是将其左右子结点交换位置，且对其左右子树也进行相同的操作。这就相当于说，要将每一个结点的左右子树交换位置。先将子结点进行翻转，就是递归。如果先将根结点的左右子树进行交换，就是迭代了。
* 这里只要求对每个结点完成交换左右子树，所以进行BFS还是DFS都可以（都是标准写法），随你开心咯😜
* 时间复杂度为$O(n)$，因为遍历了每一个结点。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.1 MB, 在所有 Java 提交中击败了5.04%的用户
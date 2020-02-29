# 94.二叉树的中序遍历

### 原题
给定一个二叉树，返回它的中序 遍历。
示例:
输入: [1,null,2,3]
   1
    \
     2
    /
   3
输出: [1,3,2]
进阶: 递归算法很简单，你可以通过迭代算法完成吗？

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal

### 两种解法

##### 1.递归（我的第一解）

```java
	public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        addMid(root, res);
        return res;
    }

    private void addMid(TreeNode root, List<Integer> res) {
        if (root == null)
            return;
        addMid(root.left, res);
        res.add(root.val);
        addMid(root.right, res);
    }
```

思路分析：

* 中序遍历的顺序：左子树 -> 根结点 -> 右子树。这也就是递归的结构，递归的结束条件，树为`null`。
* 所以函数中，先将左子树进行遍历，然后根结点放入列表中，再将右子树进行遍历。
* 遍历了N个结点，所以显然时间复杂度为$O(N)$。空间复杂度，就是函数调用栈的空间，其$O(h)$,h为树的高度。对于平衡二叉树为logn，完全不平衡二叉树则是n。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.8 MB, 在所有 Java 提交中击败了5.02%的用户

##### 2.迭代（我的第二解）

```java
	public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;
        while (!stack.isEmpty() || curNode != null) { // 特别要注意第二个条件
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            res.add(curNode.val);
            curNode = curNode.right;
        }
        return res;
    }
```

思路分析：

* 使用迭代实现中序遍历，可以想一想使用递归是怎么实现的。在递归实现中，函数调用一直顺着最左边的路径走到没有左子结点的结点然后将其放入`List`中，然后再从该结点的右子结点为根开始重复上述过程。
* 在迭代中我们用自己的栈替代函数调用栈，同样，我们要先从根结点顺着最左边的路径走到没有左子结点的结点，走的过程不断将结点放入栈中。当没有左子结点时，弹出一个结点放入`List`中，这个元素就是这棵树中的最小元素。然后将该结点的右子结点重复上述操作。
* 注意外循环的第二个条件`curNode != null`，这表明这棵树还没有遍历。
* 遍历了N个结点，所以显然时间复杂度为$O(N)$。空间复杂度取决于栈中的元素个数，最坏的情况下栈中有N个元素，比如完全不平衡的只有左边一条路径的二叉树。

代码解释：

* 第8-11行的内循环，就是从根结点顺着最左边的路径走到没有左子结点的结点。
* 14行，当前结点放入结果集中后，依照中序遍历的定义，接下来要遍历其右子树，所以`curNode = curNode.right;`指向右子树。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了66.30%的用户
* 内存消耗 :37.8 MB, 在所有 Java 提交中击败了5.02%的用户
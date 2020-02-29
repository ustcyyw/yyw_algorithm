# 230. 二叉搜索树中第K小的元素

### 原题
给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。
说明：
你可以假设 k 总是有效的，1 ≤ k ≤ 二叉搜索树元素个数。

示例 1:
输入: root = [3,1,4,null,2], k = 1

```
   3
  / \
 1   4
  \
   2
```

输出: 1

示例 2:
输入: root = [5,3,6,2,4,null,null,1], k = 3

```
   	   5
      / \
     3   6
    / \
   2   4
  /
 1
```

输出: 3

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst/)：https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst

### 两种解法

##### 1. 递归 中序遍历至第k个元素

```java
	private int count;
    private int res;
    public int kthSmallest3(TreeNode root, int k) {
        count = 0;
        midTraversal3(root, k);
        return res;
    }

    private void midTraversal3(TreeNode x, int k){
        if(x == null) return;
        if(count == k) return;
        midTraversal(x.left, k);
        count++;
        if(count == k){
            res = x.val;
            return;
        }
        midTraversal(x.right, k);
    }
```

思路分析：

* [中序遍历](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/inorderTraversal.md)的递归解法已总结。对于搜索二叉树来说，其中序遍历就是按照键的大小，从小到大依次遍历的。本题要求第k小的元素，那么遍历到第k个元素即为要找的结果。
* 本题不需要得到遍历结果，所以也就没有添加结果集这一步骤。不过在中序遍历的过程中，将元素添加进结果集的那个地方是真正搜索到某元素的地方。在本题中要替换为计数，计数这是第几个元素，当搜索到第k个元素时，找到结果，再往后的元素就不必继续了。
* 时间复杂度为：最坏情况下最后一个元素才是我们要找的元素，此时遍历了整个树，所以是$O(n)$的。空间复杂度，取决于函数调用所占用的栈，其$O(h)$,h为树的高度。对于平衡二叉树为logn，完全不平衡二叉树则是n。

代码解释：

* 1，2两行就是避免递归的传参麻烦，将计数变量及存放结果的变量设置为成员变量。
* 11行，快速结束递归调用，因为计数为k时，我们结果已经找到。

运行结果：

* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户

* 内存消耗 :41.2 MB, 在所有 Java 提交中击败了5.02%的用户

##### 2.迭代 中序遍历至第k个元素

```java
	public int kthSmallest2(TreeNode root, int k) {
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null){
            while(node != null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            count++;
            if(count == k)
                return node.val;
            node = node.right;
        }
        return -1;
    }
```

思路分析：

* [中序遍历](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/inorderTraversal.md)的迭代解法已总结。类似于方法一中，在中序遍历中使用一个计数变量`count`来计数我们搜索到了第几个元素。
* 时间复杂度为：最坏情况下最后一个元素才是我们要找的元素，此时遍历了整个树，所以是$O(n)$的。空间复杂度取决于栈中元素个数。

代码解释：

* 12-13行，找到结果即停止遍历。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了77.95%的用户
* 内存消耗 :41.1 MB, 在所有 Java 提交中击败了5.02%的用户
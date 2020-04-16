# 687. 最长同值路径

### 原题
给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
注意：两个节点之间的路径长度由它们之间的边数表示。

示例 1:
输入:

```
          	  5
             / \
            4   5
           / \   \
          1   1   5
```
输出:
2

示例 2:
输入:

              1
             / \
            4   5
           / \   \
          4   4   5
输出:
2

注意: 给定的二叉树不超过10000个结点。 树的高度不超过1000。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/longest-univalue-path)：https://leetcode-cn.com/problems/longest-univalue-path

### 两种解法

##### 1.计算经过每一个结点的同值路径长度

```java
private int res;
    public int longestUnivaluePath(TreeNode root) {
        res = 0;
        traversal(root);
        return res;
    }

    private void traversal(TreeNode x){
        if(x == null) return;
        traversal(x.left);
        height(x, x.val);
        traversal(x.right);
    }

    private int height(TreeNode x, int val){
        if(x == null) return 0;
        if(x.val != val) return 0;
        int leftH = height(x.left, val);
        int rightH = height(x.right, val);
        res = Math.max(res, leftH + rightH);
        return Math.max(leftH, rightH) + 1;
    }
```

思路分析：

* 计算经过每一个结点的同值路径长度，实际上就是将所有情况都考虑了一遍，然后在其中取最大值即为结果。
* 如何计算经过某个结点的同值路径长，定义辅助函数`int height(TreeNode x, int val)`
    * 函数表示，以结点x为根的树，从x出发，路径上结点值均为`val`的最长路径的结点数。
    * 如果`x == null`，或者`x.val != val`，这样的路径不存在，直接返回0。
    * 否则：
        * 计算`int leftH = height(x.left, val);`，左子树路径上结点值均为`val`的最长路径的结点数；`int rightH = height(x.right, val);`右子树路径上结点值均为`val`的最长路径的结点数。
        * 经过x结点的同值路径长为`leftH + rightH`，更新所求结果`res = Math.max(res, leftH + rightH);`
        * 然后依据函数定义，返回` Math.max(leftH, rightH) + 1;`
* 要对每一个结点都计算经过其的同值路径长，所以再通过函数`void traversal(TreeNode x)`递归地进行遍历（前中后序）都可以。
* 主函数对初始化`res = 0`后调用`traversal(root);`即可。

运行结果：
* 执行用时 :10 ms, 在所有 Java 提交中击败了11.12%的用户
* 内存消耗 :43.5 MB, 在所有 Java 提交中击败了5.02%的用户

##### 2.官方标答

```java
private int res;
public int longestUnivaluePath2(TreeNode root) {
        res = 0;
        sameHeight(root);
        return res;
    }

    private int sameHeight(TreeNode x){
        if(x == null) return 0;
        int sonLeft = sameHeight(x.left);
        int sonRight = sameHeight(x.right);
        int left = 0, right = 0;
        if(x.left != null && x.left.val == x.val)
            left = sonLeft + 1;
        if(x.right != null && x.right.val == x.val)
            right = sonRight + 1;
        res = Math.max(res, left + right);
        return Math.max(left, right);
    }
```

思路分析：

* 方法一中其实进行了两层递归，效率很低。
* 假设现在要计算经过结点x的最长同值路径，需要知道以其左子树为起点，向下的值为`x.val`的最长路径（不是左右连接起来的路径，是只能从根向下的路径）及以其右子树为起点，向下的值为`x.val`的最长路径。这两条路径拼接在一起就是经过结点x的最长同值路径。
* 定义辅助函数`int sameHeight(TreeNode x)`，返回值为以x为起点的，向下的值为`x.val`的最长路径的结点数。
    * 如果`x == null`，这样的路径不存在，直接返回0。
    * 计算`int sonLeft = sameHeight(x.left);`，左子树根结点的向下同值路径，如果`x.left != null && x.left.val == x.val`，左子树根结点与x的值相同，那么x从左边往下走的同值路径的结点数就为`left = sonLeft + 1;`
    * 同理对右子树计算。
    * 那么经过x的最长同值路径长度为`left + right`，更新`res = Math.max(res, left + right);`
    * 最后根据函数定义，x为起点的，向下的值为`x.val`的最长路径的结点数。向左或者向右往下走的同值路径的最大值`return Math.max(left, right);`
* 时间复杂度为$O(n)$，因为是对结点进行了遍历，空间复杂度与树高成正比。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了93.24%的用户
* 内存消耗 :44 MB, 在所有 Java 提交中击败了5.16%的用户
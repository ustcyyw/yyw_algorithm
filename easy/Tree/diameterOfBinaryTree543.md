# 543. 二叉树的直径

### 原题
给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。

示例 :
给定二叉树

          1
         / \
        2   3
       / \     
      4   5    
返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。

注意：两结点之间的路径长度是以它们之间边的数目表示。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/diameter-of-binary-tree)：https://leetcode-cn.com/problems/diameter-of-binary-tree

### 解法

```java
public static int result;
    public int diameterOfBinaryTree(TreeNode root) {
        result = 0;
        depth(root);
        return result;
    }

    private int depth(TreeNode x){
        if(x == null) return 0;
        int leftDepth = depth(x.left);
        int rightDepth = depth(x.right);
        // leftDepth - 1：左子树高度-1，即可得到左最长的边的长度，同理rightDepth - 1得到右子树最长的边。分别通过当前根结点相连接，边长+2
        result = Math.max(result, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }
```

思路分析：

* 题目定义，一棵二叉树的直径长度是任意两个结点路径长度中的最大值，并且这条路径可能穿过也可能不穿过根结点。树的题一般都可以考虑一下，如何使用递归。
    * 一个直观的想法是，二叉树的直径应该从左子树最深的叶子结点到达根然后再到达右边最深的叶子节点。
    * 但是来一个极端的例子，左子树为一个高度100的平衡二叉树，右子树为空，那么显然直径是左子树种过左子树根结点的路径。
    * 所以应该修正一下，直径应该是每一个结点的到达其左右最深的叶子结点的路径中最长的那一条。
* 那么如何求左子树最深的叶子结点到达根然后再到达右边最深的叶子节点的路径长度。
    * 左子树最深的叶子结点到达根的路径长，就是左子树的高度-1（PS：这里高度是路径的结点数）。根结点同理到达右边最深的叶子节点的路径长，就是右子树的高度-1。
    * 然后将根结点两边的路径连接到根结点，由于连接到根，左边使得直径长度+1，右边也一样。所以这条路径长度就等于左子树高度+右子树高度。
* 接下来就是写遍历每一个结点，求其子树高度+右子树高度，然后不断更新最大值。求树高就是一个很常规的递归：树高等于`max{左子树高, 右子树高} + 1`。在遍历过程就可以求其子树高度+右子树高度，然后不断更新最大值`result = Math.max(result, leftDepth + rightDepth);`
* 时间复杂度，由于遍历了每一个结点，所以为$O(1)$，空间复杂度为树高成正比，最坏情况树高为n，最好情况下为logn。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :38.8 MB, 在所有 Java 提交中击败了
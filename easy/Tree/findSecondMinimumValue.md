# 671. 二叉树中第二小的节点

### 原题
给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为 2 或 0。如果一个节点有两个子节点的话，那么这个节点的值不大于它的子节点的值。 
给出这样的一个二叉树，你需要输出所有节点中的第二小的值。如果第二小的值不存在的话，输出 -1 。

示例 1:
输入: 
```
    2
   / \
  2   5
     / \
    5   7
```
输出: 5
说明: 最小的值是 2 ，第二小的值是 5 。

示例 2:
输入: 
```
    2
   / \
  2   2
```
输出: -1
说明: 最小的值是 2, 但是不存在第二小的值。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/second-minimum-node-in-a-binary-tree)：https://leetcode-cn.com/problems/second-minimum-node-in-a-binary-tree

### 解法

```java
public int findSecondMinimumValue(TreeNode root) {
        if(root == null)
            return -1;
        return find(root, root.val);
    }

    /**
     * 按照题目描述，这个二叉树是个最小堆，最上面的元素就是最小的元素。用rootValue表示最小值
     * 找到和rootValue值不相同的最小值，与rootValue不相同的最小值其实就是第二小的值。
     */
    private int find(TreeNode x, int rootValue){
        if(x.val != rootValue) // 如果当前结点不等于根结点至，那么当x值为以x为根的最小的非rootValue的值
            return x.val;
        // 这之下都是 当前结点值为根结点值的情况
        if(x.left == null) // 递归到叶子结点 且其值为根结点值，说明没有找到第二小的值，返回失败标志-1。
            return -1;
        int leftMin = find(x.left, rootValue);
        int rightMin = find(x.right, rootValue);
        if(leftMin == -1)
            return rightMin;
        if(rightMin == -1)
            return leftMin;
        return Math.min(leftMin, rightMin);
    }
```

思路分析：

* 首先根据题目描述，根结点值一定小于或者等于子结点值，这就是一个最小堆，子结点值就是整个树中的最小值，所以要找的第二小值，就是除去根结点值的最小值，为了方便使用`rootValue`表示这棵树根节点的值。
* 树的题一般考虑递归。考虑到题目所求，递归函数的返回值应该定义为以节点x为根的树中非`rootValue`的最小值。由于一直需要信息`rootValue`，所以递归函数的参数除了结点还有`rootValue`。
* 对于以`TreeNode x`为根的结点，
    * 如果其值不为`rootValue`，那么它的值就是以它为根的树中非根结点值的最小值。
    * 如果它的值为`rootValue`，而且其已经是叶子结点`x.left == null`，在以它为根的树中没有找到非根结点值的最小值，返回失败标志-1。
    * 如果它的值为`rootValue`，但它还有左右子树，就去找左右子树的非根结点值的最小值，分别为`leftMin, rightMin`。
        * 如果`leftMin == -1`，左子树查找失败，返回右子树的查找结果（也可能是失败，但失败也是返回-1，也还是`rightMin`）。
        * 同理`rightMin == -1`，返回左子树的查找结果
        * 如果左右都没有查找失败，都找到了非根结点值的局部最小值，就要选择其中更小的一个返回。
* 时间复杂度为$O(n)$， 空间复杂度与树高成正比。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :36.8 MB, 在所有 Java 提交中击败了10.00%的用户
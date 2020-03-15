# 108. 将有序数组转换为二叉搜索树

### 原题

将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。

本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。

示例:

给定有序数组: [-10,-3,0,5,9],

一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：

```
	   0
	  / \
   -3   9
   /   /
 -10  5
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree)：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree

### 解法

```java
public TreeNode sortedArrayToBST(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        return create(nums, lo, hi);
    }

    private TreeNode create(int[] nums, int lo, int hi){
        if(lo > hi) return null;
        if(lo == hi) return new TreeNode(nums[lo]);
        int mid = (hi - lo) / 2 + lo;
        TreeNode temp = new TreeNode(nums[mid]);
        temp.left = create(nums, lo, mid - 1);
        temp.right = create(nums, mid + 1, hi);
        return temp;
    }
```

思路分析：

* 元素是有序的，要生成一棵BST，且要求BST是平衡的。也就是说对于二叉树的每个结点，要让其左子树与右子树的结点数量尽可能相等。
* 如果我们选定数组的索引为`i`的元素为根结点，那么左子树与右子树结点的值的区间就唯一确定了（因为数组有序）。左子树结点值的区间`[0, i - 1]`，右子树结点值的区间`[i + 1, nums.length - 1]`。要让BST平衡，那么所选的`i`就应该选数组的中间元素。
* 平衡BST的左子树，右子树也均是平衡BST，所以也需要用同样的策略去选值。于是可以将区间`[0, num.length - 1]`上的问题转化为两个子问题`[0, mid]`与`[mid + 1, num.length - 1]`上构建平衡二叉树。这就是递归的结构。
* `TreeNode create(int[] nums, int lo, int hi)`，第二，第三个参数即为区间范围，返回值为这个区间上平衡二叉树的根结点。递归的下沉阶段不断构建结点，上浮阶段不断更新结点的链接

代码解释：看辅助的递归函数

* 第7行，`if(lo > hi) return null;`，指定区间不存在，返回`null`。
* 第8行，`if(lo == hi) return new TreeNode(nums[lo]);`，区间仅有唯一值，生成新结点，这个结点没有左右子树，返回该结点。
* 9-12行，递归结构。因为要更新链接创建树，所以有` temp.left = create(nums, lo, mid - 1);`。
* 最后，返回`[lo, hi]`区间上构建的BST的根结点，让上一层函数中的结点更新链接。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.3 MB, 在所有 Java 提交中击败了5.03%的用户
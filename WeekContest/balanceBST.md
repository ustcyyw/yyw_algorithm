# 5179. 将二叉搜索树变平衡

### 周赛180周 第三题
给你一棵二叉搜索树，请你返回一棵 平衡后 的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。
如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是 平衡的 。
如果有多种构造方法，请你返回任意一种。 

示例：
输入：root = [1,null,2,null,3,null,4,null,null]
输出：[2,1,3,null,null,null,4]
解释：这不是唯一的正确答案，[3,1,4,null,2,null,null] 也是一个可行的构造方案。

提示：
树节点的数目在 1 到 10^4 之间。
树节点的值互不相同，且在 1 到 10^5 之间。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/balance-a-binary-search-tree)：https://leetcode-cn.com/problems/balance-a-binary-search-tree

### 解法

```java
public TreeNode balanceBST(TreeNode root) {
        if(root == null)
            return null;
        List<Integer> vals = new ArrayList<>();
        traversal(root, vals);
        return build(vals, 0, vals.size() - 1);
    }

    private void traversal(TreeNode x, List<Integer> vals){
        if(x == null) return;
        traversal(x.left, vals);
        vals.add(x.val);
        traversal(x.right, vals);
    }

    private TreeNode build(List<Integer> vals, int lo, int hi){
        if(lo > hi) return null;
        if(lo == hi) return new TreeNode(vals.get(hi));
        int mid = (hi - lo) / 2 + lo;
        TreeNode x = new TreeNode(vals.get(mid));
        x.left = build(vals, lo, mid - 1);
        x.right = build(vals, mid + 1, hi);
        return x;
    }
```

思路分析：

* 本题的最终目标是构建一棵平衡二叉搜索树，原材料是一棵不平衡的BST。由一个有序数组来构建二叉搜索树比较简单，而二叉搜索树有一个重要性质，就是其中序遍历可以得到一个有序的数组。所以问题转化为两个子问题，见下面两个链接中的具体解答。

* [有序数组转化为平衡二叉树](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/Tree/sortedArrayToBST.md)。 [二叉树的中序遍历](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/inorderTraversal.md)
* 由于不知道原BST中有几个结点，所以用` List<Integer> vals = new ArrayList<>();`来存放结点，作为中序遍历的递归函数的第二个实参。
* 时间复杂度，不论是中序遍历还是构建平衡BST，都是对结点遍历，所以时间复杂度为$O(n)$。空间复杂度，额外使用了一个List$O(n)$，以及构建平衡BST递归调用的函数栈$O(log(n))$，中序遍历的函数调用栈则正比于树高。

运行结果：

* 难度 medium 3ms。
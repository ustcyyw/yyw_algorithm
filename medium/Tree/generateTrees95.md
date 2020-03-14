# 95. 不同的二叉搜索树 II

### 原题
给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。

示例:
输入: 3
输出:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
解释:
以上的输出对应以下 5 种不同结构的二叉搜索树：

```
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/unique-binary-search-trees-ii)：https://leetcode-cn.com/problems/unique-binary-search-trees-ii

### 解法

```java
public List<TreeNode> generateTrees(int n) {
        if(n == 0)
            return new ArrayList<>();
        return sonTrees(1, n);
    }

    private List<TreeNode> sonTrees(int lo, int hi) { // [lo,hi]范围内所有的BST
        List<TreeNode> res = new ArrayList<>();
        if(lo > hi)
            res.add(null);
        else if(lo == hi)
            res.add(new TreeNode(lo));
        else {
            // 当前区间内，所有能生产的BST，每一个值都可能为根，然后要考虑其左边区间所有子树与右边区间所有子树的可能
            for(int i = lo; i <= hi; i++){
                List<TreeNode> leftSon = sonTrees(lo, i - 1);
                List<TreeNode> rightSon = sonTrees(i + 1, hi);
                for(TreeNode left : leftSon){
                    for(TreeNode right : rightSon){
                        TreeNode curRoot = new TreeNode(i);
                        curRoot.left = left;
                        curRoot.right = right;
                        res.add(curRoot);
                    }
                }
            }
        }
        return res;
    }
```

思路分析：

* 要生成一棵搜索二叉树，需要确定它的根结点，确定他的左子树，右子树。并且搜索二叉树的根结点一旦确定，那么构成其左子树和右子树的结点的值的集合就唯一确定了。
* 但是只确定结点值的集合并不能确定唯一地确定一颗树。那么当我们选定了一个根结点`i`之后，其左子树的结点值区间为`[1, i - 1]`，假设这些值可以构成`k1`颗子树；右子树的结点值区间为`[i + 1, n]`，假设这些值可以构成`k2`颗子树，那么以`i`为根的BST一共有`k1 * k2`棵。
* 以`[1, n]`的数构造BST，每一个数都可能为根结点。所以所有的BST应该是所有数为根构成的BST的集合。
* 要确定以`i`为根的BST，先确定左，右子树的所有可能的情况，再将这些情况进行组合。`[1, n]`上的问题被划分成`[1, i - 1]`与`[i + 1, n]`上的子问题。于是递归结构就找到了，我们的函数`private List<TreeNode> sonTrees(int lo, int hi)`返回`[lo, hi]`内所有数够成的BST。
* 在`sonTrees(lo, hi)`函数中，选取`[lo, hi]`的每一个数为根结点。并且将其所有可能的左子树，右子树的集合找到。`List<TreeNode> leftSon = sonTrees(lo, i - 1); List<TreeNode> rightSon = sonTrees(i + 1, hi);`然后进行组合，一个简单的二层循环。
* 注意递归截止的条件，当`lo > hi`时，无法找到BST，返回一个装有`null`的List；当`lo == hi`，唯一的BST就是单个结点，以`lo`为值。
* 另外当给定的`n==0`时，没有BST，直接返回一个空列表即可。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.4 MB, 在所有 Java 提交中击败了5.07%的用户
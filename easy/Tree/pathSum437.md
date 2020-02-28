# 437. 路径总和 III

### 原题
给定一个二叉树，它的每个结点都存放着一个整数值。
找出路径和等于给定数值的路径总数。
路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。

示例：
`root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8`

```
  	  10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1
```

返回 3。和等于 8 的路径有:

1.  5 -> 3
2.  5 -> 2 -> 1
3.  -3 -> 11

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/path-sum-iii)：https://leetcode-cn.com/problems/path-sum-iii

### 解法:回溯

```java
	private int count;
    public int pathSum(TreeNode root, int sum) {
        count = 0;
        backTrack(root, sum, new int[1000], 0);
        return count;
    }

    public void backTrack(TreeNode x, int sum, int[] pathItem, int curIndex){
        if(x == null) return;

        if(x.val == sum) count++;
        for(int i = curIndex - 1, temp = x.val; i >= 0; i--){
            temp += pathItem[i];
            if(temp == sum)
                count++;
        }

        pathItem[curIndex] = x.val;
        backTrack(x.left, sum, pathItem, curIndex + 1);
        backTrack(x.right, sum, pathItem, curIndex + 1);
    }
```

思路分析：

* 问题的难点就在于，路径的起点可以不是根结点，终点可以不是叶子结点。如果是穷举的话，需要将所有向下的路径穷举出来。
* 使用DFS的思路，我们可以将每一条从根结点到叶子结点的路径遍历，关键是怎样将每一条从根到叶上的子路径的和求出来呢。（路径 10->5->3->3 的子路径之一 5->3）可以使用一个数组来保存从根结点到当前结点的所有值，累加，每当累加量等于题目`sum`时，就表明参与累加的元素对应的结点构成的路径满足题意。
* 是否会出现重复？因为每一个进行累加的末端结点都参与了累加，所以即使有累加过相同的子路径，但由于参与加和的第一个元素值改变了，所以不会重复。
* 这里还要注意一个比较特殊的情况，就是当前结点的值刚好等于`sum`时，一个结点即满足提议，也要使得计数加1。
* DFS在搜索时会回溯，这里由于我们使用的是数字，下标的回溯即可使得数组中元素的回溯完成。

代码解释：

* `private int count`是个实例变量，所以在每次调用算法，在第三行进行重置。
* `public void backTrack(TreeNode x, int sum, int[] pathItem, int curIndex)`递归函数的第三个参数为存放从根结点到达当前结点的所有结点值（调用时不包括当前结点，在低18行在进行下次递归前才存放当前结点。）第四个参数表示，现在递归元素是第几个（从0开始计数）。
* 11行，处理特殊的情况，就是当前结点的值刚好等于`sum`时。
* 12到16行，从当前结点往上累加，当遇到累加和`temp == sum`时，找到一条子路径满足题意。
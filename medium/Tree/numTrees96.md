# 96. 不同的二叉搜索树的数量

### 原题
给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？

示例:
输入: 3
输出: 5
解释:
给定 n = 3, 一共有 5 种不同结构的二叉搜索树:

```
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/unique-binary-search-trees)：https://leetcode-cn.com/problems/unique-binary-search-trees

### 解法：

```java
public int numTrees2(int n) {
        int[] count = new int[n + 1]; // count[i]表示 i个值可以构成多少种BST
        count[0] = 1;
        count[1] = 1;
        for(int i = 2; i <= n; i++){
            for(int j = 0; j < i; j++)
                count[i] += count[j] * count[i - j - 1];
        }
        return count[n];
    }
```

思路分析：

* 这个题目基本上就是[第95题 找到所有BST](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/generateTrees95.md)。只不过本题求的是生成的BST的数量。
* `[1, n]`内的每一个值都可以做为根结点，并且唯一地确定了左右子树的值的区间为`[1, i - 1]`，`[i + 1, n]`。要知道以`i`为根可以构成多少BST，需要先求出`[1, i - 1]`，`[i + 1, n]`分别能构成多少BST，假设是k1与k2，那么以`i`为根可以构成k1*k2棵BST。这就是递归结构，定义`int count(int lo, int hi)`为`[lo, hi]`的所有值构成的BST的数量。参考95题。
* 注意递归结束的条件。`lo == hi`，只能生成一棵子树，`lo > hi`可用集合为空，也只有一棵子树（就是`null`）。于是可以得到下面的递归方法。

```java
public int numTrees(int n) {
        return count(1, n);
    }

    public int count(int lo, int hi){
        if(lo >= hi) return 1;
        int sum = 0;
        for(int i = lo; i <= hi; i++){
            sum += count(lo, i - 1) * count(i + 1, hi);
        }
        return sum;
    }
```

* 但是我们发现，由于这个题求的是数量，并不是具体的哪一棵树。`[1,2,3]`三个数字构成的BST数量与`[4,5,6]`三个数字构成的BST数量是相等的。构成多少个BST只与区间长度有关，所以上述方法有很多重复计算。
* 于是可以用记忆化搜索去解决。当然这其实就是DP，定义状态`count[i]`表示 i个值可以构成多少棵BST。边界条件就是`count[0] = 1; count[1] = 1`。0个值与一个值能构成一棵BST。
* 状态转移方程：`i`个结点构成的BST，可以左子树`0`个结点，右子树`i-1`个结点；可以左子树`1`个结点，右子树`i-2`个结点....可以左子树`j`个结点，右子树`i-j-1`个结点....可以左子树`i-1`个结点，右子树`0`个结点。这些情况加和。见第6-7行的循环。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :36.7 MB, 在所有 Java 提交中击败了5.08%的用户
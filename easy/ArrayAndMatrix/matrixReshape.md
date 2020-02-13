# 重塑矩阵

### 原题

在MATLAB中，有一个非常有用的函数 reshape，它可以将一个矩阵重塑为另一个大小不同的新矩阵，但保留其原始数据。
给出一个由二维数组表示的矩阵，以及两个正整数r和c，分别表示想要的重构的矩阵的行数和列数。
重构后的矩阵需要将原始矩阵的所有元素以相同的行遍历顺序填充。
如果具有**给定参数的reshape操作是可行且合理的，则输出新的重塑矩阵；否则，输出原始矩阵。**

示例 1:
输入: 
nums = 
[[1,2],
 [3,4]]
r = 1, c = 4
输出: 
[[1,2,3,4]]
解释:
行遍历nums的结果是 [1,2,3,4]。新的矩阵是 1 * 4 矩阵, 用之前的元素值一行一行填充新矩阵。

示例 2:
输入: 
nums = 
[[1,2],
 [3,4]]
r = 2, c = 4
输出: 
[[1,2],
 [3,4]]
解释:
没有办法将 2 * 2 矩阵转化为 2 * 4 矩阵。 所以输出原矩阵。

注意：
给定矩阵的宽和高范围在 [1, 100]。
给定的 r 和 c 都是正数。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/reshape-the-matrix)：https://leetcode-cn.com/problems/reshape-the-matrix

----

### 解法

##### 1.我的第一解

```java
public int[][] matrixReshape(int[][] nums, int r, int c) {
        if(r * c != nums.length * nums[0].length) // 无法转化的时候，输出原矩阵。
            return nums;
        int[][] result = new int[r][c];
        int count = 0;
        for(int[] row: nums)
            for(int item: row){
                result[count / c][count % c] = item;
                count++;
            }
        return result;
    }
```

思路分析：

* 无法完成reshape的意思就是，原矩阵元素无法恰好填充指定行数列数的新矩阵。所以当`r * c != nums.length * nums[0].length`时，返回原矩阵。
* 遍历原矩阵，第`0,1,2...,c-1`个元素放入第一行（索引为0），其中第`0`个元素放入第一列...第`c-1`个元素放入第c-1列。第`c,c+1...,2c-1`个元素放入第二行（索引为1）...
* 以此类推，所以需要一个变量`count`来表示当前是第几个元素，因为索引是从0开始的，所以`count=0`表示第一个元素。第`count`个元素应该放入`count/c`行，`count%c`列。
* 遍历使用嵌套的 `for-each`语句即可，每添加一个元素进入新矩阵中，使得计算`count++`。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.1 MB, 在所有 Java 提交中击败了75.12%的用户
# 托普利茨矩阵

### 原题
如果一个矩阵的每一方向由左上到右下的对角线上具有相同元素，那么这个矩阵是托普利茨矩阵。
给定一个 M x N 的矩阵，当且仅当它是托普利茨矩阵时返回 True。

示例 1:
输入: 
matrix = [
  [1,2,3,4],
  [5,1,2,3],
  [9,5,1,2]
]
输出: True
解释:
在上述矩阵中, 其对角线为:
"[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]"。
各条对角线上的所有元素均相同, 因此答案是True。

示例 2:
输入:
matrix = [
  [1,2],
  [2,2]
]
输出: False
解释: 
对角线"[1, 2]"上的元素不同。

说明:
 matrix 是一个包含整数的二维数组。
matrix 的行数和列数均在 [1, 20]范围内。
matrix[i][j] 包含的整数在 [0, 99]范围内。
进阶:
如果矩阵存储在磁盘上，并且磁盘内存是有限的，因此一次最多只能将一行矩阵加载到内存中，该怎么办？
如果矩阵太大以至于只能一次将部分行加载到内存中，该怎么办？

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/toeplitz-matrix)：https://leetcode-cn.com/problems/toeplitz-matrix

----

### 解法

##### 1. 每次仅使用两个元素（我的第一解）

```java
public boolean isToeplitzMatrix(int[][] matrix) {
        int column = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < column; j++) {
                if (matrix[i][j] != matrix[i - 1][j - 1])
                    return false;
            }
        }
        return true;
    }
```

思路分析：

* 观察矩阵其实很容易发现，满足托普利茨矩阵定义的矩阵，从第二行开始，每个元素都与左上角的元素相等。
* 所以只需要一个二层循环，外层循环从第二行开始，内层循环从每行的第二个元素开始（因为第一行，以及其余行的第一个元素都是没有左上角元素的）。如果循环过程中有一组元素不相等，即不满足条件，返回`false`。
* 由于两层循环，遍历了除第一行及每行第一个元素外的所有元素，所以时间复杂度为$O((n-1)(m-1))$；每次仅使用了两个元素，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :48.4 MB, 在所有 Java 提交中击败了5.27%的用户

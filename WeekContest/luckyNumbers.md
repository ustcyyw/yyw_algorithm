# 5356. 矩阵中的幸运数

### 180周周赛 第一题

给你一个 m * n 的矩阵，矩阵中的数字 各不相同 。请你按 任意 顺序返回矩阵中的所有幸运数。
幸运数是指矩阵中满足同时下列两个条件的元素：
在同一行的所有元素中最小
在同一列的所有元素中最大

示例 1：
输入：matrix = [[3,7,8],[9,11,13],[15,16,17]]
输出：[15]
解释：15 是唯一的幸运数，因为它是其所在行中的最小值，也是所在列中的最大值。

示例 2：
输入：matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
输出：[12]
解释：12 是唯一的幸运数，因为它是其所在行中的最小值，也是所在列中的最大值。

示例 3：
输入：matrix = [[7,8],[1,2]]
输出：[7]

提示：

```
m == mat.length
n == mat[i].length
1 <= n, m <= 50
1 <= matrix[i][j] <= 10^5
```

矩阵中的所有元素都是不同的

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/lucky-numbers-in-a-matrix)：https://leetcode-cn.com/problems/lucky-numbers-in-a-matrix

### 解法

```java
public List<Integer> luckyNumbers (int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if(matrix.length == 0 || matrix[0].length == 0)
            return res;
        int row = matrix.length, col = matrix[0].length;
        for(int i = 0; i < row; i++){
            int colMinIndex = 0; // 找到该行最小的元素的列索引
            for(int j = 1; j < col; j++){
                if(matrix[i][j] < matrix[i][colMinIndex])
                    colMinIndex = j;
            }
            int rowMaxIndex = i;
            for(int j = 0; j < row; j++){
                if(matrix[j][colMinIndex] > matrix[i][colMinIndex]){
                    rowMaxIndex = j;
                    break;
                }
            }
            if(rowMaxIndex == i)
                res.add(matrix[i][colMinIndex]);
        }
        return res;
    }
```

思路分析：

* 仔细看看幸运数的定义，该行最小且为该列最大。
* 每一行肯定能找到一个最小元素，但是它不一定是该列的最大元素，所以当找到某一行的最小元素后还要判断他是不是该行最大元素。由于要找到所有幸运数所以要对每一行都进行一次这样的判断。
* 外循环对每一行进行。首先对当前行进行一次遍历确定行中最小元素的列索引，然后再对该列进行遍历，一旦找到一个比待判断元素大的元素就停止，并且修改`rowMaxIndex`。如果列循环结束没有改变`rowMaxIndex`，则说明它是该列最大的元素，它是一个幸运数。
* 空间复杂度，取决于有多少个幸运数 $O(m)$。时间复杂度，进行了两层遍历，所以为$O(n^2)$。

运行结果：

* 难度 easy  7ms
# 面试题04. 二维数组中的查找

### 原题
编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
每行的元素从左到右升序排列。
每列的元素从上到下升序排列。

示例:
现有矩阵 matrix 如下：
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
给定 target = 5，返回 true。
给定 target = 20，返回 false。

**注意：**本题与主站 240 题相同：https://leetcode-cn.com/problems/search-a-2d-matrix-ii/

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/)：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/

### 3种解法

（最优解直接看第三种解法）

##### 1.逐行使用二分查找
```java
public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] > target)
                return false;
            if (Arrays.binarySearch(matrix[i], target) >= 0)
                return true;
        }
        return false;
    }
```
思路分析：
* 要查找矩阵中是否存在某个元素，并且表示矩阵每一行的数组都是有序的，可以对每一行进行二分查找，如果每一行都没有查找到结果，就返回`false`。
    * 使用java库函数`Arrays.binarySearch(matrix[i], target)`，返回值非负说明查找到`target`。
* 还需要处理一些特殊情况：
    * 给定的矩阵为`null`或者给定的矩阵不存在任何元素`matrix.length == 0 || matrix[0].length == 0`，肯定找不到目标元素，直接返回。
    * 矩阵的每一列也是从小到达排列的，所以在对每一行进行二分查找的循环是，如果`matrix[i][0] > target`，这一行肯定没有指定元素，更下面的每一行的所有元素都大于`matrix[i][0]`也一定找不到指定元素，所以可以直接返回`false`。
* 时间复杂度为$O(mlog(n))$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :9 ms, 在所有 Java 提交中击败了35.30%的用户
* 内存消耗 :49.6 MB, 在所有 Java 提交中击败了5.03%的用户

##### 2.区域递归
```java
public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0)
            return false;
        return search(matrix, target, 0, matrix[0].length - 1, 0, matrix.length - 1);
    }

    private boolean search(int[][] matrix, int target, int left, int right, int top, int bottom) {
        if (left > right || top > bottom) // 已无迭代区域
            return false;
        if (target < matrix[top][left] || target > matrix[bottom][right]) // 目标值比矩阵的左上角小或者比矩阵的右小角大，肯定无法不能在矩阵中找到该值
            return false;
        int mid = (left + right) / 2;
        int row = top;
        while (row <= bottom && matrix[row][mid] <= target) { // 搜索中间列是否能找到target，如果找不到就使row停在该行中间元素比target大的位置
            if (matrix[row][mid] == target)
                return true;
            row++;
        }
        return search(matrix, target, left, mid - 1, row, bottom) ||
                search(matrix, target, mid + 1, right, top, row - 1);
    }
```
思路分析：
* 根据矩阵的元素的排列规律，左上角的元素是整个区域最小的元素，如果这个元素大于`target`，那么这个区域中一定没有`target`；右下角的元素是整个区域最大的元素，如果这个元素小于`target`，那么这个区域中一定没有`target`。
* 根据上一个结论，如果`matrix[i][j] > target`，并且保证`martix[i - 1][j] < target`，则
    * 首先，第`j`列没有元素等于`taget`。
    * 并且以`matrix[i - 1][j]`为右下角的矩形区域中不存在`target`；以`matrix[i][j]`为左上角的矩形区域也不存在`target`。所以可能存在`target`的区域就只有以`matrix[i][j - 1]`为右上角的矩形区域或者以`matrix[i - 1][j + 1]`为左下角的矩形区域。
* 根据上述结论，可以定义递归的查找函数`boolean search(int[][] matrix, int target, int left, int right, int top, int bottom)`，表示在矩形区域`[top, bottom]x[left, right]`内是否存在`target`。
    * 递归结束的条件是:
        * 矩形区域不存在`left > right || top > bottom`
        * 或者矩形区域左上角元素大于`target`或者右下角的元素小于`target`。`target < matrix[top][left] || target > matrix[bottom][right]`
    * 用二分的想法，内循环去判断`mid = (left + right) / 2`这一列是否存在`target`。`while (row <= bottom && matrix[row][mid] <= target)`这个循环条件会使得如果查找不成功，`row`的值满足条件`matrix[row][j] > target`，并且保证`martix[row - 1][j] < target`
    * 于是就可以按照之前说的可能存在的思路，递归地进行查找，返回`search(matrix, target, left, mid - 1, row, bottom) ||search(matrix, target, mid + 1, right, top, row - 1);`
* 主函数首先判断，矩阵是否存在或者是否有元素，然后调用递归函数。
* 时间复杂度为$O(mlog(n))$，空间复杂度就是函数调用栈占用的空间。

运行结果：
* 执行用时 :9 ms, 在所有 Java 提交中击败了35.30%的用户
* 内存消耗 :50.8 MB, 在所有 Java 提交中击败了5.03%的用户

##### 3.固定方向行走
```java
public boolean searchMatrix3(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        int x = matrix[0].length - 1, y = 0;
        while(y < matrix.length && x >= 0){
            if(matrix[y][x] < target)
                y++;
            else if (matrix[y][x] > target)
                x--;
            else return true;
        }
        return false;
    }
```
思路分析：
* 固定方向行走，充分利用矩阵的有序性。类似于题目[378. 有序矩阵中第K小的元素](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/ArrayAndMatrix/kthSmallest.md)中的解法2。
* 在矩阵当中行走，根据当前元素的大小选择行走方向。
    * 如果我们选择左上角为起点，那么当`matrix[y][x] < target`时，要去找下一个更大的元素与`target`比较，向下和向右都有可能，不知道从哪边走。同理以右下角为起点也不不确定行走方向。
    * 选择从右上角开始，如果`matrix[y][x] < target`，去找下一个更大的元素，就只能向下走。反之要去找比当前更小的元素，就选择往左走。（选择从左下角开始也是可行的）
    * 如果在行走过程中有相等元素，就返回`true`。如果坐标超出矩形范围，说明没有找到，返回`false`。
* 以下图观察上述描绘的行走过程：

![searchMatrix图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/ArrayAndMatrix/searchMatrix%E5%9B%BE%E7%A4%BA.png?raw=true)

* 从右上角除非，超出矩形范围就停止循环，循环条件为`y < matrix.length && x >= 0`，然后返回`false`。向下走就是行坐标`y++`，向左走就是列坐标`x--`。
* 最多行走m+n次就会超出矩阵范围，或者找到了目标，所以时间复杂度为$O(m + n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :7 ms, 在所有 Java 提交中击败了86.14%的用户
* 内存消耗 :50.5 MB, 在所有 Java 提交中击败了5.03%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
# 378. 有序矩阵中第K小的元素
### 原题
给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第k小的元素。
请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素。 

示例:
matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

返回 13。

提示：
你可以假设 k 的值永远是有效的, 1 ≤ k ≤ n2 。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix)：https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix

### 2种解法
##### 1.优先队列的使用
```java
public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> MaxPQ = new PriorityQueue<>(Collections.reverseOrder());
        for (int[] row : matrix) {
            for (int num : row) {
                if (MaxPQ.size() == k && num > MaxPQ.peek())
                    break;
                MaxPQ.add(num);
                if (MaxPQ.size() > k)
                    MaxPQ.remove();
            }
        }
        return MaxPQ.remove();
    }
```
思路分析：
* 要找第k小的元素，一种最常规的做法就是使用优先队列。
    * 找第k小的元素，就保留k个最小的元素，其中最大的那个就是答案，所以可以使用最大优先队列。
    * 遍历矩阵中的元素，将元素添加到队列中，如果队列中元素数目`MaxPQ.size() > k`，就将堆点最大的元素弹出。
    * 遍历结束后弹出堆顶元素，就是最小的k个元素中最大的，即第k小的元素。
* 这里可以利用矩阵的有序性做一点小的优化：
    * 如果在遍历的过程中，队列中的元素数目已经为k了，且如果当前元素大于堆顶元素，这个元素放入队列中还会被弹出，所以就没必要放入。
    * 并且遍历的内循环是从某一行的从左到右遍历，当前元素的右边元素比当前元素更大，也没必要放入队列，所以当`MaxPQ.size() == k && num > MaxPQ.peek()`，直接打断内循环，进行下一行的遍历。
* 时间复杂度为$O(n^2log(k))$，空间复杂度为$O(k)$。

运行结果：
* 执行用时 :21 ms, 在所有 Java 提交中击败了34.35%的用户
* 内存消耗 :45.8  MB, 在所有 Java 提交中击败了7.69%的用户

##### 2.二分法的运用
```java
public int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length - 1;
        int left = matrix[0][0], right = matrix[n][n];
        while(left < right){
            int mid = left + (right - left) / 2;
            int count = countNotMoreThanMid(matrix, mid, n);
            if(count < k)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }

    private int countNotMoreThanMid(int[][] matrix, int mid, int n){
        int count = 0;
        int x = 0, y = n;
        while(x <= n && y >= 0){
            if(matrix[y][x] <= mid){
                count += y + 1;
                x++;
            }else{
                y--;
            }
        }
        return count;
    }
```
思路分析：
* 从方法一的做法可以看出，对矩阵的有序性运用不充足，比如就导致了不是最优解。
* 第k小的数字意味着小于等于它的元素一共有k个，大于它的数字有n*n-k个。假设某个数为`mid`
    * 如果小于等于`mid`的元素个数小于k，说明`mid`不是第k小的数，比`mid`小的数就更不可能是了。所以第k小的数至少是`mid + 1`。
    * 如果小于等于`mid`的元素个数大于等于k，说明`mid`可能为第k小的数，比它小的数也有可能是答案。
* 这就是二分法的思路。假定查找的范围为`[left, right]`，首先计算`int mid = left + (right - left) / 2;`，然后在矩阵中计数有多少个元素小于等于`mid`，这个数量为`count`。
    * 如果`count < k`，那么第k小的数至少为`mid + 1`，所以`left = mid + 1`。
    * 反之，`right = mid`。
    * 循环结束的条件为`left >= right`，此时`left`即为答案。
* 如果在有序矩阵中计数有多少个元素小于等于`mid`：参看下列图示的思路。
    * ![kthSmallest图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/ArrayAndMatrix/kthSmallest%E5%9B%BE%E7%A4%BA.png?raw=true)
    * 蓝色表示`mid`，绿色五角星表示小于等于它的元素，红色箭头表示在进行判断时坐标的移动方向。
* 由于矩阵从上到下递增，从左到右递增。定义辅助函数`int countNotMoreThanMid(int[][] matrix, int mid, int n)`，统计小于等于`mid`的元素个数，n表示矩阵的行数。用`x,y`表示与`mid`进行比较元素的坐标:
    * 从左下角开始查找，理由见下一个tip，这样可以不对所有元素遍历。
    * 如果`matrix[y][x] <= mid`，那么列坐标为x，行坐标小于y的元素肯定都比`mid`小，包括`matrix[y][x]`一共有`y + 1`个。`matrix[y - 1][x]`已经判断比`mid`小，所以下一个直接去判断`matrix[y][x + 1]`，所以`x++`
    * 否则，还不确定`matrix[y][x]`，要对其判断`y--`。
    * 循环结束条件就是当要判断的元素坐标越过矩阵边界`x <= n && y >= 0`
    * 从循环的坐标变化路径发现，这样进行统计时，最坏的情况是从左下角走到右上角，只需要对比2*n次元素大小。
* 接下来就是完成二分查找。二分查找的次数为$log(max - min)$，这里max，min表示矩阵的最大，最小元素的值。每次查找访问数组（进行比较）2*n次，所以时间复杂度为$2nlog(max - min)$。空间复杂度为$O(1)$。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :45.4 MB, 在所有 Java 提交中击败了7.69%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
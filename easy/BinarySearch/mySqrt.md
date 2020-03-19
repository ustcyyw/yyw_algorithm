# 69. x 的平方根
### 原题

实现 `int sqrt(int x)` 函数。
计算并返回 *x* 的平方根，其中 *x* 是非负整数。
由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
**示例 1:**

```
输入: 4
输出: 2
```

**示例 2:**

```
输入: 8
输出: 2
说明: 8 的平方根是 2.82842..., 
     由于返回类型是整数，小数部分将被舍去。
```

### 解法：二分法的运用

```java
public int mySqrt(int x) {
        long lo = 0, hi = x;
        while(lo <= hi){
            long mid = (hi - lo) / 2 + lo;
            if(mid * mid > x) hi = mid - 1;
            else if(mid * mid < x) lo = mid + 1;
            else return (int)mid;
        }
        return (int)hi;
    }
```

思路分析：

* 题目要求找到x的算术平方根，只需要返回整数部分。如果答案为y，则满足$y^2=x$或者$y^2<x$，且$(y+1)^2>x$。这样看来我们只要从1开始线性查找，肯定可以找到唯一一个满足上述两个不等式的y，就是答案。

* 查找，从1,2,3....n进行查找（当然，肯定到不了n），待检查的元素已经排序完成，要找到唯一的目标值。就很容易想到这可能是一个二分查找可以完成的任务。

* 从二分法的做法来考虑。中间元素`long mid = (hi - lo) / 2 + lo;`，一开始说过我们的目标是要计算平方的。

    * 如果`mid * mid > x`，说明`mid`比我们要找的答案大，那么查找就应该在区间`[1, mid - 1]`进行。
    * 如果`mid * mid < x`，有可能`mid`是我们要找的答案，也有可能答案在区间`[mid + 1, hi]`中。
    * 如果`mid * mid = x`，则刚好x的算术平方根为整数，`mid`就是答案，返回它。

* 二分查找的结束条件如常规的那样`lo > hi`时，`while`循环结束。循环结束时，我们需要思考，`lo`与`hi`哪一个数才是答案。就类似于[排序数组中寻找插入位置](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/BinarySearch/searchInsert.md)，可以通过举例子来判断最终应该返回哪一个值。比如求8的根，如图示：

    ![mySqrt图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/BinarySearch/mySqrt%E5%9B%BE%E7%A4%BA.png?raw=true)

    答案为2，所以最终应该返回hi。

* 注意：本题使用int，中间求平方可能会溢出，所以使用long。

* 时间复杂度为$O(log(n))$；只使用了3个辅助`long`型变量，空间复杂度为$O(n)$。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了79.59%的用户
* 内存消耗 :37.3 MB, 在所有 Java 提交中击败了5.09%的用户
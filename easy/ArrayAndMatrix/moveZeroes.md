# 283.移动零

### 原题
给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

示例:
输入: [0,1,0,3,12]
输出: [1,3,12,0,0]
说明:

必须在原数组上操作，不能拷贝额外的数组。
尽量减少操作次数。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/move-zeroes)：https://leetcode-cn.com/problems/move-zeroes

### 两种解法

##### 1.我的解法

```java
 	public void moveZeroes(int[] nums) {
        int start = 0;
        for (int i = 0; i < nums.length && nums[i] != 0; i++)
            start++;
        int d = 0; // 用来表示连续的0的个数
        for (int i = start; i < nums.length; i++) {
            int j = i + d;
            for (; j < nums.length && nums[j] == 0; j++)
                d++;
            if (j >= nums.length)
                return;
            exchange(nums, i, j);
        }
    }

    private void exchange(int[] a, int i, int j) {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
```

思路分析：

* 题目描述非常直白，要做的事情只有一个，就是要把0移到数组最后，把非0元素按顺序往前移动，占据原来是0的地方。那么我们肯定是需要两个索引的，一个索引指向0，一个索引指向非0。
* 可以先找到第一个为0的元素，索引记为`start`，然后去找位于其后的第一个非0元素。如果我们只是从第一个0元素的下一个索引开始查找其后的非0元素，如果在交换过程中出现很多连续的0，这样做会使得查找非0元素的内循环非常缓慢。比如[0,1,0,3,12]在第一次交换后变为[1,0,0,3,12]就出现了两个连续的0。
* 并且连续的0的数量至少保持不变，可能增加。比如[1,0,0,3,12]再进行一次交换得[1,3,0,0,12]，连续的0的数量没有减少。所以我们可以用一个变量`d`存放当前有多少个0连续。这样在查找非0元素的时候，直接跳过当前已知的连续的0,从`j = i + d`开始查找。
* 当然，在上一个过程中可能会发现连续0的数量增多了，那么我们需要更新`d`值。
* 当查找非0元素索引的时候，发现`j`已经等于`nums.length`说明工作完成。
* 时间复杂度是$O(n)$的，虽然看起来有两层循环，但其实只要`j`一直增加，增加到`nums.length`时就结束了。空间负责度是$O(1)$的。

代码解释：

* 3-4行，找到第一个0元素。
* 第6行开始的循环，不断将前面的0与后面的非0交换。第7行，查找非0元素的时候，直接跳过当前已知的连续的0。
* 8-9行，查找非0元素，如果初始查找位置为0，说明连续的0的数量增加了，要`d++`。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.8 MB, 在所有 Java 提交中击败了5.07%的用户

##### 官方解法

```java
	public void moveZeroes3(int[] nums) {
        int zeroIndex = 0;
        for(int nonZeroIndex = 1; nonZeroIndex < nums.length; nonZeroIndex++){
            if(nums[zeroIndex] == 0){
                if(nums[nonZeroIndex] != 0){ // nonZeroIndex所指元素非0
                    exchange(nums, zeroIndex, nonZeroIndex);
                    zeroIndex++;
                }
                // 只是zeroIndex所指元素为0， 但nonZeroIndex所指的也是0，外循环使得nonZeroIndex++去找下一个非0元素
            } else { // (nums[zeroIndex] ！= 0的情况，不需要交换，此时zeroIndex++向右移动去找0元素，外循环使得nonZeroIndex++去找下一个与0交换的元素
                zeroIndex++;
            }
        }
    }
```

思路分析：

* 一样需要两个索引，一个指向靠前的0元素，一个指向靠后的非0元素。

代码解释：

* 见代码块中的注释。
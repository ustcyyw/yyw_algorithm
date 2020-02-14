# 一维数组是否存在重复元素

### 原题

给定一个整数数组，判断是否存在重复元素。
如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。

示例 1:
输入: [1,2,3,1]
输出: true

示例 2:
输入: [1,2,3,4]
输出: false

示例 3:
输入: [1,1,1,3,3,4,3,2,4,2]
输出: true

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/contains-duplicate)：https://leetcode-cn.com/problems/contains-duplicate

----

### 两种解法

##### 1.使用 HashSet （我的第一解）

```java
public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> numsSet = new HashSet<>((int) (nums.length / 0.75F + 1.0F));
        for(int num: nums){
            if(!numsSet.contains(num))
                numsSet.add(num);
            else return true;
        }
        return false;
    }
```

思路分析：

* 要判断数组中是否有重复元素，可以遍历数组的同时记录数组中出现了哪些元素，如果某个元素已经被记录过，说明数组中有重复元素。
* 当遍历结束后没有发现重复元素，则返回`true`。
* 遍历了一遍数组，但遍历的同时使用了哈希表的添加与查找是否存在，这两个操作时间成本都是均摊$O(log(k))$，所以这个算法时间复杂度为$O(nlog(k))$。空间复杂度为$O(k)$。其中k为哈希表中元素的个数。
* 当然，计算散列函数可能会耗费一些时间。注意，将哈希表的初始容量设置为`(int) (nums.length / 0.75F + 1.0F)`

运行结果：
* 执行用时 :12 ms, 在所有 Java 提交中击败了63.45%的用户
* 内存消耗 :53.6 MB, 在所有 Java 提交中击败了5.11%的用户


##### 2.排序后看相邻元素（我的第二解）

```java
public boolean containsDuplicate2(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0; i < nums.length - 1; i++){
            if(nums[i] == nums[i + 1])
                return true;
        }
        return false;
    }
```

思路分析：

* 如果先将数组排序，数组中有重复元素则会排到相邻位置，只需要遍历数组判断是否有相邻元素相等即可。
* 排序的时间复杂度为$O(nlog(n))$，即为此算法时间复杂度。空间复杂度为$O(1)$或者$O(n)$，这取决于排序算法是否使用了辅助数组，具体选择何种排序算法是和数组大小有关的。

运行结果：
* 执行用时 :6 ms, 在所有 Java 提交中击败了85.44%的用户
* 内存消耗 :51.1 MB, 在所有 Java 提交中击败了5.11%的用户


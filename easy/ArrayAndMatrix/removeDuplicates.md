# 26. 删除排序数组中的重复项

### 原题
给定一个**排序数组**，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

示例 1:
给定数组 `nums = [1,1,2]`, 
函数应该返回新的长度 2, 并且原数组 `nums` 的前两个元素被修改为 1, 2。 
你不需要考虑数组中超出新长度后面的元素。

示例 2:
给定` nums = [0,0,1,1,1,2,2,3,3,4]`,
函数应该返回新的长度 5, 并且原数组` nums` 的前五个元素被修改为 0, 1, 2, 3, 4。
你不需要考虑数组中超出新长度后面的元素。

说明:
为什么返回数值是整数，但输出的答案是数组呢?
请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
你可以想象内部操作如下:

```
// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
int len = removeDuplicates(nums);

// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array)：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array

### 解法：原地操作的解法

```java
public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;
        int i = 0;
        for(int j = i + 1; j < nums.length; j++){
            if(nums[j] != nums[i]){
                nums[++i] = nums[j];
            }
        }
        return i + 1;
    }
```

思路分析：

* 答案要求返回的是删除了所有重复元素后的数组的长度，也就是删除重复元素后最后一个元素的索引+1。
* 将删除重复元素后最后一个元素的索引设为`i`，那么区间`[0,i]`是已经没有重复元素的子数组。从这个定义来看，只有一个元素的子数组`nums[0]`是已经没有重复元素的子数组，所以`i`的初值为0。我们不断将不重复的元素换到`i+1`，子数组最后一个元素的索引`i++`。
* 如何去找不重复的元素？因为数组是排序数组，所以提供了便利。用图示更好说明，就以` nums = [0,0,1,1,1,2,2,3]`举例子。
    * ![removeDuplicates图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/ArrayAndMatrix/removeDuplicates%E5%9B%BE%E7%A4%BA.png?raw=true)
* 从整个过程来看，只通过指针`j`遍历了一次数组，所以时间复杂度为$O(n)$，这是原地操作数组，所以空间复杂度为$O(1)$。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了99.71%的用户
* 内存消耗 :41.4 MB, 在所有 Java 提交中击败了9.63%的用户
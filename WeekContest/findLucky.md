# 5368. 找出数组中的幸运数

### 第182周周赛第一题 easy
在整数数组中，如果一个整数的出现频次和它的数值大小相等，我们就称这个整数为「幸运数」。
给你一个整数数组 `arr`，请你从中找出并返回一个幸运数。
如果数组中存在多个幸运数，只需返回 最大 的那个。
如果数组中不含幸运数，则返回 -1 。

示例 1：
输入：`arr = [2,2,3,4]`
输出：2
解释：数组中唯一的幸运数是 2 ，因为数值 2 的出现频次也是 2 。

示例 2：
输入：`arr = [1,2,2,3,3,3]`
输出：3
解释：1、2 以及 3 都是幸运数，只需要返回其中最大的 3 。

示例 3：
输入：`arr = [2,2,2,3,3]`
输出：-1
解释：数组中不存在幸运数。

示例 4：
输入：`arr = [5]`
输出：-1

示例 5：
输入：`arr = [7,7,7,7,7,7,7]`
输出：7

提示：
`1 <= arr.length <= 500`
`1 <= arr[i] <= 500`

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/find-lucky-integer-in-an-array)：https://leetcode-cn.com/problems/find-lucky-integer-in-an-array

### 解法 使用HashMap

```java
public int findLucky(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : arr)
            map.put(i, map.getOrDefault(i, 0) + 1);
        int res = -1;
        for(int key : map.keySet()){
            if(key == map.get(key))
                res = Math.max(res, key);
        }
        return res;
    }
```

思路分析：

* 由题目对幸运数字的定义，我们需要先统计每一个元素出现的次数，这很显然使用`HashMap`就可以，键为元素，值为该元素出现的次数。统计某个元素的出现次数，如果该元素的值与出现次数相同，他就是一个幸运数字。
* 对`map`中的键进行遍历。题目要求返回最大的幸运数组，所以当遇到幸运数字时，需要进行更新`res = Math.max(res, key);`
* 注意，没有幸运数的时候返回-1，所以初始状态的`res = -1`。在循环中没有找到幸运数，将其返回时才不会出现错误。
* 时间复杂度为$O(n)$，空间复杂度为$O(n)$。


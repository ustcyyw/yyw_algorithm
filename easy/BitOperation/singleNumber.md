# 136. 只出现一次的数字

### 原题
给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

说明：
你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

示例 1:
输入: [2,2,1]
输出: 1

示例 2:
输入: [4,1,2,1,2]
输出: 4

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/single-number)：https://leetcode-cn.com/problems/single-number

### 两种解法

##### 1.使用HashMap

```java
public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : nums){
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        for(int i : nums){
            if(map.get(i) == 1)
                return i;
        }
        return -1;
    }
```

思路分析：

* 只有一个元素出现了一次，其他元素都出现了两次。那么只需要通过HashMap计数每个元素出现的次数即可。键位元素，值为该元素出现的次数。
* 统计完成后，对所有键遍历，直到找到某个键对应的值为1，该键即为答案。
* 时间复杂度为$O(n)$，空间复杂度为$O(n)$
* 使用HashSet也可以，某个元素不存在与Set中时，放入Set，如果已经存在，说明是重复元素，将其删除即可。最终Set中只有我们要找的那个只出现一次的元素。

##### 2.异或的运用

```java
public int singleNumber2(int[] nums) {
        int res = 0;
        for(int i : nums)
            res ^= i;
        return res;
    }
```

思路分析：

*  题目要求使用常数的空间。所以使用HashMap/Set的方法不可以。
*  第一次接触完全想不到使用位运算，但是可以注意这个题目中涉及到的异或的一些性质。
  *  异或可以将相同的两个数变为0 即 x ^ x = 0
  *  异或具有交换律。
  *  0与一个数异或结果仍然为该数 0 ^ x = x
*  两个相同的数进行异或操作会得到0，同时异或具有交换律，所以对于这个无序数组，成对的元素依次异或的结果为0。同时0 ^ x = x，所以最终只出现的一个元素会剩下。


运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了99.75%的用户
* 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.02%的用户
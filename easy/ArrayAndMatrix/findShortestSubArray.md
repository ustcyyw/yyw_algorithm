# 697. 数组的度

### 原题
给定一个非空且只包含非负数的整数数组 `nums`, **数组的度的定义是指数组里任一元素出现频数的最大值**。
你的任务是找到与` nums` 拥有相同大小的度的最短连续子数组，返回其长度。

示例 1:
输入: [1, 2, 2, 3, 1]
输出: 2
解释: 
输入数组的度是2，因为元素1和2的出现频数最大，均为2.
连续子数组里面拥有相同度的有如下所示:
[1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
最短连续子数组[2, 2]的长度为2，所以返回2.

示例 2:
输入: [1,2,2,3,1,4,2]
输出: 6

注意:
`nums.length `在1到50,000区间范围内。
`nums[i]` 是一个在0到49,999范围内的整数。
来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/degree-of-an-array)：https://leetcode-cn.com/problems/degree-of-an-array

### 两种解法

##### 1.我的解法

```java
	public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            if (countMap.containsKey(num))
                countMap.put(num, countMap.get(num) + 1);
            else
                countMap.put(num, 1);
        }
        // 得到数组的度
        int maxCount = 0;
        for (int key : countMap.keySet()) {
            if (countMap.get(key) > maxCount)
                maxCount = countMap.get(key);
        }
        if (maxCount == 1)
            return 1;
        // 出现频率最高的数 可能不止一个
        List<Integer> maxNums = new ArrayList<>();
        for (int key : countMap.keySet()) {
            if (countMap.get(key) == maxCount)
                maxNums.add(key);
        }
        // 最小的数组，它至少得刚好包括出现频率最高的数
        int minLength = nums.length;
        for (int num : maxNums) {
            // 区间开始的索引
            int start = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == num) {
                    start = i;
                    break;
                }
            }
            int end = 0;
            for (int j = start + 1, remain = maxCount - 1; j < nums.length; j++) {
                if (nums[j] == num)
                    remain--;
                if (remain == 0) {
                    end = j;
                    break;
                }
            }
            minLength = Math.min(end - start + 1, minLength);
        }
        return minLength;
    }
```

思路分析：

* 先从数组度的定义出发，元素出现次数的最大值。要找到与` nums` 拥有相同大小的度的最短连续子数组，首先我们得先求出数组的度是多少。子数组要拥有与原数组相同的度，意味着子数组要包含 决定原数组度的 所有元素，比如示例1与2中的元素2。
* 但是决定原数组度的元素 可能不止一个，比如数组`[1,1,2,2,3,3]`，元素1，2，3都决定这个数组的度是2。包含所有1，或者包含所有2，或者包含所有3的子数组都是满足要求的子数组，找到他们之中长度最小的即可。
* 所以第一步，确定原数组的度。第二步，找到所有决定原数组度的元素。第三步，确定分别包含所有这些元素的子数组的长度。
    * 第一步，使用`Map<Integer, Integer> countMap`，Map的键为元素的值，Map的值为该元素出现的次数。遍历原数组后得到Map，再遍历Map找到最大的出现次数，就得到了数组的度`maxCount`。
    * 第二步，遍历Map找到出现次数等于`maxCount`的所有值，放入一个` List<Integer> maxNums`
    * 第三步，对于每个在`maxNums`中的值，去找它第一次出现的索引及最后一次出现的索引以得到子数组长度。更新最小值`minLength = Math.min(end - start + 1, minLength);`
* 时间复杂度，两次遍历散列表中的键，常数次便利原数组，是线性的。

代码解释：

* 第35行，`remain = maxCount - 1`，因为前面在找开始时的索引已经找到一次该值，后续每找到一次就使得`remain--`，当在某一次减少剩余次数为0时，找到了最后一次出现的索引。

运行结果：
* 执行用时 :25 ms, 在所有 Java 提交中击败了83.29%的用户
* 内存消耗 :53 MB, 在所有 Java 提交中击败了5.01%的用户

##### 2.官方解法

```java
	public int findShortestSubArray2(int[] nums) {
        Map<Integer, Integer> left = new HashMap<>(), right = new HashMap<>(), count = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            left.putIfAbsent(x, i); // 如果x是第一次出现，放入Map中，从而left可以记录某个数字第一次出现的索引
            right.put(x, i);
            count.put(x, count.getOrDefault(x, 0) + 1); // 记录x出现的次数
        }
        int maxCount = Collections.max(count.values());
        int result = nums.length;
        for (int key : count.keySet()) {
            if (count.get(key) == maxCount)
                result = Math.min(result, right.get(key) - left.get(key) + 1);
        }
        return result;
    }
```

思路分析：

* 大体思路是一致的，都是要先确定数组的度，然后找到是哪些值确定了数组的度，然后再分别找到包含等于这些值的元素的子数组长度，取其中最小的。
* 不同的是，用两个散列表分别记录各个值的元素第一次出现的位置与最后出现的位置。` Map<Integer, Integer> left = new HashMap<>(), right = new HashMap<>()`。在遍历的时候遇到一个`left`中没有的键，就添加键及索引。`right`不管遇到什么键都要更新索引。在此次遍历的同时也统计各个值出现的次数。
* 之后遍历计数散列表中的键，如果某个值出现的次数等于`maxCount`，在`left`和`right`分别查找第一次，最后一次出现的索引得到子数组的长度。更新`result = Math.min(result, right.get(key) - left.get(key) + 1);`
* 时间复杂度是线性的。

运行结果：
* 执行用时 :43 ms, 在所有 Java 提交中击败了42.63%的用户
* 内存消耗 :50.4 MB, 在所有 Java 提交中击败了5.01%的用户

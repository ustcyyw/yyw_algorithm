# 5381. 查询带键的排列

### 184周周赛第二题 medium
给你一个待查数组 queries ，数组中的元素为 1 到 m 之间的正整数。 请你根据以下规则处理所有待查项 queries[i]（从 i=0 到 i=queries.length-1）：
一开始，排列 P=[1,2,3,...,m]。
对于当前的 i ，请你找出待查项 queries[i] 在排列 P 中的位置（下标从 0 开始），然后将其从原位置移动到排列 P 的起始位置（即下标为 0 处）。注意， queries[i] 在 P 中的位置就是 queries[i] 的查询结果。
请你以数组形式返回待查数组  queries 的查询结果。

示例 1：
输入：queries = [3,1,2,1], m = 5
输出：[2,1,2,1] 
解释：待查数组 queries 处理如下：
对于 i=0: queries[i]=3, P=[1,2,3,4,5], 3 在 P 中的位置是 2，接着我们把 3 移动到 P 的起始位置，得到 P=[3,1,2,4,5] 。
对于 i=1: queries[i]=1, P=[3,1,2,4,5], 1 在 P 中的位置是 1，接着我们把 1 移动到 P 的起始位置，得到 P=[1,3,2,4,5] 。 
对于 i=2: queries[i]=2, P=[1,3,2,4,5], 2 在 P 中的位置是 2，接着我们把 2 移动到 P 的起始位置，得到 P=[2,1,3,4,5] 。
对于 i=3: queries[i]=1, P=[2,1,3,4,5], 1 在 P 中的位置是 1，接着我们把 1 移动到 P 的起始位置，得到 P=[1,2,3,4,5] 。 
因此，返回的结果数组为 [2,1,2,1] 。  

示例 2：
输入：queries = [4,1,2,2], m = 4
输出：[3,1,2,0]

示例 3：
输入：queries = [7,5,5,8,3], m = 8
输出：[6,5,0,7,5]


提示：

```
1 <= m <= 10^3
1 <= queries.length <= m
1 <= queries[i] <= m
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/queries-on-a-permutation-with-key)：https://leetcode-cn.com/problems/queries-on-a-permutation-with-key

### 两种方法

##### 1.直接按题目要求暴力法

```java
public int[] processQueries(int[] queries, int m) {
        int[] nums = new int[m];
        for (int i = 0; i < m; i++)
            nums[i] = i + 1;
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int index = search(nums, queries[i]);
            res[i] = index;
            change(nums, index);
        }
        return res;
    }

    private void change(int[] nums, int index) {
        int temp = nums[index];
        for (int i = index; i > 0; i--) {
            nums[i] = nums[i - 1];
        }
        nums[0] = temp;
    }

    private int search(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target)
                return i;
        }
        return -1;
    }
```

思路分析：

* 观察示例1，对每一个`queries`中的元素先在`P`中查找，将元素对应的索引放入结果数组，然后调整`P`中元素的位置。二层循环，m小于1000，所以这个数据直接暴力就行。
* 初始时，先将`P`准备出来。然后按步骤模拟。
* 辅助函数`int search(int[] nums, int target)`查找目标元素的索引
* 辅助函数`void change(int[] nums, int index)`，在查找某个元素后，修改数组`P`，将该元素前的元素后移一位然后该元素置于数组第一个位置。
* 时间复杂度为$O(n * m)$，空间复杂度为$O(n + m)$。

运行结果：

* 8ms

##### 2.索引与数字一一映射

```java
public int[] processQueries2(int[] queries, int m) {
        int[] numToIndex = new int[m + 1]; // 数字到索引的映射
        for (int i = 1; i <= m; i++)
            numToIndex[i] = i;
        int[] indexToNum = new int[m + 1];
        change(numToIndex, indexToNum, 1);
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = numToIndex[queries[i]] - 1;
            change(numToIndex, indexToNum, queries[i]);
        }
        return res;
    }

    private void change(int[] numToIndex, int[] indexToNum, int num) {
        int index = numToIndex[num]; // 该数字现在的索引
        numToIndex[num] = 1;
        for (int i = 1; i < index; i++) // 索引在该数字之前的数indexToNum[i]，他们的索引都要+1
            numToIndex[indexToNum[i]] += 1;
        for (int i = 1; i < numToIndex.length; i++) // 更新索引到数字的映射
            indexToNum[numToIndex[i]] = i;
    }
```

思路分析：

* 比赛的时候我没有一开始就使用暴力法，而是想建立数字与索引之间的一一映射。这样在查找某个元素的时候，直接由映射关系得到索引，然后修改映射。
* 用数组来创建一一映射。
    * `int[] numToIndex`表示数字到数组`P`索引的映射。`numToIndex`的索引表示数字，值表示该数字数组`P`索引。
    * `int[] indexToNum`表示数组`P`索引到数字的映射。`indexToNum`的索引表示数组`P`索引，值表示数字
    * 注意这里为了方便，将数组`P`的索引设定为从1开始。所以两个数组的长度均为`m + 1`。这样初始时候，索引与数字刚好相同。
* 按题目要求的查找就简化为`numToIndex[queries[i]]`，但是这里`P`中的索引从1开始，所以还要-1才是结果。
* 查找之后修改映射，使用辅助函数`void change(int[] numToIndex, int[] indexToNum, int num)`.
    * 首先，知道当次被查找的数字的索引为`int index = numToIndex[num]`
    * 然后，当次被查找的数字的索引要改为1（他被放到P的第一个位置）
    * 然后，索引在该数字之前的所有数字`indexToNum[i] for (int i = 1; i < index; i++)`，他们的索引都要加1，因为他们都会后移一位。
    * 最后，更新索引到数字的映射`indexToNum[numToIndex[i]] = i;`（这是按照映射的定义一定成立的式子）。

运行结果：

* 5ms
# 1124. 表现良好的最长时间段
### 原题
给你一份工作时间表 hours，上面记录着某一位员工每天的工作小时数。
我们认为当员工一天中的工作小时数大于 8 小时的时候，那么这一天就是「劳累的一天」。
所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
请你返回「表现良好时间段」的最大长度。

示例 1：
输入：hours = [9,9,6,0,6,6,9]
输出：3
解释：最长的表现良好时间段是 [9,9,6]。

提示：

`1 <= hours.length <= 10000`
`0 <= hours[i] <= 16`

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/longest-well-performing-interval)：https://leetcode-cn.com/problems/longest-well-performing-interval

### 2种解法
##### 1.暴力法
```java
public int longestWPI(int[] hours) {
        int n = hours.length;
        for(int i = 0; i < n; i++){
            hours[i] = hours[i] > 8 ? 1 : -1;
        }
        int res = 0;
        for(int i = 0; i < n; i++){
            int count = 0;
            for(int j = i; j < n; j++){
                count += hours[j];
                if(count > 0)
                    res = Math.max(res, j - i + 1);
            }
            if(n - i <= res)
                return res;
        }
        return res;
    }
```
思路分析：
* 工作时间大于8则属于劳累的一天，否则属于悠闲一天。对于数组的每个元素，只有两种状态，劳累or悠闲。要知道劳累的天数多还是优先天数多，就可以用1表示当天劳累，-1表示当天优先。对于天数区间`[i,j]`，累加值为正，说明劳累天数更多。
* 所以可以先将工作天数转化为用1，-1表示状态的数组`for(int i = 0; i < n; i++) hours[i] = hours[i] > 8 ? 1 : -1;`
* 然后进行二重循环，对于每一个天数区间`[i,j]`，计算这个区间的累加和`count += hours[j];`，如果大于0则更新「表现良好时间段」的最大长度。`res = Math.max(res, j - i + 1);`
    * 循环中如果发现`[i, n - 1]`的长度小于等于`res`，那么在这个范围内的所有区间都不会产生更长的表现良好时间段，就直接返回`res`。
* 时间复杂度为$O(n^2)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :326 ms, 在所有 Java 提交中击败了31.59%的用户
* 内存消耗 :42.4 MB, 在所有 Java 提交中击败了5.61%的用户

##### 2.哈希表记录前缀和的索引
```java
public int longestWPI2(int[] hours) {
        int sum = 0;
        int res = 0;
        Map<Integer, Integer> sumToIndex = new HashMap<>();
        for(int i = 0; i < hours.length; i++){
            int temp = hours[i] > 8 ? 1 : -1;
            sum += temp;
            if(sum > 0)
                res = i + 1;
            else {
                if(!sumToIndex.containsKey(sum))
                    sumToIndex.put(sum, i);
                if(sumToIndex.containsKey(sum - 1))
                    res = Math.max(res, i - sumToIndex.get(sum - 1));
            }
        }
        return res;
    }
```
思路分析：
* 同方法一中用1表示当天劳累，-1表示当前悠闲。
* 其实方法一中我们计算天数需要的仅仅是知道该区间上累加和大于0，以及区间的左右边界。我们在累加中，使用`sum[i]`表示`[0,i]`的累加和。如果`sum[j] - sum[i] > 0`，说明`[i + 1, j]`是劳累天数更多，这是一个表现良好时间段。我们可以在计算累加和的过程中记录累加和的值对应的索引信息，但是累加和可能会重复，使用哈希表记录时为了避免覆盖出差，就需要仔细考虑什么时候该记录，什么时候不需要记录。
    * 如果`sum[i] > 0`，说明从0到`i`这一天，是表现良好时间段。累加到`i`的过程中，这个区间一定是目前最长的良好时间段，所以`res = i + 1`。在这个前提下，如果后序`sum[j] - sum[i] > 0`，说明`sum[j] > 0`且`j > i `，那么区间`[0,j]`的长度大于`[0,i]`，直接更新`res = j + 1`就可以。所以累加和为正的索引不需要记录，因为左边界固定为0，直接更新就可以。
    * 如果`sum[i] <= 0`，是否就需要记录呢？如果有`x < i`且`sum[x] == sum[i]`，那么再往后如果有`sum[j] - sum[i] > 0`，也一定会有`sum[j] - sum[x] > 0`，且区间长度是`[x,j]`大于`[i,j]`，所以`i`这个信息是没有意义的，因为答案一定不是`[i,j]`。所以当`sum[i] <= 0`时，只记录第一次出现该值的索引。所以要先判断，如果`!sumToIndex.containsKey(sum)`，才记录前缀和的值及对应的索引` sumToIndex.put(sum, i);`
    * 另一个关键点如果`sum[i] <= 0`，要去查看是否有`x < i`，且`sum[i] - sum[x] > 0`，这样的区间是满足题意的时间段，但是这样的区间不止一个，要找最长的一个。其实最长的一个就是`sum[i] - sum[x] == 1`的`[x, i]`。理由如下
        * 假设`sum[i] = -1`，如果`sum[i] - sum[y] == 2`，那么`sum[y] = -3`。
        * 我们记录的是某个累加和第一次出现的索引，如果要能累加得到`sum[y] = -3`，前面一定会先出现`sum[x] = -2`（不然怎么可能靠每次-1得到-3），那此时区间`[x, i]`长度肯定大于`[y, i]`。
        * 所以`sum[i] - sum[x] == 1`，就是能满足`sum[i] - sum[x] > 0`的最长区间。
        * 所以`if(sumToIndex.containsKey(sum - 1))`，就更新`res = Math.max(res, i - sumToIndex.get(sum - 1));`
* 遍历完一次后，返回`res`，时间复杂度为$O(n)$，空间复杂度取决于哈希表中的元素的个数。
* 当然这个题中，题目的题设`hours.length <= 10000`，前缀和最小为-10000，所以也可以用数组代替哈希表，索引为键表示`sum + 10000`，值则为前缀和数组的索引。

运行结果：
* 执行用时 :22 ms, 在所有 Java 提交中击败了71.30%的用户
* 内存消耗 :41.3 MB, 在所有 Java 提交中击败了5.61%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
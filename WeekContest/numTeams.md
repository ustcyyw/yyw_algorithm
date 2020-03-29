# 5369. 统计作战单位数

### 第182周周赛第二题 medium
 n 名士兵站成一排。每个士兵都有一个 独一无二 的评分 rating 。
每 3 个士兵可以组成一个作战单位，分组规则如下：
从队伍中选出下标分别为` i、j、k` 的 3 名士兵，他们的评分分别为 `rating[i]、rating[j]、rating[k]`
作战单位需满足： `rating[i] < rating[j] < rating[k]` 或者` rating[i] > rating[j] > rating[k] ，其中  0 <= i < j < k < n`
请你返回按上述条件可以组建的作战单位数量。每个士兵都可以是多个作战单位的一部分。

示例 1：
输入：rating = [2,5,3,4,1]
输出：3
解释：我们可以组建三个作战单位 (2,3,4)、(5,4,1)、(5,3,1) 。

示例 2：
输入：rating = [2,1,3]
输出：0
解释：根据题目条件，我们无法组建作战单位。

示例 3：
输入：rating = [1,2,3,4]
输出：4

提示：
`n == rating.length`
`1 <= n <= 200`
`1 <= rating[i] <= 10^5`

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/count-number-of-teams)：https://leetcode-cn.com/problems/count-number-of-teams

### 两种解法

##### 1.确定中间人选j(评论区大佬的解法)

```java
public int numTeams2(int[] rating) {
        int res = 0;
        int n = rating.length;
        for(int i = 1; i < n - 1; i++){
            int[] countLeft = count(0, i - 1, rating, rating[i]);
            int[] countRight = count(i + 1, n - 1, rating, rating[i]);
            res += countLeft[0] * countRight[1]; // 以i为中间士兵，左边比他大的数量 * 右边比他小的数量
            res += countLeft[1] * countRight[0]; // 以i为中间士兵，左边比他小的数量 * 右边比他大的数量
        }
        return res;
    }

    private int[] count(int lo, int hi, int[] rating, int target){
        int[] res = new int[2];
        for(int i = lo; i <= hi; i++){
            if(rating[i] > target) // 计数区间[lo, hi]上比target大的元素的数量
                res[0]++;
            if(rating[i] < target)
                res[1]++;
        }
        return res;
    }
```

思路分析：

* 这个题是一个组合问题，求出满足题意的所有组合有多少组。在做数学组合题的时候，总会优先选定特殊元素。本题如果先选择第一个士兵或者最后一个士兵，就得使用暴力法来枚举出所有符合条件的组合。所以将目光放到中间元素上。
* 从1到`rating.length - 1`的士兵都有可能被选为中间士兵，根据加法原理要将所有这些情况下满足题意的组合数量相加。那么讨论第`i`个士兵被选为中间士兵的情况后，对`i`从`[1, rating.length - 1]`遍历加和即为最终答案。
* 对于第`i`个士兵被选为中间士兵的情况。其左边要选择一个比他小的元素，右边要选一个比他大的元素或者左边要选择一个比他大的元素，右边要选一个比他小的元素。
    * 其左边比他小的元素共有`a`个，右边比他大的元素有`b`个，那么这种组合方式一共有`a*b`种。
    * 所以只需要统计`[0, i-1]`与`[i + 1, rating.length - 1]`这两个区间上分别有多少个士兵比他大，比他小即可。
* 辅助函数`int[] count(int lo, int hi, int[] rating, int target)`对区间`[lo, hi]`统计比`target`大和小的元素数量，返回一个二元数组，数组第一个元素代表区间[lo, hi]上比target大的元素的数量。
* 辅助函数进行单层遍历，主函数中进行了一次遍历，所以时间复杂度为$O(n^2)$。空间复杂度为$O(1)$

##### 2.暴力解决

```java
public int numTeams(int[] rating) {
        int n = rating.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (rating[i] > rating[j] && rating[j] > rating[k])
                        count++;
                    if (rating[i] < rating[j] && rating[j] < rating[k])
                        count++;
                }
            }
        }
        return count;
    }
```

思路分析：

* 在做赛题的时候，看到`1 <= n <= 200`如此小的数据量，直接暴力法翻译题目也就通过了。
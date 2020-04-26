# 5393. 可获得的最大点数
### 原题
几张卡牌 排成一行，每张卡牌都有一个对应的点数。点数由整数数组 `cardPoints` 给出。
每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
你的点数就是你拿到手中的所有卡牌的点数之和。
给你一个整数数组 `cardPoints` 和整数` k`，请你返回可以获得的最大点数。 

示例 1：
输入：`cardPoints = [1,2,3,4,5,6,1], k = 3`
输出：12
解释：第一次行动，不管拿哪张牌，你的点数总是 1 。但是，先拿最右边的卡牌将会最大化你的可获得点数。最优策略是拿右边的三张牌，最终点数为 1 + 6 + 5 = 12 。

示例 2：
输入：`cardPoints = [2,2,2], k = 2`
输出：4
解释：无论你拿起哪两张卡牌，可获得的点数总是 4 。

示例 3：
输入：`cardPoints = [9,7,7,9,7,7,9], k = 7`
输出：55
解释：你必须拿起所有卡牌，可以获得的点数为所有卡牌的点数之和。

示例 4：
输入：`cardPoints = [1,1000,1], k = 1`
输出：1
解释：你无法拿到中间那张卡牌，所以可以获得的最大点数为 1 。 

示例 5：
输入：`cardPoints = [1,79,80,1,1,1,200,1], k = 3`
输出：202


提示：

```
1 <= cardPoints.length <= 10^5
1 <= cardPoints[i] <= 10^4
1 <= k <= cardPoints.length
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/maximum-points-you-can-obtain-from-cards)：https://leetcode-cn.com/problems/maximum-points-you-can-obtain-from-cards

### 解法
```java
public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length, scoreR = 0, scoreL = 0;
        for(int i = 0; i < k; i++)
            scoreL += cardPoints[i];
        int res = scoreL;
        for(int l = 1; l <= k; l++){
            scoreL -= cardPoints[k - l];
            scoreR += cardPoints[n - l];
            res = Math.max(res, scoreR + scoreL);
        }
        return res;
    }
```
思路分析：
* 这个第二题，其实和本周赛第一题基本是同样的，只不过数据量不一样。可以看这个题解[5392. 分割字符串的最大得分](https://github.com/ustcyyw/yyw_algorithm/blob/master/WeekContest/maxScore.md)。两个题都是要将得分记为左右两部分之后，都要求最大值。只不过两个题的赋分方式不同，本题的分值即为`carPoints[i]`。
* 本题规定了拿去牌只能从头或者尾，不可以抽取中间的，所以选取方式最终都等价于，左边一个连续子序列和右边一个连续子序列的元素之后，这两个子序列元素和为k。所以也是从所有可能的分割方式中找到最大的元素和。
* 为了避免对于每一种可能的左右子数组组合都遍历元素，我们也可以使用`scoreL, scoreR`分别表示左右子数组的元素之和。
    * 初始状态先将左边子数组分k个元素，右边子数组分0个元素，所以先通过`for(int i = 0; i < k; i++) scoreL += cardPoints[i];`得到这种分割方式的左右子数组元素之和。
    * 用`l`表示右边左数组的元素个数，`l = 0`的情况就是刚才说的初始状态，允许左子数组为空，所以循环从`int l = 1`开始，循环条件为`l <= k`。
    * `l`从0每增加1，都要将左边子数组最后一个元素拿走，然后向右边子数组从右向左添加一个元素。`scoreL -= cardPoints[k - l]; scoreR += cardPoints[n - l];`。更新完子数组和后更新所要找的最大值。
* 时间复杂度为$O(k)$，空间复杂度为$O(1)$。

运行结果：

* 2ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
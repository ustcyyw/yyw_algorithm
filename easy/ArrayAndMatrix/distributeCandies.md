# 1103.分糖果

### 原题
我们买了一些糖果 candies，打算把它们分给排好队的 n = num_people 个小朋友。
给第一个小朋友 1 颗糖果，第二个小朋友 2 颗，依此类推，直到给最后一个小朋友 n 颗糖果。
然后，我们再回到队伍的起点，给第一个小朋友 n + 1 颗糖果，第二个小朋友 n + 2 颗，依此类推，直到给最后一个小朋友 2 * n 颗糖果。
重复上述过程（每次都比上一次多给出一颗糖果，当到达队伍终点后再次从队伍起点开始），直到我们分完所有的糖果。注意，就算我们手中的剩下糖果数不够（不比前一次发出的糖果多），这些糖果也会全部发给当前的小朋友。
返回一个长度为 num_people、元素之和为 candies 的数组，以表示糖果的最终分发情况（即 ans[i] 表示第 i 个小朋友分到的糖果数）。

示例 1：
输入：candies = 7, num_people = 4
输出：[1,2,3,1]
解释：
第一次，ans[0] += 1，数组变为 [1,0,0,0]。
第二次，ans[1] += 2，数组变为 [1,2,0,0]。
第三次，ans[2] += 3，数组变为 [1,2,3,0]。
第四次，ans[3] += 1（因为此时只剩下 1 颗糖果），最终数组变为 [1,2,3,1]。

示例 2：
输入：candies = 10, num_people = 3
输出：[5,2,3]
解释：
第一次，ans[0] += 1，数组变为 [1,0,0]。
第二次，ans[1] += 2，数组变为 [1,2,0]。
第三次，ans[2] += 3，数组变为 [1,2,3]。
第四次，ans[0] += 4，最终数组变为 [5,2,3]。

提示：
1 <= candies <= 10^9
1 <= num_people <= 1000

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/distribute-candies-to-people)：https://leetcode-cn.com/problems/distribute-candies-to-people

### 解法

```java
	public int[] distributeCandies(int candies, int num_people) {
        int[] res = new int[num_people];
        int count = 1, remain = candies;
        for(int i = 0; ; i = (i + 1) % num_people){
            if(remain > count){
                res[i] += count;
                remain -= count;
                count++;
            }
            else{
                res[i] += remain;
                break;
            }
        }
        return res;
    }
```

思路分析：

* 根据题目描述，每分给一个人，下一次要分的数量就要加1，当糖果不够要分的数量时，全部分给该人，然后分糖果结束。
* 所以我们有两个信息很重要，一是当前一次性要分几个糖果给一个小朋友，用`count`表示，二是还有多少剩余糖果,用`remain`表示。
* 整个逻辑就很清晰，当剩余糖果`remain`小于等于一次性要分的糖果数`count`，就把`remain`都给他然后分发结束。否则就分`count`给他，然后下一次一次性要分的糖果数+1，更新剩余糖果数。
* 问题唯一稍微要注意的地方可能是索引`i`，因为这是一个循环分发的过程。所以`i = (i + 1) % num_people`而不是简单的`i++`。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了90.48%的用户
* 内存消耗 :37.2 MB, 在所有 Java 提交中击败了5.27%的用户

### 反思

循环数组的使用，比如用数组实现队列，用数组实现线性探测法的散列表。在更新索引的时候都可以使用`i = (i + 1) % n`这样的方式进行。
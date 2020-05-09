# 面试题61. 扑克牌中的顺子
### 原题
从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。 

示例 1:
输入: [1,2,3,4,5]
输出: True

示例 2:
输入: [0,0,1,2,5]
输出: True

限制：
数组长度为 5 
数组的数取值为 [0, 13] .

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/bu-ke-pai-zhong-de-shun-zi-lcof)：https://leetcode-cn.com/problems/bu-ke-pai-zhong-de-shun-zi-lcof

### 解法
```java
public class isStraight {
    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int countZero = 0, i = 0, gap = 0;
        for (; i < 5 && nums[i] == 0; i++)
            countZero++;
        for (i = i + 1; i < 5; i++) {
            if (nums[i] == nums[i - 1])
                return false;
            if (nums[i] - nums[i - 1] > 1)
                gap += nums[i] - nums[i - 1] - 1;
        }
        return countZero >= gap;
    }
}
```
思路分析：
* 0可以换成任意数字，他是拿来填补不连续的。比如3，5中间的空缺需要一个0填补；2，5之间的空缺需要两个0来填补；1，2，3，4，5之间没有空缺所以不需要填补。
* 判断是否能连成顺子，有两关键点：有多少个空缺，有多少个0。为了计算这两个数据，就先将数组进行排序。
    * 于是0就在数组最前面了，` for (; i < 5 && nums[i] == 0; i++) countZero++;`得到0的个数。
    * 上一个循环结束后，`i`指向第一个非0元素，要计算除0以外的数字的空缺，就要用相邻的两个非0数字作差。所以`i = i + 1`，并且如果后一个数字减去前一个数字的差为1，说明没有空缺；差大于1，则出现空缺，空缺为`nums[i] - nums[i - 1] - 1;`。
* 计算完`countZero, gap`。如果0的个数大于等于空缺个数，则可以完成填补形成顺子，否则不行。所以返回`return countZero >= gap;`。
* 使用了排序，时间复杂度为$O(nlog(n))$，空间复杂度为$O(1)$。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了84.84%的用户
* 内存消耗 :37.5 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
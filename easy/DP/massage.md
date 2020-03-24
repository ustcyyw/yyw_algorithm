# 10.17面试题 17.16. 按摩师
### 原题

一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。

示例 1：
输入： [1,2,3,1]
输出： 4
解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。

示例 2：
输入： [2,7,9,3,1]
输出： 12
解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。

示例 3：
输入： [2,1,4,5,3,1,1,3]
输出： 12
解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/the-masseuse-lcci)：https://leetcode-cn.com/problems/the-masseuse-lcci

### 解法

```java
public int massage(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;
        int first = 0, second = nums[0];
        for(int i = 1; i < nums.length; i++){
            int temp = second;
            second = Math.max(nums[i] + first, second);
            first = temp;
        }
        return second;
    }
```

思路分析：

* 这个题其实看一下示例之后很容易发现，就是 [198题.打家劫舍i](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/DP/rob198.md)。
* 这里做了一个状态压缩，因为计算截至`i`号最大的预约时间只需要`i-1`，`i - 2`的最大预约时间及`nums[i]`。所以只需要用两个变量`first, second`分别表示前一天，前前一天的状态即可。
* 扩展：
    * [213.打家劫舍ii](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/DP/rob213.md)
    * [337.打家劫舍iii](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/DP/rob337.md)


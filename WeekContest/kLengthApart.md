# 5401. 是否所有 1 都至少相隔 k 个元素
### 原题
给你一个由若干 0 和 1 组成的数组 nums 以及整数 k。如果所有 1 都至少相隔 k 个元素，则返回 True ；否则，返回 False 。 

示例 1：
输入：nums = [1,0,0,0,1,0,0,1], k = 2
输出：true
解释：每个 1 都至少相隔 2 个元素。

示例 2：
输入：nums = [1,0,0,1,0,1], k = 2
输出：false
解释：第二个 1 和第三个 1 之间只隔了 1 个元素。

示例 3：
输入：nums = [1,1,1,1,1], k = 0
输出：true

示例 4：
输入：nums = [0,1,0,1], k = 1
输出：true

提示：
1 <= nums.length <= 10^5
0 <= k <= nums.length
nums[i] 的值为 0 或 1

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/check-if-all-1s-are-at-least-length-k-places-away)：https://leetcode-cn.com/problems/check-if-all-1s-are-at-least-length-k-places-away

### 解法
```java
public class kLengthApart {
    public boolean kLengthApart(int[] nums, int k) {
        for (int pre = -100000, next = 0; next < nums.length; next++) {
            if (nums[next] == 1) {
                if (next - pre <= k)
                    return false;
                pre = next;
            }
        }
        return true;
    }
}
```
思路分析：
* 对于每一个1，与其左右最近的1的间距大于等于k，则保证了所有的1的间距大于等于k。所以只要顺序去查找1，然后将两个1的间距进行判断即可。只要这个过程中有两个相邻的1间距小于k就不满足题意。
* 需要计算两个相邻的1的间距，需要知道这两个1的索引是多少，所以使用双指针`pre, next`分别表示一前一后两个的索引。
* 通过单层循环，不断去找下一个1：
    * 将初始`pre = -100000, next = 0`。因为第一个1的左边没有0，并不需要判断判断间距，但是按照这个代码逻辑，会计算间距，那就保证计算出的间距一定大于k就好了。数组长度最长为10000，将`pre = -100000`就能保证。
    * 如果`nums[next] == 1`，找到了下一个1。此时两个1之间的间距为`next - pre + 1`，间距小于`k`也就是`next - pre <= k`。返回`false`。然后要进行下一组相邻1的判断，将`pre = next`，循环`next++`，于是从`pre + 1`继续去找下一个1。
    * 否则继续移动`next`。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

再贴一个更直观的代码（没有本质区别）：

```java
class Solution {
    public boolean kLengthApart(int[] nums, int k) {
        int pre = 0, next;
        int n = nums.length;
        for(; pre < n; pre ++){
            if(nums[pre] == 1){
                break;
            }
        }
        for(next = pre + 1; next < n; next++){
            if(nums[next] == 1){
                if(next - pre <= k)
                    return false;
                pre = next;
            }
        }
        return true;
    }
}
```

运行结果：

* 1ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
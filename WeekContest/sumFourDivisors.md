# 5178.四因数

### 181周周赛 第二题
给你一个整数数组` nums`，请你返回该数组中恰有四个因数的这些整数的各因数之和。
如果数组中不存在满足题意的整数，则返回 0 。 

示例：

输入：`nums = [21,4,7]`
输出：32
解释：
21 有 4 个因数：1, 3, 7, 21
4 有 3 个因数：1, 2, 4
7 有 2 个因数：1, 7
答案仅为 21 的所有因数的和。

提示：

```
1 <= nums.length <= 10^4
1 <= nums[i] <= 10^5
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/four-divisors)：https://leetcode-cn.com/problems/four-divisors

### 解法

```java
public int sumFourDivisors(int[] nums) {
        int sum = 0;
        for (int i : nums)
            sum += getOne(i);
        return sum;
    }

    private int getOne(int num) {
        if((int)Math.sqrt(num) == Math.sqrt(num))
            return 0;
        int sum = 0;
        int count = 0;
        for (int i = 2; i <= (int)Math.sqrt(num); i++) {
            if (num % i == 0) {
                sum += i;
                sum += num / i;
                count++;
            }
            if (count > 1)
                return 0;
        }
        return count == 1 ? sum + 1 + num : 0;
    }
```

思路分析：

* 这个题本身思路非常直观。只需要遍历每一个数，判断一个数是否有且只有4个因数（从`0~nums[i]`的遍历）。但是这样做会导致超时，所以要想办法优化。
* 在判断一个数是否有且只有4个因数的时候。
    * 首先明确，除去1和那个数本身，还有且仅有两个因数。
    * 其次，当找到某一个数`i`使得`num % i == 0`，那么必然会有`num / i`也是`num`的一个因数。所以当找到第一个因数的时候，我们已经得到至少四个因数了。
    * 从`i=2`开始进行遍历，遇到第一个因数`a`时，一定有`a < num / a`。`i`继续增加到`num / a`时，就会产生重复计数，所以要有一个截至的值。
    * 一个数的因子一定小于`(int)Math.sqrt(num)`，所以要求`i <= (int)Math.sqrt(num)`这样也能避免上述的重复。
    * 在`[2, (int)Math.sqrt(num)]`能且只能找到一个因数的才是符合题目要求的数。所以用一个变量`count`来计数，找到一个因数就让`count++`，然后记录因数和的变量`sum += i, sum += num / i`。当`count > 1`时，这个数不满足题意，直接返回因数加和为0。
    * 在循环结束后，有可能`count = 0`，此时也不符合四因数要求，返回0。否则，不要忘记加上1和`num`本身。
    * 注意`i <= (int)Math.sqrt(num)`这样的处理，会导致一个问题，比如4，9这样的数会被认为是合理的数，所以在进入遍历前，先判断是否是平方数，如果是`(int)Math.sqrt(num) == Math.sqrt(num)`就返回0。
* 剩下的就是对每一个数调用求因素和的函数，然后累加得到答案。

运行结果：

* 难度 medium 29 ms


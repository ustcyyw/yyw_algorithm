# 523. 连续的子数组和
### 原题
给定一个包含非负数的数组和一个目标整数 k，编写一个函数来判断该数组是否含有连续的子数组，其大小至少为 2，总和为 k 的倍数，即总和为 n*k，其中 n 也是一个整数。

示例 1:
输入: [23,2,4,6,7], k = 6
输出: True
解释: [2,4] 是一个大小为 2 的子数组，并且和为 6。

示例 2:
输入: [23,2,6,4,7], k = 6
输出: True
解释: [23,2,6,4,7]是大小为 5 的子数组，并且和为 42。

说明:
数组的长度不会超过10,000。
你可以认为所有数字总和在 32 位有符号整数范围内。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/continuous-subarray-sum)：https://leetcode-cn.com/problems/continuous-subarray-sum

**更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm),如果觉得还不错请别吝啬你的star~**
### 2种解法
##### 1.前缀和
```java
public boolean checkSubarraySum(int[] nums, int k) {
        int sum = 0;
        int[] preSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            preSum[i + 1] = sum;
        }
        for (int i = 0; i < preSum.length; i++) {
            for (int j = i + 2; j < preSum.length; j++) {
                int temp = preSum[j] - preSum[i];
                if ((temp == 0 && k == 0) || (k != 0 && temp % k == 0))
                    return true;
            }
        }
        return false;
    }
```
思路分析：
* 连续子数组`[i, j]`，要枚举出所有`i, j`的组合，就需要两重循环，如果对于每一组`i, j`再使用循环求子数组的和，就是三重循环了。
* 但是对于连续子数组，不必对于每一组`i, j`都求子数组的和，可以使用前缀和数组。记`preSum[i]`为索引区间`[0, i - 1]`的所有元素之和。那么：
    * `preSum[0]`就表示没有任何元素，其值为0。由于要记录没有任何元素的子数组，`preSum`这个数组的长度为`nums.length + 1`
    * `preSum[j] - preSum[i]`表示的就是索引区间为`[i, j - 1]`子数组的元素之和。
    * 计算前缀和数组，需要单层循环和一个累加变量即可。
* 然后枚举所有`i, j`的组合，需要两重循环：
    * 题目要求子数组的长度至少为2（前缀和`preSum[j] - preSum[i]`对应的区间为`[i, j - 1]`，区间长度为`j - i`），所以内层循环的`j`从`i + 2`开始。
    * 这里特别要注意`k`有可能为0，这个时候直接取余，会报错。所以判断的标准，首先如果`k == 0 && temp = 0`，说明这个子数组是0的n倍。（这个题目这种例子挺无语的。）；然后才判断`k != 0`后判断`temp % k == 0`，如果成立，也满足题意了。以上两种情况均返回`true`。
    * 注意上一个tip中逻辑判断的顺序不能反，逻辑判断的执行是会"短路"的。
* 如果在上述二重循环中没有返回，说明没有找到符合题意的子数组，那么返回`false`。
* 时间复杂度$O(n^2)$，空间复杂度为$O(n)$。

运行结果：
* 执行用时 :21 ms, 在所有 Java 提交中击败了47.96%的用户
* 内存消耗 :40.4 MB, 在所有 Java 提交中击败了20.00%的用户

##### 2.使用HashMap的单次遍历
```java
public boolean checkSubarraySum2(int[] nums, int k){
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>(); // 键为 preSum % k, 值为索引，当然要特殊处理k == 0的情况
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int temp = k == 0 ? sum : sum % k;
            if(map.containsKey(temp)){ // 出现相同的键，如果子数组长度少于2， 不需要更新值。
                if(i - map.get(temp) > 1) // 子数组要求长度至少为2。
                    return true;
                continue;
            }
            map.put(temp, i);
        }
        return false;
    }
```
思路分析：
* 类似于方法一前缀和的做法，方法二也是类似的归约的过程，如果降低时间复杂度？说实话我自己一开始是没想到的，参考了别人的做法：使用HashMap记录前缀和取模的值，以及该值对应的索引。`Map<Integer, Integer> map = new HashMap<>();`键为 `preSum % k`, 值为索引。
* 在计算前缀和的过程中，如果前缀和取模的值出现重复，两个重复值之间的间隔大于1（题目要求子数组长度至少为2）则返回`true`，否则循环结束后返回`false`。然后这里我们来详细证明一下为什么这样做是正确的。
* 首先，得知道`(A + B) % k = (A % k + B) % k`，加法减法没有区别，也就是`(A - B) % k = (A % k - B) % k`。
    * 使用`sum`表示前缀和根据题目要求，`(sum[j] - sum[i]) % k = 0`也就是`(sum[j] % k - sum[i]) % k = 0`也就是`sum[j] % k - sum[i] = n * k`，其中n为非负整数。
    * 然后对上式两边对k取余，即`sum[j] % k % k - sum[i] % k = n * k % k`，显然左边第一项就等于`sun[j] % k`，右边为0。
    * 移项就得到`sum[j] % k = sum[i] % k`。
    * 也就是说，如果子数组`[i, j - 1]`的和`sum[j] - sum[i]`是k的n倍，那么`sum[j] % k = sum[i] % k`。
* 所以通过哈希表去查看是否会在求前缀和的过程中对k取模得到的值相等即可，也就是`map.containsKey(temp)`，记录每一个取余结果的索引的意义是：要求子数组的长度至少为2，所以当出现对k取模相等时，还要判断`i - map.get(temp) > 1`，如果成立，就返回`true`;否则继续循环。
    * 注意这里不需要更新索引，因为是要求数组的最小长度，所以记录第一个索引就好。
* 同方法一，
    * 首先要将表示没有元素的那种情况记录`map.put(0, -1);`
    * 其次要特殊处理k为0的情况，此时不能取模，这种情况是要求前缀和之差为0也就是`sum[i] == sum[j]`，所以map记录的就是前缀和而不是取余。所以在累加`sum += nums[i];`之后记录值`map.put(temp, i);`之前，要先处理在map中记录什么值`int temp = k == 0 ? sum : sum % k;`。

运行结果：
* 执行用时 :4 ms, 在所有 Java 提交中击败了67.83%的用户
* 内存消耗 :40.7 MB, 在所有 Java 提交中击败了20.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
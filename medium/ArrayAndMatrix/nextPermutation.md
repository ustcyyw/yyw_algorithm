# 31. 下一个排列
### 原题
实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
必须原地修改，只允许使用额外常数空间。
以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/next-permutation)：https://leetcode-cn.com/problems/next-permutation

### 解法
```java
public class nextPermutation {
    public void nextPermutation(int[] nums) {
        int n = nums.length - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                int j = nums.length - 1;
                for (; j > i && nums[j] <= nums[i]; j--) ;
                exch(nums, i, j);
                reverse(nums, i + 1, n);
                return;
            }
        }
        reverse(nums, 0, n);
    }

    private void exch(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int lo, int hi){
        while(lo < hi){
            exch(nums, lo++, hi--);
        }
    }
}
```
思路分析：
* 要将一个数排列成下一个更大的数，改动高位数字，改变后的数一下就会变得很大，不是下一个更大的数。所以改动数字应该从低位开始。
* 举几个例子：
    * 一个数为1234。从低位开始，4是最低位，其右边没有数字与其交换。然后3，其右边有4比它大，二者交换就得到下一个更大的排列。
    * 一个数为123654。从低位开始，4是最低位，其右边没有数字与其交换。然后5，其右边没有比它大的数，与4交换只会使得排列更小。然后考虑6，与5同理。然后考虑3，可以和后面的4交换得到下一个更大的排列。
* 所以从低位开始的意义就是：
    * 从`int i = n-1`(其中`n = nums.length`)开始，如果`nums[i] >= nums[i + 1]`，说明其前一个元素更大，不做任何处理。这样递推只要`nums[i] >= nums[i + 1]`成立，说明`nums[i]`是目前最大的数字，与其右边的数字交换得到的都是更小的排列。
    * 当`nums[i] < nums[i + 1]`时，`nums[i]`右边的数字有比它大的了，但是为了保证找到下一个更大的排列而不是任意更大排列，还需要通过一个循环去找到其右边比`nums[i]`大的最小的那个数。从上一条中可以知道，`nums[i]`右边的元素是递减排列的，所以也只需要从右往左，找到第一个大于`nums[i]`的元素。`for(; j > i && nums[j] <= nums[i]; j --);`内循环结束，`j`就是要用于交换的元素。
    * 交换完后，只保证了`i`这一位及其高位排列正确了，但是`i`的右边还是递减的数字，不是下一个最大的排列。
    * 例如上面的例子123654，交换后为124653，还需要将`i`右边变为升序（124356）。这里就是`[i + 1, n]`的子数组翻转，双指针可以完成（再次强调`nums[i]`右边的元素是递减排列的）。辅助函数`void reverse(int[] nums, int lo, int hi)`完成区间`[lo, hi]`上的数组翻转。所以调用`reverse(nums, i + 1, n);`
    * 如果一直没有元素满足`nums[i] < nums[i + 1]`，说明不存在下一个更大的排列，则将数字重新排列成最小的排列，整个数组降序排列，此时将其翻转即可`reverse(nums, 0, n);`。
* 上述过程的交换是原地操作，数组翻转也只使用了交换操作，所以整个过程都是原地操作。
* 使用双指针完成数组翻转的话，时间复杂度是线性的。空间复杂度为$O(1)$的。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了99.86%的用户
* 内存消耗 :39 MB, 在所有 Java 提交中击败了30.38%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
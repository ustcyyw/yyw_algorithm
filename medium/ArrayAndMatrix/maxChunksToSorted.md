# 769. 最多能完成排序的块
### 原题
数组`arr`是`[0, 1, ..., arr.length - 1]`的一种排列，我们将这个数组分割成几个“块”，并将这些块分别进行排序。之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
我们最多能将数组分成多少块？

示例 1:
输入: `arr = [4,3,2,1,0]`
输出: 1
解释:
将数组分成2块或者更多块，都无法得到所需的结果。
例如，分成 [4, 3], [2, 1, 0] 的结果是 [3, 4, 0, 1, 2]，这不是有序的数组。

示例 2:
输入:` arr = [1,0,2,3,4]`
输出: 4
解释:
我们可以把它分成两块，例如 [1, 0], [2, 3, 4]。
然而，分成 [1, 0], [2], [3], [4] 可以得到最多的块数。
注意:

`arr` 的长度在 [1, 10] 之间。
`arr[i]`是` [0, 1, ..., arr.length - 1]`的一种排列。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/max-chunks-to-make-sorted)：https://leetcode-cn.com/problems/max-chunks-to-make-sorted

### 解法：判断区间内元素是否合理，双指针
```java
public int maxChunksToSorted(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; ) {
            int j = i + 1;
            for (; j <= arr[i]; j++) {
                if (arr[j] > arr[i]) { // 在arr[i]到其正确位置的区间内，有比它大的数，不能分为一个块
                    break;
                }
            }
            if (j <= arr[i]) { // j <= arr[i]意味着：在arr[i]在其正确位置区间内，有比它大的数
                i = j; // 指针i移动到j，arr[i]成为当前最大的数字，下一次循环要去判断[j + 1,arr[i]]内有没有比它大的数
            } else {
                count++;
                i = arr[i] + 1;
            }
        }
        return count;
    }
```
思路分析：
* 题目中规定，数组中的元素只能是`[0, 1, ..., arr.length - 1]`，所以对于数字`i`排序后其索引为`i`，那么对于数字`arr[i]`升序排序后索引为`arr[i]`。
* 上面的结论也就是说，要完成分块，那么`arr[i]`这个数字所在块对应的区间右边界至少为`arr[i]`，该块的区间最小为`[i, arr[i]]`，否则对这个块排序后`arr[i]`是绝对不会放在正确的索引`arr[i]`处。
* 但是为什么是至少呢？因为在区间`[i, arr[i]]`上，不确定是否有元素`arr[j] > arr[i]`。如果有，将这个块划分为`[i, arr[i]]`排序后，`arr[i]`这个元素不会被放在索引`arr[i]`处（被更大的元素占据了），不是正确位置。
* 所以要得到最多能分为几块，就要尽可能将区间分得细碎，每一个小区间都需要保证，小区间的右边界索引刚好是小区间内最大的元素的值，否则无法还原成升序数组。
* 有了这个指导思想，可以通过双指针一次遍历数组得到答案，如下图所示：
    * ![maxChunksToSorted图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/ArrayAndMatrix/maxChunksToSorted%E5%9B%BE%E7%A4%BA.png?raw=true)
    * 首先，对于数字`arr[i]`，其可能最小的块为`[i, arr[i]]`，要去验证这个最琐碎的块是否满足题意，就需要从`int j = i + 1`开始，遍历区间`[i, arr[i]]`去判断这个区间内最大的数是否为`arr[i]`。
        * 如果不是即`if (arr[j] > arr[i]) break`，内循环会被中断。此时`j <= arr[i]`，这种情况下`[i, arr[i]]`的划分不满足题意，将指针`i`移动到`j`，在下一次外循环中判断区间分割右边界是否为`arr[i]`。
        * 反之，说明区间`[i, arr[i]]`合理，区间块数+1，且指针`i`移动到`j+1`，在下一次外循环中继续去寻找最小的合理区间。
* 外循环结束后，返回`count`。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :36.8 MB, 在所有 Java 提交中击败了33.33%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
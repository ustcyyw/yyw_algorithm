# 45. 跳跃游戏 II
### 原题
给定一个非负整数数组，你最初位于数组的第一个位置。
数组中的每个元素代表你在该位置可以跳跃的最大长度。
你的目标是使用最少的跳跃次数到达数组的最后一个位置。

示例:
输入: [2,3,1,1,4]
输出: 2
解释: 跳到最后一个位置的最小跳跃数是 2。
从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。

说明:
假设你总是可以到达数组的最后一个位置。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/jump-game-ii)：https://leetcode-cn.com/problems/jump-game-ii

### 解法
```java
public class jump {
    public int jump(int[] nums) {
        int n = nums.length;
        int[] count = new int[n];
        int start = 0;
        for(int i = 0; i < n && start < n; i++){
            if(i + nums[i] < start)
                continue;
            for(int j = start; j < n && j <= i + nums[i]; j++)
                count[j] = count[i] + 1;
            start = i + nums[i] + 1;
        }
        return count[n - 1] - 1;
    }
}
```
思路分析：
* 一开始是想用动态规划的，结果超时了，代码如下：

    * ```java
        class Solution {
            public int jump(int[] nums) {
                int n = nums.length;
                int[] count = new int[n];
                Arrays.fill(count, Integer.MAX_VALUE);
                count[0] = 0;
                for(int i = 1; i < n; i++){
                    for(int j = 0; j < i; j++){
                        if(nums[j] >= i - j){
                            count[i] = Math.min(count[i], count[j] + 1);
                        }
                    }
                }
                return count[n - 1];
            }
        }
        ```

    * 不得不想想为什么会超时，原来有很多的更新操作是徒劳的。举个例子：比如到达第2，3，4元素都需要1步，从第2，3，4元素都可以到达第五个元素，那么到达第五个元素需要2步，从第2个元素跳过来与从第3，4元素调过来没有区别，在这个超时做饭中都会进行更新。

* 所以如何克服重复计算的问题呢。继续上面的例子，如果从第二个元素可以跳到第5个元素，到达第五个元素的步数就确定为2了，后续就不用尝试从3，4个元素跳到五了。这个意思就是：如果我们使用`count[i]`表示到达第`i`个元素需要的最少步数，那么我们只对`count[i]`赋值一次。这样做的正确性可以这样思考：

    * 假设第0个元素可以跳到第`i`个元素，无法跳到第`i + 1`个元素。
    * 那么`count[0]`到`count[i]`肯定被赋值了第一次，赋值为1。
    * 对于第1个元素自然只能从第0个元素跳过去。
    * 对于第2个元素，从第1个元素跳过去需要两次，从第0个元素跳过去只需要1次。显然只要不是智障，肯定从第0个元素跳过去，赋值过的`count[2]`不需要再赋值。
    * 同理对于第3个元素，从第1，2个元素跳过去需要2次，当然从第0个元素直接跳过去，赋值过的`count[3]`不需要再赋值。
    * 同理直到第`i`个元素都只一次赋值为1即可。
    * 那么第`i + 1`个元素，此时`count[i + 1]`还没有赋值，因为第0个元素无法跳到那，这就注定了这个元素要从`[1, i]`的元素跳过去，所以依次去查看从这`[1, i]`中的哪一个元素可以跳过去，比如找到第`j`个元素，然后给`count[i + 1]`赋值为2，第一次赋值之后，如果再去考虑从`[j + 1, i]`中跳过去效果一致，就没必要去查看了，所以还是只赋值了一次。

* 所以解决问题的关键就是，对任何一个`count[i]`只赋值一次。我们用`int start`表示当前哪一个元素（的索引）及其后面的元素没有赋值，那么`[0, start - 1]`已经被赋值了一次，`[start, n - 1]`还需要进行一次赋值。

* 在这个问题中，我们让每一个元素尽可能的去对`count`赋值。对于元素`nums[i]`，其能跳到的最远地方为`i + nums[i]`。

    * 如果`i + nums[i] < start`说明它的跳跃范围内的所有`count`元素都被赋值过了，就跳过。
    * 否则，就将`count`数组从索引`start`开始赋值至`i + nums[i]`，`count[j] = count[i] + 1;`。在完成这些元素的唯一一次赋值后，别忘记`start`的意义，所以它被更新为`i + nums[i] + 1;`
    * 可以看下面的图示来再理解一下，以[2,3,1,1,4,2,1]为例子：
    * ![jump图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/hard/Greedy/jump%E5%9B%BE%E7%A4%BA.png?raw=true)

* PS：其实这个解法挺像是BFS的：

    * 最少步数就类比于最短路径。
    * 可以跳跃到的元素就类比于相邻结点。
    * 只对`count`元素赋值一次类比于搜索过程中每个元素只访问一次。

* 这里注意两点：

    * 外循环条件`start < n`，因为如果`start >= n`说明所有的`count`数组元素都被赋值过了，后面就不需要继续循环了。
    * 返回值`count[n - 1] - 1;`，这里减1是因为，如果仔细看代码，会发现按这个逻辑写，到第0个元素需要1步，就导致到所有元素的最小步数都比答案多1。（当然也可以在循环开始之前完成第0个元素到其可到范围内的`count`元素的初始化）

* 时间复杂度为$O(n)$的，因为只对所有`count`的元素赋值了1次，空间复杂度为$O(n)$的。

运行结果：

 * 执行用时 :2 ms, 在所有 Java 提交中击败了94.93%的用户
 * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了5.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
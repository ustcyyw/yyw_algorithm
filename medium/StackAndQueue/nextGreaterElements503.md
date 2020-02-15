# 下一个更大元素503

### 原题
给定一个**循环**数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。

示例 1:
输入: [1,2,1]
输出: [2,-1,2]
解释: 第一个 1 的下一个更大的数是 2；
数字 2 找不到下一个更大的数； 
第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
注意: 输入数组的长度不会超过 10000。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/next-greater-element-ii)：https://leetcode-cn.com/problems/next-greater-element-ii

----

### 两种解法

**本题的解法，与**[当日温度](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/StackAndQueue/dailyTemperatures739.md)**这题类似，唯一不同的就是本题是一根循环数组。对于冗余信息的舍去，有效信息的保留及栈的使用结果数组的使用均可参考题"当日温度"**

##### 1.使用栈（我的第一解）

```java
public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        int loop = 2;
        while (loop > 0) { // 类比找距离下一次升温间隔天数的题目，但是本题的数组是个循环数组，所以需要两次循环才能得出结果
            for (int i = nums.length - 1; i >= 0; --i) {
                while (!stack.isEmpty() && nums[i] >= stack.peek())
                    stack.pop();
                if (!stack.empty())
                    result[i] = stack.peek();
                else
                    result[i] = -1;
                stack.push(nums[i]);
            }
            loop--;
        }
        return result;
    }
```

思路分析：

* 输出每一个元素的下一个更大的元素，意味着要知道`result[i]`，需要知道原数组`nums`中`i+1,i+2……`的信息，我们可以从数组的末尾向前开始遍历。
* 只要找下一个更大元素，所以其实有很多信息是冗余的。类似于题目“当日温度”，我们要将冗余信息剔除。冗余信息的判断也类似，即在遍历过程中`i+1,i+2……`位置的数如果比`i`这个位置的数小，就没必要记录了，但如果比`i`这个位置的数大，则连同`nums[i]`也是有用信息。
* 这个题的区别在于两点：
    * 这个数组是循环的，也就是说除了最大值外，其它所有元素都能找到下一个更大值。如果最大值不是最后一个元素，那么最大值之后的元素，有可能在单次遍历中无法找到下一个更大值。在第一次遍历中，最大值一定会被当做有效信息记录下来，所以在第二遍循环即可将答案全部找到。
    * 这里要记录的信息是值的大小，因为我们要找的答案是下一个最大值。
* 类似题目"当日温度"，我们也可以使用栈来记录有用信息，同样的操作来舍去冗余信息。
* 每个元素最多两次出入栈，所以时间复杂度为$O(n)$，空间复杂度取决于栈中元素及数组大小$O(n+k)$

代码解释：

* 4~5行，设置`loop`变量使得数组从后往前循环两遍。
* 7-8行，除去冗余的信息。9-10行，有下一个更小值时，赋值给答案`result[i]`。11-12行，第一遍循环时没找到记录为-1，第二遍循环会将其覆盖为答案（除了最大值元素）
* 13行，当前元素的大小，对于其前面的元素来说都是有用信息，放入栈中。

运行结果：
* 执行用时 :56 ms, 在所有 Java 提交中击败了41.17%的用户
* 内存消耗 :40.4 MB, 在所有 Java 提交中击败了48.94%的用户
##### 2.利用结果数组（我的第二解）

```java
public int[] nextGreaterElements2(int[] nums) {
        int[] temp = new int[nums.length * 2]; // 用来记录当前数字与下一个比它大的数组的距离（索引之差）
        // 按照方法一的思路，最多循环两次就能得到结果，这里直接将原数组加长，重复一遍也可以达到效果。
        int[] doubleNums = new int[nums.length * 2];
        System.arraycopy(nums, 0, doubleNums, 0, nums.length);
        System.arraycopy(nums, 0, doubleNums, nums.length, nums.length);

        for (int i = doubleNums.length; i >= 0; --i) {
            for (int j = i + 1; j < doubleNums.length; j += temp[j]) { // temp[j]表示j这个位置的数与下一个比它大的数的距离
                if (doubleNums[i] < doubleNums[j]) {
                    temp[i] = j - i;
                    break;
                } else if (temp[j] == 0) {
                    temp[i] = 0;
                    break;
                }
            }
        }
        int[] result = new int[nums.length];
        for (int i = 0; i < result.length; i++) {
            // temp[i]表示i这个位置的数与下一个比它大的数的距离 再加上i即为比它大的数的索引。但如果temp[i] == 0，意味着i这个位置的数最大
            result[i] = temp[i] == 0 ? -1 : doubleNums[i + temp[i]];
        }
        return result;
    }
```

思路分析：

* 本方法关键同"当日温度"题，使用一个数组`temp`记录当前元素与下一个更大元素之间的距离。这样在从后往前进行遍历的时候，在查找下一个更大元素的时候，可以跳过那些冗余的元素。（详细解释看"当日温度"链接中的md文件）
* 类似于方法2中所说，处理最大值之后的元素，需要两次遍历。在本方法中没有办法去设置循环遍历，所以创建一个长度是`nums`数组两倍的数组`doubleNums`，并将`nums`中的元素按顺序copy两遍，这样就达到了两遍遍历的效果。
* 区别"当日温度"的地方在于，这里需要的答案是元素值，而不是当前元素与下一个更大元素之间的距离。所以之后还需要通过位置索引找到元素的值。例如当`temp[i]==0`时，含义就是`i`这个索引对应的元素即为最大值，所以`result[i]=-1`，否则应该赋值为最近的下一个更大元素值`doubleNums[i + temp[i]]`。
* 内循环近似认为是常数次操作，则方法中两次遍历，时间复杂度为$O(n)$。使用了一个两倍长的辅助数组及一个答案数组，所以空间复杂度$O(3n)$。

代码解释：

* 8~18行，相当于从后往前两次遍历数组，得到每个元素距离下一个元素最大值的距离。
* 20~23行，将距离信息转化为元素值的信息。`i + temp[i]`的值即为比`i`这个元素更大的下一个元素的索引。

运行结果：
* 执行用时 :4 ms, 在所有 Java 提交中击败了98.85%的用户
* 内存消耗 :40.1 MB, 在所有 Java 提交中击败了61.49%的用户

# 565.数组嵌套

### 原题

索引从0开始长度为N的数组A，包含0到N - 1的所有整数。找到并返回最大的集合S，`S[i] = {A[i], A[A[i]], A[A[A[i]]], ... }`且遵守以下的规则。

假设选择索引为`i`的元素`A[i]`为S的第一个元素，S的下一个元素应该是`A[A[i]]`，之后是`A[A[A[i]]]...` 以此类推，不断添加直到S出现重复的元素。

示例 1:

输入: `A = [5,4,0,3,1,6,2]`
输出: 4
解释: 
`A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.`

其中一种最长的 `S[K]`:
`S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}`
注意:

N是[1, 20,000]之间的整数。
A中不含有重复的元素。
A中的元素大小在[0, N-1]之间。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/array-nesting)：https://leetcode-cn.com/problems/array-nesting

---

### 两种解法

##### 1.转化为找最大的环（我的第一解）

```java
public int arrayNesting(int[] nums) {
        int result = 0;
        boolean[] flag = new boolean[nums.length]; // false表示还没有访问过该元素
        for(int i = 0; i < nums.length; i++){
            if(flag[i])
                continue;

            int count = 0;
            int index = i;
            while (!flag[index]){
                count++;
                flag[index] = true;
                index = nums[index];
            }
            result = Math.max(count, result);
        }
        return result;
    }
```

思路分析：

* 首先看集合S中的元素，第一个元素的值是第二个元素的下标，第二个元素的值是第三个元素的下标，以此类推。而且题目中说"长度为N的数组A，包含0到N - 1的所有整数"，所以每个元素的值都可以看成指向了下一个元素（索引即链接）。
* 不断将当前元素值所指的元素添加到集合S中，直到出现重复元素，这就意味着此时刚好成为一个环。并且N个元素刚好是0~N-1的N个数字，所以不可能出现两个元素指向同一元素的情况（可能有自环）。
* 所以题目转化为找结点最多的环。从第一个元素开始，先查看其是否已添加到环中，如果已经在环中则略过，因为已经将整个环的元素访问过且计数；如果没有在已计数过的环中，则不断以当前值为索引去查看环的下一个元素，直到查找都某一个元素已经在环中。
* 所以用一个布尔数组`flag`去记录已经包含在环中的元素，数组下标与`nums`下标一一对应，访问过该索引对应的元素后`flag`设置为`true`。每次开始计数一个新的环，就将变量`count`重置为0。
* 由于每个元素只访问了一次，所以时间复杂度为$O(n)$，由于使用了辅助数组，所以空间复杂度为$O(n)$。

代码解释：

* 第4行，外循环，检查每个元素是否已在计数过的环中。5~6行，如果在则直接跳过内循环。
* 10~14行，不断查找环中下一个元素，直到某个元素已在环中，成环完成。`count`最终即为当前环的元素个数，于是15行，每次内循环结束后将环的最大元素数进行更新。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :50.6 MB, 在所有 Java 提交中击败了19.36%的用户

##### 2.找最大的换，不使用额外空间（官方标答）

```java
public int arrayNesting2(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != Integer.MAX_VALUE) {
                int start = nums[i], count = 0;
                while (nums[start] != Integer.MAX_VALUE) {
                    int temp = start;
                    start = nums[start];
                    count++;
                    nums[temp] = Integer.MAX_VALUE;
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }
```

思路分析：

* 思路与方法一一致。只不过，标识某元素是否访问过可以换一种方式。在这个算法中由于每个元素只需要访问一次，所以访问过的`nums`的元素值已经没有价值。我们可以将访问过的值设置为`Integer.MAX_VALUE`,相当于已访问标识。
* 由于每个元素只访问了一次，所以时间复杂度为$O(n)$，由于在原数组进行标识，所以空间复杂度为$O(1)$。

----

### 反思

这个题的思路来源，最开始我感觉集合S很像是《算法》第四版的1.5节中，连接散点。所以才开始考虑集合S中元素之间的关系，确实是一个点链接到另外一个点的形式。


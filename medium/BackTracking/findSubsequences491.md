# 491. 递增子序列
### 原题
给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。

示例:
输入: [4, 6, 7, 7]
输出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]

说明:
给定数组的长度不会超过15。
数组中的整数范围是 [-100,100]。
给定数组中可能包含重复数字，相等的数字应该被视为递增的一种情况。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/increasing-subsequences)：https://leetcode-cn.com/problems/increasing-subsequences

### 解法
```java
public class findSubsequences491 {
    private List<List<Integer>> res;
    private int n;
    public List<List<Integer>> findSubsequences(int[] nums) {
        res = new ArrayList<>();
        n = nums.length;
        if(n == 0)
            return res;
        backTrack(nums, new int[n], 0, -1, Integer.MIN_VALUE);
        return res;
    }

    private void backTrack(int[] nums, int[] temp, int curPos, int preIndex, int pre){
        if(curPos > 1){
            List<Integer> tempRes = new ArrayList<>();
            for(int i = 0; i < curPos; i++)
                tempRes.add(temp[i]);
            res.add(tempRes);
        }

        Set<Integer> set = new HashSet<>();
        for(int i = preIndex + 1; i < n; i++){
            if(!set.contains(nums[i]) && nums[i] >= pre){
                set.add(nums[i]);
                temp[curPos] = nums[i];
                backTrack(nums, temp, curPos + 1, i, nums[i]);
            }
        }
    }
}

```
思路分析：
* 题目要求的就是一系列组合：满足元素个数大于1，并且是递增的（相等也视为递增）。组合与排列问题都想到使用回溯来解决。
* 回溯算法，要考虑回溯函数的定义`void backTrack(int[] nums, int[] temp, int curPos, int preIndex, int pre)`:
    * 首先第一个参数，显然是待选择的原数组。
    * 第二个参数`int[] temp`则是用于存放已选的数字。
    * 由于题目规定元素个数要大于1即可，所以我们需要一个变量`int curPos`这一次函数调用要寻找的数字放入`temp`的索引`curPos`处，所以当`curPos > 1`时，意味着当前`temp`的数字已经大于1个，要将这个组合放入结果List中。
        * 新建一个表示某组合的List`List<Integer> tempRes = new ArrayList<>();`，从`temp`中`[0, curPos - 1]`的数字放入`tempRes`。
        * 然后`res.add(tempRes);`
    * 由于所选的组合要递增，所以需要第五个参数`int pre`表示上一个数字是多少，当前要选的数字就必须大于`pre`。
    * 第四个参数的作用，见下一个tip。
* 关于组合问题避免结果重复的处理方式：
    * 回溯函数的第四个参数`preIndex`，表示上一个选定的元素在`nums`中的索引，则这一次数字的选取要从`preIndex + 1`开始去选择满足条件的元素。用题目给的示例举例：
        * 第一个数字选择了4，第二个数字选`nums[2]`的数字7，第三个数字选择了`nums[3]`的数字7
        * 第一个数字选择了4，第二个数字选`nums[3]`的数字7，第三个数字选择了`nums[2]`的数字7
        * 这两种组合重复，所以在组合问题中，要有一个参数表示上一次选取数字的索引来避免此类重复
    * 另外一种重复的情况是，当前`curPos`的数字选择的重复。用题目给的示例举例：
        * 第一，二个数字选择4，6，第三个数字选择`nums[2]`的数字7。
        * 第一，二个数字选择4，6，第三个数字选择`nums[3]`的数字7。
        * 这两种组合重复，所以在选择当前数字是，使用`Set<Integer> set = new HashSet<>();`来存放当前已选择的数字。
* 关于回溯：这里使用`int[] temp`来存放数字，那么在递归调用时，会将`curPos`这个索引的值不断覆盖，并且在存放结果时放入List的数字为`[0, curPos - 1]`，就自然达到了回溯的目的。
* 回溯函数的主题：首先遍历从`int i = preIndex + 1`开始，满足条件`!set.contains(nums[i]) && nums[i] >= pre`（避免当前选数重复且满足递增条件）的数字，先将其放入`set.add(nums[i]);`，然后赋值到`temp[curPos] = nums[i];`。再递归调用` backTrack(nums, temp, curPos + 1, i, nums[i]);`，参数按照前面定义传入即可。
* 主函数中，给成员变量（简化回溯函数的传参）赋值，判断如果`n == 0`则不需要选择直接返回，否则调用回溯函数` backTrack(nums, new int[n], 0, -1, Integer.MIN_VALUE);`
    * 第三个参数：这次调用要选择数字放入`temp[0]`（选第一个数字）
    * 第四个参数，使得这次调用从`-1 + 1 = 0`开始选数字。
    * 第五个参数表示，之前没有选择数字，所以第一个数字可以选任何数字（因为`nums[i] >= Integer.MIN_VALUE`肯定成立）。

运行结果：
* 执行用时 :6 ms, 在所有 Java 提交中击败了92.85%的用户
* 内存消耗 :47.3 MB, 在所有 Java 提交中击败了100.00%的用户
----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
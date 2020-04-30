# 90. 子集 II
### 原题
给定一个可能包含重复元素的整数数组 `nums`，返回该数组所有可能的子集（幂集）。
说明：解集不能包含重复的子集。

示例:
输入: [1,2,2]
输出:

```
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/subsets-ii)：https://leetcode-cn.com/problems/subsets-ii

### 解法
```java
public class subsetsWithDup {
    private List<List<Integer>> res;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        res = new ArrayList<>();
        res.add(new ArrayList<>());
        if (nums == null || nums.length == 0)
            return res;
        Arrays.sort(nums);
        for (int i = 1; i <= nums.length; i++) {
            backTrack(nums, i, 0, new int[i], 0);
        }
        return res;
    }

    private void backTrack(int[] nums, int k, int start, int[] temp, int curIndex) {
        if (curIndex == k) {
            List<Integer> tempRes = new ArrayList<>();
            for (int i : temp)
                tempRes.add(i);
            res.add(tempRes);
            return;
        }
        for (int i = start; i < nums.length - k + curIndex + 1; i++) {
            if (i != start && nums[i] == nums[i - 1]) // 重复元素的解决方式就是，在选择某一位元素时，已经选过的就跳过
                continue;
            temp[curIndex] = nums[i];
            backTrack(nums, k, i + 1, temp, curIndex + 1);
        }
    }
}
```
思路分析：
* 这个题与[78. 子集](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BackTracking/subsets78.md)基本一致，思路基本一致，代码基本也一致（回溯函数的参数，剪枝的技巧等都一致）。不同的是本题当中原集合中有重复元素，要排除重复元素带来的子集选取的重复。
* 重复元素的解决方式就是，在选择某一位元素时，已经选过的就跳过。要能达到这个要求，需要使得数组有序，这样在选择某个元素`i`的时候与前面一个元素比较，相等`nums[i] == nums[i - 1]`则说明已经在上一次循环中选择过了，所以跳过。当然如果`i == start`，这是第一个数，不需要跳过。
* 注意处理重复元素的时候不可以使用哈希表，举一个例子，对于[4,4,1,4]。第一个元素选择了索引为0的4：
    * 第二个元素选了索引为1的4，然后记录了当前选择过4，那么第二个元素还可以选择1。
    * 第三个元素上述两种情况分别选择1与索引为3的4。得到的子集是（4，4，1）与（4，1，4），从集合的角度来说，这样就出现了重复。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.8 MB, 在所有 Java 提交中击败了5.01%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
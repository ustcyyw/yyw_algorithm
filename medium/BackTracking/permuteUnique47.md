# 47.有重复数字序列的所有不同全排列

### 原题
给定一个可包含重复数字的序列，返回所有不重复的全排列。
示例:
输入: [1,1,2]
输出:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/permutations-ii)：https://leetcode-cn.com/problems/permutations-ii

----

### 解法：回溯

```java
private List<List<Integer>> result = null;
    private boolean[] used = null;

    public List<List<Integer>> permuteUnique(int[] nums) {
        result = new ArrayList<>();
        if(nums.length == 0)
            return result;
        used = new boolean[nums.length];
        backTrack(nums, new int[nums.length], 0);
        return result;
    }

    private void backTrack(int[] nums, int[] temp, int curIndex){
        if(curIndex == nums.length){
            List<Integer> tempRes = new ArrayList<>();
            for(int i : temp)
                tempRes.add(i);
            result.add(tempRes);
            return;
        }
        // 与46题的区别，有重复数字。如果某一位选定重复数字，不会得到有效结果，所以当不是当前位重复数字时才进行递归。
        HashSet<Integer> unique = new HashSet<>();
        for(int i = 0; i < nums.length; i++){
            if(!used[i] && !unique.contains(nums[i])){
                used[i] = true;
                unique.add(nums[i]);
                temp[curIndex] = nums[i];
                backTrack(nums, temp, curIndex + 1);
                used[i] = false; // 回溯 在选定这个数字的递归调用回溯到这里时，这个数字在之后的情况中，是没有用过的。
            }
        }
    }
```

思路分析：

* 与[第46题](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BackTracking/permute46.md)是相似题目，均是排列问题。但是本题的原序列中有重复元素，如果只是简单的进行全排列，会出现多个重复的结果。

* 排列问题：如果选定了第一位的数字，那么将剩余数字排列到2-n位上即可；如果选定了第二位数字，那么将剩余数字排列到3-n位上即可......以此类推，直到得到排定第n位数字。

* 仅仅从排列的角度来说，递归关系就是这样，且需要一个布尔数组`boolean[] used`来记录序列中的某个数字是否已经被使用。注意，在排列问题中，回溯时候要记得改变状态。当这一个数字在当前位的递归结束，回溯上来之后，它就变为未使用状态。

* 本题中要求排列结果没有重复，所以要考虑如何去掉重复的。我们可以以题目中的示例来看一看：

    * 仅仅做全排列时，一共有六个结果

    ```
       1     1     2
      1 2   1 2   1 1
      2 1   2 1   1 1
    ```

    * 第三第四个结果与第一第二个结果重复，这是因为选定第一位元素的时候，重复数字1被选定了两次；第六个结果与第五个结果重复，这是因为选定第二位元素的时候，重复数字1被选定了两次。
    * 由此，要去除排列的重复结果，只需要在当前位选定数字时，相等的数字只选一次。

代码解释：

* 14行`curIndex == nums.length`，意味着已经选定所有位的数字，（15-19行）此时将记录了各个位数字的`temp`数组中的数字放入List，再将单个结果放入`result`中。
* 22行，用于记录在当前位选定数字时，使用过的数字。
* 23行，给当前位选定数字的循环，但是要满足条件。第24行`!used[i] && !unique.contains(nums[i])`,第一个条件的意义是，先前位选定的数字，当前位不能用；第二个条件的意义是，选定当前位数字，相等的数字仅选一次就涵盖了不重复的所有结果。
* 25-28行，记录当前位使用了此数字，当前位此数字进入SET，`temp[curIndex]`记录当前位的数字，递归调用。29行，回溯时改变状态。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了96.34%的用户
* 内存消耗 :41.3 MB, 在所有 Java 提交中击败了17.43%的用户
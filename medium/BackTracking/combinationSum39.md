# 39.组合整数

### 原题

给定一个**无重复元素**的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的**组合**。
candidates 中的数字可以**无限制重复被选取**。
说明：
所有数字（包括 target）都是正整数。
解集不能包含重复的组合。

示例 1:
输入: candidates = [2,3,6,7], target = 7,
所求解集为:
[
  [7],
  [2,2,3]
]

示例 2:
输入: candidates = [2,3,5], target = 8,
所求解集为:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/combination-sum)：https://leetcode-cn.com/problems/combination-sum

### 解法 回溯

```java
	private List<List<Integer>> result = null;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        result = new ArrayList<>();
        if(candidates.length == 0)
            return result;
        List<Integer> temp = new ArrayList<>();
        temp.add(Integer.MIN_VALUE);
        backTrack(candidates, target, temp, 0);
        return result;
    }

    // 要求不重复，所以元素的选定要比列表中最后一个元素大
    private void backTrack(int[] candidates, int target, List<Integer> curItems, int curSum){
        if(curSum == target){
            List<Integer> temp = new ArrayList<>(curItems);
            temp.remove(0);
            result.add(temp);
            return;
        }

        int pre = curItems.get(curItems.size() - 1);
        for(int i : candidates){
            if(i >= pre && curSum + i <= target){
                curItems.add(i);
                backTrack(candidates, target, curItems, curSum + i);
                curItems.remove(curItems.size() - 1);
            }
        }
    }
```

思路分析：

* 首先题目中这是一个无重复元素的组合问题。组合问题直接想到的就是这是一个需要使用回溯来解决的问题。
* 递归关系也很常规，选定第`i`个元素之后，要得到最终结果就只需要去选择`i+1,i+2...`知道满足要求。题目要求组合中元素的和为`target`，所以`curSum == target`就是递归结束的条件。
* 组合问题中为了避免出现重复，一般都会在递归中要求当前选择的元素要大于上一个选定的元素。否则可能会出现（1，4）与（4，1）这样重复的组别。但是本题中允许`candidates`中的元素重复使用，所以将条件变为，要求当前选择的元素要大于等于上一个选定的元素。
* 进行递归的另外一个条件就是，当前元素选定后，元素的累加和`curSum + i`小于等于`target`，因为不满足这个条件，继续递归再添加元素只会使得累加和离`target`越来越远。

代码解释：

* 第7行，在空List添加第一个元素为最小int，是因为我们在递归中要对元素大小进行判断（为保持组合的唯一性），第21行`int pre = curItems.get(curItems.size() - 1);`，第23行`i >= pre`，所以当进行第一个递归时，没有事先放入一个哨兵元素，会出现错误。然后在满足题意的组合出现时，要将这个哨兵元素剔除，第16行`temp.remove(0);`。
* 22-28行，对当前所有可能的元素进行选定，单层循环。24行选定的元素放入结果集中`curItems.add(i);`，然后进行递归，递归时要将累加和进行更新` curSum + i`。
* 第26行，就是回溯`curItems.remove(curItems.size() - 1);`，将尝试过的元素剔除结果集，进行其他路径的递归。

运行结果：
* 执行用时 :5 ms, 在所有 Java 提交中击败了79.45%的用户
* 内存消耗 :41.2 MB, 在所有 Java 提交中击败了7.23%的用户
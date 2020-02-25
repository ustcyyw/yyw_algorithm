# 40.组合整数II

### 原题

给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
candidates 中的**每个数字在每个组合中只能使用一次**。

说明：
所有数字（包括目标数）都是正整数。
解集不能包含重复的组合。

示例 1:
输入: candidates = [10,1,2,7,6,1,5], target = 8,
所求解集为:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]

示例 2:
输入: candidates = [2,5,2,1,2], target = 5,
所求解集为:
[
  [1,2,2],
  [5]
]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/combination-sum-ii)：https://leetcode-cn.com/problems/combination-sum-ii

### 两种解法

##### 1.回溯（我的解法）

```java
	private List<List<Integer>> result = null;
    private boolean[] used = null;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        result = new ArrayList<>();
        if(candidates.length == 0)
            return result;
        used = new boolean[candidates.length];
        backTrack2(candidates, target, 0, new ArrayList<>(), 0);
        return result;
    }

    private void backTrack2(int[] candidates, int target, int pre, List<Integer> temp, int curSum){
        if(curSum == target){
            result.add(new ArrayList<>(temp));
            return;
        }

        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < candidates.length; i++){
            int num = candidates[i];
            if(!used[i] && !set.contains(num) && num >= pre && curSum + num <= target){
                temp.add(num);
                used[i] = true;
                set.add(num);
                backTrack2(candidates, target,num ,temp, curSum + num);
                temp.remove(temp.size() - 1);
                used[i] = false;
            }
        }
    }
```

思路分析：

* 递归结构与[第39题](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BackTracking/combinationSum.md)一致，只不过是否进行下一步递归的条件有差异。
* 重点分析一下进行递归的条件。
    * 本题中`candidates`有数值相等的元素，并且每个数字（元素）只能在集合中出现一次。为了不出现一个元素被使用两次，需要一个布尔数组记录某元素是否使用，只有某元素没有被使用时才可能对它递归。条件`!used[i]`
    * 由于`candidates`有数值相等的元素，但是集合不能重复。不能出现（1，1），（1，1）这样的组合，在`candidates`中有两个1时，只判断是否使用过并不行，所以在选定某一位数位的元素时，使用` HashSet<Integer> set`来防止出现相等数值导致的重复，递归条件`!set.contains(num)`。
    * 另外，对于集合，一个固定的条件，防止出现（1，4）（4，1）这样的重复，要求`num >= pre`。
    * 最后，当前元素选定后，元素的累加和`curSum + i`小于等于`target`，因为不满足这个条件，继续递归再添加元素只会使得累加和离`target`越来越远。条件`curSum + num <= target`
* 其余的就是处理元素的状态了。当某个元素满足上面四个条件时，递归前改变状态，回溯时也要改变状态。递归前的两个"已使用"，` used[i] = true;set.add(num);`emmmm 😫 太难描述了。

代码解释：

* 第12行，递归函数的参数解释`backTrack2(int[] candidates, int target, int pre, List<Integer> temp, int curSum)`。第三个参数是上一位选定的元素的数值，第四个参数是结果集合，第五个参数是当前结合元素的累加和。所以第8行，第一次调用时，第3和第5个传入的实参都为0。
* 26-27即为回溯的操作。当前元素的分枝调用结束后，改为未使用状体，并将其从结果集中剔除。

运行结果：
* 执行用时 :7 ms, 在所有 Java 提交中击败了48.46%的用户
* 内存消耗 :41.2 MB, 在所有 Java 提交中击败了8.49%的用户
# 216.组合总数iii

### 原题
找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种**组合中不存在重复的数字**。
说明：
所有数字都是正整数。
解集不能包含重复的组合。

示例 1:
输入: k = 3, n = 7
输出: [[1,2,4]]

示例 2:
输入: k = 3, n = 9
输出: [[1,2,6], [1,3,5], [2,3,4]]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/combination-sum-iii)：https://leetcode-cn.com/problems/combination-sum-iii

### 解法 回溯

```java
	private List<List<Integer>> result = null;
    public List<List<Integer>> combinationSum3(int k, int n) {
        result = new ArrayList<>();
        if (k >= n || n == 0)
            return result;
        backTrack3(k, n, 0, 0, 0, new int[k]);
        return result;
    }

    private void backTrack3(int k, int n, int pre, int curSum, int curIndex, int[] temp) {
        if (curSum == n && curIndex == k){
            List<Integer> tempRes = new ArrayList<>();
            for(int i : temp)
                tempRes.add(i);
            result.add(tempRes);
            return;
        }
        if(curIndex == k) // 这里要注意 元素个数有k个 但和不满足的 没有继续的可能
            return;
        // 初始化保证了不重复，第1减枝，第2个条件是可以取的元素的最大值为9
        for(int i = pre + 1; i <= n - curSum && i <= 9; i++){
            temp[curIndex] = i;
            backTrack3(k, n, i, curSum + i, curIndex + 1, temp);
        }
    }
```

思路分析:

* 这也是一个组合问题，用回溯算法解决。递归结构：当选定了第一位的数字时，要得到最后结果只需要选定第2，3...k位数字；当选定了第2位数字时，要得到最后结果只需要选定第3，4...k位数字......选定了第k个数字时，不再递归。
* 对于组合问题，要避免出现（1，4）与（4，1）这样重复的结果，都要求当前所选的数字大于前面的数字。本题中备选数字是1-9的自然数，所以在选定某一位数字时，可选的数字从`int i = pre + 1`开始。
* 要求之一是组合中元素之和为定值n：
    * 得到结果的条件之一是，元素之和为n，另外一个条件是元素的数量为k。即`curSum == n && curIndex == k`。
    * 在进行递归时，如果当前选定元素与之前所有元素的和大于`n`就不再往下递归，即要满足`i <= n - curSum`才进行递归。
* 回溯的过程，就是在下一次递归之前`temp[curIndex] = i`赋值会覆盖旧元素。
* 递归终止的条件，`curIndex == k`,只不过在满足`curSum == n`时要将此时的组合添加到结果集中，否则直接返回。

代码解释：

* 解释一下递归函数`backTrack3(int k, int n, int pre, int curSum, int curIndex, int[] temp)`的参数。`pre`表示上一个选定的元素（上一层函数调用中传入的`i`），`curSum`表示之前选定的元素之和，`curIndex`表示现在在选第几位数字，从第0位开始，`temp`存放选定的元素。

运行结果：

 * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
 * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了5.08%的用户
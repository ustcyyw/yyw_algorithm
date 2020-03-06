# 面试题57 - II. 和为s的连续正数序列

### 原题
输入一个正整数 target ，输出所有和为 target 的**连续正整数**序列（至少含有两个数）。
序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。

示例 1：
输入：target = 9
输出：[[2,3,4],[4,5]]

示例 2：
输入：target = 15
输出：[[1,2,3,4,5],[4,5,6],[7,8]]

限制：
1 <= target <= 10^5

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/he-wei-sde-lian-xu-zheng-shu-xu-lie-lcof)：https://leetcode-cn.com/problems/he-wei-sde-lian-xu-zheng-shu-xu-lie-lcof

### 解法：双指针

```java
	public int[][] findContinuousSequence(int target) {
        if(target == 2) return null;
        List<int[]> lists = new ArrayList<>();
        int min = 1, max = 2;
        int sum = 3;
        while(min <= target / 2){
            if(sum > target){
                sum -= min;
                min++;
            }
            else{
                if(sum == target)
                    lists.add(getOneArray(min, max));
                max++;
                sum += max;
            }
        }
        return lists.toArray(new int[0][]);
    }

    private int[] getOneArray(int lo, int hi){
        int[] res = new int[hi - lo + 1];
        for(int i = lo; i <= hi; i++){
            res[i - lo] = i;
        }
        return res;
    }
```

思路分析：

* 题目中提到，序列是连续的。只要知道第一个元素及最后一个元素就能确定序列的和。
* 但是每一次都进行求和显然是很费劲，用一个变量`sum`来记录当前序列的和。从序列[1,2]开始查找。所以初状态的`sum = 3`。
* 当`sum < target`时，要往序列中添加元素看是否添加过后和为目标，我们从小到达进行查找，所以这种情况下只能添加更大的元素，添加元素过后，表示序列最后一个元素的`max++`。当`sum > target`时，要往序列中删除元素，删除大的元素等于回到已经判断过的序列，所以要删除小的元素，删除之后表示序列最后一个元素的`min--`。
* 当`sum = target`时，找到一个符合的结果，此时要生成一个数组。并且要改变序列的状态去找下一个符合的结果。同样，删除最大元素是回到已经判断过的序列，所以要删除最小的元素或者添加下一个更大的元素（二者选一）

代码解释：

* 第6行，循环的条件。条件：因为序列需要至少两个数加和，如果较小的数都已经大于等于目标的一半了，就不可能再找到满足的序列。
* 18行。emmm 这题目不知道为什么发疯要返回二维数组💥

运行结果：
* 执行用时 :4 ms, 在所有 Java 提交中击败了75.32%的用户
* 内存消耗 :37.4 MB, 在所有 Java 提交中击败了100.00%的用户
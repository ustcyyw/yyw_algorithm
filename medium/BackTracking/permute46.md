# 46.全排列

### 原题

给定一个没有重复数字的序列，返回其所有可能的全排列。
示例:
输入: [1,2,3]
输出:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/permutations)：https://leetcode-cn.com/problems/permutations

----

### 一种解法

##### 1.迭代回溯

```java
 private List<List<Integer>> result = null;
    public List<List<Integer>> permute(int[] nums) {
        result = new ArrayList<>();
        if(nums == null || nums.length == 0)
            return result;

        List<Integer> remain = new ArrayList<>();
        for(int i : nums)
            remain.add(i);
        array(remain, new int[nums.length], 0);
        return result;
    }

    private void array(List<Integer> remain, int[] temp, int curIndex){
        if(remain.size() == 0){
            List<Integer> localResult = new ArrayList<>();
            for(int i : temp)
                localResult.add(i);
            result.add(localResult);
            return;
        }

        for(int i = remain.size() - 1; i >= 0; i--){
            temp[curIndex] = remain.remove(0);
            array(remain, temp, curIndex + 1);
            remain.add(temp[curIndex]);
        }
    }
```

思路分析：

* 进行全排列，当在第一位排定一个数字后，要得到全排列的结果之一，就只需要再选定剩下的2-n位数字，但是可选数字要将第一位排定的数字排除；再排定第二位的数字后，要得到全排列的结果之一，就只需要再选定3-n位数字，但是可选数字要将第一，二位排定的数字排除......以此类推，直到只剩最后一个数字可选，且该数字被放入最后一位。
* 递归的关系已经比较清楚了，在选定第`curIndex`数字时，剩余可选数字为`List<Integer> remain`，这时候我们拿出其中一个数字，放到已记录前`curIndex - 1`的数组的第`curIndex`个元素。然后就需要接着去确定`curIndex + 1`位的数字，当然，此时`remain`中要把这一次选定的数字给剔除。
* 递归的结束条件，显然就是没有剩余未排定的数字可以选时`remain.size() == 0`，此时要将这个排列以`List<Integer>`的形式放入结果集中。
* 麻烦的地方在于两点：
    * 怎么表示下一次递归时剩余可选元素，我们可以先将这次可选的元素1剔除，进行调用，调用结束后，再将元素1放回；剔除元素2，进行下一次调用，调用结束后，再将元素2放回......因为在每一位选定数字时，每个剩余可选数字都是结果之一，所以得进行这样的遍历。
    * 如何将当前已选定的值进行传递，我们使用了数组，通过索引去修改数组元素值，这样当某个结果选出来后，回溯后再往下调用时，可以将之前的排列结果进行覆盖。

代码解释：

* 4~5行，特殊情况的处理，不能忘记了。
* 7~9行，初始化可选剩余元素的列表，一开始所有数字都是可选的。
* 10行，递归调用开始，一开始所有数字均可选，要选定第一位数字，索引为0。
* 23~27行，单次遍历，将当前位所有可能的数字进行考虑。24行，`remain.remove(0)`移除第一个元素，这样就可以修改下一位调用时剩余的可选数字，并记录在`temp[curIndex]`表示这一次将该数字进行排列。25行往下一位数字进行递归调用。26行，回溯回来后，该数字在另外一种结果中又变为可选的数字了，所以重新添`remain.add(temp[curIndex])`。添加到了末尾，所以在循环中每次取第一个元素，也能将所有结果都考虑完备。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了58.96%的用户
* 内存消耗 :41 MB, 在所有 Java 提交中击败了5.03%

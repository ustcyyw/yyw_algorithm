# 5404. 用栈操作构建数组
### 原题
给你一个目标数组 target 和一个整数 n。每次迭代，需要从  list = {1,2,3..., n} 中依序读取一个数字。
请使用下述操作来构建目标数组 target ：
Push：从 list 中读取一个新元素， 并将其推入数组中。
Pop：删除数组中的最后一个元素。
如果目标数组构建完成，就停止读取更多元素。
题目数据保证目标数组严格递增，并且只包含 1 到 n 之间的数字。
请返回构建目标数组所用的操作序列。
题目数据保证答案是唯一的。

示例 1：
输入：target = [1,3], n = 3
输出：["Push","Push","Pop","Push"]
解释： 
读取 1 并自动推入数组 -> [1]
读取 2 并自动推入数组，然后删除它 -> [1]
读取 3 并自动推入数组 -> [1,3]

示例 2：
输入：target = [1,2,3], n = 3
输出：["Push","Push","Push"]

示例 3：
输入：target = [1,2], n = 4
输出：["Push","Push"]
解释：只需要读取前 2 个数字就可以停止。

示例 4：
输入：target = [2,3,4], n = 4
输出：["Push","Pop","Push","Push","Push"]

提示：

```
1 <= target.length <= 100
1 <= target[i] <= 100
1 <= n <= 100
target 是严格递增的
```

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/build-an-array-with-stack-operations

### 解法
```java
public class buildArray {
    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        int pre = 0;
        for(int i = 0; i < target.length; i++){
            for(int j = pre + 1; j < target[i]; j++){
                res.add("Push");
                res.add("Pop");
            }
            res.add("Push");
            pre = target[i];
        }
        return res;
    }
}
```
思路分析：
* `target`是一个递增序列。如果`target[i]`大于当前的数`j`，那么这个数放进去之后应该弹出，直到当前的数`j == target[i]`，才将当前数放进去。接下来就该考虑`target[i + 1]`，由于`target[i + 1] > target[i]`，那么下一个可能被放入并且保留的数应该从`target[i] + 1`开始，然后重复上述步骤。
* 外循环`for(int i = 0; i < target.length; i++)`就是构建目标数组，内循环`for(int j = pre + 1; j < target[i]; j++)`
    * 从`pre + 1`开始尝试，第一次内循环时，要构建`target[0]`，要从数字1开始构建，所以在循环外部初始化`int pre = 0`。
    * 内循环中`j < target[i]`，所以不断放入再弹出。
    * 内循环停止时，就是`j = target[i]`时，此时要`res.add("Push");`，然后更新`pre = target[i];`，这样下一次才从`target[i] + 1`开始尝试。
* 虽然是两层循环，但是`target[i]`最大为n，尝试的数字也就是从1到n，所以时间复杂度为$O(n)$。

运行结果：

* 0ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
# 5394. 对角线遍历 II
### 原题
给你一个列表` nums` ，里面每一个元素都是一个整数列表。请你依照下面各图的规则，按顺序返回` nums` 中对角线上的整数。

示例 1：
输入：`nums = [[1,2,3],[4,5,6],[7,8,9]]`
输出：`[1,4,2,7,5,3,8,6,9]`

示例 2
输入：`nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]`
输出：`[1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]`

示例 3：
输入：`nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]`
输出：`[1,4,2,5,3,8,6,9,7,10,11]`

示例 4：
输入：`nums = [[1,2,3,4,5,6]]`
输出：`[1,2,3,4,5,6]`

提示：

```
1 <= nums.length <= 10^5
1 <= nums[i].length <= 10^5
1 <= nums[i][j] <= 10^9
nums 中最多有 10^5 个数字。
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/diagonal-traverse-ii)：https://leetcode-cn.com/problems/diagonal-traverse-ii

### 解法
```java
public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int count = 0;
        for(List l : nums)
            count += l.size();
        int[] res = new int[count];
        count = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0, nums.get(0).get(0)});
        while (!queue.isEmpty()){
            int[] item = queue.remove();
            int x = item[0], y = item[1];
            res[count++] = item[2];
            add(queue, x + 1, y , nums);
            add(queue, x, y + 1, nums);
        }
        return res;
    }

    private void add(Queue<int[]> queue, int x, int y, List<List<Integer>> nums){
        if(nums.size() > x && nums.get(x).size() > y && nums.get(x).get(y) > 0){
            queue.add(new int[]{x, y, nums.get(x).get(y)});
            nums.get(x).set(y, -1);
        }
    }
```
思路分析：
* 每一条对角线的横纵坐标之和相同，用这样的思路去遍历会超时，因为这个矩形是10^5 * 10^5的。
* 观察数字被添加到结果数组中的顺序。以示例1来看，元素以坐标表示。
    * 先添加`[0, 0]`，然后是`[1, 0]`，`[0, 1]`
    * 然后添加`[2, 0]`，`[1, 1]`，`[1, 1]`，`[0, 2]`……
    * 这是一个bfs，并且添加临近元素的顺序是先下再右。
* bfs用队列来模拟就好，但是本题中有两个问题要解决：
    * 如何确定某一个用坐标`[x, y]`表示的元素存在。由于每一行都可能是元素不满的，所以取元素之前要先判断`nums.size() > x && nums.get(x).size() > y`
    * 另外一个问题就是如何判断某个元素已经被取过，因为在遍历中比如`[1, 0]`的右边一个元素和`[0, 1]`的下一个元素是同一个，要避免重复获取。如果使用二维的`boolean`数组来标识是否已取过（超过空间限制，亲测）。题目中的条件`1 <= nums[i][j]`可以利用，我们取了某个元素，就将其设置为`-1`，这样就只有当`nums.get(x).get(y) > 0`时才取这个元素。
* 队列中的元素是一个三元数组，前两个值分别表示x，y，第三个值即为该坐标对应的元素。
* 由于答案要求的是数组，所以先遍历`nums`中的每个list来确定数组总共有多少个元素。
* 遍历了每一个元素，时间复杂度是$O(n)$的，这里n表示数字个数。

运行结果：

* 42ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
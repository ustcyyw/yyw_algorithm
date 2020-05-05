# LCP 08. 剧情触发时间
### 原题
在战略游戏中，玩家往往需要发展自己的势力来触发各种新的剧情。一个势力的主要属性有三种，分别是文明等级（C），资源储备（R）以及人口数量（H）。在游戏开始时（第 0 天），三种属性的值均为 0。
随着游戏进程的进行，每一天玩家的三种属性都会对应增加，我们用一个二维数组 increase 来表示每天的增加情况。这个二维数组的每个元素是一个长度为 3 的一维数组，例如 [[1,2,1],[3,4,2]] 表示第一天三种属性分别增加 1,2,1 而第二天分别增加 3,4,2。
所有剧情的触发条件也用一个二维数组 requirements 表示。这个二维数组的每个元素是一个长度为 3 的一维数组，对于某个剧情的触发条件 c[i], r[i], h[i]，如果当前 C >= c[i] 且 R >= r[i] 且 H >= h[i] ，则剧情会被触发。
根据所给信息，请计算每个剧情的触发时间，并以一个数组返回。如果某个剧情不会被触发，则该剧情对应的触发时间为 -1 。

示例 1：
输入： increase = [[2,8,4],[2,5,0],[10,9,8]] requirements = [[2,11,3],[15,10,7],[9,17,12],[8,1,14]]
输出: [2,-1,3,-1]
解释：
初始时，C = 0，R = 0，H = 0
第 1 天，C = 2，R = 8，H = 4
第 2 天，C = 4，R = 13，H = 4，此时触发剧情 0
第 3 天，C = 14，R = 22，H = 12，此时触发剧情 2
剧情 1 和 3 无法触发。

示例 2：
输入： increase = [[0,4,5],[4,8,8],[8,6,1],[10,10,0]] requirements = [[12,11,16],[20,2,6],[9,2,6],[10,18,3],[8,14,9]]
输出: [-1,4,3,3,3]

示例 3：
输入： increase = [[1,1,1]] requirements = [[0,0,0]]
输出: [0]

限制：
```
1 <= increase.length <= 10000
1 <= requirements.length <= 100000
0 <= increase[i] <= 10
0 <= requirements[i] <= 100000
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/ju-qing-hong-fa-shi-jian)：https://leetcode-cn.com/problems/ju-qing-hong-fa-shi-jian

### 解法
```java
public class getTriggerTime {
    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int n = requirements.length;
        int m = increase.length;
        int[] cs = new int[m + 1];
        int[] rs = new int[m + 1];
        int[] hs = new int[m + 1];
        prepare(cs, rs, hs, increase);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            doResult(res, i, cs, requirements[i][0], m + 1);
            doResult(res, i, rs, requirements[i][1], m + 1);
            doResult(res, i, hs, requirements[i][2], m + 1);
        }
        return res;
    }

    private void prepare(int[] cs, int[] rs, int[] hs, int[][] increase) {
        int c = 0, r = 0, h = 0;
        for (int i = 0; i < cs.length - 1; i++) {
            c += increase[i][0];
            r += increase[i][1];
            h += increase[i][2];
            cs[i + 1] = c;
            rs[i + 1] = r;
            hs[i + 1] = h;
        }
    }

    private void doResult(int[] res, int i, int[] s, int target, int arrLength) {
        if (res[i] == -1) return;
        int temp = search(s, target);
        if (temp == arrLength) {
            res[i] = -1;
            return;
        }
        res[i] = Math.max(res[i], temp);
    }

    private int search(int[] nums, int target) {
        int temp = Arrays.binarySearch(nums, target);
        if(temp < 0) return -temp - 1;
        for (int i = temp; i >= 0; i--) {
            if (nums[i] != nums[temp])
                return i + 1;
        }
        return 0;
    }
}
```
思路分析：
* 对于每一个剧情，有点像反向的“木桶效应”，其哪一天触发，取决于C,R,H中最后满足条件的那个属性需要的天数。也就是说需要找到C,R,H分别满足`C >= c[i], R >= r[i], H >= h[i] `的最早的天数，然后这三个天数中取最大值，就是该剧情的触发天数。

* 根据题目描述，三个属性是递增的，要知道什么时候三个属性分别满足触发条件，就需要根据`increase`计算出每一天三个属性值分别是多少。使用辅助函数`prepare(int[] cs, int[] rs, int[] hs, int[][] increase)`。

    * 注意`cs, rs, hs`的长度为`m + 1`，因为第0天三项属性为0，有的剧情要求的属性值为0，所以第0天不能忘记。
    * 然后通过循环累加，记录到每一天的累加和到相应的数组中。（PS：大佬们说这个叫前缀和?）

* 然后就是如何去查找某一属性满足条件的最早的那一天。

    * 使用线性查找，这个数据量必然超时。
    * 在计算存放每一天的属性值的数组时，算出来的数组是递增的，那么就应该使用二分查找。
    * 如果直接调用库函数，返回的结果并不一定是满足条件的最早的那一天，因为可能有一大堆相等的元素，返回的索引不一定是最左边的那个索引。所以改造二分查找。`search(int[] nums, int target)`:
        * 首先调用库函数，得到`temp`。如果`temp < 0`说明没有找到相等元素，这个位置是要插入的位置，这个位置本来的元素就是大于`target`的最小的元素，所以返回`-temp - 1`（因为库函数返回值是那个元素索引+1再取负，要得到索引就这样算）。
        * 否则，就是查找成功，从`temp`开始，向左找到第一个不等于`nums[temp]`的元素，其索引为`i`，那么`i + 1`就是属性满足条件的最早的那一天。

* 再来看辅助函数`void doResult(int[] res, int i, int[] s, int target, int arrLength)`：

    * 第一个参数即为答案数组`res[i]`代表第i个剧情的触发时间
    * 这个函数用于查找某一个属性满足要求的最早天数，然后修改`res[i]`。第三，四个参数为相应的属性数组与剧情要求的属性值，第五个参数是天数数组的长度。
    * 如果`res[i] == -1`，就意味着某一个属性值无法满足，剧情不会触发，那么对当前属性的查找就不用了，直接返回。
    * 查找结果`temp = search(s, target)`，如果`temp == arrLength`，说明这一个剧情的这个属性达不到，所以`res[i] = -1`。否则，就更新`res[i] = Math.max(res[i], temp);`

* 主函数中生成天数为索引的属性数组后，对剧情当层循环调用三个属性的`doResult`即可。

* 时间复杂度为$O(nlog(m))$，空间复杂度为$O(3m + n)$。

* 不利用库函数，自己写的寻找有序数组中大于等于`target`的索引最小的元素的索引

    * ```java
        private int mySearch(int[] nums, int target) {
                int lo = 0, hi = nums.length - 1;
                while (lo <= hi) {
                    int mid = (hi - lo) / 2 + lo;
                    if (nums[mid] < target) lo = mid + 1;
                    else hi = mid - 1;
                }
                return lo;
            }
        ```

    * 二分法，但是比使用库函数再线性搜索的方法跑出的结果更慢，神奇的库函数优化真好。

运行结果：

 * 执行用时 :51 ms, 在所有 Java 提交中击败了56.31%的用户
 * 内存消耗 :97.6 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
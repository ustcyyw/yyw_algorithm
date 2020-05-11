# 11. 盛最多水的容器
### 原题
给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。

说明：你不能倾斜容器，且 n 的值至少为 2。
图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。 

示例：
输入：[1,8,6,2,5,4,8,3,7]
输出：49

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/container-with-most-water

### 解法
```java
public class maxArea {
    public int maxArea(int[] height) {
        int res = 0;
        for (int i = 0, j = height.length - 1; i < j; ) {
            if (height[i] > height[j])
                res = Math.max(res, (j - i) * height[j--]);
            else
                res = Math.max(res, (j - i) * height[i++]);
        }
        return res;
    }
}
```
思路分析：
* 要确定盛水的面积，需要知道两边的高度，然后取最短之一为“高”。还需要知道两边之间的距离，为底。
* 某个位置的高度为`height[i]`，假定左右两边的索引分别为`i, j`。根据题目给的示意图，左右两边之间的位置就是`j - i`。所以面积的计算方式就是：
    * 如果`height[i] > height[j]`，那么面积等于`(j - i) * height[j]`
    * 否则面积为`(j - i) * height[i]`
* 要枚举出所有的`(i, j)`组合，计算面积再取最大，这样就是暴力法了。
* 如果一开始将`i = 0, j = height.length - 1`，此时底最长。不管改变`i`还是`j`，底都会变短，在这样的情况下，如何才有可能得到一个更大的面积？既然底都要变短，那就改变之前限制面试的短板短，将两边中矮的一边换掉，看是否能得到一个更长的边从而实现“高”变长。
    * 如果`height[i] > height[j]`，那下一次尝试面积就应该将`j--`，左移；反之下一次尝试应该`i++`。
* 如果对于某一组`(i, j)`，在尝试将短边换更长的时候为什么不是i左移或者j右移呢？因为按照上述规则，那种情况已经被尝试过了：比如是因为`height[i - 1] < height[j]`才来尝试`(i, j)`的，左移`i`就是回到已经考虑过的情况了。
* 综上，在不断尝试的过程中，还需要更新面积`res = Math.max(res, (j - i) * height[j--]);`或者`res = Math.max(res, (j - i) * height[i++]);`。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：

* 执行用时 :3 ms, 在所有 Java 提交中击败了91.66%的用户
* 内存消耗 :40.8 MB, 在所有 Java 提交中击败了5.04%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
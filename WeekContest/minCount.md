# LCP 06. 拿硬币
### 原题
桌上有 n 堆力扣币，每堆的数量保存在数组 coins 中。我们每次可以选择任意一堆，拿走其中的一枚或者两枚，求拿完所有力扣币的最少次数。

示例 1：
输入：[4,2,1]
输出：4
解释：第一堆力扣币最少需要拿 2 次，第二堆最少需要拿 1 次，第三堆最少需要拿 1 次，总共 4 次即可拿完。

示例 2：
输入：[2,3,10]
输出：8

限制：
1 <= n <= 4
1 <= coins[i] <= 10

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/na-ying-bi)：https://leetcode-cn.com/problems/na-ying-bi

### 解法
```java
public int minCount(int[] coins) {
        int res = 0;
        for(int i : coins)
            res += count(i);
        return res;
    }

    private int count(int num){
        int count = 0;
        while(num > 0){
            num -= 2;
            count++;
        }
        return count;
    }
```
思路分析：
* 每次能拿2个或者1个币，要得到最少获取次数，每次都拿2个币即可（只剩1个时也假装拿2个，然后剩余-1个硬币即可）。
* 对于每一堆硬币，需要取的最少次数使用辅助函数`int count(int num)`计算，每次拿两个硬币，只要剩余硬币数大于0就一直取。
* 最终结果就是遍历每一堆硬币，将每一堆硬币的获取数累加。

运行结果：

* 0ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
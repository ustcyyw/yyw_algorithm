# 5400. 旅行终点站
### 原题
给你一份旅游线路图，该线路图中的旅行线路用数组 paths 表示，其中` paths[i] = [cityAi, cityBi]` 表示该线路将会从` cityAi` 直接前往` cityBi` 。请你找出这次旅行的终点站，即没有任何可以通往其他城市的线路的城市。
题目数据保证线路图会形成一条不存在循环的线路，因此只会有一个旅行终点站。

示例 1：
输入：paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
输出："Sao Paulo" 
解释：从 "London" 出发，最后抵达终点站 "Sao Paulo" 。本次旅行的路线是 "London" -> "New York" -> "Lima" -> "Sao Paulo" 。

示例 2：
输入：paths = [["B","C"],["D","B"],["C","A"]]
输出："A"
解释：所有可能的线路是：
"D" -> "B" -> "C" -> "A". 
"B" -> "C" -> "A". 
"C" -> "A". 
"A". 
显然，旅行终点站是 "A" 。

示例 3：
输入：paths = [["A","Z"]]
输出："Z"

提示：
```
1 <= paths.length <= 100
paths[i].length == 2
1 <= cityAi.length, cityBi.length <= 10
cityAi != cityBi
```
所有字符串均由大小写英文字母和空格字符组成。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/destination-city)：https://leetcode-cn.com/problems/destination-city

### 解法
```java
public class destCity {
    public String destCity(List<List<String>> paths) {
        Map<String, String> map = prepare(paths);
        String from = paths.get(0).get(0);
        while(true){
            if(!map.containsKey(from))
                return from;
            from = map.get(from);
        }
    }

    private Map<String, String> prepare(List<List<String>> paths){
        Map<String, String> map = new HashMap<>();
        for(List<String> path : paths)
            map.put(path.get(0), path.get(1));
        return map;
    }
}
```
思路分析：
* 关键就是要从出发城市`from`去找到它到达的下一个城市，如果发现它没有可以到达的城市，那么它就是终点，否则就将`from`移动到下一个城市。
* 题目中已经说过保证线路图会形成一条不存在循环的线路，所以从哪一个城市出发最终结果都一样，那么将` String from = paths.get(0).get(0); `。
* 如何来查找某个城市能到达的下一个城市，可以使用一个`Map<String, String> map`，键为出发城市，值为到达城市。构造这个`map`使用`Map<String, String> prepare(List<List<String>> paths)`。
    * 于是，发现它没有可以到达的城市就是`!map.containsKey(from)`，此时`return from`。
    * 否则就将`from`移动到下一个城市。`from = map.get(from);`。
* `while`循环一定会结束，因为题目说了，一定有终点。
* 时间复杂度是线性的，空间复杂度也是线性的。

运行结果：

* 2ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
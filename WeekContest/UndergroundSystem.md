# 5370. 设计地铁系统

### 182周周赛第三题 medium
请你实现一个类` UndergroundSystem` ，它支持以下 3 种方法：
1. `checkIn(int id, string stationName, int t)`

编号为 id 的乘客在 t 时刻进入地铁站 `stationName` 。
一个乘客在同一时间只能在一个地铁站进入或者离开。

2. `checkOut(int id, string stationName, int t)`

编号为 id 的乘客在 t 时刻离开地铁站` stationName` 。
3. `getAverageTime(string startStation, string endStation)` 

返回从地铁站 `startStation` 到地铁站 `endStation` 的平均花费时间。
平均时间计算的行程包括当前为止所有从` startStation` 直接到达` endStation` 的行程。
调用 `getAverageTime` 时，询问的路线至少包含一趟行程。
你可以假设所有对 `checkIn` 和` checkOut `的调用都是符合逻辑的。也就是说，如果一个顾客在 `t1` 时刻到达某个地铁站，那么他离开的时间 `t2` 一定满足 `t2 > t1` 。所有的事件都按时间顺序给出。 

示例：

输入：

```
["UndergroundSystem","checkIn","checkIn","checkIn","checkOut","checkOut","checkOut","getAverageTime","getAverageTime","checkIn","getAverageTime","checkOut","getAverageTime"]
[[],[45,"Leyton",3],[32,"Paradise",8],[27,"Leyton",10],[45,"Waterloo",15],[27,"Waterloo",20],[32,"Cambridge",22],["Paradise","Cambridge"],["Leyton","Waterloo"],[10,"Leyton",24],["Leyton","Waterloo"],[10,"Waterloo",38],["Leyton","Waterloo"]]
```

输出：
`[null,null,null,null,null,null,null,14.0,11.0,null,11.0,null,12.0]`

解释：

```
UndergroundSystem undergroundSystem = new UndergroundSystem();
undergroundSystem.checkIn(45, "Leyton", 3);
undergroundSystem.checkIn(32, "Paradise", 8);
undergroundSystem.checkIn(27, "Leyton", 10);
undergroundSystem.checkOut(45, "Waterloo", 15);
undergroundSystem.checkOut(27, "Waterloo", 20);
undergroundSystem.checkOut(32, "Cambridge", 22);
undergroundSystem.getAverageTime("Paradise", "Cambridge");       // 返回 14.0。从 "Paradise"（时刻 8）到 "Cambridge"(时刻 22)的行程只有一趟
undergroundSystem.getAverageTime("Leyton", "Waterloo");          // 返回 11.0。总共有 2 躺从 "Leyton" 到 "Waterloo" 的行程，编号为 id=45 的乘客出发于 time=3 到达于 time=15，编号为 id=27 的乘客于 time=10 出发于 time=20 到达。所以平均时间为 ( (15-3) + (20-10) ) / 2 = 11.0
undergroundSystem.checkIn(10, "Leyton", 24);
undergroundSystem.getAverageTime("Leyton", "Waterloo");          // 返回 11.0
undergroundSystem.checkOut(10, "Waterloo", 38);
undergroundSystem.getAverageTime("Leyton", "Waterloo");          // 返回 12.0
```

提示：
总共最多有 20000 次操作。
1 <= id, t <= 10^6
所有的字符串包含大写字母，小写字母和数字。
`1 <= stationName.length <= 10`
与标准答案误差在 10^-5 以内的结果都视为正确结果。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/design-underground-system

### 解法

```java
public class UndergroundSystem {
    private Map<String, Integer> time;
    private Map<String, Integer> count;
    private Map<Integer, Integer> idToTime;
    private Map<Integer, String> idToStation;
    public UndergroundSystem() {
        time = new HashMap<>();
        count = new HashMap<>();
        idToTime = new HashMap<>();
        idToStation = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        idToTime.put(id, t);
        idToStation.put(id, stationName);
    }

    public void checkOut(int id, String stationName, int t) {
        int startTime = idToTime.get(id);
        String startStation = idToStation.get(id);
        String stationInfo = startStation + stationName;
        time.put(stationInfo, time.getOrDefault(stationInfo, 0) + t - startTime);
        count.put(stationInfo, count.getOrDefault(stationInfo, 0) + 1);
    }

    public double getAverageTime(String startStation, String endStation) {
        String stationInfo = startStation + endStation;
        return time.get(stationInfo) * 1.0 / count.get(stationInfo);
    }
}
```

思路分析：

* 首先说一下我在写这个题的时候，被罚时的一次，我考虑漏了同一个人可能会进出地铁站多次，我考虑一个id只会对应一个路线。
* 题目说了，所有`api`的调用都符合逻辑。所以某一个`id`（后文使用某一个人这样的称呼）调用了`checkIn`后才会调用`checkOut`，某一个人调用了`checkIn`之后，没有调用`checkOut`前不能再调用`checkIn`。所以使用一个`Map<Integer, Integer> idToTime`来存放某个人（id）`checkIn`的时间，以`id`为键，进入时间为值。这样做不会出现信息覆盖，因为：
    * 调用`checkOut`之前，不会有这个人调用`checkIn`，不会信息覆盖。
    * 调用`checkOut`之后，这个人调用`checkIn`，之前的信息已经被处理过了，覆盖也无所谓。
* 类似的想法，我们使用`Map<Integer, String> idToStation`来放某个人（id）`checkIn`的初始站台名称，以`id`为键，进入的站台名为值，也不会出现信息覆盖。
* 记录进站的时间地点信息，是为了在出站的时候，存放该路线的信息。包括该人次该路线停留的时间，及更新该路线一共有多少人次走过（注意是人次，不是该人）。
* 存放这两个信息是为了`getAverageTime`服务，使用`Map<String, Integer> time`记录该线路上花费的总时间，使用`Map<String, Integer> count`记录该线路总的出行人次。这两个量直接求平均，注意返回值是`double`类型喔😁。
    * 解释一下键。键是用初始站的名字与结束站的名字拼接而成的，没有同名车站，所以这样的拼接方式与线路一一对应（初始站于结束站唯一确定线路）
    * 由于所需要的数据都是存好的，只需要从`hashmap`中`get`出来即可。时间复杂度为$O(1)$的。
* 3个`api`的时间复杂度都是$O(1)$的。

代码解释：

* `checkIn`只将初始信息进行存储。
* `checkOut`先通过`id`得到初始信息，然后`String stationInfo = startStation + stationName`唯一确定路线，再将完成了的这个出行的时间累加入该线路的总时间上`time.put(stationInfo, time.getOrDefault(stationInfo, 0) + t - startTime);`，将该线路的出行人次+1`count.put(stationInfo, count.getOrDefault(stationInfo, 0) + 1);`。
# 5389. 点菜展示表
### 原题
给你一个数组 orders，表示客户在餐厅中完成的订单，确切地说， `orders[i]=[customerNamei,tableNumberi,foodItemi]` ，其中` customerNamei` 是客户的姓名，`tableNumberi` 是客户所在餐桌的桌号，而` foodItemi` 是客户点的餐品名称。

请你返回该餐厅的 点菜展示表 。在这张表中，表中第一行为标题，其第一列为餐桌桌号 “Table” ，后面每一列都是按字母顺序排列的餐品名称。接下来每一行中的项则表示每张餐桌订购的相应餐品数量，第一列应当填对应的桌号，后面依次填写下单的餐品数量。

注意：客户姓名不是点菜展示表的一部分。此外，表中的数据行应该按餐桌桌号升序排列。


示例 1：

输入：orders = [["David","3","Ceviche"],["Corina","10","Beef Burrito"],["David","3","Fried Chicken"],["Carla","5","Water"],["Carla","5","Ceviche"],["Rous","3","Ceviche"]]
输出：[["Table","Beef Burrito","Ceviche","Fried Chicken","Water"],["3","0","2","1","0"],["5","0","1","0","1"],["10","1","0","0","0"]] 
解释：
点菜展示表如下所示：
Table,Beef Burrito,Ceviche,Fried Chicken,Water
3    ,0           ,2      ,1            ,0
5    ,0           ,1      ,0            ,1
10   ,1           ,0      ,0            ,0
对于餐桌 3：David 点了 "Ceviche" 和 "Fried Chicken"，而 Rous 点了 "Ceviche"
而餐桌 5：Carla 点了 "Water" 和 "Ceviche"
餐桌 10：Corina 点了 "Beef Burrito" 

示例 2：

输入：orders = [["James","12","Fried Chicken"],["Ratesh","12","Fried Chicken"],["Amadeus","12","Fried Chicken"],["Adam","1","Canadian Waffles"],["Brianna","1","Canadian Waffles"]]
输出：[["Table","Canadian Waffles","Fried Chicken"],["1","2","0"],["12","0","3"]] 
解释：
对于餐桌 1：Adam 和 Brianna 都点了 "Canadian Waffles"
而餐桌 12：James, Ratesh 和 Amadeus 都点了 "Fried Chicken"

示例 3：

输入：orders = [["Laura","2","Bean Burrito"],["Jhon","2","Beef Burrito"],["Melissa","2","Soda"]]
输出：[["Table","Bean Burrito","Beef Burrito","Soda"],["2","1","1","1"]]

提示：

```
1 <= orders.length <= 5 * 10^4
orders[i].length == 3
1 <= customerNamei.length, foodItemi.length <= 20
customerNamei 和 foodItemi 由大小写英文字母及空格字符 ' ' 组成。
tableNumberi 是 1 到 500 范围内的整数。
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/display-table-of-food-orders-in-a-restaurant)：https://leetcode-cn.com/problems/display-table-of-food-orders-in-a-restaurant

### 解法
```java
public class displayTable {
    public List<List<String>> displayTable(List<List<String>> orders) {
        Set<String> foodName = new HashSet<>();
        Set<String> tableID = new HashSet<>();
        Map<String, Map<String, Integer>> map = new HashMap<>(); // 卓号对应菜谱 菜谱是一个map
        buildMap(orders, foodName, tableID, map);

        String[] foods = getSortedArr(foodName, null);
        String[] tables = getSortedArr(tableID, Comparator.comparingInt(x -> Integer.parseInt((String) x)));

        List<List<String>> res = new ArrayList<>();

        List<String> line1 = new ArrayList<>();
        line1.add("Table");
        for (String s : foods)
            line1.add(s);
        res.add(line1);

        for (String table : tables) {
            List<String> line = new ArrayList<>();
            line.add(table);
            Map<String, Integer> order = map.get(table);
            for (String s : foods) {
                line.add(String.valueOf(order.getOrDefault(s, 0)));
            }
            res.add(line);
        }
        return res;
    }

    private void buildMap(List<List<String>> orders, Set<String> foodName,
                          Set<String> tableID, Map<String, Map<String, Integer>> map) {
        for (List<String> order : orders) {
            String food = order.get(2);
            foodName.add(food);
            String tableNum = order.get(1);
            tableID.add(tableNum);
            if (!map.containsKey(tableNum))
                map.put(tableNum, new HashMap<>());
            Map<String, Integer> foodCount = map.get(tableNum);
            foodCount.put(food, foodCount.getOrDefault(food, 0) + 1);
        }
    }

    private String[] getSortedArr(Set<String> items, Comparator c) {
        String[] foods = new String[items.size()];
        int index = 0;
        for (String food : items) {
            foods[index++] = food;
        }
        if (c != null)
            Arrays.sort(foods, c);
        else
            Arrays.sort(foods);
        return foods;
    }
}
```
思路分析：
* 这个题读懂题目就会发现他极其恶心了，给的数据结构和要返回的数据结构都很麻烦，但其实要做的事情就是模拟。（恶心?，周赛的时候图块，代码比现在这个还恶心更多，乱七八糟的）
* 首先注意输出的格式：
    * 表格第一行，需要对菜名对应的字符串按字典序排列，所以我们得需要知道菜单中出现了哪些菜，还要对菜名排序。
    * 表格第一列，需要对桌号进行排序，桌号是字符串，但是要按数字进行排序，也需要知道出现了哪些卓号。
    * 表格中要统计每一桌每一样菜点了几分，没点的填0。
* 根据输出的格式，选择合适的数据结构。
    * 每一桌都要生成菜谱，所以使用`Map<String, Map<String, Integer>> map`，以桌号为键，菜单为值。菜单也是一个键值结构，菜名为键，份数为值。
    * 由于还需要知道出现了多少种菜，多少桌号。分别使用`Set<String> foodName, tableID`来记录。这个记录是没有顺序的，输出需要排序，所以后续转化为数组来排序。
* 辅助函数`void buildMap`，遍历分散的菜单，产生菜名与桌号的集合，并且将分散的菜单以桌号为键整合到一起，不断更新各桌所点菜的数量。
* 辅助函数`String[] getSortedArr(Set<String> items, Comparator c)`用于对菜名及桌号排序。
    * 第二个参数是为了服务桌号排序，因为桌号给的是字符串类型，但是要按照数字顺序排序，不使用自定义的`Comparator c`就错了，自定义为`Comparator.comparingInt(x -> Integer.parseInt((String) x))`
* 准备工作完成，生产表头（"table" ，按字典序的菜名`for (String s : foods)`）。然后按桌号` for (String table : tables)`生成表格的每一行：通过桌号先拿到统计了各个菜出现次数的map，然后按照蔡名字典序获取次数，没有出现的设置为0。

运行结果：

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
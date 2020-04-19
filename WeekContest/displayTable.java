import java.util.*;

/**
 * @Time : 2020年4月19日10:46:51
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */
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

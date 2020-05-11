# 127. 单词接龙
### 原题
给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
每次转换只能改变一个字母。
转换过程中的中间单词必须是字典中的单词。
说明:
如果不存在这样的转换序列，返回 0。
所有单词具有相同的长度。
所有单词只由小写字母组成。
字典中不存在重复的单词。
你可以假设 beginWord 和 endWord 是非空的，且二者不相同。

示例 1:
输入:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]
输出: 5
解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
返回它的长度 5。

示例 2:
输入:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

输出: 0
解释: endWord "cog" 不在字典中，所以无法进行转换。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/word-ladder)：https://leetcode-cn.com/problems/word-ladder

### 2种解法
##### 1.bfs
```java
private int length;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int n = wordList.size();
        if(n == 0)
            return 0;
        length = beginWord.length();
        boolean[] marked = new boolean[n];
        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(beginWord, 1));
        while(!queue.isEmpty()){
            Pair<String, Integer> temp = queue.remove();
            for(int i = 0; i < n; i++){
                String next = wordList.get(i);
                if(!marked[i] && canTransform(temp.getKey(), next)){
                    if(next.equals(endWord))
                        return temp.getValue() + 1;
                    marked[i] = true;
                    queue.add(new Pair<>(wordList.get(i), temp.getValue() + 1));
                }
            }
        }
        return 0;
    }

    private boolean canTransform(String from, String to){
        int count = 0;
        for(int i = 0; i < length; i++){
            if(from.charAt(i) != to.charAt(i)){
                if(count == 1)
                    return false;
                else count++;
            }
        }
        return true;
    }
```
思路分析：
* 单词之间按照规则可以进行转换，那么符合转换规则的两个单词就可以视为是连通的，那么`startWord`与字典中的单词就够成一副无向图。要做的事情就是找到`startWord`与`endWord`之间的最短路径长度，或者这两个单词没有连通就返回0。所以显然要使用BFS。
* 一个关键问题就是如何判断两个单词是否连接，根据规则，使用辅助函数`boolean canTransform(String from, String to)`进行判断。
    * 单词中只有一个字母能进行转换，就是说只有一个字母不同。
    * 并且单词长度一致，所以可以遍历两个单词的对应位置，并且使用`count`记数对应位置字母不相同的次数。
    * 如果遇到对应位置不相同，再判断如果`count == 1`，说明之前已经有过不相等的字母，无法转换返回`false`。否则，就将`count++`，表示已经有一次字母不相等。
* 在主函数中就是标准的BFS了：
    * 由于字典中的单词都不相同，所以可以使用` boolean[] marked = new boolean[n];`，通过下标来表示字典中某个单词是否已访问。
    * 由于这里要求得最短路径，所以队列的元素应该使用`Pair<String, Integer>`，以单词为键，值表示变换到这个单词的最短路径的结点数（根据题目示例，计数的是路径结点数）。所以将`startWord`放进去时，放入的是`queue.add(new Pair<>(beginWord, 1));`
    * 接下来用队列模拟BFS，取出当前单词及到达此单词的借点数，遍历字典中的单词，如果该单词没有访问过并且可以由当前单词转换得到`!marked[i] && canTransform(temp.getKey(), next)`
        * 然后判断下一个单词如果`next.equals(endWord)`，就完成变换了，返回`temp.getValue() + 1;`
        * 否则，将下一个单词设置为已访问，然后`queue.add(new Pair<>(wordList.get(i), temp.getValue() + 1))`，结点数要+1。
* 最后，如果BFS完成还没有返回结果，相当于已经找到了`startWord`可通过转换得到的所有单词，但是没有`endWord`，所以最终结果返回0。
* 时间复杂度为$O(n * word.length)$。空间复杂度取决于队列中的元素。

运行结果：
* 执行用时 :531 ms, 在所有 Java 提交中击败了32.64%的用户
* 内存消耗 :41.2 MB, 在所有 Java 提交中击败了44.22%的用户

##### 2.双向bfs
```java
	private int length;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int n = wordList.size();
        boolean flag = false;
        for(int i = 0; i < n; i++){
            if(wordList.get(i).equals(endWord)){
                flag = true;
                break;
            }
        }
        if(!flag) return 0;
        length = beginWord.length();
        Map<String, Integer> beginMap = new HashMap<>(); // 键是访问到的字符串， 值是访问到该字符串时，转换链条的长度
        Map<String, Integer> endMap = new HashMap<>();
        Queue<String> q1 = new LinkedList<>();
        Queue<String> q2 = new LinkedList<>();
        q1.add(beginWord);
        q2.add(endWord);
        beginMap.put(beginWord, 1);
        endMap.put(endWord, 1);
        while(!q1.isEmpty() && !q2.isEmpty()){
            int temp = search(q1, beginMap, endMap, wordList, n);
            if(temp != -1) return temp;
            temp = search(q2, endMap, beginMap, wordList, n);
            if(temp != -1) return temp;
        }
        return 0;
    }
    
    private int search(Queue<String> q, Map<String, Integer> m1, Map<String, Integer> m2, List<String> wordList, int n){
        String s1 = q.remove();
        for(int i = 0; i < n; i++){
            String next = wordList.get(i);
            if(!m1.containsKey(next) && canTransform(s1, next)){
                if(m2.containsKey(next))
                    return m1.get(s1) + m2.get(next);
                q.add(next);
                m1.put(next, m1.get(s1) + 1);
            }
        }
        return -1;
    }

    private boolean canTransform(String from, String to){
        int count = 0;
        for(int i = 0; i < length; i++){
            if(from.charAt(i) != to.charAt(i)){
                if(count == 1)
                    return false;
                else count++;
            }
        }
        return true;
    }
```
思路分析：
* 首先确定，肯定是使用BFS，并且转换的辅助函数同方法一。那么方法二的优化思路是什么呢？
    * 考虑一下BFS，其实大致是从起点出发不断向外扩宽，每次都从最外层向外扩展基本相同长度，其访问的范围可以近似想象为一个圆圈。
    * 从单一点出发进行BFS画出的圆，比从两个点出发画出的两个圆的面积更大。面积更大就表示访问的点更多。
    * 可以看下面形象的示意图：
    * ![ladderLength.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BFSorDFS/ladderLength.png?raw=true)
* 所以我们从`startWord, endWord`同时开始进行BFS，所以需要两个队列来模拟两个起点的BFS。并且使用两个`Map<String, Integer>`来表示两个BFS中分别访问到的结点，既然要求单词个数，值其实就是到达该单词时候的结点数。`startWord, endWord`分别放入队列，并且分别在`map`中记录访问情况及结点数为1。
* 然后同时进行BFS，循环条件`while(!q1.isEmpty() && !q2.isEmpty())`。
* 然后看辅助函数`int search(Queue<String> q, Map<String, Integer> m1, Map<String, Integer> m2, List<String> wordList, int n)`:
    * 第一个参数是某个模拟BFS的队列，二三两个参数分别是分别记录从`startWord, endWord`为起点访问过的单词。第四个参数是单词字典。第五个参数`int n`有两个作用，如果从两个起点出发的路径已经连通，表示已经找到了最短路径，此时返回n就是这个最短路径的结点数，否则还没有连通就返回-1表示还要继续进行路径搜索
    * 然后类似方法1的主函数，取出一个单词，然后遍历单词字典，如果`!m1.containsKey(next) && canTransform(s1, next)`表示当前单词可转换为下一个单词，并且从这一边的起点还没有访问过该点。如果此时`m2.containsKey(next)`表示从另外一个起点已经到达`next`，此时路径连通了，返回`return m1.get(s1) + m2.get(next);`。否则将`next`设置为从这一边起点已访问，并放入相应队列。对字典的循环结束，就返回-1，表示还没有连通。
* 再看主函数，对两个起点调用相应的`search`函数，如果返回值不为-1就返回。否则继续。直到循环结束说明`startWord`不能转换为`endWord`，返回0。

运行结果：
* 执行用时 :119 ms, 在所有 Java 提交中击败了55.80%的用户
* 内存消耗 :39.5 MB, 在所有 Java 提交中击败了64.98%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
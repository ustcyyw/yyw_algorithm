package medium.BFSorDFS;

/**
 * @Time : 2020年3月19日12:47:34
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import javafx.util.Pair;

import java.util.*;

/**
 * 给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
 *
 * 每次转换只能改变一个字母。
 * 转换过程中的中间单词必须是字典中的单词。
 *
 * 说明:
 * 如果不存在这样的转换序列，返回 0。
 * 所有单词具有相同的长度。
 * 所有单词只由小写字母组成。
 * 字典中不存在重复的单词。
 * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
 * 示例 1:
 *
 * 输入:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出: 5
 * 解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 *      返回它的长度 5。
 *
 * 示例 2:
 * 输入:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 * 输出: 0
 * 解释: endWord "cog" 不在字典中，所以无法进行转换。
 */
public class ladderLength {
    /**
     * 执行用时 :531 ms, 在所有 Java 提交中击败了32.64%的用户
     * 内存消耗 :41.2 MB, 在所有 Java 提交中击败了44.22%的用户
     */
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
        int count = 1;
        for(int i = 0; i < length; i++){
            if(from.charAt(i) != to.charAt(i)){
                if(count == 0)
                    return false;
                else count--;
            }
        }
        return true;
    }

    /**
     * 评论区的双向bfs
     * 执行用时 :119 ms, 在所有 Java 提交中击败了48.97%的用户
     * 内存消耗 :39.5 MB, 在所有 Java 提交中击败了64.98%的用户
     */
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
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
        while(!q1.isEmpty() && !q2.isEmpty()){ // 其中之一有空 说明转换链断了 无法转换得到endword，所以结束循环返回0
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
}

package medium.Sort;

/**
 * @Time : 2020年3月9日19:41:23
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import javafx.util.Pair;

import java.util.*;

/**
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 * 说明：
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/top-k-frequent-elements
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class topKFrequent {
    /**
     * 执行用时 :22 ms, 在所有 Java 提交中击败了57.84%的用户
     * 内存消耗 :42.6 MB, 在所有 Java 提交中击败了6.82%的用户
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> numToCount = new HashMap<>();
        for(int i : nums)
            numToCount.put(i, numToCount.getOrDefault(i, 0) + 1);
        PriorityQueue<Pair<Integer, Integer>> minPQ = new PriorityQueue<>(new Order());
        for(int key : numToCount.keySet()){
            minPQ.add(new Pair<>(key, numToCount.get(key)));
            if(minPQ.size() > k)
                minPQ.remove();
        }
        List<Integer> res = new ArrayList<>();
        while(!minPQ.isEmpty())
            res.add(0, minPQ.remove().getKey());

        return res;
    }

    private class Order implements Comparator<Pair<Integer, Integer>> {
        public int compare(Pair<Integer, Integer> v, Pair<Integer, Integer> w){
            return v.getValue() - w.getValue();
        }
    }

    /**
     * 用桶排序的思想：以出现次数为数组下标，将数放入通过频率找到的“桶”中，这样下表大的桶中的元素，就是出现次数多的数
     * 执行用时 :13 ms, 在所有 Java 提交中击败了98.51%的用户
     * 内存消耗 :42.7 MB, 在所有 Java 提交中击败了6.12%的用户
     */
    public List<Integer> topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> numToCount = new HashMap<>();
        for(int i : nums)
            numToCount.put(i, numToCount.getOrDefault(i, 0) + 1);

        List<Integer>[] countToNum =(List<Integer>[])new ArrayList[nums.length + 1];
        for(int key : numToCount.keySet()){
            int count = numToCount.get(key);
            if(countToNum[count] == null)
                countToNum[count] = new ArrayList<>();
            countToNum[count].add(key);
        }

        List<Integer> res = new ArrayList<>();
        // 这里第二个循环条件合适：因为题目给的k刚好保证了不会有出现频率相同的元素多出的情况
        for(int i = nums.length; i >= 0 && res.size() < k; i--){
            if(countToNum[i] != null)
                res.addAll(countToNum[i]);
        }
        return res;
    }
}

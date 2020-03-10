# 前K个高频元素

### 原题
给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

示例 1:
输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]

示例 2:
输入: nums = [1], k = 1
输出: [1]

说明：
你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/top-k-frequent-elements)：https://leetcode-cn.com/problems/top-k-frequent-elements

### 两种解法

##### 1.最小优先队列（我的解法）

```java
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
```

思路分析：

* 要得到出现次数前k高的元素，首先得知道各个元素分别出现了多少次。可以使用` Map<Integer, Integer> numToCount`以元素为键，以该元素出现的次数为值，遍历一次原数组即可得到。
* 接下来就是排序，遇到前k高这样的描述，自然想到不必将他们完全排序，可以使用优先队列。要留下最高的k个元素，那么就使用最小优先队列。当队列中元素超过k时就弹出最小的元素，这样当所有元素都入队一次后（这个过程中还有出队），就得到了出现次数最多的元素。
* 但是这样有一个问题：我们在统计出现次数的时候，散列表的键是元素，值是出现次数。我们排序的是次数，但是最终答案不是出现次数，而是元素。所以我们还得将次数映射回元素。会有多个元素出现次数相同，所以用`Map`来构建映射时不可以的。
* 构建映射，只要键值对放在一起就行，所以想到内置的`Pair<Integer, Integer>`，将元素与出现次数一起封装到这个类中，以元素为键，以该元素出现的次数为值，然后放入最小优先队列中。最后取出优先队列中剩余的k个元素，取出`Pair<Integer, Integer>`中的键。
* 在构建最小优先队列时，我们需要传入自定义的`Comparator`实例来让优先队列按照`Pair`中的值进行排序。自定义`Comparator`需要实现实现`compare`方法。
* 注意优先队列中依次取出的元素是升序排列的，但答案需要降序排列，就将每次取出的元素放在List的第一个元素的位置即可。
* 出现次数的统计是$O(n)$的，使用优先队列的操作中，每个元素都入队了，近似认为队列元素一直为k，那么时间复杂度是$O(nlog(k))$的，最后将队列中k个元素放入List中，所需的操作为$O(klog(k))$，总体来看时间复杂度为$O(nlog(k))$。

代码解释：

* 2-4行，出现次数的统计。
* 18-22行，实现自定义的``Comparator``
* 5-10行，不断将含有元素及出现次数信息的`Pair<Integer, Integer>`放入队列，将小的元素踢出队列，只剩下最大的k个。
* 11-13行，取出的元素放在List的第一个元素的位置，实现按出现次数从大到小排列。

运行结果：

* 执行用时 :22 ms, 在所有 Java 提交中击败了57.84%的用户
* 内存消耗 :42.6 MB, 在所有 Java 提交中击败了6.82%的用户


##### 2.桶排序的思路（官方标答）

```java
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
```

思路分析：

* 每个元素出现次数的统计同方法1。这里不同的是如何找到出现次数最高的k个元素，可以使用桶排序的思路，将出现次数与桶数组的索引对应，将元素放入对应的桶中。再从索引最大的桶开始取元素，直到取的元素的个数等于k。
* 会有多个元素出现次数相同，所以不能只使用一个`int[]`，需要使用一个`List<Integer>[]`。
* 链表数组的大小要确保不会出现数组索引越界，如果原数组中所有元素都相等，那么出现频率为`nums.length`，要将出现次数与桶数组索引对应，出现频率为`nums.length`时，桶数组最大的索引为`nums.length`，所以桶数组的长度为`nums.length+1`。
* 时间复杂度为`O(n)`,不论是统计元素的出现次数还是进行桶排序还是取出桶中元素，都是单次遍历

代码解释：

* 10-11，该出现次数对应的桶还不存在，建立一个。
* 16-19行，按出现次数从高到低，从对应的桶中取出元素放入List中。注意循环的第二个条件：题目给的k刚好保证了不会有出现频率相同的元素多出的情况。

运行结果：
* 执行用时 :13 ms, 在所有 Java 提交中击败了98.51%的用户
* 内存消耗 :42.7 MB, 在所有 Java 提交中击败了6.12%的用户
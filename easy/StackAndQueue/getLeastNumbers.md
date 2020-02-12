# 找到数组中最小的k个数

### 原题

输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。

示例 1：

输入：arr = [3,2,1], k = 2
输出：[1,2] 或者 [2,1]
示例 2：

输入：arr = [0,1,2,1], k = 1
输出：[0]


限制：

0 <= k <= arr.length <= 1000
0 <= arr[i] <= 1000

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof)：https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof

----

### 一种解法

##### 1.使用最大优先队列（我的第一解）

```java
public int[] getLeastNumbers(int[] arr, int k) {
        int[] result = new int[k];
        PriorityQueue<Integer> MaxPQ = new PriorityQueue<>(new NumReverseComparator());
        for(int i : arr){
            MaxPQ.add(i);
            if(MaxPQ.size() > k)
                MaxPQ.remove();
        }
        int i = 0;
        while(!MaxPQ.isEmpty())
            result[i++] = MaxPQ.remove();
        return result;
    }
static class NumReverseComparator implements Comparator<Integer>{
        public int compare(Integer i, Integer j){
            return -(i - j);
        }
    }
```

思路分析：

* 要保留最小的k个数，这个问题典型的可以使用优先队列，不断将数组中元素放入队列，不断将最大元素出队。保持队列中有k个元素，遍历结束后，即可得到最小的k个数。
* 保持队列的元素个数为k，于是不论是入队还是出队，底层堆的恢复顺序的时间复杂度都是$O(log(k))$的，这个问题中每个元素都入队出队各一次，所以时间复杂度为$O(2nlog(k))$。空间复杂度为$O(k)$的。
* 另外一种很直观的解法，快速排序后取前k个元素，时间复杂度为$O(nlog(n))$。在k比较小的时候，使用优先队列显然更快。
* 在java中，内置了最小优先队列，没有最大优先队列。但是优先队列的构造方法可以传入`Comparator`。所以我们可以自己实现一个`Comparator`，使得数的比较方式颠倒，就可以得到一个最大优先队列了。

代码分析：

* 14~17行 实现`Comparator`接口，需要override方法`public int compare(Integer i, Integer j)`。为了得到最大优先队列，需要改变数比较的规则，所以` return -(i - j);`

运行结果：
* 执行用时 :25 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :48.8 MB, 在所有 Java 提交中击败了100.00%的用户

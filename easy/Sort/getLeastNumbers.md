# 面试题40. 最小的k个数

### 原题
输入整数数组 `arr` ，找出其中最小的 `k` 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。

**示例 1：**

```
输入：arr = [3,2,1], k = 2
输出：[1,2] 或者 [2,1]
```
**示例 2：**

```
输入：arr = [0,1,2,1], k = 1
输出：[0]
```
**限制：**
- `0 <= k <= arr.length <= 10000`
- `0 <= arr[i] <= 10000`

[原题链接](https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/)： https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/

### 两种解法

##### 1.最大优先队列

```java
public int[] getLeastNumbers(int[] arr, int k) {
        if(k == 0)
            return new int[0];
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(new Order());
        for(int i = 0; i < k; i++)
            maxPQ.add(arr[i]);
        for(int i = k; i < arr.length; i++){
            if(arr[i] < maxPQ.peek()){
                maxPQ.add(arr[k]);
                maxPQ.remove();
            }
        }
        int[] res = new int[k];
        for(int i = k - 1; i >= 0; i++)
            res[i] = maxPQ.remove();
        return res;
    }

    private class Order implements Comparator<Integer>{
        public int compare(Integer v, Integer w){
            return w - v;
        }
    }
```

思路分析：

* 要求最小的k个数（当然求最大的k个数也一样），并不需要将整个数组排序，因为我们只要知道最小k个是哪一些，顺序不重要。但是还是涉及到序号，也与排序是相关的，一个常规的做法就是使用优先队列。
* 将元素顺序放入优先队列，保持队列的元素个数为`k`，要留下最小的`k`个数，所以出队的应该是队列中最大的元素——我们应该使用最大优先队列。
* 由于题目中规定了`0 <= k <= arr.length`。
    * 处理特殊情况，`k == 0`时，不需要找数，直接返回`return new int[0];`
    * 除去特殊情况，我们可以先将前`k`个元素放入优先队列。然后从第`k+1`个元素开始，先与堆顶元素相比，如果比堆顶元素相等或者更大，就没必要入队，因为入队之后队列的`size > k`，要弹出一个元素，那么刚才入队的元素立即被弹出。这样我们可以节约该元素入队和出队时恢复堆有序的消耗。
* 时间复杂度为$O(nlog(k))$，空间复杂度为$O(k)$。

代码解释：

* java中没有最大优先队列，但是优先队列接受一个`Comparator`为参数指定优先顺序。所以我们需要自己定义一个`Comparator`，重写`public int compare(Integer v, Integer w)`，在`v`大的时候返回负数。

运行结果：
* 执行用时 :23 ms, 在所有 Java 提交中击败了30.42%的用户
*  内存消耗 :43.3 MB, 在所有 Java 提交中击败了100.00%的用户
##### 2.快速排序切分运用

```java
public int[] getLeastNumbers2(int[] arr, int k) {
        if(k == 0 || arr.length == 0)
            return new int[0];
        int lo = 0, hi = arr.length - 1;
        while(lo < hi){
            int temp = partition(arr, lo, hi);
            if(temp > k - 1) hi = temp - 1;
            else if(temp < k - 1) lo = temp + 1;
            else break;
        }
        return Arrays.copyOf(arr, k);
    }

    private int partition(int[] arr, int lo, int hi){
        int v = arr[lo];
        int i = lo, j = hi + 1;
        while(true){
            while(arr[++i] < v) if(i == hi) break;
            while(arr[--j] > v) if(j == lo) break;
            if(i >= j)
                break;
            exch(arr, i, j);
        }
        exch(arr, lo, j);
        return j;
    }

    private void exch(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
```

思路分析：

* 同样，不用将数组完全排序，但这毕竟与顺序相关，需要运用排序算法的一些思路。
* 在快速排序中，假如某一次切分点的索引为`k-1`，切分点位于排序数组中的正确位置且切分点左边的元素都比它小。切分点左边包括切分点一共有`k`个元素，这些元素即为答案。所以我们的目标就变为了用切分去找索引为`k-1`的切分点。
* 假设在某次切分中，切分点索引为`j`：
    * 如果`j > k - 1`，说明要找的`k`个元素现在在区间`[0, j - 1]`，我们需要在`[0, j - 1]`上使用切分去找到索引为`k-1`的切分点。
    * 如果`j < k - 1`，说明`[0, j]`区间内的元素不够`k`个，还有一部分在区间`[j + 1, arr.length]`，我们需要在`[j + 1, arr.length]`上使用切分去找到索引为`k-1`的切分点。
    * 当`j == k - 1`，找到结果，不需要继续下一次切分。返回此时部分有序数组`arr`的前`k`个元素。
* 在输入随机的情况下，认为只需要常数次切分就可以完成，每次切分只遍历切分区间上的每个元素，可以认为在线性时间内可以完成。空间复杂度为$O(1)$。

代码解释：

* `private int partition(int[] arr, int lo, int hi)`在区间`[lo, hi]`进行切分。切分点都选择元素`arr[lo]`。切分函数的写法很固定：18， 19行分别找到大于等于切分点，小于等于切分点的元素（这里要注意防止索引越界），然后进行交换，使得最终切分点左右两边的元素与其大小顺序是正确的。24-25行，将切分点放到正确位置，返回切分点索引`j`。
* 5-10行的循环就是思路分析中说的，不断使用切分直到切分点索引为`k - 1`。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了99.75%的用户
* 内存消耗 :42.6 MB, 在所有 Java 提交中击败了100.00%的用户
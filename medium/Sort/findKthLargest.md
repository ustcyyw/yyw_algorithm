# 215. 数组中的第K个最大元素

### 原题
在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

示例 1:
输入: [3,2,1,5,6,4] 和 k = 2
输出: 5

示例 2:
输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
输出: 4

说明:
你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/kth-largest-element-in-an-array)：https://leetcode-cn.com/problems/kth-largest-element-in-an-array

### 两种解法

##### 1.快速排序切分的使用

```java
public int findKthLargest(int[] nums, int k) {
        int index = nums.length - k;
        int lo = 0, hi = nums.length - 1;
        while(lo < hi){
            int j = partition(nums, lo, hi);
            if(j < index)
                lo = j + 1;
            else if(j > index)
                hi = j - 1;
            else return nums[j];
        }
        return nums[index];
    }

private int partition(int[] nums, int lo, int hi){
        int i = lo, j = hi + 1;
        while(i < j){
            while(nums[++i] < nums[lo])
                if(i == hi)
                    break;
            while(nums[--j] > nums[lo]);
            	if(j <= i)
                	break;
            exch(nums, i, j);
        }
        exch(nums, lo, j);
        return j;
    }

private void exch(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
```

思路分析：

* 数组中的第K个最大元素，可以排序来找到索引为`int index = nums.length - k`的元素。但是只是在找第k大的那个元素，其他位置有序都是额外的操作。
* 在快速排序中，每次切分都会使得切分点那个元素交换到正确的位置。如果切分点的索引恰好就是`index`，那么它就是我们要找的元素。如果切分点索引小于`index`，目标元素大于等于切分点，在切分过程中已经把大于等于切分点的元素交换到切分点的右边，所以要在切分点索引的右边查找。如果切分点索引大于`index`，目标元素小于等于切分点，在切分过程中已经把小于等于切分点的元素交换到切分点的左，所以要在切分点索引的左边查找。
* 由此可以看出，这是不断在缩小的区间中使用切分，直到切分点的索引为`index`，这个索引在有序数组中对应的就是第k大的元素，而切分将元素放在有序数组中正确的位置。
* 时间复杂度平均情况下是$O(n)$的，最坏情况比如升序数组中找最大的元素，每次切分只使得区间缩小1，这时的时间复杂度为$O(n^2)$。

代码解释：

* 12行，前面的循环中一直没有使得切分点索引为`index`，此时`index`这个位置已经是有序数组正确的元素了，就是我们要找的结果。
* 15行开始，定义的切分函数，每次将区间的第一个元素当切分点。

运行结果：
* 执行用时 :9 ms, 在所有 Java 提交中击败了49.97%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.05%的用户

##### 1.最小优先队列

```java
public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        for(int i = 0; i < k; i++)
            minPQ.add(nums[i]);
        for(int i = k; i < nums.length; i++){
            if(nums[i] < minPQ.peek())
                continue;
            minPQ.add(nums[i]);
            minPQ.remove();
        }
        return minPQ.remove();
    }
```

思路分析：

* 要找到第k大的元素，这类问题很容易想到使用最小优先队列，保证优先队列中元素个数为k，元素个数超过k时就将队列中第一个元素删除。这样最终队列的第一个元素，就是原数组中第k大的元素。
* 题目中说明了k总是小于数组元素个数，这样我们就可以先将k个元素放入队列中。在遍历剩下的元素时，当某个元素大于队列的第一个元素（某个元素小于队列第一个元素时，如果放入队列也会被立马弹出，直接跳过），我们将其放入，此时队列中的元素个数为k+1，弹出第一个元素。这样就避免了对队列中元素个数的判断。
* 近似认为每个元素都进行了一次入队出队，队列元素个数为k。时间复杂度为$O(nlog(k))$。

运行结果：
* 执行用时 :4 ms, 在所有 Java 提交中击败了70.18%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.05%的用户

### 反思

```java
public int findKthLargest3(int[] nums, int k) {
        int m = nums.length - k + 1;
        if(m < k){
            PriorityQueue<Integer> maxPQ = new PriorityQueue<>(new numOrder());
            for(int i = 0; i < m; i++)
                maxPQ.add(nums[i]);
            for(int i = m; i < nums.length; i++){
                if(nums[i] > maxPQ.peek())
                    continue;
                maxPQ.add(nums[i]);
                maxPQ.remove();
            }
            return maxPQ.remove();
        }
        else {
            PriorityQueue<Integer> minPQ = new PriorityQueue<>();
            for(int i = 0; i < k; i++)
                minPQ.add(nums[i]);
            for(int i = k; i < nums.length; i++){
                if(nums[i] < minPQ.peek())
                    continue;
                minPQ.add(nums[i]);
                minPQ.remove();
            }
            return minPQ.remove();
        }
    }

private class numOrder implements Comparator<Integer>{
        public int compare(Integer v, Integer w){
            return -(v - w);
        }
    }
```

由方法2可知，时间复杂度为$O(nlog(k))$。通过判断k与`nums.length - k + 1`的大小，使用不同的优先队列，可以让优先队列中的元素个数尽可能的小，这样出队入队的消耗会小一些，尤其是当k的值很大的时候，这样做事可以有改进空间的。
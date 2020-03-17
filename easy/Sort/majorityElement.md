# 169. 多数元素

### 原题
给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
你可以假设数组是非空的，并且给定的数组总是存在多数元素。

示例 1:
输入: [3,2,3]
输出: 3

示例 2:
输入: [2,2,1,1,1,2,2]
输出: 2

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/majority-element)：https://leetcode-cn.com/problems/majority-element

### 两种解法

##### 1.使用HashMap计数

```java
public int majorityElement(int[] nums) {
        int n = nums.length / 2;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : nums)
            map.put(i, map.getOrDefault(i, 0) + 1);
        for(int key : map.keySet()){
            if(map.get(key) > n)
                return key;
        }
        return -1;
    }
```

思路分析：

* 要找到数组中出现次数大于 ⌊ n/2 ⌋ 的元素，所以需要对出现的元素进行计数。一般都会使用哈希表，所以使用` Map<Integer, Integer> map = new HashMap<>();`来对元素进行计数。
* 第一遍遍历数组，如果某个元素不在`map`中就将该元素为键，值为1插入`map`，如果某个元素已经在`map`中，将其值取出再添加计数1然后修改其值`map.put(i, map.getOrDefault(i, 0) + 1);`
* 最后遍历所有键，直到找到某个出现次数大于 ⌊ n/2 ⌋ 的元素。
* 时间复杂度为$O(n)$，空间复杂度就是散列表所占用的空间$O(n)$。

运行结果：
* 执行用时 :14 ms, 在所有 Java 提交中击败了36.78%的用户
* 内存消耗 :47.1 MB, 在所有 Java 提交中击败了5.07%的用户

##### 2.三向切分的快速排序运用

```java
private int[] num;
    private int n;
    public int majorityElement2(int[] nums) {
        num = nums;
        n = nums.length / 2;
        return partition(0, nums.length - 1);
    }

    private int partition(int lo, int hi){
        int i = lo + 1, lt = lo, gt = hi;
        int v = num[lo];
        while(i <= gt){
            if(num[i] > v) exch(i, gt--);
            else if(num[i] < v) exch(i++, lt++);
            else i++;
        }
        if(lt - lo > n) return partition(lo, lt - 1);
        else if (hi - gt > n) return partition(gt + 1, hi);
        else return num[lt];
    }

    private void exch(int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
```

思路分析：

* 注意到要找的元素出现次数大于 ⌊ n/2 ⌋ 的元素，说明这个数组中已经有一个重复很多次的元素。这个让我想到了三向切分的归并排序，这个排序算法就是为了解决大量重复元素的数组的排序，会很高效。
* [LeetCode 75.颜色分类](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Sort/sortColors.md)类似也运用了这个想法，可以参考一下。这里再次强调三向切分的四个重要区间。
* 三向切分最重要的是四个区间（索引对应的区间），定要好四个区间的边界（以下都是闭区间），就很容易完成：
    - 比切分点小的元素`[lo, lt - 1]`
    - 比与切分点相等的元素`[lt, i - 1]`
    - 还没有确定大小的区间`[i, gt]`
    - 比切分点大的区间`[gt + 1, hi]`
* 每一次的切分完成，比切分点小的元素都在区间`[lo, lt - 1]`；比切分点大的元素都在区间`[gt + 1, hi]`。与切分点相等的元素在区间`[lt, gt]`。
* 这个与我们的目标是如何联系的呢？联系就在于，多数元素出现次数大于 ⌊ n/2 ⌋实际上就是说明了多数元素元素所在的区间长度大于 ⌊ n/2 ⌋，如果区间`[lo, lt - 1]`长度小于⌊ n/2 ⌋，那么多数元素一定不在这个区间；反之区间`[lo, lt - 1]`长度大于⌊ n/2 ⌋，那么要找的元素一定在这个区间，因为剩余的区间长度不足⌊ n/2 ⌋。对于区间`[gt + 1, hi]`同理。
* 注意在代码中为了避免多次计算，用实例变量`private int n;`表示区间的长度一半：` n = nums.length / 2;`
* 考虑如果多数元素位于区间`[lo, lt - 1]`，也就是区间长度`lt - lo > n`，我们只知道这个区间的元素小于当次的切分点。这个区间内是无序的，我们不知道要返回哪一个元素，所以要进行递归的查找。同理当多数元素位于区间`[gt + 1, hi]`也需要递归查找。
* 但是如果多数元素不在上述两个区间，那它位于`[lt, gt]`，这个区间都是和切分点相等的，这就是我们要找的多数元素。

代码解释：

* 三向切分的写法比较固定，参考上面给出的75题中的解释。或者参考[《算法 第四版》2.3节](https://algs4.cs.princeton.edu/23quicksort/)

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了85.34%的用户
* 内存消耗 :42 MB, 在所有 Java 提交中击败了35.52%的用户
# 350. 两个数组的交集 II

### 原题
给定两个数组，编写一个函数来计算它们的交集。

示例 1:
输入: nums1 = [1,2,2,1], nums2 = [2,2]
输出: [2,2]

示例 2:
输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出: [4,9]
说明：
输出结果中**每个元素出现的次数，应与元素在两个数组中出现的次数一致**。
我们可以不考虑输出结果的顺序。

进阶:
如果给定的数组已经排好序呢？你将如何优化你的算法？
如果 nums1 的大小比 nums2 小很多，哪种方法更优？
如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/intersection-of-two-arrays-ii)：https://leetcode-cn.com/problems/intersection-of-two-arrays-ii

### 两种解法

##### 1.不考虑内存 和 磁盘的限制

```java
public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> temp = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        for(int i = 0, j = 0; i < nums1.length && j < nums2.length; ){
            if(nums1[i] > nums2[j]) j++;
            else if(nums1[i] < nums2[j]) i++;
            else {
                temp.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = temp.get(i);
        return res;
    }
```

思路分析：

* 不考虑内存限制的时候，所以可以对数组进行排序。这题就和 [349.两个数组的交集](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/Sort/intersection349.md)思路一样了。都是先排序后使用双指针，这题还更简单一些，因为最终结果每个相交元素出现的次数，和两个数组中该元素相交次数相同（不用考虑去重）。
* 将两个数组排序后，用`i`表示数组1的索引，`j`表示数组2的索引。
    * 如果`nums1[i] < nums2[j]`，那么下一个可能相等的元素应该是`nums1[i + 1]`，所以`i++`。
    * 同理，如果`nums1[i] > nums2[j]`时，`j++`。
    * 如果`nums1[i] == nums2[j]`，这就是交集中的一个元素，同时移动两个指针去找下一个可能相等的元素`i++, j++`。注意题目中交集中允许出现重复元素，重复次数就是该元素相交的次数。
* 先排序后双指针的单次遍历，所以时间复杂度为$O(nlog(n))$。交集元素个数不确定，只能先使用`List`来存放元素。空间复杂度为$O(k)$，k为交集的元素个数。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了80.55%的用户
* 内存消耗 :39.6 MB, 在所有 Java 提交中击败了5.04%的用户

##### 2.考虑内存与磁盘限制

```java
public int[] intersect2(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map1 = new HashMap<>((int)(nums1.length / 0.75F + 1.0F));
        Map<Integer, Integer> map2 = new HashMap<>((int)(nums1.length / 0.75F + 1.0F));
        for(int i : nums1)
            map1.put(i, map1.getOrDefault(i, 0) + 1);
        for(int i : nums2)
            map2.put(i, map2.getOrDefault(i, 0) + 1);
        List<Integer> temp = new ArrayList<>();
        for(int key : map1.keySet()){
            if(map2.containsKey(key)){
                for(int i = 0; i < Math.min(map1.get(key), map2.get(key)); i++)
                    temp.add(key);
            }
        }
        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = temp.get(i);
        return res;
    }
```

思路分析：

* 考虑内存限制，内存中无法完整保存整个数组，所以没法使用排序的方式。交集，其实就是要知道有没有相同的元素，并且这里要求最终结果中某元素出现次数要与相交次数相等，所以不但要知道哪些元素在两个数组中都出现了，还需要知道分别出现了几次。
* 可以采用`Map<Integer, Integer> map1, map2`分别记录两个数组中出现的元素及对应的次数。只需要通过单次遍历即可完成。`for(int i : nums1) map1.put(i, map1.getOrDefault(i, 0) + 1)`。
* 统计完成后，对于数组一出现的某个元素，看是否在数组2中也出现，如果都出现就将其放到结果集中，放置的次数就等于相交次数，取决于出现次数少的那个数组。放置次数为`Math.min(map1.get(key), map2.get(key))`，这里再用一个内循环循环添加即可。
    * 为什么选择数组一种出现的元素来遍历，因为题目设定，数组一的长度比数组二小很多。
* 不知道最终结果有几个元素，所以还是先使用`List<Integer> temp`来存放，最终再转换成数组输出。空间复杂度为$O(k)$，k为交集的元素个数。
* 算法的时间复杂度是线性的，都是单次的遍历。

运行结果：
* 执行用时 :6 ms, 在所有 Java 提交中击败了32.05%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.04%的用户
# 5364. 按既定顺序创建目标数组

### 181周 周赛第一题
给你两个整数数组` nums` 和` index`。你需要按照以下规则创建目标数组：
目标数组` target` 最初为空。
按从左到右的顺序依次读取` nums[i]` 和 `index[i]`，在 `target` 数组中的下标` index[i]` 处插入值` nums[i]` 。
重复上一步，直到在` nums` 和` index `中都没有要读取的元素。
请你返回目标数组。
题目保证数字插入位置总是存在。

示例 1：
输入：`nums = [0,1,2,3,4], index = [0,1,2,2,1]`
输出：`[0,4,1,3,2]`
解释：

```
nums       index     target
0            0        [0]
1            1        [0,1]
2            2        [0,1,2]
3            2        [0,1,3,2]
4            1        [0,4,1,3,2]
```

示例 2：
输入：`nums = [1,2,3,4,0], index = [0,1,2,3,0]`
输出：`[0,1,2,3,4]`

解释：

```
nums       index     target
1            0        [1]
2            1        [1,2]
3            2        [1,2,3]
4            3        [1,2,3,4]
0            0        [0,1,2,3,4]
```

示例 3：
输入：`nums = [1], index = [0]`
输出：`[1]`

提示：

```
1 <= nums.length, index.length <= 100
nums.length == index.length
0 <= nums[i] <= 100
0 <= index[i] <= i
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/create-target-array-in-the-given-order)：https://leetcode-cn.com/problems/create-target-array-in-the-given-order

### 解法

```java
public int[] createTargetArray(int[] nums, int[] index) {
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < n; i++){
            res.add(index[i], nums[i]);
        }
        int[] target = new int[n];
        for(int i = 0; i < n; i++){
            target[i] = res.get(i);
        }
        return target;
    }
```

思路分析：

* 题目啰嗦一大堆，其实就是不断在数组的指定的索引`index[i]`处插入元素。这里注意题目提示中有一个限制条件`index[i] <= i`，也就是说在插入第`i`个元素时，索引最多为为最后一个元素。
* 有了上述条件，直接使用java中`ArrayList`类中的`add(int index, Object o)`函数即可将制定元素插入到指定位置。最后再将`List`中元素依次放到数组中即可。
* `ArrayList`是使用数组进行实现，当然这里我们也可以自己写一个数组的实现，不过每次插入都需要进行大量的数组的移动。时间复杂度为$O(n^2)$的。为了避免大量数组的移动，也可以采用链表来进行插入过程，最后顺序遍历转换为数组。

运行结果：

* 难度 easy。 1ms
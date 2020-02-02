## 两数之和等于指定值的下标查找

[toc]

### 原题目

​	给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

​	假设每种输入只会对应一个答案。但是，不能重复利用这个数组中同样的元素。

示例:

​	给定 `nums = [2, 7, 11, 15], target = 9`。因为 `nums[0] + nums[1] = 2 + 7 = 9`，所以返回 `[0, 1]`

​	来源：力扣（LeetCode）
​	[原题链接](https://leetcode-cn.com/problems/two-sum) ： https://leetcode-cn.com/problems/two-sum

---

### 三种解法

##### 1.暴力解法（我的第一解）

```java
public static int[] towSum1(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }
```

暴力解法就是简单的二重循环，时间复杂度显然是$O(n^2)$

稍微注意一点就是第二层循环开始时`j​`从`i+1`开始，避免已经验证不可能的两数组合。

运行结果为：

* 执行用时 :33 ms, 在所有 Java 提交中击败了23.51%的用户
* 内存消耗 :37.6 MB, 在所有 Java 提交中击败,33.02%的用户

##### 2.借鉴快排中找切分点的方法（我的第二解，并不知道如何取名:joy:）

```java
 public static int[] towSum2(int[] nums, int target) {
        // 要进行排序 先把原数组留一个备份
        int[] temp = new int[nums.length];
        System.arraycopy(nums, 0, temp, 0, nums.length);
        Arrays.sort(nums);
        int[] result = new int[2];

        // 有点类似于快排找切分点的做法 从最大最小向target靠近
        int m = 0, n = nums.length - 1;
        while (true) {
            int sum = nums[m] + nums[n];
            if (sum == target) {
                result[0] = m;
                result[1] = n;
                break;
            } else if (sum > target)
                n--;
            else m++;
        }

        // 由备份找到原来的索引在哪
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == nums[result[0]]) {
                result[0] = i;
                break;
            }
        }
        // 避免nums中有相等元素时候的错误 有一个下标要从右向左
        for (int i = temp.length - 1; i >= 0; i--) {
            if (temp[i] == nums[result[1]]) {
                result[1] = i;
                break;
            }
        }
        return null;
    }
```

首先来回溯我的思路：

* 暴力解法是从杂乱无章的数里面按顺序挑出两个，如果数组是有序的我挑一个最小的数再挑一个比target稍微小一点的数来求和，这样更容易很快找到解。但是怎么实现呢？
* 不容易确定数组中哪个数比target稍小，极端一点如果target比数组中所有数小，按刚才的思路就是第一个数与最后一个数相加。**但如果加和比target要小我就应该把小的数替换成稍微大一点的；如果加和比target大我就应该把大的数替换成稍微小一点的。**
* 在数组有序的情况下，替换相加数的操作就**等价于移动索引**。这个就非常像快排中找切分点，并且仅一个解，所以可以近似认为这个过程时间复杂度是$O(1)$的。
* 那么问题来了，题目要的是原索引而非有序数组的索引，所以第一步我得**先保存原数组**。java中有现有代码，时间复杂度是$O(n)$的。
* 在找到排序后数组中的索引后，我们需要从保存的原数组中找到原先的索引，这里也通过一层循环完成，找到相等值时原数组的下标即为答案。时间复杂度$O(n)$
* 一共有三次单层循环，算法的效率很高，时间复杂度$O(n)$。但是用了一个保存原数组信息的克隆数组，空间复杂度并不友好。

然后来解释一下代码：

* `while (true)`这个循环中`result`记录的是排序后数组的下标。
* 后两个循环中通过条件`temp[i] == nums[result[1]]`来确定在原数组中的下标。
* 这里有一个问题，为何两个循环一个从下标`0`开始，一个从`length - 1`开始？因为如果`nums`中存在两个相等元素相加得`target`的情况，都从`0`开始或者`length - 1`开始会得到相同下标，导致错误。

运行结果为：

* 执行用时 :2 ms, 在所有 Java 提交中击败了99.47%的用户
* 内存消耗 :38.2 MB, 在所有 Java 提交中击败了7.17%的用户

##### 3.官方标答，使用HashMap

```java
public static int[] towSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>((int) (nums.length / 0.75F + 1.0F));
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }
```

思路分析：

* 看标答发很熟悉这样的做法，但是没有想到，上一次看见类似的做法是在《算法 第四版》中统计长文本中大量重复的单词个数的算法中。
* 这里以值为键，索引为值。通过`target - nums[i]`是否存在于 hashmap 中来判断是否已找到答案。否，则将该数放入哈希map这样就使得只用进行一次遍历。而且我们知道当散列函数选择恰当的时候，在哈希map中查找一个键的时间复杂度均摊下来约为$O(1)$。所以这个算法复杂度下约为$O(n)$。
* 空间复杂度，就是 hashmap 所占的内存。java中 hashmap 的实现方式是链表数组，所以空间复杂度约为$O(n)$。

代码解释：

* `new HashMap<>((int) (nums.length / 0.75F + 1.0F))`这样设置可以让 hashmap 效率最高。参考阿里巴巴开发者手册中，有一个对初始容量的建议：

    $initialCapacity = (需要存储的元素个数/负载因子) + 1, 负载因子默认0.75$

运行结果：

* 我并没有提交。时间也是2ms左右


# 1013. 将数组分成和相等的三个部分

### 原题
给你一个整数数组 A，只有可以将其划分为三个和相等的非空部分时才返回 true，否则返回 false。
形式上，如果可以找出索引` i+1 < j `且满足` (A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2] + ... + A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1])` 就可以将数组三等分。

示例 1：
输出：[0,2,1,-6,6,-7,9,1,2,0,1]
输出：true
解释：0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1

示例 2：
输入：[0,2,1,-6,6,7,9,-1,2,0,1]
输出：false

示例 3：
输入：[3,3,6,5,-2,2,5,1,-9,4]
输出：true
解释：3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4

提示：
3 <= A.length <= 50000
-10^4 <= A[i] <= 10^4

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/partition-array-into-three-parts-with-equal-sum)：https://leetcode-cn.com/problems/partition-array-into-three-parts-with-equal-sum

### 解法

```java
public boolean canThreePartsEqualSum(int[] A) {
        int sum = 0;
        for(int i : A)
            sum += i;
        if(sum % 3 != 0)
            return false;
        int i = 0, count = 0;
        for(int temp = 0; i < A.length; i++){
            temp += A[i];
            if(temp == sum / 3){
                count++;
                break;
            }
        }
        i++; // 跳出上一个循环后，i是上一个子数组最后一个元素的索引，+1后为当前子数组的第一个元素索引
        for(int temp = 0; i < A.length; i++){
            temp += A[i];
            if(temp == sum / 3){
                count++;
                break;
            }
        }
        return count == 2 && i != A.length - 1;
    }
```

思路分析：

* 要将数组分为三个连续的部分，且每个部分的和一致。所以首先要得到整个数组所有元素之和，才能知道子数组之和为应该多少。用`sum`表示所有元素之和，那么子数组的和为`sum / 3`。
* 如果数组之和不能被3整除，每个元素都是整数，说明三个子数组的和不可能相等。
* 再次遍历数组，要去确定是否能找到3个和为`sum / 3`的数组。用`count`来计算在遍历过程中和为`sum / 3`的子数组数量。8-14行，第一个子数组的寻找。15行：跳出上一个循环后，`i`是上一个子数组最后一个元素的索引，+1后为当前子数组的第一个元素索引。16行22行，第二个子数组的寻找。
* 注意我们只去寻找了2个子数组，因为如果已经找到这样的两个子数组后，剩余元素的和一定也为`sum / 3`，除了一种特殊情况，就是此时`i`的值为原数组最后一个元素的索引，此时已经不存在剩余元素，没有第三个子数组。所以返回值为`count == 2 && i != A.length - 1;`
* 自左向右的遍历，保证题目要求索引` i+1 < j `且满足` (A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2] + ... + A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1])`
* 时间复杂度为$O(n)$；空间复杂度为$O(1)$。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了86.52%的用户
* 内存消耗 :44.6 MB, 在所有 Java 提交中击败了100.00%的用户
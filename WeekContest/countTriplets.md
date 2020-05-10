# 5405. 形成两个异或相等数组的三元组数目
### 原题
给你一个整数数组 arr 。
现需要从数组中取三个下标 i、j 和 k ，其中 (0 <= i < j <= k < arr.length) 。
a 和 b 定义如下：
a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
注意：^ 表示 按位异或 操作。

请返回能够令 a == b 成立的三元组 (i, j , k) 的数目。 

示例 1：
输入：arr = [2,3,1,6,7]
输出：4
解释：满足题意的三元组分别是 (0,1,2), (0,2,2), (2,3,4) 以及 (2,4,4)

示例 2：
输入：arr = [1,1,1,1,1]
输出：10

示例 3：
输入：arr = [2,3]
输出：0

示例 4：
输入：arr = [1,3,5,7,9]
输出：3

示例 5：
输入：arr = [7,11,12,9,5,2,7,17,22]
输出：8

提示：
1 <= arr.length <= 300
1 <= arr[i] <= 10^8

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor)：https://leetcode-cn.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor

### 2种解法
##### 1.直接模拟的暴力法
```java
public int countTriplets(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            int a = 0;
            for (int j = i; j < arr.length; j++) {
                a ^= arr[j];
                int b = 0;
                for (int k = j + 1; k < arr.length; k++) {
                    b ^= arr[k];
                    if (a == b)
                        count++;
                }
            }
        }
        return count;
    }
```
思路分析：
* 这是一个三元组，所以要遍历出所有情况，显然三重循环。
* 要用于比较的两个数，a异或的元素是`[i, j - 1]`，所以在第一层对i的循环中设置局部变量`int a = 0;`，在第二重循环内部`a ^= arr[j];`就可以计算出a，注意这里索引为`j`。原本题目要求`j > i`，那么第一个可取的`j = i + 1`，第一个可取的a就是`arr[i]`，所以这里二重循环从`j = i`开始，刚好符合题目要求。
* 第二个要比较的b异或的元素是`[j, k]`，所以在第二层循环中设置局部变量`int b = 0`，由于外层循环中`a`异或到索引为`j`的元素，所以b要从`int k = j + 1`开始异或。
* 在第三层循环内判断`a == b ?`，如果是就进行`count++`。
* 三层循环，时间复杂度为$O(n^3)$。空间复杂度为$O(1)$。

运行结果：

* 28ms

##### 2.N平方的解法
```java
public int countTriplets2(int[] arr) {
        int count = 0;
        for(int i = 0; i < arr.length; i++){
            int temp = arr[i];
            for(int k = i + 1; k < arr.length; k++){
                temp ^= arr[k];
                if(temp == 0)
                    count += k - i;
            }
        }
        return count;
    }
```
思路分析：
* 这种解法需要记住异或的两个性质：
    * 如果`a == b`，那么`a ^ b == 0`。反之也成立。
    * 异或具有结合率。`(a1 ^ a2) ^ a3 =a1 ^ (a2 ^ a3) `。
* 题目中`a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1], b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]`。那么`a ^ b = arr[i] ^ arr[i + 1] ^ ... ^ arr[k]`。
* 如果`a ^ b == 0`，那么说明`a == b`。那a，b是多少呢？异或具有交换律，既然`arr[i] ^ arr[i + 1] ^ ... ^ arr[k] == 0`，将等式的左边通过结合律分为两个部分，每个部分至少一个元素。
    * 区间`[i, k]`，一共有`k - i  + 1`个元素。
    * 举个例子将1，2，3，4分成左右两部分，每个部分至少一个元素，有3种分法。那么对于`k - i  + 1`个元素，就有`k - i`种分法。
    * 所以这里就可以找到`k - i`组三组元`(i , j, k)`。
* 所以只需要枚举所有可能的区间`[i, k], k < i`，计算区间所有元素的异或，如果某一个区间元素异或为0，就产生`k - i`组满足题意的三元组`count += k - i;`。
* 时间复杂度为$O(n^2)$。空间复杂度为$O(1)$。

运行结果：

* 1ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹
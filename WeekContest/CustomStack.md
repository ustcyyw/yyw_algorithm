# 1381. 设计一个支持增量操作的栈

### 180周周赛第二题
请你设计一个支持下述操作的栈。
实现自定义栈类 `CustomStack` ：
- `CustomStack(int maxSize)`：用 `maxSize` 初始化对象，`maxSize` 是栈中最多能容纳的元素数量，栈在增长到 `maxSize` 之后则不支持 `push` 操作。
- `void push(int x)`：如果栈还未增长到 `maxSize` ，就将 `x` 添加到栈顶。
- `int pop()`：返回栈顶的值，或栈为空时返回 **-1** 。
- `void inc(int k, int val)`：栈底的 `k` 个元素的值都增加 `val` 。如果栈中元素总数小于 `k` ，则栈中的所有元素都增加 `val` 。

**示例：**
```
输入：
["CustomStack","push","push","pop","push","push","push","increment","increment","pop","pop","pop","pop"]
[[3],[1],[2],[],[2],[3],[4],[5,100],[2,100],[],[],[],[]]
输出：
[null,null,null,2,null,null,null,null,null,103,202,201,-1]
解释：
CustomStack customStack = new CustomStack(3); // 栈是空的 []
customStack.push(1);                          // 栈变为 [1]
customStack.push(2);                          // 栈变为 [1, 2]
customStack.pop();                            // 返回 2 --> 返回栈顶值 2，栈变为 [1]
customStack.push(2);                          // 栈变为 [1, 2]
customStack.push(3);                          // 栈变为 [1, 2, 3]
customStack.push(4);                          // 栈仍然是 [1, 2, 3]，不能添加其他元素使栈大小变为 4
customStack.increment(5, 100);                // 栈变为 [101, 102, 103]
customStack.increment(2, 100);                // 栈变为 [201, 202, 103]
customStack.pop();                            // 返回 103 --> 返回栈顶值 103，栈变为 [201, 202]
customStack.pop();                            // 返回 202 --> 返回栈顶值 202，栈变为 [201]
customStack.pop();                            // 返回 201 --> 返回栈顶值 201，栈变为 []
customStack.pop();                            // 返回 -1 --> 栈为空，返回 -1
```

**提示：**
- `1 <= maxSize <= 1000`
- `1 <= x <= 1000`
- `1 <= k <= 1000`
- `0 <= val <= 100`
- 每种方法 `increment`，`push` 以及 `pop` 分别最多调用 `1000` 次

### 解法

```java
public class CustomStack {
    private int[] aux;
    private int n;
    public CustomStack(int maxSize) {
        aux = new int[maxSize];
        n = 0;
    }

    public void push(int x) {
        if(n == aux.length)
            return;
        aux[n++] = x;
    }

    public int pop() {
        if(n == 0) return -1;
        return aux[--n];
    }

    public void increment(int k, int val) {
        int boundary = Math.min(k, n);
        for(int i = 0; i < boundary; i++)
            aux[i] += val;
    }
}
```

思路分析：

* 设计一个栈，并且明确了这个栈的元素个数是有一个最大值的。参考[LeetCode155.最小栈](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/StackAndQueue/MinStack155.md)的实现思路。链表与数组中选择一个实现：因为本题设定了元素个数的最多为`maxSize`，所以选择数组实现不再需要付出改变数组大小的成本。
* 数组的实现，关键就是栈顶元素索引的表示。这里使用元素个数`n`去想办法表示，当栈中有一个元素时`n=1`。而栈顶元素为`aux[0]`，所以栈顶元素均用`aux[n-1]`表示。元素入栈时`aux[n]`是没有存放元素的，入栈后栈的元素数量需要加一，所以入栈可以写为` aux[n++] = x;`出栈时，`n--`就使得当前栈顶访问不到，达到删除效果。
* 对于出栈与入栈操作，时间复杂度都是$O(1)$。对于增加操作，依据题意，将栈底的`k`个元素增加`val`，如果栈中元素少于`k`个，就将所有元素都进行增加。依照`n`与索引的关系，改变的元素的索引从0开始截止`Math.min(k, n)-1`，进行单层遍历。时间复杂度是线性的。

运行结果：

* 难度 medium 7 ms
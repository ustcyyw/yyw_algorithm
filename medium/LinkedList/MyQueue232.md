# 用栈实现队列

[toc]

### 原题

使用栈实现队列的下列操作：

push(x) -- 将一个元素放入队列的尾部。
pop() -- 从队列首部移除元素。
peek() -- 返回队列首部的元素。
empty() -- 返回队列是否为空。
示例:

```java
MyQueue queue = new MyQueue();

queue.push(1);
queue.push(2);  
queue.peek();  // 返回 1
queue.pop();   // 返回 1
queue.empty(); // 返回 false
```

说明:

你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/implement-queue-using-stacks)：https://leetcode-cn.com/problems/implement-queue-using-stacks

----

### 我的解法

```java
public class MyQueue232 {
    private Stack<Integer> in = null;
    private Stack<Integer> out = null;
    /**
     * Initialize your data structure here.
     */
    public MyQueue232() {
        in = new Stack<>();
        out = new Stack<>();
    }
    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        in.push(x);
    }
    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if (out.empty()) {
            int size = in.size();
            for (int i = 0; i < size; i++)
                out.push(in.pop());
        }
        return out.pop();
    }
    /**
     * Get the front element.
     */
    public int peek() {
        if (out.empty()) {
            int size = in.size();
            for (int i = 0; i < size; i++)
                out.push(in.pop());
        }
        return out.peek();
    }
    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return in.empty() && out.empty();
    }
}
```

思路分析：

* 栈：后进先出；队列：先进先出。要使用栈的标准API实现队列的标准API，使用一个栈必然会使得元素无法先进先出，栈的特点就是可以颠倒入和出顺序，那么使用两个栈颠倒两次顺序，不就可以完成先进先出了嘛。
* 确定使用两个栈之后，就要考虑如何高效实现，如果其中一个栈只是用于颠倒元素顺序时候使用的暂时存放元素的栈，那么每次入队操作，出队操作都需要遍历栈中元素，显然很慢。
* 如果其中一个栈负责存放入队的元素，记为` Stack<Integer> in`；另外一个栈存放出队元素，记为`Stack<Integer> out`。
* 则每次入队操作只需要一次栈的`push`操作。时间复杂度为$O(1)$。
* 出队操作，当`out`为空时，需要将`in`中所有元素弹出并放入`out`中，再将此时`out`中栈顶元素弹出即可，此时要遍历一次`in`中的元素。如果`out`不为空时，之间弹出其栈顶元素即可。从均摊的角度来看，n个元素的弹出，需要从`in`中`pop`n次，再`push`n次全部压入`out`，再`pop`n次从而达到n次出队操作。平均的操作为1次`push`加上2次`pop`。所以时间复杂度为$O(n)$。
* `peek`操作与`pop`类似，只不过取元素时不删除栈中元素，其余一致。
* 判断是否为空，由于我们使用了两个栈来存放元素，所以必须两个栈都为空时`in.empty() && out.empty()`才能判断队列为空。

图示：用图示例 push(1),push(2),push(3),pop(),push(4),peek()。

![MyQueue232.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/StackAndQueue/MyQueue232.jpg?raw=true)

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :33.9 MB, 在所有 Java 提交中击败了61.98%的用户
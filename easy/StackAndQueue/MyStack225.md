# 用队列实现栈

[toc]

### 原题

使用队列实现栈的下列操作：

```java
push(x) -- 元素 x 入栈
pop() -- 移除栈顶元素
top() -- 获取栈顶元素
empty() -- 返回栈是否为空
```

注意:

你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/implement-stack-using-queues)：https://leetcode-cn.com/problems/implement-stack-using-queues

----

### 两种解法

##### 1.出队时调整元素顺序(我的解法)

```java
public class MyStack225 {
    private int tail;
    private Queue<Integer> q;
    /**
     * Initialize your data structure here.
     */
    public MyStack225() {
        q = new LinkedList<Integer>();
    }
    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        tail = x;
        q.add(x);
    }
    /**
     * Removes the element on top of the stack and returns that element.
     * 出栈的时间复杂度为 n
     */
    public int pop() {
        int size = q.size();
        for (int i = 0; i < size - 2; i++)
            q.add(q.remove());
        tail = q.remove(); // 堆顶第二个元素 让下次调用top时使用
        q.add(tail);
        return q.remove();
    }
    /**
     * Get the top element.
     */
    public int top() {
        return tail;
    }
    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return q.isEmpty();
    }
}
```

思路分析：

* 这个题乍一看很像使用两个栈实现一个队列，但是队列并没有后进先出这个可以颠倒元素顺序的功能，所以显然不能指望使用两个队列实现栈了。
* 使用一个队列存放元素`Queue<Integer> q`，当我要调用`top()`时，队列的API都无法直接访问栈顶元素，所以考虑用一个常量`tail`一直存储着栈顶(队列`q`队尾)的元素，调用的时候`top()`时直接返回该值，则`top()`是$O(1)$的。
* 考虑入栈时直接让元素加入到队列中，但是要记得更新`tail`的值。入栈操作是$O(1)$。
* 弹出栈顶元素时，也就是要将队列`q`中最后一个元素删除并返回。假设现在栈中(队列中)有n个元素，我将前n-1个元素依次出队再入队，此时我要弹出的元素就在队列头部，此时调用`q.remove()`即可将栈顶元素弹出。不过这里要注意更新变量`tail`，因为弹出之后栈顶元素就变了。这个过程需要遍历每个元素，所以时间复杂度为$O(n)$。

图示：用图示例 push(1),push(2),push(3),pop(),push(4),peek()。

![MyStack225.2.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/StackAndQueue/MyStack225.2.jpg?raw=true)

代码解释：

* 23~24行 循环终止条件`i < size - 2`，这个终止条件将前n-2个元素出队再入队。然后
* 25行 ` tail = q.remove();`更新变量`tail`。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :33.7 MB, 在所有 Java 提交中击败了96.32%的用户

##### 2.入队时调整元素顺序（官方标答）

```java
class MyStack {
    Queue<Integer> queue;
    
    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
        int size = queue.size();
        while (size-- > 1) {
            queue.add(queue.poll());
        }
    }


    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
```

思路分析：

* 标答使出栈操作时间复杂度为$O(1)$，但是入栈时每次都调整元素顺序，通过遍历（让前n-1个元素先出队再入队）使得每次新进的元素都调整到队列头，所以队列中元素的排序顺序是后入栈的元素在队列头，满足后进先出。入栈操作时间复杂度为$O(n)$。

图示：用图示例 push(1),push(2),push(3),pop()

![MyStack225.1.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/StackAndQueue/MyStack225.1.jpg?raw=true)


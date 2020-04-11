# 637. 二叉树的层平均值

### 原题
给定一个非空二叉树, 返回一个由每层节点平均值组成的数组.

示例 1:
输入:

```
    3
   / \
  9  20
    /  \
   15   7
```

输出: [3, 14.5, 11]
解释:
第0层的平均值是 3,  第1层是 14.5, 第2层是 11. 因此返回 [3, 14.5, 11].

注意：
节点值的范围在32位有符号整数范围内。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/average-of-levels-in-binary-tree)：https://leetcode-cn.com/problems/average-of-levels-in-binary-tree

### 两种解法

##### 1.bfs层次遍历

```java
public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root, 0));
        int curLayer = 0;
        long sum = 0;
        int count = 0;
        while (!queue.isEmpty()){
            Pair<TreeNode, Integer> pair = queue.remove();
            TreeNode node = pair.getKey();
            if(pair.getValue() == curLayer){
                count++;
                sum += node.val;
            }
            else{
                res.add(sum * 1.0 / count);
                curLayer++;
                sum = node.val;
                count = 1;
            }
            if(node.left != null)
                queue.add(new Pair<>(node.left, curLayer + 1));
            if(node.right != null)
                queue.add(new Pair<>(node.right, curLayer + 1));
        }
        res.add(sum * 1.0 / count);
        return res;
    }
```

思路分析：

* 求每一层结点值的平均值，显然最直接就想到层次遍历，求每一层的元素之和，记录每一层的元素个数。这显然是一个广度优先遍历。要知道某一个结点是那一层的，就需要一个额外的表示第几层的信息，所以队列中的元素使用`Pair<TreeNode, Integer>`
* 使用变量`sum`记录当前层的结点值的累加和，`count`记录当前层结点的累计个数，`curLayer`记录当前在统计哪一层。
* 在进行遍历时，取出的当前结点和其层次信息
    * 如果其层次信息为当前层，则`count++; sum += node.val;`
    * 如果其层次信息大于当前层，说明已经到了下一层第一个结点。这个时候需要处理上一层的累计信息，将上一层的平均值信息放入结果集中`res.add(sum * 1.0 / count);`。然后重置`sum = node.val`，`count = 1`表示记录了这一个结点，为第一个；`curLayer++`这是新一层的层次。
    * 之后将其左右子结点（如果存在）放入队列中，其子结点的层次为当前层次+1。
* 遍历完成后，最后一层的信息并没有放入结果集合，所以最后还需要`res.add(sum * 1.0 / count);`
* 对每一个结点都进行了遍历，所以时间复杂度为$O(n)$，空间复杂度，取决于有多少个节点存在于队列中。

运行结果：
* 执行用时 :6 ms, 在所有 Java 提交中击败了11.23%的用户
* 内存消耗 :42.4 MB, 在所有 Java 提交中击败了26.78%的用户

##### 2.递归：先遍历根结点

```java
	private List<Long> sum;
    private List<Integer> count;
    public List<Double> averageOfLevels2(TreeNode root) {
        sum = new ArrayList<>();
        count = new ArrayList<>();
        List<Double> res = new ArrayList<>();
        preTraversal(root, 0);
        int n = sum.size();
        for(int i = 0; i < n; i++)
            res.add(sum.get(i) * 1.0 / count.get(i));
        return res;
    }

    public void preTraversal(TreeNode x, int layer){
        if(x == null) return;
        if(layer < sum.size()){ // 由于使用的是前序遍历，所以可以保证每层的第一个数据是依次进入列表的
            sum.set(layer, sum.get(layer) + x.val);
            count.set(layer, count.get(layer) + 1);
        }
        else{
            sum.add((long)x.val);
            count.add(1);
        }
        preTraversal(x.left, layer + 1); // 这里先遍历左子树还是右子树无所谓
        preTraversal(x.right, layer + 1);
    }
```

思路分析：

* 只要能记录结点的层次信息，采用深度优先搜索也可以。这时候就需要对每一层的累加和及累计结点数都进行存储，可以采用List的方式（Map也可以），索引表示层次，值表示累加和或者累计结点数。`private List<Long> sum; private List<Integer> count;`，设计为成员变量是为了递归时减少传参的麻烦。
* 现在要解决的就是如何将索引与层次对应正确。
    * 进行遍历的递归函数`void preTraversal(TreeNode x, int layer)` 第二个参数代表层次信息。
    * 如果保证先遍历根结点，无论是按照（根 左子树 右子树的前序遍历）还是按照（根 右子树 左子树的顺序）都是先将上层的一个结点信息保留，才会去保留下层的节点信息。
    * 所以就可以用`sum.size()`来表示现在记录了多少层次的部分累计信息。
    * 如果`layer > sum.size()`说明当前层是没有记录的且最靠上的层，直接进行记录` sum.add((long)x.val); count.add(1);`。这样索引就与层次对应了。
    * 反之如果`layer < sum.size()`，当前层已经记录了部分信息，先将之前的累计信息取出再累加后放回该层信息。List的set方法可以指定值的存放索引。`sum.set(layer, sum.get(layer) + x.val); count.set(layer, count.get(layer) + 1);`
    * 然后递归，先左子树还是右子树均可，子树的层次信息为当前层+1。
* 递归完成后，所有结点被遍历，此时将累加信息依次取出，计算平均值再放入结果List中即可。
* 对每一个结点都进行了遍历，所以时间复杂度为$O(n)$。空间复杂度与树高成正比（无论是函数调用栈的内存还是记录数据的两个list）。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了85.00%的用户
* 内存消耗 :42.7 MB, 在所有 Java 提交中击败了22.01%的用户
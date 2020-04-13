# 501. 二叉搜索树中的众数

### 原题
给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
假定 BST 有如下定义：

结点左子树中所含结点的值小于等于当前结点的值
结点右子树中所含结点的值大于等于当前结点的值
左子树和右子树都是二叉搜索树
例如：
给定 BST [1,null,2,2],

   1
    \
     2
    /
   2
返回[2].
提示：如果众数超过1个，不需考虑输出顺序
进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/find-mode-in-binary-search-tree)：https://leetcode-cn.com/problems/find-mode-in-binary-search-tree

### 两种解法

##### 1.使用List即时存放出现最多的数

```java
	private List<Integer> items;
    private int maxCount;
    private int curCount;
    private TreeNode pre;

    public int[] findMode(TreeNode root) {
        if (root == null)
            return new int[0];
        items = new ArrayList<>();
        // 这里设置为1是因为 在递归中 BST中最左边的结点被跳过了，作为初状态 该结点值出现一次，记录的出现最多次数一开始也为1
        maxCount = 1;
        curCount = 1;
        midTraversal(root);
        // 最右端结点的处理，从递归中看，最后一个结点与前一个结点相等只更新了curCount的值；不相等则只考虑到倒数第二个结点。
        if(curCount > maxCount)
            return new int[]{pre.val};
        if(curCount == maxCount)
            items.add(pre.val);
        int[] res = new int[items.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = items.get(i);
        return res;
    }

    private void midTraversal(TreeNode x) {
        if (x == null) return;
        midTraversal(x.left);
        if(pre != null){
            if(x.val != pre.val){ // 说明上一个值的结点数量已经统计完成 要看出现次数的情况
                if(curCount > maxCount){ // 出现次数更多，清空之前的出现少的数，更新最大出现次数
                    maxCount = curCount;
                    items.clear();
                    items.add(pre.val);
                }
                else if(curCount == maxCount){ // 不止一个众数
                    items.add(pre.val);
                }
                curCount = 1; // 当前值与上一个结点值不同，重置计数
            }
            else curCount++; // 当前值与上一个结点值相同，当前值的出现次数增加。
        }
        pre = x;
        midTraversal(x.right);
    }
```

思路分析：

* 找众数，说明要统计出现次数，一般会直接想到搞个哈希表来计数（就像后面的方法二）。但是如果一个有序数组中统计出现次数，使用双指针就能很好解决，类似的这里给我们的树不是一般的树，而是BST，中序遍历相当于遍历有序数组。

* 利用BST这个特性，我们不需要使用哈希表来计数。如同双指针的做法，这里也需要记录上一个结点`TreeNode pre;`这样才能知道当前结点值与谁比较；另外还需要记录某个值的出现次数`curCount`，以及出现次数的最大值`maxCount`（否则你咋知道谁出现最多次）。并且这里遍历过程中的众数信息需要记录（List存放众数）及更新。

* 在中序遍历中：

    * 如果`pre == null`，说明这是遍历的第一个结点，不需要处理（第一个结点的初条件在主函数中设定）。

    * 如果当前结点值与上一个结点值相等，那么这个数字的出现次数+1。
    * 否则，我们先去判断，上一个数字的出现次数`curCount`与之前的最大出现次数`maxCount`谁更大：
        * 如果上一个数字出现次数最大，需要更新众数信息。首先更新最大出现次数`maxCount = curCount;`。然后将之前记录的众数清空，再将上一个数字放入`items.clear();  items.add(pre.val);`
        * 如果一个数字出现次数等于最大出现次数，那么目前来看，它也是可能的众数，加入列表`items.add(pre.val);`
        * 否则，上一个数字一定不是众数，不管它，继续保留List中的数字。
        * 最后，重置计数`curCount = 1;`，表示当前数字出现一次了。
    * 然后更新`pre = x;`

* 回到主函数：

    * 特殊情况处理，`root == null`直接返回`new int[0]`。
    * 然后上文提到的初始化，因为最左边结点在中序的递归中不处理，所以，我们要首先将`maxCount = 1`，因为树非空总会有数字出现一次，然后`curCount = 1`，代表最左边结点被我们在主函数计数了。
    * 调用辅助函数后，还需要处理最后一个结点的值，因为在递归中不会有再下一个结点值与之不相等然后来判断它的值是否为众数。
        * 所以如果`curCount > maxCount`，说明最后一个结点才是唯一众数，`return new int[]{pre.val};`
        * 如果`curCount == maxCount`，说明最后一个结点也是众数，`items.add(pre.val);`
    * 最后，将List转成数组返回。

* 这个题要注意边界的考虑！

* 时间复杂度为$O(n)$， 不考虑递归调用栈的话，空间复杂度可以认为是常数，中间状态List的元素并不会很多。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.25%的用户

##### 2.使用哈希表记录元素出现次数

```java
	private Map<Integer, Integer> map;
    private int count, max;
    public int[] findMode2(TreeNode root) {
        if (root == null)
            return new int[0];
        map = new HashMap<>();
        count =  0;
        max = 0;
        midTraversal2(root);
        int[] res = new int[count];
        for(int key : map.keySet()){
            if(map.get(key) == max)
                res[--count] = key;
        }
        return res;
    }

    private void midTraversal2(TreeNode x){
        if(x == null)
            return;
        midTraversal2(x.left);
        int temp = map.getOrDefault(x.val, 0) + 1;
        map.put(x.val, temp);
        if(temp > max){
            max = temp;
            count = 1;
        } else if(temp == max){
            count++;
        }
        midTraversal2(x.right);
    }
```

思路分析：

* 看完方法1再来看方法二，显然简单了很多，因为这里没有使用BST的特性，必然也会慢不少。
* 思路就是利用哈希表计数，不过也在遍历中计数了众数的个数，以及众数出现了几次，这样方便后续对键遍历时找到众数和开辟数组。

运行结果：

* 5ms 
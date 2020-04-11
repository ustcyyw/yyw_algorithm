# 257. 二叉树的所有路径

### 原题
给定一个二叉树，返回所有从根节点到叶子节点的路径。
说明: 叶子节点是指没有子节点的节点。

示例:
输入:

```
   1
 /   \
2     3
 \
  5
```

输出: ["1->2->5", "1->3"]
解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/binary-tree-paths)：https://leetcode-cn.com/problems/binary-tree-paths

### 两种解法

##### 1.利用StringBuilder回溯

```java
private List<String> res;
    public List<String> binaryTreePaths(TreeNode root) {
        res = new ArrayList<>();
        if(root == null) return res;
        traversal(root, new StringBuilder());
        return res;
    }

    private void traversal(TreeNode x, StringBuilder sb){
        if(x.left == null && x.right == null)
            res.add(sb.toString() + x.val);
        else {
            String temp = x.val + "->";
            sb.append(temp);
            if(x.left != null)
                traversal(x.left, sb);
            if(x.right != null)
                traversal(x.right, sb);
            sb.delete(sb.length() - temp.length(), sb.length());
        }
    }
```

思路分析：

* 答案要求所有达到根结点的路径的字符串表示，这是一个典型的回溯问题。回溯在于，走到一个叶子结点，要查找别的路径时，要先回退到根结点。递归结构就是，当前节点添加到路径中后，先走左子树，后走右子树（相反也可以）。
* 递归函数`void traversal(TreeNode x, StringBuilder sb)`，第二个参数是用于记录路径的字符串表示的，也是为了方便进行回溯，因为`StringBuilder`可以使用制定删除区间的`delete(from, to)`方法。
    * 如果当前结点是叶子结点`x.left == null && x.right == null`。将之前保持的路径信息转化为字符串再拼接这个叶子结点的值。之前的路径信息的结尾是`"->"`，见下一条解释。
    * 如果当前结点不是叶子结点，将当前结点值与`"->"`拼接为`temp`并且放入路径信息中。然后进行左右子树的遍历。遍历后需要回溯，删除当前结点的路径信息` sb.delete(sb.length() - temp.length(), sb.length());`
* 主函数先处理特殊情况`root == null`，直接返回。如果不是就调用递归函数后返回。
* 对每一个结点都进行了遍历，所以时间复杂度为$O(n)$。空间复杂度有两部分，函数调用栈与树高成正比，最坏为$O(n)$，最好为$O(log(n))$。另外一部分是存放路径的空间，与路径总数正比。

运行结果：
* 执行用时 :7 ms, 在所有 Java 提交中击败了44.53%的用户
* 内存消耗 :38.1 MB, 在所有 Java 提交中击败了5.33%的用户

##### 2.利用List回溯

```java
private List<String> res;
    public List<String> binaryTreePaths(TreeNode root) {
        res = new ArrayList<>();
        if(root == null) return res;
        traversal2(root, new ArrayList<>());
        return res;
    }

    private void traversal2(TreeNode x, List<Integer> vals){
        if(x.left == null && x.right == null){
            StringBuilder sb = new StringBuilder();
            for(int i : vals)
                sb.append(i).append("->");
            sb.append(x.val);
            res.add(sb.toString());
        }
        else {
            vals.add(x.val);
            if(x.left != null)
                traversal2(x.left, vals);
            if(x.right != null)
                traversal2(x.right, vals);
            vals.remove(vals.size() - 1);
        }
    }
```

思路分析：

* 递归结构一致，只是用于回溯的数据结构不一样，这里使用List来存放路径信息，那么回溯时删除最后一个元素即可`vals.remove(vals.size() - 1);`。在递归到叶子结点时，就将元素依次取出按照题目规定的表示方式构造字符串，然后放入结果集中即可。
* 对每一个结点都进行了遍历，所以时间复杂度为$O(n)$。空间复杂度有两部分，函数调用栈与树高成正比，最坏为$O(n)$，最好为$O(log(n))$。另外一部分是存放路径的空间，与路径总数正比。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了73.73%的用户
* 内存消耗 :38.5 MB, 在所有 Java 提交中击败了5.33%的用户
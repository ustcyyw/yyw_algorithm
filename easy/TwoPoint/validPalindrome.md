# 680. 验证回文字符串 Ⅱ

### 原题
给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。

示例 1:
输入: "aba"
输出: True

示例 2:
输入: "abca"
输出: True
解释: 你可以删除c字符。

注意:
字符串只包含从 a-z 的小写字母。字符串的最大长度是50000。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/valid-palindrome-ii)：https://leetcode-cn.com/problems/valid-palindrome-ii

### 两种解法

##### 1.双指针（我的解法）

```java
public boolean validPalindrome(String s) {
        return isValid(s, true) || isValid(s, false);
    }

    private boolean isValid(String s, boolean deleteLeft){
        boolean flag = true;
        for(int i = 0, j = s.length() - 1; i < j; i++, j--){
            if(s.charAt(i) != s.charAt(j)){
                if(flag){
                    if(deleteLeft){
                        if(s.charAt(i + 1) == s.charAt(j)){
                            i++;
                            flag = false;
                        }
                        else return false;
                    }
                    else {
                        if(s.charAt(i) == s.charAt(j - 1)){
                            j--;
                            flag = false;
                        }
                        else return false;
                    }
                }
                else return false;
            }
        }
        return true;
    }
```

思路分析：

* 由于回文字符串的对称性，需要比较关于中轴的两个字符是否相等，所以都是使用双指针，一个指向字符串的头，另外一个指向字符串的尾，对比完一组字符后，两个指针均向中心移动一个位置。
* 但是本题中允许删除一个字符（或者不删除），所以在双指针的基础上还需要处理可能需要删除字符的情况。
* 什么时候需要删除一个字符？就是当对应的两个字符不相等的时候，要删除其中一个。删除左边的字符与删除右边的字符不一样。
    * 如果删除左边字符，也就是删除`i`对应的字符，那么删除后，与`j`关于中心对应的字符就是`i + 1`。判断`s.charAt(i + 1) == s.charAt(j)`成立，说明删除`i`后可能可以构成回文串，否则又一个对应的字符不相等，直接返回`false`。
    * 如果删除右边字符，也就是删除`j`对应的字符，那么删除后，与`i`关于中心对应的字符就是`j - 1`。判断`s.charAt(i) == s.charAt(j - 1)`成立，说明删除`j`后可能可以构成回文串，否则又一个对应的字符不相等，直接返回`false`。
* 定义辅助函数定义函数`boolean isValid(String s, boolean deleteLeft)`，
    * 第二个参数表示第一次遇到对应位置不相等的字符时删除左边的字符还是删除右边的字符。
    * 在这个函数中，还需要注意，第一个遇到对应位置不相等的字符时才进行删除，所以用变量` boolean flag`来表示是否是第一次。所以在删除字符串的操作之后，要将`flag = false`。
    * 第二次遇到对应位置不相等的字符时，就会直接返回`false`
    * 当双指针相遇（或者相交），循环停止，那么这个字符串就是一个回文串或者通过删除一个字符而变成回文串的。返回`true`
* 主函数返回`isValid(s, true) || isValid(s, false);`，因为删除左边的字符与删除右边的字符不一样。可能删除左边字符得到回文串也可能是删除右边字符，所以用或的逻辑。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :9 ms, 在所有 Java 提交中击败了75.64%的用户
* 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.09%的用户

##### 2.双指针（官方解法）

```java
public boolean validPalindrome2(String s) {
        for(int i = 0, j = s.length() - 1; i < j; i++, j--){
            if(s.charAt(i) != s.charAt(j)){
                // 第一次出现不等的情况，删左边元素或者删右边元素再检查剩余元素是否为回文
                return isPalindrome(s, i + 1, j) || isPalindrome(s, i, j - 1);
            }
        }
        //循环中没有出现左右两边不相等的情况，不需要删除就是回文，返回true
        return true;
    }

    private boolean isPalindrome(String s, int lo, int hi){
        for(; lo < hi; lo++, hi--){
            if(s.charAt(lo) != s.charAt(hi))
                return false;
        }
        return true;
    }
```

思路分析：

* 大体思路与方法一一致。只是这里删除左边的字符与删除右边的字符的处理方式是通过移动指针来完成。
* 辅助函数`boolean isPalindrome(String s, int lo, int hi)`表示`[lo, hi]`的子字符串是否为回文字符串，这里就是简单的双指针移动。
* 主函数中也是使用双指针的移动，当第一次遇到对应位置不相等的字符时，就返回`isPalindrome(s, i + 1, j) || isPalindrome(s, i, j - 1)`。这里通过修改传入的索引实参实现了删除左边的字符与删除右边的字符。删除左边字符传入`i + 1`，删除右边字符传入`j - 1`，同样使用逻辑或。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :9 ms, 在所有 Java 提交中击败了76.34%的用户
* 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.09%的用户
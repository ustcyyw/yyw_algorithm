package easy.ArrayAndMatrix;

/**
 * @Time : 2020年3月11日10:44:20
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给你一个整数数组 A，只有可以将其划分为三个和相等的非空部分时才返回 true，否则返回 false。
 * 形式上，如果可以找出索引 i+1 < j 且满足 
 * (A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2] + ... + A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1]) 就可以将数组三等分。
 *
 * 示例 1：
 * 输出：[0,2,1,-6,6,-7,9,1,2,0,1]
 * 输出：true
 * 解释：0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1
 *
 * 示例 2：
 * 输入：[0,2,1,-6,6,7,9,-1,2,0,1]
 * 输出：false
 *
 * 示例 3：
 * 输入：[3,3,6,5,-2,2,5,1,-9,4]
 * 输出：true
 * 解释：3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4
 *  
 *
 * 提示：
 * 3 <= A.length <= 50000
 * -10^4 <= A[i] <= 10^4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-array-into-three-parts-with-equal-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class canThreePartsEqualSum {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了86.52%的用户
     * 内存消耗 :44.6 MB, 在所有 Java 提交中击败了100.00%的用户
     */
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
        i++;
        for(int temp = 0; i < A.length; i++){
            temp += A[i];
            if(temp == sum / 3){
                count++;
                break;
            }
        }
        return count == 2 && i != A.length - 1;
    }

    public static void main(String[] args) {
        canThreePartsEqualSum c = new canThreePartsEqualSum();
        int[] test = {3,3,6,5,-2,2,5,1,-9,4}; // [6,1,1,13,-1,0,-10,20]
        System.out.println(c.canThreePartsEqualSum(test));
    }
}

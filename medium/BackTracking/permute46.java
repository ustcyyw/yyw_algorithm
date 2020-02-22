package medium.Backtracking;

import com.sun.jmx.snmp.SnmpNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年2月22日22:05:20
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */
public class permute46 {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了58.96%的用户
     * 内存消耗 :41 MB, 在所有 Java 提交中击败了5.03%
     */
    private List<List<Integer>> result = null;
    public List<List<Integer>> permute(int[] nums) {
        result = new ArrayList<>();
        if(nums == null || nums.length == 0)
            return result;

        List<Integer> remain = new ArrayList<>();
        for(int i : nums)
            remain.add(i);
        array(remain, new int[nums.length], 0);
        return result;
    }

    private void array(List<Integer> remain, int[] temp, int curIndex){
        if(remain.size() == 0){
            List<Integer> localResult = new ArrayList<>();
            for(int i : temp)
                localResult.add(i);
            result.add(localResult);
            return;
        }

        for(int i = remain.size() - 1; i >= 0; i--){
            temp[curIndex] = remain.remove(0);
            array(remain, temp, curIndex + 1);
            remain.add(temp[curIndex]);
        }
    }
}

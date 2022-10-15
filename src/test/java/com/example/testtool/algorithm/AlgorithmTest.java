/**
 * Author:   Herewe
 * Date:     2022/3/20 13:19
 * Description:
 */
package com.example.testtool.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmTest {
}

class Solution {
    public static int search(int[] nums, int target) {
        // 不能暴力，直接二分
        int i = 0;
        int j = nums.length - 1;
        int mid = (i + j) / 2;
        int right = 0;
        int left = 0;
        while (i <= j) {
            mid = (i + j) / 2;
            if (nums[mid] > target) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }
        }
        right = i;
        i = 0;
        j = nums.length - 1;
        while (j >= i) {
            mid = (i + j) / 2;
            if (nums[mid] >= target) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }

        }
        left = j;
        return right - left - 1;
    }
//intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2,
    public static void main(String[] args) {
        int[] a = {5,7,7,8,8,10};
        System.out.println(search(a, 6));
    }
}

class ListNode {
    @Test
    public void test() {
        largestNumber(new int[]{0,0});
    }

    public String largestNumber(int[] nums) {
        // 自定义一个compare,
        // 再使用常用的排序方法进行排序。
        for(int i = 0; i < nums.length; i++){
            for(int j = 0;j<nums.length-i-1;j++){
                if(compare(nums[j],nums[j+1])){
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
        String s = "";
        for(int i = 0; i < nums.length; i++){
            s += nums[i];
        }
        return s;
    }

    private boolean compare(int i,int j){
        char[] s1 = (String.valueOf(i) + String.valueOf(j)).toCharArray();
        char[] s2 = (String.valueOf(j) + String.valueOf(i)).toCharArray();
        for(int index = 0;index<s1.length; index++){
            if(s1[index] == s2[index]){
                continue;
            }
            if(s1[index] > s2[index]){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
}

/**
 * Author:   Herewe
 * Date:     2022/3/20 13:19
 * Description:
 */
package com.example.testtool.algorithm;

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
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

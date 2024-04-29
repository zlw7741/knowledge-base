package com.knowledge.algorithm.binarySearch;

/**
 * @author zhoulinwen
 * @title: BinarySearch
 * @description: 二分查找  非递归算法
 * @date 2024/4/15 7:16 PM
 * 题目：数组 {1,3, 8, 10, 11, 67, 100}, 编程实现二分查找， 分别使用递归和非递归的方式完成
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {1,3, 8,9,9,9,10, 11, 67, 100};
        int target = 9;
        // 非递归的方式
        int i = binarySearchNonRecursion(array, target);
        System.out.println("非递归的方式二分查找目标下标为：" + i);
        int i1 = binarySearchRecursion(array, target,0,array.length-1);
        System.out.println("递归的方式二分查找目标下标为：" + i1);
    }

    /**
     * 二分查找（非递归），针对有序数组
     * @param array 有序数组
     * @param target 目标数字
     * @return 目标下标，没有返回-1
     */
    public static int binarySearchNonRecursion(int[] array, int target){
        int startIndex = 0;// 开始下标
        int endIndex = array.length -1; // 结束下标
        
        // 1.如果左边下标小于右边下标，就执行
        while (startIndex <= endIndex){
            // 2.获取中间下标
            int mid = (startIndex + endIndex)/2;
            if(array[mid] == target){ // 如果中间下标等于目标值，返回下标
                return mid;
            } else if (array[mid] > target) { // 如果中间的数大于目标数
                endIndex = mid - 1;
            }else { // 中间的数小于目标数
                startIndex = mid + 1;
            }
        }
        // 否则没有找到
        return -1;
    }

    /**
     * 二分查找 递归的方式，针对有序数组
     * @param array 有序数组
     * @param target 目标
     * @return 下标
     */
    public static int binarySearchRecursion(int[] array, int target,int startIndex,int endIndex){
        // 如果左下标大于右下标，说明没有找到
        if(startIndex > endIndex){
            return -1;
        }
        // 中间下标
        int mid = (startIndex + endIndex)/2;
        if(array[mid] == target){ // 如果找到，返回
            return mid;
        } else if (array[mid] > target) { // 如果中间的数大于目标数，向左查找
            return binarySearchRecursion(array, target, startIndex, mid-1);
        } else {// 否则中间的数小于目标数，向右查找
            mid = (mid + 1 + array.length) / 2;
            return binarySearchRecursion(array, target, mid+1, endIndex);
        }
    }
    
}

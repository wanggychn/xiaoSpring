package com.algorithm.demo;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {90, 5, 12, 16, 8, 0};
        // 外层遍历次数，如果只有一个元素，则不用再继续遍历；所以 i < arr.length - 1
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层对比大小，外层每循环一遍，则拿到一个最大值，则数组长度 - 1；所以 j < arr.length -i -i
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果左边值比相邻的右边值大，则交换位置
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        for (int j : arr) {
            System.out.println(j);
        }
    }

}

/*
把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。

输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。

例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为 1。
*/
class Solution {
    
    public int minArray(int[] numbers) {
        int l = 0, r = numbers.length - 1;
        int mid = l;
        
        while (numbers[l] >= numbers[r]) {
            if (r - l == 1) {
                mid = r;
                break;
            }
            mid = l + (r - l) / 2;
            
            /* l = mid = r的情况无法确定最小值在左边还是右边，采用顺序查找 */
            if (numbers[l] == numbers[mid] && 
                numbers[r] == numbers[mid]) {
                mid = searchMinIdx(numbers, l, r);
                break;
            }
            
            if (numbers[mid] >= numbers[l]) {
                l = mid;
            } else if (numbers[mid] <= numbers[r]){
                r = mid;
            }
        }
        
        return numbers[mid];
    }
    /* 顺序查找指定数组区间内的最小数字 */
    private int searchMinIdx(int[] nums, int l, int r) {
        int ret = l;
        for (int i = l + 1; i <= r; i++) {
            if (nums[i] < nums[ret]) {
                ret = i;
            }
        }
        return ret;
    }
    
}

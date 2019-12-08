//[33]搜索旋转排序数组
//假设按照升序排序的数组在预先未知的某个点上进行了旋转。 
//
// ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。 
//
// 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。 
//
// 你可以假设数组中不存在重复的元素。 
//
// 你的算法时间复杂度必须是 O(log n) 级别。 
//
// 示例 1: 
//
// 输入: nums = [4,5,6,7,0,1,2], target = 0
//输出: 4
// 
//
// 示例 2: 
//
// 输入: nums = [4,5,6,7,0,1,2], target = 3
//输出: -1 
// Related Topics 数组 二分查找


public class SearchInRotatedSortedArray {

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[l] <= nums[mid] && (target < nums[l] || target > nums[mid])) {
                l = mid + 1;
            } else if (target < nums[l] && target > nums[mid]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l == r && nums[l] == target ? l : -1;
    }

    public static void main(String[] args) {
        int result;
        SearchInRotatedSortedArray solution = new SearchInRotatedSortedArray();

        result = solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
        System.out.println(result);
        if (result != 4) {
            throw new AssertionError("case1");
        }

        result = solution.search(new int[]{1, 3}, 3);
        System.out.println(result);
        if (result != 1) {
            throw new AssertionError("case2");
        }

        result = solution.search(new int[]{5, 1, 3}, 5);
        System.out.println(result);
        if (result != 0) {
            throw new AssertionError("case3");
        }
    }
}
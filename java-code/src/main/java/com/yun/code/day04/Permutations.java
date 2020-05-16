package com.yun.code.day04;
import java.util.ArrayList;
import java.util.List;
public class Permutations {
    //题目描述：Given a collection of distinct integers, return all possible permutations.（给定一组不同的整数，返回其所有的可能组合）
    public List<List<Integer>> permute(int[] nums) {
        //一个全局变量，用于保存所有集合
        List<List<Integer>> list = new ArrayList<>();
        //传入三个参数，没有附加参数
        backtrack(list, new ArrayList<>(), nums);
        return list;
    }
    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
        //一个终结条件，也就是满足条件的时候
        if(tempList.size() == nums.length){
            //全局变量添加一个满足条件的集合
            list.add(new ArrayList<>(tempList));
        } else{
            for(int i = 0; i < nums.length; i++){//0
                if(tempList.contains(nums[i])) continue;
                //如果tempList没有包含nums[i]才添加
                tempList.add(nums[i]);
                //递归调用，此时的tempList一直在变化，直到满足终结条件才结束
                backtrack(list, tempList, nums);
                System.out.println("tempList的内容:"+tempList+"-------"+"i的值:"+i);
                //它移除tempList最后一个元素的作用就是返回上一次调用时的数据，也就是希望返回之前的节点再去重新搜索满足条件。这样才能实现回溯
                tempList.remove(tempList.size() - 1);
            }
        }
    }
    public static void main(String[] args){
        int[] nums={1,2,3};
        List<List<Integer>> permute = (new Permutations()).permute(nums);

        System.out.println(permute);
    }
}

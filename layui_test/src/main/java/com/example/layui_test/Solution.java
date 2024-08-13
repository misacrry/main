package com.example.layui_test;

import java.util.HashSet;

public class Solution {

    public static int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int length = 0;
        int max = 0;
        int times = 0;
        for (int i = 0; i < s.length(); i++) {
            char num = s.charAt(i);
            System.out.println(s.charAt(i));
            if(set.add(s.charAt(i))) {
                length++;
            } else  {
                if(length >= max){
                    max = length;
                }
                times++;
                i=times-1;
                length = 0;
                set.clear();
            }
            if(max == 65536) {
                return 65536;
            }
        }
        if(times==0){
            return s.length();
        }
        return Math.max(max, length);
    }

    public static void  main(String[] args) {
        String  s = "tmmzuxt";

        System.out.println(lengthOfLongestSubstring(s));
    }
}

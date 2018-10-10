package com.blogspot.alexeykutovenko.scalemodelsreader.utilities;

public class MyAppHelper {
    public static String convertArrayToString(String[] array){
        String strSeparator = "__,__";
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }
}

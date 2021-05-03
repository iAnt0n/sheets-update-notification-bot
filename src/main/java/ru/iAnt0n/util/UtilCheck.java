package ru.iAnt0n.util;

import java.util.List;

public class UtilCheck {
    public static boolean checkEquality(List<String> l1, List<String> l2){
        if (l1==null||l2==null){
            return false;
        }
        if (l1.size()!=l2.size()) return false;
        else {
            for (int i = 0; i < l1.size(); i++){
                if (!l1.get(i).equals(l2.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
}

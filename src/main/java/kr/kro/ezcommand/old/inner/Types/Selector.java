package kr.kro.ezcommand.old.inner.Types;

import java.util.List;

public class Selector
{
    public static void setSelectorList(List list)
    {
        for(int i=0;i<selectorList.values().length;i++)
        {
            list.add(selectorList.values()[i].kr);
        }
    }
    public static selectorList getSelectorListValue(String name)
    {
        selectorList[] list = selectorList.values();
        for(int i=0;i<list.length;i++)
        {
            if(list[i].code == name)
                return list[i];
            if(list[i].kr == name)
                return list[i];
        }
        return null;
    }

    public enum selectorList
    {
        allEntity ("@e","모든 엔티티"),
        allPlayer ("@a","모든 플레이어"),
        randomPlayer ("@r","랜덤 플레이어"),
        nearestPlayer ("@p","가장 가까운 플레이어"),
        executor ("@s","명령어 실행자 (자신)"),

        playerNickname ("","");

        private final String code;
        private final String kr;

        selectorList(String code,String kr) {
            this.code = code;
            this.kr = kr;
        }

        public String toStringCode() {
            return this.code;
        }
        public String toString() {return this.kr;}
        /*public selectorList valueOf(String name)
        {
            selectorList[] list = selectorList.values();
            for(int i=0;i<list.length;i++)
            {
                if(list[i].code == name)
                    return list[i];
                if(list[i].kr == name)
                    return list[i];
            }
            return null;
        }*/
    }

    public static boolean isNickName(String name)
    {
        char[] name1 = name.toLowerCase().toCharArray();
        for(int i=0;i<name1.length;i++)
        {
            if(!(name1[i] == '_' || ('0'<=name1[i] && name1[i]<='9') || ('a'<=name1[i] && name1[i]<='z')))
            {
                return false;
            }
        }
        return true;
    }
}

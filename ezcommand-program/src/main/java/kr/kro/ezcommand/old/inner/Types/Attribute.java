package kr.kro.ezcommand.old.inner.Types;

import java.util.List;

public class Attribute
{
    public static void setAttributeList(List list)
    {
        for(int i=0;i<AttributeList.values().length;i++) {
            list.add(AttributeList.values()[i].kr);
        }
    }

    public static void setModifierTypeList(List list)
    {
        for(int i=0;i<modifierType.values().length;i++) {
            list.add(modifierType.values()[i].kr);
        }
    }

    public enum AttributeList
    {
        Generic_MAX_HEALTH ("minecraft:generic.max_health","최대 체력"),
        Generic_FOLLOW_RANGE ("minecraft:generic.follow_range","탐지 범위"),
        Generic_KNOCKBACK_RESISTANCE ("minecraft:generic.knockback_resistance","밀치기 저항"),
        Generic_MOVEMENT_SPEED ("minecraft:generic.movement_speed","이동 속도"),
        Generic_ATTACK_DAMAGE ("minecraft:generic.attack_damage","공격력"),
        Generic_ARMOR ("minecraft:generic.armor","방어력"),
        Generic_ARMOR_TOUGHNESS ("minecraft:generic.armor_toughness","갑옷 방어력"),
        Generic_ATTACK_KNOCKBACK ("minecraft:generic.attack_knockback","밀치기"),

        //PLAYER:
        Generic_Player_ATTACK_SPEED ("minecraft:generic.attack_speed","공격 속도 (플레이어)"),
        Generic_Player_LUCK ("minecraft:generic.luck","행운 (플레이어)"),

        //HORSE:
        Horse_JUMP ("minecraft:horse.jump","점프력 (말)"),

        //BEE & PARROT:
        Generic_BeeParrot_FLYING_SPEED ("minecraft:generic.flying_speed","비행 속도 (꿀벌, 앵무새)"),

        //ZOMBIE:
        Zombie_SPAWN_REINFORCEMENTS ("minecraft:zombie.spawn_reinforcements","아군 소환률 (좀비)");

        private final String code;
        private final String kr;

        AttributeList(String code,String kr) {
            this.code=code;
            this.kr=kr;
        }

        public boolean equalsName(String otherName) {
            // (otherName == null) check is not needed because name.equals(null) returns false
            return code.equals(otherName);
        }

        public String toStringCode() {
            return this.code;
        }
        public String toString() {return this.kr;}
    }

    public enum modifierType {
        ADD ("add","더하기"),
        MULTIFLY_BASE ("multifly_base","모아서 곱하기"),
        MULTIFLY ("multifly","곱하기");

        private final String code;
        private final String kr;

        modifierType(String code,String kr) {
            this.code = code;
            this.kr = kr;
        }

        public String toStringCode() {
            return this.code;
        }
        public String toString() {return this.kr;}
    }
}
package kr.kro.ezcommand.old.inner.Types;

import java.util.List;

public class Effect
{
    public static void setEffectList(List list)
    {
        for(int i=0;i<EffectType.values().length;i++)
        {
            list.add(EffectType.values()[i].kr);
        }
    }

    public enum EffectType {
        ABSORPTION ("minecraft:absorbtion","흡수"),
        UNLUCK ("minecraft:unluck","불운"),
        BAD_OMEN ("minecraft:bad_omen","흉조"),
        BLINDNESS ("minecraft:blindness","실명"),
        CONDUIT_POWER ("minecraft:conduit_power","전달체의 힘"),
        DOLPHINS_GRACE ("minecraft:dolphins_grace","돌고래의 우아함"),
        FIRE_RESISTANCE ("minecraft:fire_resistance","화염 저항"),
        GLOWING ("minecraft:glowing","발광"),
        HASTE ("minecraft:haste","성급함"),
        HEALTH_BOOST ("minecraft:health_boost","재생"),
        HERO_OF_THE_VILLAGE ("minecraft:hero_of_the_village","마을의 영웅"),
        HUNGER ("minecraft:hunger","허기"),
        INSTANT_DAMAGE ("minecraft:instant_damage","즉시 피해"),
        INSTANT_HEALTH ("minecraft:instant_health","즉시 회복"),
        INVISIBILITY ("minecraft:invisibility","투명"),
        JUMP_BOOST ("minecraft:jump_boost","점프 강화"),
        LEVITATION ("minecraft:levitation","공중 부양"),
        LUCK ("minecraft:luck","행운"),
        MINING_FATIGUE ("minecraft:mining_fatigue","채굴 피로"),
        NAUSEA ("minecraft:nausea","실명"),
        NIGHT_VISION ("minecraft:night_vision","야간 투시"),
        POISON ("minecraft:poison","독"),
        REGENERATION ("minecraft:regeneration","재생"),
        RESISTANCE ("minecraft:resistance","저항"),
        SATURATION ("minecraft:saturation","포화"),
        SLOW_FALLING ("minecraft:slow_falling","느린 낙하"),
        SLOWNESS ("minecraft:slowness","속도 감소"),
        SPEED ("minecraft:speed","속도 증가"),
        STRENGTH ("minecraft:strength","힘"),
        WATER_BREATHING ("minecraft:water_breathing", "수중 호흡"),
        WEAKNESS ("minecraft:weakness","나약함"),
        WITHER ("minecraft:wither","위더");

        private final String code;
        private final String kr;

        private EffectType(String code,String kr) {
            this.code = code;
            this.kr = kr;
        }

        public String toStringCode() {
            return this.code;
        }
        public String toString() {return this.kr;}
    }
}
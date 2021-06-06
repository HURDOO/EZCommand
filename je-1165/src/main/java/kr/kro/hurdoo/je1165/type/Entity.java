package kr.kro.hurdoo.je1165.type;

import kr.kro.ezcommand.engine.parser.NBT;

import java.util.ArrayList;
import java.util.List;

public class Entity
{
    private List<NBT> nbt = new ArrayList<>();
    private final String name;
    private final String parse;
    private final List<NBT> nbtList;
    private final Entity parent;

    public Entity(String name,String parse,List<NBT> nbts, Entity[] parents)
    {
        this.name = name;
        this.parse = parse;
        nbtList = nbts;

        if(parents.length > 1) throw new IllegalArgumentException("Parent cannot exceed one");
        if(parents.length == 1) parent = parents[0];
        else parent = null;
    }
}

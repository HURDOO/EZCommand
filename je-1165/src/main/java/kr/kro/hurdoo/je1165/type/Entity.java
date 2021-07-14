package kr.kro.hurdoo.je1165.type;

import java.util.ArrayList;
import java.util.List;

public class Entity
{
    private List<NBT> nbt = new ArrayList<>();
    private final String name;
    private final String parse;
    private final Entity parent;

    public Entity(String name,String parse,List<NBT> nbts, Entity[] parents)
    {
        this.name = name;
        this.parse = parse;
        nbt.addAll(nbts);

        if(parents.length > 1) throw new IllegalArgumentException("Parent cannot exceed one");
        if(parents.length == 1) parent = parents[0];
        else parent = null;
    }
}

package kr.kro.hurdoo.je1165.type.entity;

import kr.kro.hurdoo.je1165.type.NBT;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Entity
{
    public static final List<Entity> entities = new LinkedList<>();

    private List<NBT> nbt = new ArrayList<>();
    private final String name;
    private final String parse;
    private final Entity parent;

    private final boolean isRealEntity;
    private final File image;

    private final EntityNode ui;

    public Entity(String name,String parse,List<NBT> nbts, boolean isRealEntity, Entity parent, String imageLoc)
    {
        entities.add(this);

        this.name = name;
        this.parse = parse;
        nbt.addAll(nbts);
        this.parent = parent;
        this.isRealEntity = isRealEntity;
        File file = new File(imageLoc);
        if(!file.exists() || !file.isFile()) image = null;
        else image = file;
        ui = new EntityNode(this);
    }

    public File getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public EntityNode getUi() {
        return ui;
    }
    public boolean isRealEntity() {
        return isRealEntity;
    }

}

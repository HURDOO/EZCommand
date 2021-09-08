package kr.kro.hurdoo.je1165.type.entity;

import kr.kro.ezcommand.engine.parser.file.EZData;
import kr.kro.hurdoo.je1165.type.NBT;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Entity implements EZData
{
    public static final List<Entity> entities = new ArrayList<>();

    private final List<NBT> nbt = new ArrayList<>();
    private final String name;
    private final String parse;
    private final Entity parent;

    private final boolean isRealEntity;
    private final File image;

    public Entity(String name,String parse,List<NBT> nbts, boolean isRealEntity, Entity parent, String imageLoc)
    {
        entities.add(this);

        this.name = name;
        this.parse = parse;
        nbt.addAll(nbts);
        this.parent = parent;
        this.isRealEntity = isRealEntity;
        if(imageLoc != null) {
            File file = new File(imageLoc);
            if (file.exists() && file.isFile()) image = file;
            else image = null;
        }
        else image = null;
    }

    public File getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public EntityNode getUi() {
        return new EntityNode(this);
    }
    public boolean isRealEntity() {
        return isRealEntity;
    }

    @Override
    public String toCommand() {
        return parse;
    }
}

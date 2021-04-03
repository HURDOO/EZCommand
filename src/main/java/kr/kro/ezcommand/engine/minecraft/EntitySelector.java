package kr.kro.ezcommand.engine.minecraft;

import kr.kro.ezcommand.engine.minecraft.Edition;
import kr.kro.ezcommand.engine.minecraft.EntityType;
import kr.kro.ezcommand.engine.minecraft.Gamemode;

public class EntitySelector {

    /**
     * @a, @e, @r, @p, @s
     *
     * Actually there is two more, but they are Education Edition... supporting from further version, maybe?
     */
    private enum SelectorType {
        AllPlayer, // @a
        AllEntity, // @e
        RandomPlayer, // @r
        NearPlayer, // @p
        Myself; // @s
    }
    private SelectorType selectorType;

    /**
     * Type of Entity.
     *
     * JAVA: @e
     * BEDROCK: @e, @r(Replacement of @e[sort=random])
     */
    private EntityType type;
    public void setEntityType(EntityType type)
    {
        this.type = type;
    }
    public EntityType getEntityType() {
        return type;
    }

    /**
     * Location of Entity.
     *
     * [x=min(x1,x2),dx=max(x1,x2)-min(x1,x2)]
     * y,z equals.
     */
    public double LOCATION_DEFAULT=40000000;
    private double
        x1=LOCATION_DEFAULT,x2=LOCATION_DEFAULT,
        y1=LOCATION_DEFAULT,y2=LOCATION_DEFAULT,
        z1=LOCATION_DEFAULT,z2=LOCATION_DEFAULT;
    public void setLocationX(double x1,double x2) {
        this.x1 = x1;
        this.x2 = x2;
    }
    public void setLocationY(double y1,double y2) {
        this.y1 = y1;
        this.y2 = y2;
    }
    public void setLocationZ(double z1,double z2) {
        this.z1 = z1;
        this.z2 = z2;
    }

    /**
     * Distance between Command's executed location & Entity.
     *
     * JAVA: [distance=min(r1,r2)..max(r1,r2)]
     * BEDROCK: [rm=min(r1,r2) , r=max(r1,r2)]
     */
    public double DISTANCE_DEFAULT = -1;
    private double r1=DISTANCE_DEFAULT,r2=DISTANCE_DEFAULT;
    public void setDistance(double r1,double r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    /**
     * Limitation of Entity count.
     *
     * JAVA: sort,limit
     * Bedrock: nearest[c=limit], furtherest[c=limit]
     */
    private enum Sort {
        NEAREST,
        FURTHEREST,
        RANDOM,
        ARBITRARY;
    }
    private int limit;
    private Sort sort;
    public void setLimitSort(int limit,Sort sort) {
        this.limit = limit;
        this.sort = sort;
    }
    public Sort getSort() {
        return sort;
    }

    /**
     * Level of Player.
     *
     * JAVA: [level=min(l1,l2)..max(l1,l2)]
     * BEDROCK: [lm=min(l1,l2) , max(l1,l2)]
     */
    private int l1,l2;
    public void setLevel(int l1,int l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    /**
     * Gamemode of Player.
     *
     * JAVA: gamemode
     * BEDROCK: gamemode (survival for 0,s / creative for 1,c / adventure for 2,a / no spectator)
     */
    private Gamemode gamemode;
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    /**
     * Name of Entity.
     */
    private String name;
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Rotation of Entity.
     *
     * JAVA: [x_rotation=min(rx1,rx2)..max(rx1,rx2) , y_rotation=min(rx1,rx2)..max(rx1,rx2)]
     * BEDROCK: [rxm=min(rx1,rx2) , rx=max(rx1,rx2) , rmy=min(ry1,ry2) , ry=max(ry1,ry2)]
     */
    private double rx1,rx2,ry1,ry2;
    public void setRotationX(double rx1,double rx2) {
        this.rx1 = rx1;
        this.rx2 = rx2;
    }
    public void setRotationY(double ry1,double ry2) {
        this.ry1 = ry1;
        this.ry2 = ry2;
    }

    /** @TODO
     * Scoreboard score;
     * ScoreboardTag tag;
     * ScoreboardTeam team;
     * NBT nbt; (JAVA)
     * Advancement advancement; (JAVA)
     * Predicate predicate; (JAVA)
     * Family family; (BEDROCK)
     */
    String custom;
    public void setCustomParameter(String custom) {
        this.custom = custom;
    }

    private StringBuilder command = new StringBuilder();
    private int cnt=0;
    public String toString(Edition edition)
    {
        switch(selectorType) {
            case AllPlayer:
                command.append("@a");
                break;
            case AllEntity:
                command.append("@e");
                break;
            case RandomPlayer:
                command.append("@r");
                break;
            case NearPlayer:
                command.append("@p");
                break;
            case Myself:
                command.append("@s");
                break;
            default:
                throw new NullPointerException("EntitySelector is Null");
        }

        if(type != null)
        {
            append("minecraft:" + type.toString().toLowerCase());
        }

        if(x1 != LOCATION_DEFAULT && x2 != LOCATION_DEFAULT)
        {
            double x = Math.min(x1,x2);
            double dx = Math.max(x1,x2) - x;
            append("x=" + x + ",dx=" + dx);
        }
        else if(x1!=LOCATION_DEFAULT)
            append("x=" + x1);

        if(y1 != LOCATION_DEFAULT && y2 != LOCATION_DEFAULT)
        {
            double y = Math.min(y1,y2);
            double dy = Math.max(y1,y2) - y;
            append("y=" + y + ",dy=" + dy);
        }
        else if(y1!=LOCATION_DEFAULT)
            append("y=" + y1);

        if(z1 != LOCATION_DEFAULT && z2 != LOCATION_DEFAULT)
        {
            double z = Math.min(z1,z2);
            double dz = Math.max(z1,z2) - z;
            append("z=" + z + ",dz=" + dz);
        }
        else if(z1!=LOCATION_DEFAULT)
            append("z=" + z1);

        if(r1 != DISTANCE_DEFAULT && r2 != DISTANCE_DEFAULT)
        {
            if(r1 != r2)
            {
                double r = Math.max(r1,r2);
                double rm = Math.min(r1,r2);
                append("rm=" + rm + ",r=" + r);
            }
            else
                append("r=" + r1);
        }
        else if(r1 != DISTANCE_DEFAULT)
            append("r=" + r1);

        return command.toString();
    }
    private void append(String string) {
        if(cnt == 0) command.append("[" + string);
        else command.append("," + string);
        cnt++;
    }
}

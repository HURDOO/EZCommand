package kr.kro.ezcommand.engine.thirdparty;

import java.util.ArrayList;
import java.util.List;


public class EZPack
{
    private String name;
    private String version;
    private String description;
    private String author;
    private String pack_code;
    private String api_version;
    private final List<String> dependencyEZPacks = new ArrayList<>();
    private final List<String> dependencyEZPlugins = new ArrayList<>();

    public EZPack(String name, String version, String pack_code, String api_version) {
        this.name = name;
        this.version = version;
        this.pack_code = pack_code;
        this.api_version = api_version;
    }


    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }
    public String getPack_code() {
        return pack_code;
    }
    public String getApi_version() {
        return api_version;
    }
    public List<String> getDependencyEZPacks() {
        return dependencyEZPacks;
    }
    public List<String> getDependencyEZPlugins() {
        return dependencyEZPlugins;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}

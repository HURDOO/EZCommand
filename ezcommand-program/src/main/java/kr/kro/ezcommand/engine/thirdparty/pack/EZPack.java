package kr.kro.ezcommand.engine.thirdparty.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class EZPack {
    private final String name;
    private final String version;
    private final String description;
    private final String author;
    private final String pack_code;
    private final String api_version;
    public final List<String> dependencyPacks = new ArrayList<>();
    public final List<String> dependencyPlugins = new ArrayList<>();

    public EZPack(String name, String version, String pack_code, String api_version,
                  String description, String author, Collection<String> dependencyPacks, Collection<String> dependencyPlugins) {

        // needed
        this.name = name;
        this.version = version;
        this.pack_code = pack_code;
        this.api_version = api_version;

        // optional
        this.description = description;
        this.author = author;
        if(dependencyPacks != null) this.dependencyPacks.addAll(dependencyPacks);
        if(dependencyPlugins != null) this.dependencyPlugins.addAll(dependencyPlugins);
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
}

package kr.kro.ezcommand.engine.thirdparty.plugin;

import java.util.ArrayList;
import java.util.List;

public class EZPluginYML {
    private final String name;
    private final String main;
    private final String version;
    private final String pluginCode;
    private final String apiVersion;

    private final String description;
    private final String author;
    public final List<String> dependencyPack = new ArrayList<>();
    public final List<String> dependencyPlugin = new ArrayList<>();

    public EZPluginYML(String name,String main,String version,String pluginCode,String apiVersion,
                       String description,String author,List<String> dependencyPack,List<String> dependencyPlugin) {
        this.name = name;
        this.main = main;
        this.version = version;
        this.pluginCode = pluginCode;
        this.apiVersion = apiVersion;

        this.description = description;
        this.author = author;
        if(dependencyPack != null) this.dependencyPack.addAll(dependencyPack);
        if(dependencyPlugin != null) this.dependencyPlugin.addAll(dependencyPlugin);
    }

    public String getName() {
        return name;
    }
    public String getMain() {
        return main;
    }
    public String getVersion() {
        return version;
    }
    public String getPluginCode() {
        return pluginCode;
    }
    public String getApiVersion() {
        return apiVersion;
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }
}

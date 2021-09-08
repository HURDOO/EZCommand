package kr.kro.ezcommand.engine.thirdparty.plugin;

import java.util.ArrayList;
import java.util.List;

public class EZPlugin {
    private final String name;
    private final String main;
    private final String version;
    private final String pluginCode;
    private final String apiVersion;

    private final String description;
    private final String author;
    public final List<String> dependencyPacks = new ArrayList<>();
    public final List<String> dependencyPlugins = new ArrayList<>();

    public EZPlugin(String name, String main, String version, String pluginCode, String apiVersion,
                    String description, String author, List<String> dependencyPacks, List<String> dependencyPlugins) {
        this.name = name;
        this.main = main;
        this.version = version;
        this.pluginCode = pluginCode;
        this.apiVersion = apiVersion;

        this.description = description;
        this.author = author;
        if(dependencyPacks != null) this.dependencyPacks.addAll(dependencyPacks);
        if(dependencyPlugins != null) this.dependencyPlugins.addAll(dependencyPlugins);
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

package kr.kro.ezcommand.engine.thirdparty.plugin;

public class EZPlugin {
    private final String name;
    private final String version;
    private final String pluginCode;

    private final String description;
    private final String author;

    public EZPlugin(String name,String version,String pluginCode,
                    String description,String author)
    {
        this.name = name;
        this.version = version;
        this.pluginCode = pluginCode;
        this.description = description;
        this.author = author;
    }

    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public String getPluginCode() {
        return pluginCode;
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }

}

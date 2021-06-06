package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.engine.thirdparty.EZPack;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class YAMLParser {
    public static EZPack parseEZPack(Map<String, Object> yaml) {
        Yaml parser = new Yaml();

        String name = yaml.get("name").toString();
        String version = yaml.get("version").toString();
        String pack_code = yaml.get("pack-code").toString();
        String api_version = yaml.get("api-version").toString();

        assert name != null;
        assert version != null;
        assert pack_code != null;
        assert api_version != null;

        EZPack pack = new EZPack(name, version, pack_code, api_version);

        pack.setDescription(yaml.get("description").toString());
        pack.setAuthor(yaml.get("author").toString());

        Map<String, String> dependencies = parser.load(parser.dump(yaml.get("dependencies")));

        List<String> dependencyPack = parser.load(parser.dump(dependencies.get("ezpack")));
        if (dependencyPack != null) {
            for (String str : dependencyPack) {
                pack.getDependencyEZPacks().add(str);
            }
        }

        List<String> dependencyPlugin = parser.load(parser.dump(dependencies.get("ezplugin")));
        if (dependencyPlugin != null) {
            for (String str : dependencyPlugin) {
                pack.getDependencyEZPlugins().add(str);
            }
        }

        return pack;
    }


}

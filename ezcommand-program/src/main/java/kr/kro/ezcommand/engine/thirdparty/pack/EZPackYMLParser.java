package kr.kro.ezcommand.engine.thirdparty.pack;

import org.apache.commons.lang3.Validate;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class EZPackYMLParser {

    public static EZPack parseEZPack(Map<String, Object> yaml) {
        Yaml parser = new Yaml();

        String name = yaml.get("name").toString();
        String version = yaml.get("version").toString();
        String pack_code = yaml.get("pack-code").toString();
        String api_version = yaml.get("api-version").toString();

        String description = yaml.get("description").toString();
        String author = yaml.get("author").toString();

        Map<String, String> dependencies = parser.load(parser.dump(yaml.get("dependencies")));
        List<String> dependencyPack = parser.load(parser.dump(dependencies.get("ezpack")));
        List<String> dependencyPlugin = parser.load(parser.dump(dependencies.get("ezplugin")));

        EZPack pack = new EZPack(name, version, pack_code, api_version,
                description, author, dependencyPack, dependencyPlugin);

        return pack;
    }


}


import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class YamlImporter extends FileImporter {

    @Override
    public void importFile(File file, ReactorStorages reactorMap) throws IOException {
        if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
            try {
                Yaml yaml = new Yaml();
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    Iterable<Object> objects = yaml.loadAll(inputStream);
                    for (Object object : objects) {
                        @SuppressWarnings("unchecked")
                        Map<String, ?> map = (Map<String, ?>) object;
                        for (String key : map.keySet()) {
                            Map<?, ?> innerMap = (Map<?, ?>) map.get(key);
                            Reactor reactor = parseDict(innerMap);
                            reactorMap.addReactor(key, reactor);
                        }
                    }
                }
            } catch (IOException e) {}
        } else
            if (next != null) {
            next.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }

    private Reactor parseDict(Map<?, ?> innerMap){
        String type = (String) innerMap.get("type");
        String reactor_class = (String) innerMap.get("class");
        Double burnup = ((Number) innerMap.get("burnup")).doubleValue();
        Double kpd = ((Number) innerMap.get("kpd")).doubleValue();
        Double enrichment = ((Number) innerMap.get("enrichment")).doubleValue();
        Double thermal_capacity = ((Number) innerMap.get("termal_capacity")).doubleValue();
        Double electrical_capacity = ((Number) innerMap.get("electrical_capacity")).doubleValue();
        Integer life_time = (Integer) innerMap.get("life_time");
        Double first_load = ((Number) innerMap.get("first_load")).doubleValue();

        Reactor reactor = new Reactor(type, reactor_class, burnup, kpd, enrichment, thermal_capacity, electrical_capacity, life_time, first_load, "YAML");
        return reactor;
    }
}
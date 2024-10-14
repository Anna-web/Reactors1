
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class JsonImporter extends FileImporter {

    @Override
    public void importFile(File file, ReactorStorages reactorMap) throws IOException {
        if (file.getName().endsWith(".json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(file);

            for (Iterator<String> it = rootNode.fieldNames(); it.hasNext(); ) {
                String fieldName = it.next();
                JsonNode reactorNode = rootNode.get(fieldName);
                if (reactorNode != null && reactorNode.isObject()) {
                    Reactor reactor = parseReactor(reactorNode);
                    reactorMap.addReactor(fieldName, reactor);
                }
            }
        } else
            if (next != null) {
            next.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }

    private Reactor parseReactor(JsonNode reactorNode) {
        String type = reactorNode.has("type") ? reactorNode.get("type").asText() : null;
        String reactor_class = reactorNode.has("class") ? reactorNode.get("class").asText() : null;
        double burnup = reactorNode.has("burnup") ? reactorNode.get("burnup").asDouble() : 0.0;
        double kpd = reactorNode.has("kpd") ? reactorNode.get("kpd").asDouble() : 0.0;
        double enrichment = reactorNode.has("enrichment") ? reactorNode.get("enrichment").asDouble() : 0.0;
        double thermal_capacity = reactorNode.has("thermal_capacity") ? reactorNode.get("termal_capacity").asDouble() : 0.0;
        double electrical_capacity = reactorNode.has("electrical_capacity") ? reactorNode.get("electrical_capacity").asDouble() : 0.0;
        int life_time = reactorNode.has("life_time") ? reactorNode.get("life_time").asInt() : 0;
        double first_load = reactorNode.has("first_load") ? reactorNode.get("first_load").asDouble() : 0.0;

        return new Reactor(type, reactor_class, burnup, kpd, enrichment, thermal_capacity, electrical_capacity, life_time, first_load, "JSON");
    }
}
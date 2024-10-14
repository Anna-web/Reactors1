
import java.util.HashMap;
import java.util.Map;

public class ReactorStorages {
    private Map<String, Reactor> reactorMap;
    public ReactorStorages() {
        this.reactorMap = new HashMap<>();
    }
    public void addReactor(String key, Reactor reactor) {
        reactorMap.put(key, reactor);
    }
    public Map<String, Reactor> getReactorMap() {
        return reactorMap;
    }
}
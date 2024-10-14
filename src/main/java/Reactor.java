
public class Reactor {
    private String type;
    private String reactor_class;
    private Double burnup;
    private Double kpd;
    private Double enrichment;
    private Double thermal_capacity;
    private Double electrical_capacity;
    private Integer life_time;
    private Double first_load;
    private String source;

    public Reactor(String type, String reactor_class, Double burnup, Double kpd,
                   Double enrichment, Double thermal_capacity, Double electrical_capacity,
                   Integer life_time, Double first_load, String source) {
        this.type = type;
        this.reactor_class = reactor_class;
        this.burnup = burnup;
        this.kpd = kpd;
        this.enrichment = enrichment;
        this.thermal_capacity = thermal_capacity;
        this.electrical_capacity = electrical_capacity;
        this.life_time = life_time;
        this.first_load = first_load;
        this.source = source;
    }

    @Override
    public String toString() {
        return  "   Reactor class: " + reactor_class  + "\n" +
                "   Burnup: " + burnup + "\n" +
                "   KPD: " + kpd + "\n" +
                "   Enrichment: " + enrichment + "\n" +
                "   Thermal capacity: " + thermal_capacity + "\n" +
                "   Electrical capacity: " + electrical_capacity + "\n" +
                "   Life time:  " + life_time + "\n" +
                "   First load: " + first_load + "\n" +
                "   Source: " +source;
    }
}
package sample;

public enum Priority {
    CRITIC("Krytyczny"),
    HIGH("Wysoki"),
    MEDIUM("Średni"),
    NORMAL("Normalny");

    String priority;

Priority(String priority){
    this.priority = priority;
}
public String getPriority(){
    return priority;
}


}

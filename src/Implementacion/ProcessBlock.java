package Implementacion;

public class ProcessBlock {
    private String name; // Nombre del proceso
    private int arrivalTime; // Tiempo de llegada
    private int burstsToExecute; // Número de ráfagas por ejecutar
    private int burstsExecuted; // Número de ráfagas ejecutadas
    private int priority; // Prioridad del proceso
    private String state; // Estado del proceso (nuevo, listo, ejecutando, terminado)
    private int startTime = -1; // -1 indica que aún no ha comenzado
    private int endTime = -1; // -1 indica que aún no ha terminado

    // Constructor
    public ProcessBlock(String name, int arrivalTime, int burstsToExecute, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstsToExecute = burstsToExecute;
        this.burstsExecuted = 0; // Inicialmente, no se han ejecutado ráfagas
        this.priority = priority;
        this.state = "nuevo"; // Estado inicial
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstsToExecute() {
        return burstsToExecute;
    }

    public void setBurstsToExecute(int burstsToExecute) {
        this.burstsToExecute = burstsToExecute;
    }

    public int getBurstsExecuted() {
        return burstsExecuted;
    }

    public void setBurstsExecuted(int burstsExecuted) {
        this.burstsExecuted = burstsExecuted;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    // Método para actualizar el estado del proceso
    public void updateState(String newState) {
        setState(newState);
    }

    // Método para incrementar las ráfagas ejecutadas
    public void executeBurst() {
        if (this.burstsExecuted < this.burstsToExecute) {
            this.burstsExecuted++;
        }
        if (this.burstsExecuted == this.burstsToExecute) {
            updateState("terminado");
        }
    }

    public boolean isRunningAt(int time) {
        return time >= startTime && time < endTime;
    }
}

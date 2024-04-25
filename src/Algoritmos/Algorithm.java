package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.List;

public abstract class Algorithm {
    protected List<ProcessBlock> processList; // Lista de procesos a planificar

    // Constructor
    public Algorithm(List<ProcessBlock> processList) {
        this.processList = processList;
    }

    // Método abstracto para ejecutar el algoritmo de planificación
    public abstract void execute();

    // Getter y Setter para la lista de procesos
    public List<ProcessBlock> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessBlock> processList) {
        this.processList = processList;
    }

}

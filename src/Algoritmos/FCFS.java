package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.List;

public class FCFS extends Algorithm {

    // Constructor que utiliza el constructor de la clase base
    public FCFS(List<ProcessBlock> processList) {
        super(processList);
    }

    // Implementación del método execute() para FCFS
    @Override
    public void execute() {
        int currentTime = 0;
        for (ProcessBlock process : processList) {
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
            process.setStartTime(currentTime);
            // Suponiendo que burstsToExecute es la duración total de ejecución del proceso
            currentTime += process.getBurstsToExecute();
            process.setEndTime(currentTime);
            process.setState("terminado");
            // Actualizar otras estadísticas según sea necesario
        }
    }
}

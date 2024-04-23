package Algoritmos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HRRN extends Algorithm {

    // Constructor que utiliza el constructor de la clase base
    public HRRN(List<ProcessBlock> processList) {
        super(processList);
    }

    // Implementación del método execute() para HRRN
    @Override
    public void execute() {
        // Inicializar el tiempo actual del sistema
        int currentTime = 0;

        while (!allProcessesCompleted()) {
            // Calcular el ratio de respuesta para cada proceso que no ha terminado
            for (ProcessBlock process : processList) {
                if (!process.getState().equals("terminado")) {
                    double waitingTime = currentTime - process.getArrivalTime();
                    double responseRatio = (waitingTime + process.getBurstsToExecute()) / (double) process.getBurstsToExecute();
                    process.setPriority((int) (responseRatio * 100));  // Uso de la prioridad para almacenar el ratio temporalmente
                }
            }

            // Seleccionar el proceso con el mayor ratio de respuesta
            ProcessBlock nextProcess = Collections.max(processList, Comparator.comparingInt(ProcessBlock::getPriority));

            // Procesar el proceso seleccionado
            nextProcess.setState("ejecutando");
            while (nextProcess.getBurstsExecuted() < nextProcess.getBurstsToExecute()) {
                nextProcess.executeBurst();
                currentTime++;
            }
            nextProcess.setState("terminado");
        }
    }

    private boolean allProcessesCompleted() {
        for (ProcessBlock process : processList) {
            if (!process.getState().equals("terminado")) {
                return false;
            }
        }
        return true;
    }
}

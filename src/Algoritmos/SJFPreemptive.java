package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJFPreemptive extends Algorithm {

    // Constructor que utiliza el constructor de la clase base
    public SJFPreemptive(List<ProcessBlock> processList) {
        super(processList);
    }

    // Implementación del método execute() para SJF preemptivo
    @Override
    public void execute() {
        int currentTime = 0;
        ProcessBlock currentProcess = null;

        // Ordenar los procesos por tiempo de llegada para manejar los que llegan al mismo tiempo
        Collections.sort(processList, Comparator.comparingInt(ProcessBlock::getArrivalTime));

        while (!allProcessesCompleted()) {
            // Seleccionar el proceso con el menor tiempo de ráfaga restante que ha llegado
            ProcessBlock nextProcess = findNextProcess(currentTime);

            if (nextProcess != null) {
                if (currentProcess == null || nextProcess.getBurstsToExecute() < currentProcess.getBurstsToExecute() - currentProcess.getBurstsExecuted()) {
                    if (currentProcess != null) {
                        currentProcess.setState("listo"); // El proceso actual es desalojado
                    }
                    nextProcess.setState("ejecutando");
                    currentProcess = nextProcess;
                }
            }

            // Simula la ejecución de una unidad de tiempo
            if (currentProcess != null) {
                currentProcess.executeBurst();
                if (currentProcess.getBurstsExecuted() == currentProcess.getBurstsToExecute()) {
                    currentProcess.setState("terminado");
                    currentProcess = null; // Liberar el proceso actual para permitir que el siguiente se ejecute
                }
            }
            currentTime++; // Avanzar el reloj
        }
    }

    private boolean allProcessesCompleted() {
        return processList.stream().allMatch(p -> p.getState().equals("terminado"));
    }

    private ProcessBlock findNextProcess(int currentTime) {
        return processList.stream()
            .filter(p -> p.getArrivalTime() <= currentTime && !p.getState().equals("terminado"))
            .min(Comparator.comparingInt(p -> p.getBurstsToExecute() - p.getBurstsExecuted()))
            .orElse(null);
    }
}

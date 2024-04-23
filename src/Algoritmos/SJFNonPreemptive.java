package Algoritmos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJFNonPreemptive extends Algorithm {

    // Constructor que utiliza el constructor de la clase base
    public SJFNonPreemptive(List<ProcessBlock> processList) {
        super(processList);
    }

    // Implementación del método execute() para SJF no preemptivo
    @Override
    public void execute() {
        // Ordenar los procesos por tiempo de llegada para manejar procesos que llegan al mismo tiempo
        Collections.sort(processList, new Comparator<ProcessBlock>() {
            public int compare(ProcessBlock p1, ProcessBlock p2) {
                if (p1.getArrivalTime() == p2.getArrivalTime()) {
                    return Integer.compare(p1.getBurstsToExecute(), p2.getBurstsToExecute());
                }
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // Mientras haya procesos no terminados
        while (!allProcessesCompleted()) {
            // Encuentra el proceso listo con el menor número de ráfagas por ejecutar
            ProcessBlock nextProcess = findNextProcess();

            if (nextProcess != null) {
                nextProcess.setState("ejecutando");
                // Ejecutar todas las ráfagas del proceso seleccionado
                while (nextProcess.getBurstsExecuted() < nextProcess.getBurstsToExecute()) {
                    nextProcess.executeBurst();
                }
                nextProcess.setState("terminado");
            }
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

    private ProcessBlock findNextProcess() {
        ProcessBlock shortest = null;
        for (ProcessBlock process : processList) {
            if (!process.getState().equals("terminado") && (shortest == null || process.getBurstsToExecute() < shortest.getBurstsToExecute())) {
                shortest = process;
            }
        }
        return shortest;
    }
}

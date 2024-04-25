package Algoritmos;

import Implementacion.ProcessBlock;
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
        // Ordenar los procesos por tiempo de llegada y por ráfagas en caso de empate
        Collections.sort(processList, Comparator.comparingInt(ProcessBlock::getArrivalTime)
                                                .thenComparingInt(ProcessBlock::getBurstsToExecute));

        int currentTime = 0;

        // Mientras haya procesos no terminados
        while (!allProcessesCompleted()) {
            // Encuentra el proceso listo con el menor número de ráfagas por ejecutar que haya llegado
            ProcessBlock nextProcess = findNextProcess(currentTime);

            if (nextProcess != null) {
                // Actualizar tiempo de inicio si aún no ha comenzado
                if (nextProcess.getStartTime() == -1) {
                    nextProcess.setStartTime(currentTime);
                }

                // Ejecutar todas las ráfagas del proceso seleccionado
                nextProcess.setState("ejecutando");
                while (nextProcess.getBurstsExecuted() < nextProcess.getBurstsToExecute()) {
                    nextProcess.executeBurst();
                    currentTime++; // Incrementa el tiempo actual del sistema
                }
                nextProcess.setEndTime(currentTime); // Establecer el tiempo de finalización
                nextProcess.setState("terminado");
            } else {
                currentTime++; // Incrementar el tiempo si no hay procesos listos
            }
        }
    }

    private boolean allProcessesCompleted() {
        return processList.stream().allMatch(process -> process.getState().equals("terminado"));
    }

    private ProcessBlock findNextProcess(int currentTime) {
        return processList.stream()
            .filter(process -> process.getArrivalTime() <= currentTime && process.getState().equals("nuevo"))
            .min(Comparator.comparingInt(ProcessBlock::getBurstsToExecute))
            .orElse(null);
    }
}

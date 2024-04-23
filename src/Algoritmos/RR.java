package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class RR extends Algorithm {
    private int quantum; // Quantum de tiempo asignado a cada proceso

    // Constructor que utiliza el constructor de la clase base
    public RR(List<ProcessBlock> processList, int quantum) {
        super(processList);
        this.quantum = quantum;
    }

    // Implementación del método execute() para Round Robin
    @Override
    public void execute() {
        Queue<ProcessBlock> readyQueue = new LinkedList<>(processList);

        // Inicializar el tiempo actual del sistema
        int currentTime = 0;

        while (!readyQueue.isEmpty()) {
            ProcessBlock currentProcess = readyQueue.poll();
            if (currentProcess != null) {
                currentProcess.setState("ejecutando");

                // Calcular el tiempo real que el proceso será ejecutado en este quantum
                int timeToExecute = Math.min(quantum, currentProcess.getBurstsToExecute() - currentProcess.getBurstsExecuted());
                // Simular la ejecución del proceso
                for (int i = 0; i < timeToExecute; i++) {
                    currentProcess.executeBurst();
                    currentTime++;
                }

                // Verificar si el proceso ha terminado
                if (currentProcess.getBurstsExecuted() == currentProcess.getBurstsToExecute()) {
                    currentProcess.setState("terminado");
                } else {
                    // Si el proceso no ha terminado, se agrega de nuevo al final de la cola
                    currentProcess.setState("listo");
                    readyQueue.offer(currentProcess);
                }
            }
        }
    }
}

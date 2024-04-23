package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FCFS extends Algorithm {

    // Constructor que utiliza el constructor de la clase base
    public FCFS(List<ProcessBlock> processList) {
        super(processList);
    }

    // Implementación del método execute() para FCFS
    @Override
    public void execute() {
        // Ordenar los procesos por tiempo de llegada
        Collections.sort(processList, new Comparator<ProcessBlock>() {
            public int compare(ProcessBlock p1, ProcessBlock p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // Procesar cada proceso en el orden que llegaron
        for (ProcessBlock process : processList) {
            process.setState("ejecutando");
            // Simular la ejecución de cada ráfaga de ejecución
            while (process.getBurstsExecuted() < process.getBurstsToExecute()) {
                process.executeBurst();
            }
            process.setState("terminado");
        }
    }
}

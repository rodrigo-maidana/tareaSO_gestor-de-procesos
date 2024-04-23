package Algoritmos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Priority extends Algorithm {

    // Constructor que utiliza el constructor de la clase base
    public Priority(List<ProcessBlock> processList) {
        super(processList);
    }

    // Implementación del método execute() para el algoritmo de Prioridad
    @Override
    public void execute() {
        // Ordenar los procesos por prioridad (menor número, mayor prioridad) y por tiempo de llegada como desempate
        Collections.sort(processList, new Comparator<ProcessBlock>() {
            @Override
            public int compare(ProcessBlock p1, ProcessBlock p2) {
                if (p1.getPriority() == p2.getPriority()) {
                    return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                }
                return Integer.compare(p1.getPriority(), p2.getPriority());
            }
        });

        // Procesar cada proceso según el orden de prioridad
        for (ProcessBlock process : processList) {
            process.setState("ejecutando");
            // Ejecutar todas las ráfagas del proceso seleccionado
            while (process.getBurstsExecuted() < process.getBurstsToExecute()) {
                process.executeBurst();
            }
            process.setState("terminado");
        }
    }
}

package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;

public abstract class Algorithm {
    protected JTable table; // Tabla de visualización de la planificación
    protected ArrayList<ProcessBlock> processList; // Lista de procesos a planificar
    protected ArrayList<ProcessBlock> localProcessList; // Lista de Procesos en cola de listos
    protected Map<String, Integer> namesMap = new HashMap<>();

    // Constructor
    public Algorithm(JTable table, ArrayList<ProcessBlock> processList) {
        this.processList = processList;
        this.table = table;
        this.namesMap = new HashMap<String, Integer>();
        fillNamesMap();
        this.localProcessList = new ArrayList<>();
    }

    // Método abstracto para ejecutar el algoritmo de planificación
    public abstract void execute();

    // Getter y Setter para la lista de procesos
    public ArrayList<ProcessBlock> getProcessList() {
        return processList;
    }

    public void setProcessList(ArrayList<ProcessBlock> processList) {
        this.processList = processList;
    }

    // llena el mapa de procesos
    public void fillNamesMap() {
        int i = 0;
        for (ProcessBlock process : processList) {
            namesMap.put(process.getName(), i);
            i++;
        }
    }

    public int getRow(String name) {
        Integer row = namesMap.get(name);
        return row;
    }

    public boolean existenRafagasPorEjecutar() {
        return processList.stream().anyMatch(p -> p.getBurstsToExecute() > 0);
    }

    public void ordenarProcesosPorRafagaRestante() {
        localProcessList.sort(Comparator.comparingInt(ProcessBlock::getBurstsToExecute));
    }

    public void actualizarListaProcesos(int tiempoActual) {
        for (ProcessBlock proceso : processList) {
            if (proceso.getArrivalTime() <= tiempoActual && !localProcessList.contains(proceso)
                    && proceso.getBurstsToExecute() > 0) {
                localProcessList.add(proceso);
            }
        }
    }

    public void ordenarProcesosPorPrioridad() {
        Collections.sort(localProcessList, new Comparator<ProcessBlock>() {
            @Override
            public int compare(ProcessBlock p1, ProcessBlock p2) {
                return p1.getPriority() - p2.getPriority(); // Ordenar ascendentemente por prioridad
            }
        });
    }

}

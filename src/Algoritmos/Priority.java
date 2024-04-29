package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Priority extends Algorithm {

    public Priority(JTable table, ArrayList<ProcessBlock> processList) {
        super(table, processList);
    }

    @Override
    public void execute() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int currentTime = 0; // Momento de llegada de procesos

        while (existenRafagasPorEjecutar()) {
            actualizarListaProcesos(currentTime);
            if (!localProcessList.isEmpty()) {
                ordenarProcesosPorPrioridad();
                ProcessBlock currentProcess = localProcessList.get(0);
                int processRow = getRow(currentProcess.getName());

                // Ejecutar el proceso actual por una unidad de tiempo
                modelo.setValueAt("X", processRow, currentTime + 1);
                currentProcess.executeBurst();

                // Remover el proceso si ha terminado
                if (currentProcess.getBurstsToExecute() == 0) {
                    localProcessList.remove(currentProcess);
                }
            }

            currentTime++;
        }
    }

}

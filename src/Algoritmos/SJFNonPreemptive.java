package Algoritmos;

import Implementacion.ProcessBlock;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SJFNonPreemptive extends Algorithm {

    public SJFNonPreemptive(JTable table, ArrayList<ProcessBlock> processList) {
        super(table, processList);
    }

    @Override
    public void execute() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int currentTime = 0; // Momento de llegada de procesos

        while (existenRafagasPorEjecutar()) {
            actualizarListaProcesos(currentTime);
            if (!localProcessList.isEmpty()) {
                ordenarProcesosPorRafagaRestante();
                ProcessBlock currentProcess = localProcessList.get(0);
                int processRow = getRow(currentProcess.getName());

                // Ejecutar el proceso actual hasta que termine
                while (currentProcess.getBurstsToExecute() > 0) {
                    modelo.setValueAt("X", processRow, currentTime + 1);
                    currentProcess.executeBurst();
                    currentTime++;
                }

                // Remover el proceso cuando termine
                localProcessList.remove(currentProcess);
            }
        }
    }
}

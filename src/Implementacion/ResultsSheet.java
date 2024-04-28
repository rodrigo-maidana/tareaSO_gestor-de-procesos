package Implementacion;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ResultsSheet extends javax.swing.JFrame {
        private ArrayList<ProcessBlock> processList;
        private String nombre;

        /**
         * Creates new form FrameGantt
         */
        public ResultsSheet(ArrayList<ProcessBlock> processList, String nombre) {
                this.processList = processList;
                this.nombre = nombre;
                initComponents();
                // calcula las cantidad de tiempo
                int totalRafagas = 0;
                for (ProcessBlock proceso : processList) {
                        totalRafagas += proceso.getBurstsToExecute();
                }
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.addColumn("Procesos");
                for (int i = 0; i < totalRafagas; i++) {
                        modelo.addColumn(i);
                }
                for (ProcessBlock proceso : processList) {
                        modelo.addRow(new Object[] { proceso.getName() });
                }
                jLabel1.setText("Algoritmo " + nombre);
        }

        private void initComponents() {

                jLabel1 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                waitAverageJLabel = new javax.swing.JLabel();
                executionAverageJLabel = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                jLabel1.setText("Nombre del algorimto");

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                jScrollPane1.setViewportView(jTable1);

                waitAverageJLabel.setText("jLabel2");

                executionAverageJLabel.setText("jLabel2");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(26, Short.MAX_VALUE)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                789,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(16, 16, 16))
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(231, 231, 231)
                                                                                                .addComponent(jLabel1))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(76, 76, 76)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(waitAverageJLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                243,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(executionAverageJLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                47,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                193,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(29, 29, 29)
                                                                .addComponent(waitAverageJLabel)
                                                                .addGap(34, 34, 34)
                                                                .addComponent(executionAverageJLabel)
                                                                .addContainerGap(88, Short.MAX_VALUE)));

                pack();
        }// </editor-fold>

        public ArrayList<ProcessBlock> getProcessList() {
                return processList;
        }

        public String getNombre() {
                return nombre;
        }

        public javax.swing.JTable getTabla() {
                return jTable1;
        }

        public javax.swing.JLabel getWaitAverageLabel() {
                return waitAverageJLabel;
        }

        public javax.swing.JLabel getExecutionAverageLabel() {
                return executionAverageJLabel;
        }

        // Variables declaration - do not modify
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel executionAverageJLabel;
        private javax.swing.JLabel waitAverageJLabel;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable jTable1;
        // End of variables declaration
}
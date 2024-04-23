package Implementacion;

import Algoritmos.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<ProcessBlock> leerArchivo(String rutaArchivo) throws IOException {
        List<ProcessBlock> processList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
        String line = br.readLine(); // Leer y descartar la línea de encabezado

        while ((line = br.readLine()) != null) {
            String[] values = line.split(";");
            if (values.length == 4) {
                String name = values[0];
                int arrivalTime = Integer.parseInt(values[1]);
                int bursts = Integer.parseInt(values[2]);
                int priority = Integer.parseInt(values[3]);
                ProcessBlock process = new ProcessBlock(name, arrivalTime, bursts, priority);
                processList.add(process);
            }
        }
        br.close();
        return processList;
    }
}

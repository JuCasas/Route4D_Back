package com.company.readers;

import com.company.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {
    public static List<Vehicle> leerVehiculos() {
        return null;
    }

    public static List<Pedido> obtenerListaPedidos() {
        try {
            File archivo = new File("src/com/company/resources/ventas202109.txt");
            Scanner myReader = new Scanner(archivo);
            List<Pedido> pedidosList = new ArrayList<>();
            String strDate = obtenerFechaNombrePedido(archivo.getName());
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                Pedido pedido = getPedidoFromLine(line, strDate);
                pedidosList.add(pedido);
            }
            myReader.close();
            return pedidosList;
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrio un error");
            e.printStackTrace();
        }
        return null;
    }

    private static Pedido getPedidoFromLine(String line, String strDate) {
        Pedido pedido = new Pedido();

        int dia = getIntFromLine(line, ":");
        line = line.substring(line.indexOf(':') + 1);
        int hh = getIntFromLine(line, ":");
        line = line.substring(line.indexOf(':') + 1);
        int mm = getIntFromLine(line, ",");
        line = line.substring(line.indexOf(',') + 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d H:m:s");
        String cadenaFecha = strDate + "-" + dia + " " + hh + ":" + mm + ":0";
        LocalDateTime fechaPedido = LocalDateTime.parse(cadenaFecha, formatter);
        pedido.setFechaPedido(fechaPedido);

        // set nodo
        int x = getIntFromLine(line, ",");
        line = line.substring(line.indexOf(',') + 1);
        int y = getIntFromLine(line, ",");
        line = line.substring(line.indexOf(',') + 1);
        pedido.setIdDestino(x + 71 * y + 1);

        // set cant Pedido m3
        int cant = getIntFromLine(line, ",");
        line = line.substring(line.indexOf(',') + 1);
        pedido.setCantidad(cant);

        // set plazo maximo de entrega
        int hLimite = Integer.parseInt(line);
        pedido.setPlazoEntrega(fechaPedido.plusHours(hLimite));
        pedido.setFechaEntrega(null);
        pedido.setFechaPedido(LocalDateTime.now());
        return pedido;
    }

    private static String obtenerFechaNombrePedido(String name) {

        String strDate = name.substring(6, 10) + "-" + name.substring(10, 12);
        return strDate;
    }

    private static Integer convertLocalDateToMinutes(LocalDateTime ldt) {
        LocalDateTime d1 = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0);
        return (int) ChronoUnit.MINUTES.between(d1, ldt);
    }

    public static List<Bloqueos> obtenerCallesBloqueadas() {
        List<Bloqueos> listaBloqueos = new ArrayList<>();
        try {
            File archivo = new File("src/resources/bloqueos202109.txt");
            Scanner myReader = new Scanner(archivo);
            List<IntervaloB> listaIntervalos = new ArrayList<>();

            String strDate = obtenerFechaNombre(archivo.getName());
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                IntervaloB intervalo = obtenerIntervalo(data, strDate);
                listaIntervalos.add(intervalo);
                data = data.substring(data.indexOf(',') + 1);
                List<IntervaloBN> listaIntervalosN = new ArrayList<>();
                while (data.length() != 0) {

                    IntervaloBN intervaloNodo = new IntervaloBN();
                    int indexChar = data.indexOf(',');
                    int x, y;
                    x = Integer.parseInt(data.substring(0, indexChar));
                    data = data.substring(indexChar + 1);
                    indexChar = data.indexOf(',');
                    if (indexChar == -1) {
                        y = Integer.parseInt(data);
                        data = "";
                    } else {
                        y = Integer.parseInt(data.substring(0, indexChar));
                        data = data.substring(indexChar + 1);
                    }
                    intervaloNodo.setNodo_id(x + 71 * y + 1);
                    intervaloNodo.setIntervalo_id(intervalo.getId());
                    listaIntervalosN.add(intervaloNodo);
                    Bloqueos cb = new Bloqueos(intervalo.getId(), convertLocalDateToMinutes(intervalo.getInicio()),
                            convertLocalDateToMinutes(intervalo.getFin()));
                    listaBloqueos.add(cb);

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrio un error");
            e.printStackTrace();
        }
        return listaBloqueos;
    }

    private static Integer getIntFromLine(String line, String c) {
        int indexChar = line.indexOf(c);
        return Integer.parseInt(line.substring(0, indexChar));
    }

    private static IntervaloB obtenerIntervalo(String line, String strDate) {
        IntervaloB intervalo = new IntervaloB();

        int dia = getIntFromLine(line, ":");
        line = line.substring(line.indexOf(':') + 1);
        int hh = getIntFromLine(line, ":");
        line = line.substring(line.indexOf(':') + 1);
        int mm = getIntFromLine(line, "-");
        line = line.substring(line.indexOf('-') + 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d H:m:s");
        LocalDateTime inicio = LocalDateTime.parse(strDate + "-" + dia + " " + hh + ":" + mm + ":0", formatter);
        intervalo.setInicio(inicio);

        dia = getIntFromLine(line, ":");
        line = line.substring(line.indexOf(':') + 1);
        hh = getIntFromLine(line, ":");
        line = line.substring(line.indexOf(':') + 1);
        mm = getIntFromLine(line, ",");
        LocalDateTime fin = LocalDateTime.parse(strDate + "-" + dia + " " + hh + ":" + mm + ":0", formatter);
        intervalo.setFin(fin);

        return intervalo;
    }

    private static String obtenerFechaNombre(String nombre) {
        return nombre.substring(8, 11) + "-" + nombre.substring(11, 13);
    }

    // lectura de vehiculos
    public List<Vehicle> leerVehiculos(List<Vehicle> lista) {
        return lista;
    }
}

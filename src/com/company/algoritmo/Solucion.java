package com.company.algoritmo;

import com.company.algoritmo.dijkstra.Dijkstra;
import com.company.algoritmo.kmeans.Kmeans;
import com.company.model.*;
import com.company.readers.Reader;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Solucion {

    public List<Pedido> listaPedidos;
    public List<Vehicle> listaVehiculos;
    public List<Cluster> listaClusters;
    public List<Bloqueos> listaBloqueos;
    public List<Ruta> listaRutas;
    public Dijkstra dijkstra;
    public Kmeans kmeans;
    public int cantCluster1=0;
    public int cantCluster2=0;
    public int cantCluster3=0;
    public int cantCluster4=0;
    public int cantVehiculos1=0;
    public int cantVehiculos2=0;
    public int cantVehiculos3=0;
    public int cantVehiculos4=0;
    public int cantidadTotal=0;

    public String inicializar(){
        listaVehiculos = Reader.leerVehiculos(); //falta implementar
        listaPedidos = Reader.obtenerListaPedidos(); //falta implementar
        if(listaVehiculos.size() == 0) return "No hay vehículos disponibles para las rutas";
        if(listaPedidos.size() == 0) return "No hay pedidos en cola";
        obtenerCantidadClusters();
        kmeans = new Kmeans(cantVehiculos1,cantVehiculos2,cantVehiculos3,cantVehiculos4);
        listaBloqueos = Reader.obtenerCallesBloqueadas();
        obtenerListaAdyacente();
        return "correcto";
    }

    public void obtenerCantidadClusters(){
        //Se podría hacer en el metodo del reader
        for(Vehicle vehiculo:listaVehiculos){
            if(vehiculo.getTipo()==1) cantVehiculos1++;
            if(vehiculo.getTipo()==2) cantVehiculos2++;
            if(vehiculo.getTipo()==3) cantVehiculos3++;
            if(vehiculo.getTipo()==4) cantVehiculos4++;
        }

        System.out.println(listaVehiculos.size());
        int k = (int) (0.9 * (cantidadTotal /(cantVehiculos1*25+cantVehiculos2*15+cantVehiculos3*10+cantVehiculos4*5)));
        if(k > 10) k = 10;
        if(k < 3) k = 3;
        cantCluster1 = cantVehiculos1 * k;
        cantCluster2 = cantVehiculos2 * k;
        cantCluster3 = cantVehiculos3 * k;
        cantCluster4 = cantVehiculos4 * k;

    }

    public void obtenerListaAdyacente(){
        int origen, destino;
        InputStream grafo = getClass().getClassLoader().getResourceAsStream("grafo.txt");
        Scanner sc = new Scanner( grafo );
        dijkstra = new Dijkstra(Inicializacion.V, listaBloqueos);
        for( int i = 0 ; i < Inicializacion.E ; ++i ){
            origen = sc.nextInt() +1;
            destino = sc.nextInt() +1;
            dijkstra.addEdge(origen, destino);
        }
    }
    public List<Vehicle> inicializarVehiculos() {
        List<Vehicle> lista = new ArrayList<>();
        for(int i = 0; i < cantCluster1;i++){
            Vehicle vehiculo = new Vehicle();
            vehiculo.setTipo(1);
            vehiculo.setCapacidad(25);
            vehiculo.setEstado(1);
            vehiculo.setPeso(2.5);
            vehiculo.setPesoCarga(12.5);
            vehiculo.setVelocidad(50);
            lista.add(vehiculo);
        }
        for(int i=0; i<cantCluster2;i++){
            Vehicle vehiculo = new Vehicle();
            vehiculo.setTipo(2);
            vehiculo.setCapacidad(15);
            vehiculo.setEstado(1);
            vehiculo.setPeso(2.0);
            vehiculo.setPesoCarga(7.5);
            vehiculo.setVelocidad(50);
            lista.add(vehiculo);
        }
        for(int i=0; i<cantCluster3;i++){
            Vehicle vehiculo = new Vehicle();
            vehiculo.setTipo(3);
            vehiculo.setCapacidad(10);
            vehiculo.setEstado(1);
            vehiculo.setPeso(1.5);
            vehiculo.setPesoCarga(5.0);
            vehiculo.setVelocidad(50);
            lista.add(vehiculo);
        }
        for(int i=0; i<cantCluster4;i++){
            Vehicle vehiculo = new Vehicle();
            vehiculo.setTipo(4);
            vehiculo.setCapacidad(5);
            vehiculo.setEstado(1);
            vehiculo.setPeso(1.0);
            vehiculo.setPesoCarga(2.5);
            vehiculo.setVelocidad(50);
            lista.add(vehiculo);
        }
        return lista;
    }
    public List<Cluster> inicializarClusters(List<Vehicle>  vehiculos){
        List<Cluster> lista = new ArrayList<Cluster>();
        for(Vehicle vehiculo: vehiculos){
            Cluster cluster =  new Cluster();
            //override compare method
            cluster.setPedidos(new PriorityQueue<Pedido>(500,
                    (i, j) -> {
                        if(Math.abs(i.getCordX() - Inicializacion.almacenCX) + Math.abs(i.getCordY() - Inicializacion.almacenCY) > Math.abs(j.getCordX() - Inicializacion.almacenCX) + Math.abs(j.getCordY() - Inicializacion.almacenCY)) return 1;
                        else if (Math.abs(i.getCordX() - Inicializacion.almacenCX) + Math.abs(i.getCordY() - Inicializacion.almacenCY) < Math.abs(j.getCordX() - Inicializacion.almacenCX) + Math.abs(j.getCordY() - Inicializacion.almacenCY)) return -1;
                        else if (i.getCantidad() > j.getCantidad()) return 1;
                        else if (i.getCantidad() < j.getCantidad()) return -1;
                        else return 1;
                    }
            ));
            cluster.setC1(0);
            cluster.setC2(0);
            cluster.setVehiculo(vehiculo);
            lista.add(cluster);
        }
        return lista;
    }

    public void obtenerPedidosClusterizados(){
        int cantClusters = cantCluster1 + cantCluster2+cantCluster3+cantCluster4;
        List<Vehicle> vehiculos = inicializarVehiculos();

        //inicializar clusters
        List<Cluster> clustersList = inicializarClusters(vehiculos);
        List<Cluster> clustersAns = inicializarClusters(vehiculos);

        //Clusterizacion
        listaClusters = kmeans.kmeans(listaPedidos,clustersList,cantClusters,clustersAns);
        Double SSE = kmeans.getOptimo(listaPedidos,clustersAns,cantClusters);
        System.out.println("------------------------------------------------------");
        System.out.println("Rutas calculadas con un SSE=" + SSE);
        System.out.println("------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    public String generarRutas(){
        LocalDateTime tiempo1,tiempo2;
        tiempo1 = LocalDateTime.now();
        obtenerPedidosClusterizados();
        obtenerRutas();
        asignarRutas();
        tiempo2 = LocalDateTime.now();
        System.out.println("Tiempo de ejecución del algortimo: " + (tiempo2.getSecond()-tiempo1.getSecond()) + " segundos");
        return "Rutas generadas exitosamente";
    }

    public void obtenerRutas(){

        //calculamos el tiempo en minutos en que iniciamos a correr el algoritmo
        LocalDateTime tiempo = LocalDateTime.now();
        LocalDateTime d1 = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0);
        int tiempoMinutosInicio = (int) ChronoUnit.MINUTES.between(d1, tiempo);

        //para calcular el tiempo máximo de entrega
        int maximoTiempo = -1;

        //inicializamos la lista de rutas
        listaRutas = new ArrayList< Ruta >();

        for(Cluster cluster:listaClusters){
            //asignamos el tiempo en minutos en que iniciamos a correr el algoritmo
            int tiempoMinutos = tiempoMinutosInicio;
            if(cluster.getPrimerPedido() == null) continue;
            //imprimos en forma de reporte la información relacionada a la ruta
            System.out.println("------------------------------------------------------");
            System.out.println("Cluster: " + cluster.getVehiculo().getTipo());
            System.out.println("Capacidad: " + cluster.getCapacidad() + "/" + cluster.getVehiculo().getCapacidad());
            // System.out.println("Tiempo inicial en minutos: " + tiempoMinutos);
            System.out.println("------------------------------------------------------");

            //incializamos la ruta
            Ruta ruta = new Ruta(cluster.getVehiculo(), cluster.getCapacidad());

            //seteamos el origen a nuestro almacén
            int origen = Inicializacion.almacen;

            //nos servirá para hallar un ruta si estamos en un nodo bloqueado
            int ultimoViable = Inicializacion.almacen;

            //para el firstPedido
            if(cluster.getPrimerPedido() != null){
                Pedido pedido = cluster.getPrimerPedido();
                ruta.agregarPedido(pedido);

                System.out.println("x:  " + pedido.getCordX() + "   y: " + pedido.getCordY() + "   z: " + pedido.getTiempoLimite() + "   cant: " + pedido.getCantidad() + "   idNodo: " + pedido.getNodoId());

                boolean estaBloqueada = estaBloqueada(tiempoMinutos, origen);

                if(estaBloqueada){
                    System.out.println("Bloqueado!");
                    origen = ultimoViable;
                }

                dijkstra.dijkstra ( origen, tiempoMinutos, (int) Math.round(cluster.getVehiculo().getVelocidad()));
                System.out.printf("Ruta: ");

                int tamanoIni = ruta.getCamino().size();

                dijkstra.printShortestPath(pedido.getNodoId(), ruta, 1);

                int tamanoFin = ruta.recorrido.size();

                if(tamanoFin - tamanoIni >= 2) {
                    ultimoViable = ruta.recorrido.get(ruta.recorrido.size() - 2);
                }

                int tiempoEnLlegar = (tamanoFin - tamanoIni-1) * 60 / ((int) Math.round(cluster.vehiculo.getVelocidad()));
                System.out.println("Nodos recorridos: " + (tamanoFin - tamanoIni - 1) + "   Tiempo llegada en minutos: " + tiempoEnLlegar + " minutos");

                tiempoMinutos += tiempoEnLlegar;

                origen = pedido.getNodoId();

                if(cluster.pedidos.size() != 0) System.out.println();
            }

            //iteramos mientras sacamos pedidos de la cola de prioridad del cluster
            //ordenados por distancia manhattan al almacén
            while(!cluster.pedidos.isEmpty()){

                //extraemos un pedido del cluster
                APedido pedido = cluster.pedidos.poll();
                ruta.addPedido(pedido);
                //imprimir información del pedido
                System.out.println("x:  " + pedido.x + "   y: " + pedido.y + "   z: " + pedido.minFaltantes + "   cant: " + pedido.cantidad + "   idNodo: " + pedido.getNodoId());

                //verificamos si nos encontramos en un nodo bloqueado
                //esto puede ocurrir ya que hemos entregado un pedido en un nodo bloqueado
                //o si el almancén es un nodo bloqueado
                boolean estaBloqueada = estaBloqueada(tiempoMinutos, origen);

                if(estaBloqueada){
                    System.out.println("Bloqueado!");
                    origen = ultimoViable;
                    ruta.addNodo(origen);
                }

                //corremos el algoritmo de dijkstra
                dijkstraAlgorithm.dijkstra( origen, tiempoMinutos, (int) Math.round(cluster.vehiculo.getVelocidad()) );
                System.out.printf("Ruta: ");

                //tamano antes de la nueva parte de la ruta
                int tamanoIni = ruta.recorrido.size();

                //obtenemos la ruta en un array
                dijkstraAlgorithm.printShortestPath(pedido.getNodoId(), ruta, 1);

                //tamano luego de la nueva parte de la ruta
                int tamanoFin = ruta.recorrido.size();

                // para obtener el último nodo que no está bloqueado si es que acabamos de entregar un pedido en un nodo bloqueado
                if(tamanoFin - tamanoIni >= 2) {
                    ultimoViable = ruta.recorrido.get(ruta.recorrido.size() - 2);
                }

                //calculamos el tiempo que tomó en llegar
                int tiempoEnLlegar = (tamanoFin - tamanoIni-1) * 60 / ((int) Math.round(cluster.vehiculo.getVelocidad()));
                System.out.println("Nodos recorridos: " + (tamanoFin - tamanoIni - 1) + "   Tiempo llegada en minutos: " + tiempoEnLlegar + " minutos");


                // calculamos el nuevo tiempo en el que nos encontramos
                tiempoMinutos += tiempoEnLlegar;

                //cambiamos el origen
                origen = pedido.getNodoId();

                //detalle estético, la última línea no imprime una nueva en el reporte
                if(cluster.pedidos.size() != 0) System.out.println();
            }

            //tiempo que tomó realizar la entrega
            int diferenciaTiempo = tiempoMinutos - tiempoMinutosInicio;

            if(diferenciaTiempo > maximoTiempo){
                maximoTiempo = diferenciaTiempo;
            }
            System.out.println("------------------------------------------------------");
            System.out.println("Tiempo de entrega: " + diferenciaTiempo + " minutos");
            System.out.println("------------------------------------------------------");

            if(cluster.firstPedido != null){
                System.out.println("Camino de retorno al almacén:  ");

                origen = ruta.recorrido.get(ruta.recorrido.size() - 1);
                boolean estaBloqueada = estaBloqueada(tiempoMinutos, origen);

                if(estaBloqueada){
                    System.out.println("Bloqueado!");
                    origen = ultimoViable;
                    ruta.addNodoRetorno(origen);
                }

                dijkstraAlgorithm.dijkstra( origen, tiempoMinutos, (int) Math.round(cluster.vehiculo.getVelocidad()) );

                int tamanoIni = ruta.retorno.size();

                dijkstraAlgorithm.printShortestPath(Configuraciones.almacen, ruta, 2);
            }

            System.out.println();
            System.out.println();
            listaRutas.add(ruta);
        }
        System.out.println("Máximo tiempo de entrega: " + maximoTiempo + " minutos");
    }

    public void asignarRutas(){
        int contadorAutos = 0;
        log.info("Asignar rutas: ");
        log.info("Size Chof Auto: " + listaChoferesAuto.size());
        log.info("Size Chof Moto: " + listaChoferesMoto.size());
        log.info("cantAutos: " + cantAutos);
        log.info("cantMotos: " + cantMotos);
        for(Usuario chofer: listaChoferesAuto){
            if(contadorAutos == cantAutos) break;
            int minimo = Integer.MAX_VALUE;
            int contador = 0;
            int minCont = -1;
            for(Ruta ruta: listaRutas){
                if(ruta.vehiculo.getTipo_id() == 1 && ruta.chofer == null && minimo > ruta.tiempoMin){
                    minimo = ruta.tiempoMin;
                    minCont = contador;
                }
                contador++;
            }
            if(minCont == -1) break;
            listaRutas.get(minCont).chofer = chofer;
            listaRutas.get(minCont).vehiculo = listaAutos.get(contadorAutos);
            log.info("MinCont: " + minCont);
            log.info("Auto: " + listaRutas.get(minCont).chofer);
            contadorAutos++;
        }

        int contadorMotos = 0;
        for(Usuario chofer: listaChoferesMoto){
            if(contadorMotos == cantMotos) break;
            int minimo = Integer.MAX_VALUE;
            int contador = 0;
            int minCont = -1;
            for(Ruta ruta: listaRutas){
                if(ruta.vehiculo.getTipo_id() == 2 && ruta.chofer == null && minimo > ruta.tiempoMin){
                    minimo = ruta.tiempoMin;
                    minCont = contador;
                }
                contador++;
            }
            if(minCont == -1) break;
            listaRutas.get(minCont).chofer = chofer;
            listaRutas.get(minCont).vehiculo = listaMotos.get(contadorMotos);
            log.info("MinCont: " + minCont);
            log.info("Auto: " + listaRutas.get(minCont).chofer);
            contadorMotos++;
        }

        for(int i=listaRutas.size()-1; i>=0; i--){
            if(listaRutas.get(i).chofer == null) listaRutas.remove(i);
            else {
                Ruta ruta = listaRutas.get(i);
                AlgoRuta algoRuta = new AlgoRuta();
                algoRuta.setInicio(LocalDateTime.now());
                algoRuta.setDistancia(0.0);
                algoRuta.setCosto(0.0);
                algoRuta.setUsuario_id(ruta.chofer.getId());
                algoRuta.setVehiculo_id(ruta.vehiculo.getId());
                algoRuta.setEstado_id(2);
                algoritmoRepository.save(algoRuta);
                usuarioRepository.cambiarEstadoUsuario(algoRuta.getUsuario_id());
                algoritmoRepository.cambiarEstadoVehiculo(algoRuta.getVehiculo_id());
                int orden = 1;
                for(APedido pedido: ruta.pedidos){
                    algoritmoRepository.cambiarEstadoPedido(pedido.id);
                    algoritmoRepository.insertarPedidoRuta(algoRuta.getId(), pedido.id, orden);
                    orden++;
                }
                orden = 1;
                for(int nodo: ruta.recorrido){
                    algoritmoRepository.insertarNodoRuta(algoRuta.getId(),nodo,orden);
                    orden++;
                }
                for(int nodo: ruta.retorno){
                    algoritmoRepository.insertarNodoRuta(algoRuta.getId(),nodo,orden);
                    orden++;
                }
            }
            System.out.println(i);
        }
    }

    private boolean estaBloqueada(int tiempoMinutos, int nodoId){
        for( Bloqueos par : listaBloqueos ){
            if( ( tiempoMinutos >= par.getInicio() ) && ( tiempoMinutos < par.getFin() ) ){
                return par.estaNodo(nodoId);
            }
        }
        return false;
    }


}

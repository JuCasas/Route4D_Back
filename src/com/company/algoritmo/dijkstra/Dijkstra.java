package com.company.algoritmo.dijkstra;

import com.company.model.Bloqueos;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    private final int MAX = 3500;  //maximo numero de vértices
    private final int INF = Integer.MAX_VALUE;  //definimos un valor grande que represente la distancia infinita inicial, basta conque sea superior al maximo valor del peso en alguna de las aristas

    private final List<List< Nodo >> adyacencia = new ArrayList<List<Nodo>>(); //lista de adyacencia
    private final int[] distancia = new int[ MAX ];          //distancia[ u ] distancia de vértice inicial a vértice con ID = u
    private final boolean[] visitado = new boolean[ MAX ];   //para vértices visitados
    private final PriorityQueue< Nodo > cola = new PriorityQueue<Nodo>(); //priority queue propia de Java, usamos el comparador definido para que el de menor valor este en el tope
    private int V;                                      //numero de vertices
    private final int[] previo = new int[ MAX ];              //para la impresion de caminos
    private boolean dijkstraEjecutado;
    private final List<Bloqueos> callesBloqueadas;

    public Dijkstra(int V, List<Bloqueos> bloqueos){
        this.V = V;
        //inicializar
        for( int i = 0 ; i <= V ; ++i )
            adyacencia.add(new ArrayList<Nodo>()) ;
        dijkstraEjecutado = false;
        this.callesBloqueadas = bloqueos;
    }

    //función de inicialización
    private void init(){
        for( int i = 0 ; i <= V ; ++i ){
            distancia[ i ] = INF;  //inicializamos todas las distancias con valor infinito
            visitado[ i ] = false; //inicializamos todos los vértices como no visitados
            previo[ i ] = -1;      //inicializamos el previo del vertice i con -1
        }
    }

    //Paso de relajacion
    private void relajacion( int actual , int adyacente , int peso ){
        //Si la distancia del origen al vertice actual + peso de su arista es menor a la distancia del origen al vertice adyacente
        if( distancia[ actual ] + peso < distancia[ adyacente ] ){
            distancia[ adyacente ] = distancia[ actual ] + peso;  //relajamos el vertice actualizando la distancia
            previo[ adyacente ] = actual;                         //a su vez actualizamos el vertice previo
            cola.add( new Nodo( adyacente , distancia[ adyacente ] ) ); //agregamos adyacente a la cola de prioridad
        }
    }

    public void dijkstra( int inicial, int tiempoMinutos, int velocidad ){
        init(); //inicializamos nuestros arreglos
        cola.add( new Nodo( inicial , 0 ) ); //Insertamos el vértice inicial en la Cola de Prioridad
        distancia[ inicial ] = 0;      //Este paso es importante, inicializamos la distancia del inicial como 0
        int actual , adyacente , peso, tiempoTranscurrido;
        while( !cola.isEmpty() ){                   //Mientras cola no este vacia
            actual = cola.element().primero;            //Obtengo de la cola el nodo con menor peso, en un comienzo será el inicial
            cola.remove();                           //Sacamos el elemento de la cola
            if( visitado[ actual ] ) continue; //Si el vértice actual ya fue visitado entonces sigo sacando elementos de la cola
            visitado[ actual ] = true;         //Marco como visitado el vértice actual
            tiempoTranscurrido = distancia[ actual ] * 60 / ( velocidad );
            tiempoTranscurrido = tiempoMinutos + tiempoTranscurrido;
            boolean estaBloqueada = estaBloqueada(tiempoTranscurrido, actual);
            //   System.out.println("bloqueado: " + estaBloqueada);
            for(int i = 0; i < adyacencia.get( actual ).size() ; ++i ){ //reviso sus adyacentes del vertice actual
                adyacente = adyacencia.get( actual ).get( i ).primero;   //id del vertice adyacente
                if(estaBloqueada) peso = INF;
                else peso = adyacencia.get( actual ).get( i ).segundo;        //peso de la arista que une actual con adyacente ( actual , adyacente )
                if( !visitado[ adyacente ] ){        //si el vertice adyacente no fue visitado
                    relajacion( actual , adyacente , peso ); //realizamos el paso de relajacion
                }
            }
        }
        dijkstraEjecutado = true;
    }

    public void addEdge( int origen , int destino){
        //llenando lista de adyacencia
        adyacencia.get( origen ).add( new Nodo( destino , 1 ) );
    }

    public void printShortestPath(int destino, Ruta ruta, int tipo){
        if( !dijkstraEjecutado ){
            System.out.println("Es necesario ejecutar el algorithmo de Dijkstra antes de poder imprimir el camino mas corto");
            return;
        }
        print( destino, ruta, tipo );
        //   System.out.printf("\n");
    }

    //Impresion del camino mas corto desde el vertice inicial y final ingresados
    private void print(int destino, Ruta ruta, int tipo ){
        if( previo[ destino ] != -1 ){    //si aun poseo un vertice previo
            print( previo[ destino ], ruta, tipo );  //recursivamente sigo explorando
        }
        if( previo[ destino ] != -1 ){
            if(tipo == 1) ruta.addNodo(destino);
            else ruta.addNodoRetorno(destino);
        }
        //   System.out.printf("%d " , destino );        //terminada la recursion imprimo los vertices recorridos
    }

    public int getNumberOfVertices() {
        return V;
    }

    public void setNumberOfVertices(int numeroDeVertices) {
        V = numeroDeVertices;
    }

    private boolean estaBloqueada(int tiempoMinutos, int nodoId){
        for( Bloqueos par : callesBloqueadas){
            if( ( tiempoMinutos >= par.getMinutosInicio() ) && ( tiempoMinutos < par.getMinutosFin() ) ){
                return par.estaNodo(nodoId);
            }
        }
        return false;
    }
}

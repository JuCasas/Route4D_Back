package com.company.algoritmo.dijkstra;

import com.company.model.Bloqueos;
import com.company.model.Ruta;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    private final int MAX = 3500;  //maximo numero de vértices
    private final int INF = Integer.MAX_VALUE;  //

    private final List<List< Nodo >> adyacencia = new ArrayList<List<Nodo>>(); //lista de adyacencia
    private final int[] distancia = new int[ MAX ];          //distancia[ u ] distancia de vértice inicial a vértice con ID = u
    private final boolean[] visitado = new boolean[ MAX ];   //para vértices visitados
    private final PriorityQueue< Nodo > cola = new PriorityQueue<>(); //priority queue propia de Java, usamos el comparador definido para que el de menor valor este en el tope
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

    //inicializacion previa
    private void init(){
        int i;
        for(i=0;i<=V ;i++){
            distancia[i] = INF;
            visitado[i] = false; //No visitado
            previo[i] = -1;
        }
    }

    //edge relaxation   -- fuente de shortest path algorithm
    private void relaxation(int actual , int adyacente , int peso ){
        //Si la distancia del origen al vertice actual + peso de su arista es menor a la distancia del origen al vertice adyacente
        if( distancia[ actual ] + peso < distancia[ adyacente ] ){
            distancia[ adyacente ] = distancia[ actual ] + peso;  //actualizamos peso
            previo[ adyacente ] = actual;                         //el vertice previo
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
                    relaxation( actual , adyacente , peso ); //relaxation
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
        imprimeRuta( destino, ruta, tipo );
    }

    //Impresion del camino mas corto desde el vertice inicial y final ingresados
    private void imprimeRuta(int destino, Ruta ruta, int tipo ){
        if( previo[ destino ] != -1 ){    //si aun poseo un vertice previo
            imprimeRuta(previo[destino],ruta,tipo);  //recursivamente sigo explorando
        }
        if( previo[ destino ] != -1 ){

            if(tipo == 1) ruta.agregarNodo(destino);
            else ruta.agregarNodoR(destino);
        }
        //   System.out.printf("%d " , destino );        //terminada la recursion imprimo los vertices recorridos
    }

    public int getNumeroVerties() {
        return V;
    }

    private boolean estaBloqueada(int tiempoMinutos, int nodoId){
        for( Bloqueos bloqueo : callesBloqueadas){
            if( ( tiempoMinutos >= bloqueo.getInicio()) && ( tiempoMinutos < bloqueo.getFin() ) ){
                return bloqueo.estaNodo(nodoId);
            }
        }
        return false;
    }
}

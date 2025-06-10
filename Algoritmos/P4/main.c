/*
Javier Hernandez Martinez
Paula Carril Gontan
*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <math.h>

#define TAM_MAX 1000

typedef int **matriz;

double microsegundos(){
    struct timeval t;
    if(gettimeofday(&t,NULL) < 0){
        return 0.0;
    }
    return (t.tv_usec + t.tv_sec * 1000000.0);
}

void inicializarSemilla(){
    srand(time(NULL));
}

matriz crearMatriz(int n){
    int i;
    matriz aux;
    
    if((aux=malloc(n*sizeof(int *)))==NULL){
        return NULL;
    }
    for(i=0;i<n;i++){
        if((aux[i]=malloc(n*sizeof(int *)))==NULL){
            return NULL;
        }
    }
    return aux;
}

void iniMatriz(matriz m, int n){
    int i,j;

    for(i=0;i<n;i++){
        for(j=i+1;j<n;j++){
            m[i][j]=rand()%TAM_MAX+1;
        }
    }
    for(i=0;i<n;i++){
        for(j=0;j<i;j++){
            if(i==j){
                m[i][j]=0;
            }
            else{
                m[i][j]=m[j][i];
            }
        }
    }
}

void liberarMatriz(matriz m, int n){
    int i;

    for(i=0;i<n;i++){
        free(m[i]);
    }
    free(m);
}

void dijkstra(matriz grafo, matriz distancias, int tam){
    int n, i, j, k, w, min, v=0;
    int *noVisitados=malloc(tam*sizeof(int));

    for(n=0;n<tam;n++){
        for(i=0;i<tam;i++){
            noVisitados[i]=1;
            distancias[n][i]=grafo[n][i];
        }
        noVisitados[n]=0;
        for(j=0;j<tam-2;j++){ //Repetir tam-2 veces
            //Escojer el nodo de noVisitados mas cerca de n
            min = TAM_MAX+1;//TAM_MAX+1 representa la ausencia de conexion
            for (k = 0; k < tam; k++) {
                if (noVisitados[k] == 1 && distancias[n][k] < min) {
                    min = distancias[n][k];
                    v = k;
                }
            }
            //Quitar ese nodo de noVisitados
            noVisitados[v]=0;
            for(w=0;w<tam;w++){
                if(noVisitados[w]==1 &&
                   distancias[n][w]>distancias[n][v]+grafo[v][w]){
                    distancias[n][w]=distancias[n][v]+grafo[v][w];
                }
            }
        }
    }
    free(noVisitados);
}

void imprimir_matriz_cuadrada(matriz m, int n){
    int i, j;

    for(i=0;i<n;i++){
        for(j=0;j<n;j++){
            if(j==0){
                printf("(%3d",m[i][j]);
            }
            else if(j==n-1){
                printf("%3d)\n",m[i][j]);
            }
            else{
                printf("%3d",m[i][j]);
            }
        }
    }
}

void test1(){
    int i, j;

    int a1[5][5]={{0, 1, 8, 4, 7},{1, 0, 2, 6, 5},{8, 2, 0, 9, 5},
                  {4, 6, 9, 0, 3},{7, 5, 5, 3, 0}};
    matriz m1=crearMatriz(5);
    for(i=0;i<5;i++){for(j=0;j<5;j++){m1[i][j]=a1[i][j];}}

    printf("Caso 1 (5 nodos):\nMatriz de Adyacencia:\n");
    imprimir_matriz_cuadrada(m1,5);
    printf("\nMatriz de distancias minimas:\n");
    matriz distancias1=crearMatriz(5);
    dijkstra(m1,distancias1,5);
    imprimir_matriz_cuadrada(distancias1,5);
    liberarMatriz(m1,5);
    liberarMatriz(distancias1,5);

    int a2[4][4]={{0, 1, 4, 7},{1, 0, 2, 8},{4, 2, 0, 3},{7, 8, 3, 0}};
    matriz m2=crearMatriz(4);
    for(i=0;i<4;i++){for(j=0;j<4;j++){m2[i][j]=a2[i][j];}}

    printf("\nCaso 2 (4 nodos):\nMatriz de Adyacencia:\n");
    imprimir_matriz_cuadrada(m2,4);
    printf("\nMatriz de distancias minimas:\n");
    matriz distancias2=crearMatriz(4);
    dijkstra(m2,distancias2,4);
    imprimir_matriz_cuadrada(distancias2,4);
    liberarMatriz(m2,4);
    liberarMatriz(distancias2,4);
}

void tiempos(int n){
    double tiempo, t1, t2, tiempoAux, rAj=0,rSob=0,rSub=0;
    int i;
    matriz m=crearMatriz(n);
    matriz d=crearMatriz(n);
    
    iniMatriz(m,n);
    t1=microsegundos();
    dijkstra(m,d,n);
    t2=microsegundos();
    tiempo=t2-t1;

    if(tiempo<500){
        t1=microsegundos();
        for(i=0;i<1000;i++){
            iniMatriz(m,n);
            dijkstra(m,d,n);
        }
        t2=microsegundos();
        tiempo=t2-t1;

        t1=microsegundos();
        for(i=0;i<1000;i++){
            iniMatriz(m,n);
        }
        t2=microsegundos();
        tiempoAux=t2-t1;

        tiempo=(tiempo-tiempoAux)/1000;

        printf("(*)");
    }
    else{
        printf("   ");
    }

    rSub=tiempo/pow(n,2.5);
    rAj=tiempo/pow(n,2.747);
    rSob=tiempo/pow(n,3.2);
    
    printf("%15d%15.3lf%15.6lf%15.6lf%15.6lf\n",n,tiempo,rSub,rAj,rSob);

    liberarMatriz(m,n);
    liberarMatriz(d,n);
}

int main(){
    int i, j;
    inicializarSemilla();

    test1();
    puts("");

    for(j=0;j<5;j++){
        printf("Dijkstra:\n%18s%15s","n","t(n)");
        printf("%15s%15s%15s\n","t(n)/n^2.5","t(n)/n^2.747","t(n)/n^3.2");
        for(i=16;i<=TAM_MAX;i*=2){
            tiempos(i);
        }
    }
    
    return 0;
}
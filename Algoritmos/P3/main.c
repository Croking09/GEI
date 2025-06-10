/*
Javier Hernandez Martinez
Paula Carril Gontan
*/
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <sys/time.h>
#include <math.h>
#include <stdbool.h>

#define TAM 256000

struct monticulo{
    int ultimo;
    int vector[TAM];
};

typedef struct monticulo *pmonticulo;

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

void ascendente(int v[], int n){
    int i;

    for(i=0;i<n;i++){
        v[i]=i+1;
    }
}

void descendente(int v[], int n){
    int i;

    for(i=0;i<n;i++){
        v[i]=n-i;
    }
}

void aleatorio(int v[], int n) {
    int i, m;

    m=2*n+1;

    for(i=0;i<n;i++){
        v[i]=(rand()%m)-n;
    }
}

bool comprobarOrdenado(int *v, int n){
    int i;

    for(i=0;i<n-1;i++){
        if(v[i]>v[i+1]){
            return false;
        }
    }
    return true;
}

void inicializar(pmonticulo m){
    m->ultimo=-1;
}

void flotar(pmonticulo m, int i){
    int aux;

    while(i>0 && m->vector[i/2] > m->vector[i]){
        aux=m->vector[i/2];
        m->vector[i/2]=m->vector[i];
        m->vector[i]=aux;
    }
    i/=2;
}

void insertar(pmonticulo m, int x){
    if(m->ultimo==TAM-1){
        exit(0);
    }
    else{
        m->ultimo++;
        m->vector[m->ultimo]=x;
        flotar(m,m->ultimo);
    }
}

void hundir(pmonticulo m, int i){
    int hijoIzq, hijoDer, j, aux;

    do{
        hijoIzq=2*i+1;
        hijoDer=2*i+2;
        j=i;

        if(hijoDer<=m->ultimo && m->vector[hijoDer]<m->vector[i]){
            i=hijoDer;
        }
        if(hijoIzq<=m->ultimo && m->vector[hijoIzq]<m->vector[i]){
            i=hijoIzq;
        }
        aux=m->vector[j];
        m->vector[j]=m->vector[i];
        m->vector[i]=aux;
    }while(j!=i);
}

void crearMonticulo(int v[], int n, pmonticulo m){
    int i;
    
    for(i=0;i<n;i++){
        m->vector[i]=v[i];
    }
    m->ultimo=n-1;

    for(i=(n-1)/2;i>=0;i--){
        hundir(m,i);
    }
}

int quitarMenor(pmonticulo m){
    int x;
    
    if(m->ultimo+1==0){
        return -1;
    }
    else{
        x=m->vector[0];
        m->vector[0]=m->vector[m->ultimo];
        m->ultimo--;
        if(m->ultimo+1>0){
            hundir(m,0);
        }
        return x;
    }
}

void ordenarPorMonticulos(int v[], int n){
    int i;
    pmonticulo m = malloc(sizeof(struct monticulo));
    
    crearMonticulo(v, n, m);
    for(i=0; i<n; i++){
        v[i]=quitarMenor(m);
    }

    free(m);
}

void printHeap_Horizontal(struct monticulo m, int index, int prof) {
    if(m.ultimo+1==0){
        printf("Monticulo vacio\n");
        return;
    }

    if (index > m.ultimo) {
        return;
    }

    prof++;

    //Hijo derecho
    printHeap_Horizontal(m, 2 * index + 2, prof);

    //Espacios para formato
    for (int i = 0; i < prof - 1; i++) {
        printf("    ");
    }

    //Nodo
    printf("%4d\n", m.vector[index]);

    //Hijo izquierdo
    printHeap_Horizontal(m, 2 * index + 1, prof);
}

void test1(){
    pmonticulo heap = malloc(sizeof(struct monticulo));
    int n=9, v[n], i;

    printf("Test de monticulos:\n(Se lee de izquierda a derecha)\n\n");

    aleatorio(v,n);
    printf("Vector inicializado: ");
    for(i=0;i<n;i++){
        if(i==0){
            printf("[%d, ",v[i]);
        }
        else if(i==n-1){
            printf("%4d]\n",v[i]);
        }
        else{
            printf("%4d, ",v[i]);
        }
    }
    crearMonticulo(v,n,heap);
    printf("Vector del monticulo: ");
    for(i=0;i<n;i++){
        if(i==0){
            printf("[%d, ",heap->vector[i]);
        }
        else if(i==n-1){
            printf("%4d]\n",heap->vector[i]);
        }
        else{
            printf("%4d, ",heap->vector[i]);
        }
    }
    printf("\nMonticulo inicial:\n");
    printf("---------------------------\n");
    printHeap_Horizontal(*heap,0,0);
    printf("---------------------------\n");
    for(i=0;i<n;i++){
        printf("Eliminamos el menor (%d)\n",quitarMenor(heap));
        printf("---------------------------\n");
        printHeap_Horizontal(*heap,0,0);
        printf("---------------------------\n");
    }

    free(heap);
}

void test2(void (*inicializacion)(int *v, int n), int n){
    int i;
    int *v=malloc(sizeof(int) * n);
    bool ordenado=false;
    //TODO funcion listar vector
    inicializacion(v,n);
    for(i=0;i<n;i++){
        if(i==0){
            printf("[%d, ",v[i]);
        }
        else if(i==n-1){
            printf("%3d]\n",v[i]);
        }
        else{
            printf("%3d, ",v[i]);
        }
    }
    ordenado=comprobarOrdenado(v,n);
    printf("Ordenado? %d\n",ordenado);

    printf("\nOrdenacion por monticulos\n");
    ordenarPorMonticulos(v,n);
    for(i=0;i<n;i++){
        if(i==0){
            printf("[%d, ",v[i]);
        }
        else if(i==n-1){
            printf("%3d]\n",v[i]);
        }
        else{
            printf("%3d, ",v[i]);
        }
    }
    ordenado=comprobarOrdenado(v,n);
    printf("Ordenado? %d\n",ordenado);

    free(v);
}

void testearOrdenacion(){
    printf("TEST DE ORDENACION:\n");

    printf("Inicializacion ascendente\n");
    test2(ascendente,9);
    printf("\n---------------------------------------------");
    printf("\nInicializacion descendente\n");
    test2(descendente,9);
    printf("\n---------------------------------------------");
    printf("\nInicializacion aleatoria\n");
    test2(aleatorio,9);
}

void tiemposCreacion(int n){
    int *v, i;
    double t1, t2, tiempo, rAj=0, rSob=0, rSub=0, rAj2;

    pmonticulo heap = malloc(sizeof(struct monticulo));

    v=malloc(sizeof(int)*n);

    aleatorio(v,n);
    t1=microsegundos();
    crearMonticulo(v,n,heap);
    t2=microsegundos();
    tiempo=t2-t1;
    if(tiempo<500){
        t1=microsegundos();
        for(i=0;i<1000;i++){
            crearMonticulo(v,n,heap);
        }
        t2=microsegundos();
        tiempo=(t2-t1)/1000;
        printf("(*)");
    }
    else{
        printf("   ");
    }

    rSub=tiempo/pow(n,0.8);
    rAj=tiempo/pow(n,1.105);
    rAj2=tiempo/n;
    rSob=tiempo/pow(n,1.3);

    printf("%15d%15.3lf%22.6lf%19.6lf%19.6lf%22.6lf\n", n, tiempo, rSub, rAj, rAj2, rSob);

    free(heap);
    free(v);
}


void tiemposOrdenacion(void (*inicializacion)(int *v, int n), int n){
    int *v, i;
    double t1, t2, tiempo, tiempoAux, rAj=0, rSob=0, rSub=0;

    v=malloc(sizeof(int)*n);

    inicializacion(v,n);

    t1=microsegundos();
    ordenarPorMonticulos(v,n);
    t2=microsegundos();
    tiempo=t2-t1;
    if(tiempo<500){
        t1=microsegundos();
        for(i=0;i<1000;i++){
            inicializacion(v,n);
            ordenarPorMonticulos(v,n);
        }
        t2=microsegundos();
        tiempo=t2-t1;

        t1=microsegundos();
        for(i=0;i<1000;i++){
            inicializacion(v,n);
        }
        t2=microsegundos();
        tiempoAux=t2-t1;

        tiempo=(tiempo-tiempoAux)/1000;

        printf("(*)");
    }
    else{
        printf("   ");
    }

    rSub=tiempo/n;
    rAj=tiempo/(n*log(n));
    rSob=tiempo/pow(n,1.2);

    printf("%15d%15.3lf%22.6lf%19.6lf%22.6lf\n", n, tiempo, rSub, rAj, rSob);

    free(v);
}


int main(){
    int i, j;

    test1();
    puts("");
    testearOrdenacion();

    printf("\nTIEMPOS:\n");
    for(i=0;i<3;i++){
        printf("Creacion de un monticulo:\n"
        "%18s%15s%22s%19s%19s%22s\n","n","t(n)","t(n)/n^0.8",
        "t(n)/n^1.105","t(n)/n","t(n)/n^1.3");
        for(j=500;j<=TAM;j*=2){
            tiemposCreacion(j);
        }
        puts("");
    }
    printf("Ordenacion por monticulos:\n");
    for(i=0;i<3;i++){
        printf("Inicializacion ascendente:\n"
               "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
               "t(n)/(nlog(n))","t(n)/n^1.2");
        for(j=500;j<=TAM;j*=2){
            tiemposOrdenacion(ascendente,j);
        }
        puts("");
        printf("Inicializacion descendente:\n"
               "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
               "t(n)/(nlog(n))","t(n)/n^1.2");
        for(j=500;j<=TAM;j*=2){
            tiemposOrdenacion(descendente,j);
        }
        puts("");
        printf("Inicializacion aleatoria:\n"
               "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
               "t(n)/(nlog(n))","t(n)/n^1.2");
        for(j=500;j<=TAM;j*=2){
            tiemposOrdenacion(aleatorio,j);
        }
    }

    return 0;
}

int main(){
    int i, j;
    /*
    test1();
    puts("");
    testearOrdenacion();

    printf("\nTIEMPOS:\n");
    for(i=0;i<3;i++){
        printf("Creacion de un monticulo:\n"
        "%18s%15s%22s%19s%19s%22s\n","n","t(n)","t(n)/n^0.8",
        "t(n)/n^1.105","t(n)/n","t(n)/n^1.3");
        for(j=500;j<=TAM;j*=2){
            tiemposCreacion(j);
        }
        puts("");
    }
    printf("Ordenacion por monticulos:\n");
    for(i=0;i<3;i++){
        printf("Inicializacion ascendente:\n"
               "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
               "t(n)/(nlog(n))","t(n)/n^1.2");
        for(j=500;j<=TAM;j*=2){
            tiemposOrdenacion(ascendente,j);
        }
        puts("");
        printf("Inicializacion descendente:\n"
               "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
               "t(n)/(nlog(n))","t(n)/n^1.2");
        for(j=500;j<=TAM;j*=2){
            tiemposOrdenacion(descendente,j);
        }
        puts("");
        printf("Inicializacion aleatoria:\n"
               "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
               "t(n)/(nlog(n))","t(n)/n^1.2");
        for(j=500;j<=TAM;j*=2){
            tiemposOrdenacion(aleatorio,j);
        }
    }
    */

    pmonticulo heap = malloc(sizeof(struct monticulo));
    inicializar(heap);

    insertar(heap, 7);
    insertar(heap, 2);
    insertar(heap, 9);
    insertar(heap, 6);

    printHeap_Horizontal(*heap, 0, 0);
    return 0;
}

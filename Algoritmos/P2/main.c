/*
Javier Hernandez Martinez
Paula Carril Gontan
*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <sys/time.h>
#include <stdbool.h>

double microsegundos(){
    struct timeval t;
    if(gettimeofday(&t,NULL) < 0){
        return 0.0;
    }
    return (t.tv_usec + t.tv_sec * 1000000.0);
}

void inicializar_semilla() {
    srand(time(NULL));
}

void aleatorio(int v[], int n) {
    int i, m;

    m=2*n+1;

    for(i=0;i<n;i++){
        v[i]=(rand()%m)-n;
    }
}

void ascendente(int *v, int n){
    int i;

    for(i=0;i<n;i++){
        v[i]=i+1;
    }
}

void descendente(int *v, int n){
    int i;

    for(i=0;i<n;i++){
        v[i]=n-i;
    }
}

void listar_vector(int *v, int n){
    int i;

    printf("[");
    for(i=0;i<n;i++){
        printf("%3d", v[i]);
    }
    printf("]\n");
}

void ord_ins (int v[], int n) {
    int x, i, j;
    for (i = 1; i < n; i++) {
        x = v[i];
        j = i - 1;
        while (j >= 0 && v[j] > x) {
            v[j + 1] = v[j];
            j = j - 1;
        }
        v[j + 1] = x;
    }
}

void ord_shell (int v [], int n){
    int i,incremento, tmp, j;
    bool seguir;

    incremento=n;

    do{
        incremento/=2;
        for(i=incremento;i<n;i++){
            tmp=v[i];
            j=i;
            seguir=true;
            while(j-incremento>=0 && seguir){
                if(tmp<v[j-incremento]){
                    v[j]=v[j-incremento];
                    j-=incremento;
                }else{
                    seguir=false;
                }
            }
            v[j]=tmp;
        }
    }while(incremento!=1);
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

void test(void (*inicializacion)(int *v, int n), int n){
    int *v=malloc(sizeof(int) * n);
    bool ordenado=false;

    inicializacion(v,n);
    listar_vector(v,n);
    ordenado=comprobarOrdenado(v,n);
    printf("Ordenado? %d\n",ordenado);

    printf("\nOrdenacion por insercion\n");
    ord_ins(v,n);
    listar_vector(v,n);
    ordenado=comprobarOrdenado(v,n);
    printf("Ordenado? %d\n",ordenado);

    printf("\nOrdenacion por shell\n");
    ord_shell(v,n);
    listar_vector(v,n);
    ordenado=comprobarOrdenado(v,n);
    printf("Ordenado? %d\n",ordenado);

    free(v);
}

void tiempos(void (*algoritmo)(int *v, int n), void (*inicializacion)(int *v,
            int n), int n, double cSub, double cAj, double cSob){
    double tiempo, t1, t2, tiempAux, rAj = 0, rSob = 0, rSub = 0;
    int *v = malloc(sizeof(int) * n), i;

    inicializacion(v, n);
    t1 = microsegundos();
    algoritmo(v, n);
    t2 = microsegundos();
    tiempo = t2 - t1;

    if (tiempo < 500) {
        t1=microsegundos();
        for(i=0;i<1000;i++){
            inicializacion(v,n);
            algoritmo(v,n);
        }
        t2=microsegundos();
        tiempAux=t2-t1;
        t1=microsegundos();
        for(i=0;i<1000;i++){
            inicializacion(v,n);
        }
        t2=microsegundos();
        tiempo=t2-t1;

        tiempo=(tiempAux-tiempo)/1000;
        printf("(*)");
    } else {
        printf("   ");
    }

    rSub = tiempo / cSub;
    rAj = tiempo / cAj;
    rSob = tiempo / cSob;

    printf("%15d%15.3lf%22.6lf%19.6lf%22.6lf\n", n, tiempo, rSub, rAj, rSob);
    free(v);
}

void testear(){
    printf("Inicializacion ascendente\n");
    test(ascendente,9);
    printf("\n---------------------------------------------");
    printf("\nInicializacion descendente\n");
    test(descendente,9);
    printf("\n---------------------------------------------");
    printf("\nInicializacion aleatoria\n");
    test(aleatorio,9);
}

void tiemposIns(){
    int j;

    printf("Ordenacion por insercion con inicializacion ascendente:\n"
    "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n^0.9",
    "t(n)/n","t(n)/n^1.1");
    for(j=500;j<=32000;j*=2){
        tiempos(ord_ins,ascendente,j,pow(j,0.9),j,pow(j,1.1));
    }
    puts("");
    printf("Ordenacion por insercion con inicializacion descendente:\n"
    "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n^1.9",
    "t(n)/n^2","t(n)/n^2.1");
    for(j=500;j<=32000;j*=2){
        tiempos(ord_ins,descendente,j,pow(j,1.9),pow(j,2),pow(j,2.1));
    }
    puts("");
    printf("Ordenacion por insercion con inicializacion aleatoria:\n"
    "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n^1.9",
    "t(n)/n^2","t(n)/n^2.1");
    for(j=500;j<=32000;j*=2){
        tiempos(ord_ins,aleatorio,j,pow(j,1.9),pow(j,2),pow(j,2.1));
    }
    puts("");
}

void tiemposShell(){
    int j;

    printf("Ordenacion por shell con inicializacion ascendente:\n"
    "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/(nlog n)^0.9",
    "t(n)/(nlog n)","t(n)/(nlog n)^1.1");
    for(j=500;j<=32000;j*=2){
        tiempos(ord_shell,ascendente,
        j,pow(j*log(j),0.9),j*log(j),pow(j*log(j),1.1));
    }
    puts("");
    printf("Ordenacion por shell con inicializacion descendente:\n"
    "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n^0.9",
    "t(n)/n^1.144","t(n)/n^1.4");
    for(j=500;j<=32000;j*=2){
        tiempos(ord_shell,descendente,
        j,pow(j,0.9),pow(j,1.144),pow(j,1.4));
    }
    puts("");
    printf("Ordenacion por shell con inicializacion aleatoria:\n"
    "%18s%15s%22s%19s%22s\n","n","t(n)","t(n)/n",
    "t(n)/n^1.192)","t(n)/n^1.4");
    for(j=500;j<=32000;j*=2){
        tiempos(ord_shell,aleatorio,j,j,pow(j,1.192),pow(j,1.4));
    }
    puts("");
}

int main() {
    int i;
    inicializar_semilla();

    testear();

    puts("");

    for(i=0;i<5;i++){
        tiemposIns();
        tiemposShell();
    }
    
    return 0;
}

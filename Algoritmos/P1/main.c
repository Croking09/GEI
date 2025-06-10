#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <sys/time.h>
#include <math.h>

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

int sumaSubMax1(int v[], int n){
    int sumaMax, i, estaSuma, j;

    sumaMax=0;
    estaSuma=0;

    for(i = 0; i < n; i++){
        estaSuma = 0;
        for(j = i; j < n; j++){
            estaSuma+=v[j];
            if(estaSuma > sumaMax){
                sumaMax=estaSuma;
            }
        }
    }
    return sumaMax;
}

int sumaSubMax2(int v[], int n){
    int estaSuma, sumaMax, j;

    estaSuma=0;
    sumaMax=0;

    for(j=0;j<n;j++){
        estaSuma+=v[j];
        if(estaSuma>sumaMax){
            sumaMax=estaSuma;
        }
        else if(estaSuma<0){
            estaSuma=0;
        }
    }
    return sumaMax;
}

void listar_vector(int *v, int n){
    int i;
    
    printf("[");
    for(i=0;i<n;i++){
        printf("%3d", v[i]);
    }
    printf("]");
}

double microsegundos(){
    struct timeval t;

    if(gettimeofday(&t,NULL) < 0){
        return 0.0;
    }
    return (t.tv_usec + t.tv_sec * 1000000.0);
}

void test1(){
    printf("%33s%15s\n","sumaSubMax1","sumaSubMax2");
    int secuencia1[5] = {-9,2,-5,-4,6};
    int secuencia2[5] = {4,0,9,2,5};
    int secuencia3[5] = {-2,-1,-9,-7,-1};
    int secuencia4[5] = {9,-2,1,-7,-8};
    int secuencia5[5] = {15,-2,-5,-4,16};
    int secuencia6[5] = {7,-5,6,7,-7};
    
    listar_vector(secuencia1,5);
    int a1 = sumaSubMax1(secuencia1,5);
    int a2 = sumaSubMax2(secuencia1,5);
    printf("%15d%15d\n",a1,a2);
    listar_vector(secuencia2,5);
    int b1 = sumaSubMax1(secuencia2,5);
    int b2 = sumaSubMax2(secuencia2,5);
    printf("%15d%15d\n",b1,b2);
    listar_vector(secuencia3,5);
    int c1 = sumaSubMax1(secuencia3,5);
    int c2 = sumaSubMax2(secuencia3,5);
    printf("%15d%15d\n",c1,c2);
    listar_vector(secuencia4,5);
    int d1 = sumaSubMax1(secuencia4,5);
    int d2 = sumaSubMax2(secuencia4,5);
    printf("%15d%15d\n",d1,d2);
    listar_vector(secuencia5,5);
    int e1 = sumaSubMax1(secuencia5,5);
    int e2 = sumaSubMax2(secuencia5,5);
    printf("%15d%15d\n",e1,e2);
    listar_vector(secuencia6,5);
    int f1 = sumaSubMax1(secuencia6,5);
    int f2 = sumaSubMax2(secuencia6,5);
    printf("%15d%15d\n",f1,f2);
}

void test2(){
    int a, b, v[9], i;

    printf("%33s%15s%15s\n","","sumaSubMax1","sumaSubMax2");
    for(i=0;i<10;i++){
        aleatorio(v,9);
        listar_vector(v,9);
        a=sumaSubMax1(v,9);
        b=sumaSubMax2(v,9);
        printf("%15d%15d\n",a,b);
    }
}

void tiempos1(int n){
    double tiempo, t1, t2, tiempAux, rAj=0,rSob=0,rSub=0;
    int *v=malloc(sizeof(int)*32000);
    int i;

    aleatorio(v,n);
    t1=microsegundos();
    sumaSubMax1(v,n);
    t2=microsegundos();
    tiempo=t2-t1;

    if(tiempo<500){
        tiempAux=microsegundos();
        for(i=0;i<1000;i++){
            sumaSubMax1(v,n);
        }
        tiempo=microsegundos();
        
        tiempo=(tiempo-tiempAux)/1000;
        printf("(*)");
    }
    else{
        printf("   ");
    }

    rSub=tiempo/pow(n,1.8);
    rAj=tiempo/pow(n,2);
    rSob=tiempo/pow(n,2.2);
    
    printf("%15d%15.3lf%15.6lf%15.6lf%15.6lf\n",n,tiempo,rSub,rAj,rSob);

    free(v);
}

void tiempos2(int n){
    double tiempo, t1, t2, tiempAux, rAj=0,rSob=0,rSub=0;
    int *v=malloc(sizeof(int)*256000);
    int i;

    aleatorio(v,n);
    t1=microsegundos();
    sumaSubMax2(v,n);
    t2=microsegundos();
    tiempo=t2-t1;

    if(tiempo<500){
        tiempAux=microsegundos();
        for(i=0;i<1000;i++){
            sumaSubMax2(v,n);
        }
        tiempo=microsegundos();
        
        tiempo=(tiempo-tiempAux)/1000;
        printf("(*)");
    }
    else{
        printf("   ");
    }

    rSub=tiempo/pow(n,0.8);
    rAj=tiempo/n;
    rSob=tiempo/pow(n,1.1);

    printf("%15d%15.3lf%15.6lf%15.6lf%15.6lf\n",n,tiempo,rSub,rAj,rSob);

    free(v);
}

int main() {
    int i, j;
    inicializar_semilla();

    for(j=0;j<5;j++){
        printf("sumaSubMax1:\n%18s%15s","n","t(n)");
        printf("%15s%15s%15s\n","t(n)/n^1.8","t(n)/n^2","t(n)/n^2.2");
        for(i=500;i<=32000;i*=2){
            tiempos1(i);
        }
        puts("");
        printf("sumaSubMax2:\n%18s%15s","n","t(n)");
        printf("%15s%15s%15s\n","t(n)/n^0.8","t(n)/n^2","t(n)/n^1.1");
        for(i=500;i<=256000;i*=2){
            tiempos2(i);
        }
    }
    
    return 0;
}

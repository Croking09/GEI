#include <stdio.h>
#include <sys/time.h>
#include <mpi/mpi.h>
#include <stdlib.h>
#include <string.h>

#define DEBUG 0
#define SUPERDEBUG 0

#define N 775

void print_Matrix(float *matrix, int rows) {
    for(int i=0;i<rows;i++) {
        for(int j=0;j<N;j++) {
            printf("%4.1f ",matrix[i * N + j]);
        }
        printf("\n");
    }
}

void print_Vector(float *vector, int size) {
    for(int i = 0; i < size ; i++) {
        printf("%4.1f\n", vector[i]);
    }
}

void print_TamDespl(int *tam, int *despl, int numprocs) {
    printf("Tam: [");
    for(int i=0;i<numprocs;i++) {
        if(i == numprocs - 1) {
            printf("%d]\n", tam[i]);
        }
        else {
            printf("%d, ", tam[i]);
        }
    }
    printf("Despl: [");
    for(int i=0;i<numprocs;i++) {
        if(i == numprocs - 1) {
            printf("%d]\n", despl[i]);
        }
        else {
            printf("%d, ", despl[i]);
        }
    }
}

int calc_Rows(int rank, int numprocs) {
    if(rank < N % numprocs) {
        return ((N / numprocs) + 1);
    } else {
        return (N / numprocs);
    }
}

void calc_TamDespl(int *tam, int *despl, int numprocs, int scatter) {
    for (int i = 0; i < numprocs; i++) {
        if (i < N % numprocs) {
            // rows'
            tam[i] = (N / numprocs + 1);
        } else {
            // rows''
            tam[i] = (N / numprocs);
        }
        if(scatter) {
            tam[i] *= N; //Ao ser unha matriz son tam[i]*n elementos a enviar
        }

        if (i == 0) {
            despl[i] = 0;
        } else {
            despl[i] = despl[i - 1] + tam[i - 1]; //Mira o nº de elementos do proceso anterior para saber desde donde empeza a contar
        }
    }    
}

int main(int argc, char *argv[] ) {
    int i, j, rows, rank, numprocs;
    float *matrix = NULL; // matrix como vector porque scatterv pide un buffer continuo
    float *vector = NULL;
    int *despl = NULL;
    int *tam = NULL;
    float *rec_matrix = NULL; // Igual que matrix
    float *result = NULL;
    float *result_parc = NULL;
    struct timeval  tv1, tv2;
    int microseconds_comm, microseconds_calculo;
    int media_comm, media_calculo;

    char message[100];

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    if(rank == 0 && DEBUG) {
        printf("\n[%d] Utilizando N = %d y %d procesos\n", rank, N, numprocs);
    }

    vector = malloc(sizeof(float) * N);
    despl = malloc(sizeof(int) * numprocs);
    tam = malloc(sizeof(int) * numprocs);

    // Inicializar Matriz y Vector
    if (rank == 0) {
        matrix = malloc(sizeof(float) * N * N);
        for (i = 0; i < N; i++) {
            vector[i] = i;
            for (j = 0; j < N; j++) {
                matrix[i * N + j] = i + j;
            }
        }
        if(DEBUG) {
            printf("[%d] Matriz inicializada\n", rank);
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                printf("Matriz:\n");
                print_Matrix(matrix, N);

                printf("\nVector:\n");
                print_Vector(vector, N);
                printf("=================================================================\n\n");
            }
        }

        // Construir vectores de tamaños y desplazamientos
        calc_TamDespl(tam, despl, numprocs, 1);
        if(DEBUG) {
            printf("[%d] Vectores de tam y despl inicializados\n", rank);
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                print_TamDespl(tam, despl, numprocs);
                printf("=================================================================\n\n");
            }
        }
    }

    // Calcular cuantas filas le tocan a cada proceso
    rows = calc_Rows(rank, numprocs);
    if(DEBUG) {
        if(rank == 0) {
            printf("[%d] Using %d rows\n", rank, rows);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
        }
        else {
            sprintf(message, "[%d] Using %d rows", rank, rows);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
        
    }

    // Tiempo antes de enviar el vector y la matriz
    gettimeofday(&tv1, NULL);

    // Enviar vector a todos los procesos
    MPI_Bcast(vector, N, MPI_FLOAT, 0, MPI_COMM_WORLD);
    if (DEBUG) {
        if(rank == 0) {
            printf("[%d] Vector enviado a todos los procesos\n", rank);
            printf("[%d] Vector recibido\n", rank);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                print_Vector(vector, N);
                printf("=================================================================\n\n");
            }
        }
        else {
            sprintf(message, "[%d] Vector recibido", rank);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Enviar matriz a todos los procesos
    rec_matrix = malloc(sizeof(float) * rows * N);
    MPI_Scatterv(matrix, tam, despl, MPI_FLOAT, rec_matrix, rows * N, MPI_FLOAT, 0, MPI_COMM_WORLD);
    if(DEBUG) {
        if(rank == 0) {
            printf("[%d] Matriz enviada a todos los procesos\n", rank);
            printf("[%d] Matriz recibida\n", rank);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                printf("Matriz parcial del proceso 0:\n");
                print_Matrix(rec_matrix, rows);
                printf("=================================================================\n\n");
            }
        }
        else {
            sprintf(message, "[%d] Matriz recibida", rank);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Tiempo después de enviar el vector y la matriz
    gettimeofday(&tv2, NULL);
    microseconds_comm = (tv2.tv_usec - tv1.tv_usec) + 1000000 * (tv2.tv_sec - tv1.tv_sec);

    // Liberar la matriz original para optimizar memoria
    if(rank == 0) {
        free(matrix);
    }

    // Tiempo antes del cálculo parcial
    gettimeofday(&tv1, NULL);

    // Calcular resultado parcial
    result_parc = (float *)malloc(sizeof(float) * rows);
    for(i=0;i<rows;i++) {
        result_parc[i]=0;
        for(j=0;j<N;j++) {
            result_parc[i] += rec_matrix[i * N + j] * vector[j];
        }
    }
    if(DEBUG) {
        if(rank == 0) {
            printf("[%d] Resultado parcial calculado\n", rank);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                printf("Resultado parcial del proceso 0:\n");
                print_Vector(result_parc, rows);
                printf("=================================================================\n\n");
            }
        }
        else {
            sprintf(message, "[%d] Resultado parcial calculado", rank);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Tiempo después del cálculo parcial
    gettimeofday(&tv2, NULL);
    microseconds_calculo = (tv2.tv_usec - tv1.tv_usec) + 1000000 * (tv2.tv_sec - tv1.tv_sec);
        
    // Liberar vector para optimizar memoria
    free(vector);
    
    // Construir vectores de tamaños y desplazamientos
    calc_TamDespl(tam, despl, numprocs, 0);
    if(DEBUG) {
        if(rank == 0) {
            printf("[%d] Vectores de tam y despl inicializados\n", rank);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                print_TamDespl(tam, despl, numprocs);
                printf("=================================================================\n\n");
            }
        }
        else {
            sprintf(message, "[%d] Vectores de tam y despl inicializados", rank);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Tiempo antes de recibir los resultados parciales
    gettimeofday(&tv1, NULL);

    // Recibir resultados parciales
    if(rank == 0) {
        result = (float *)malloc(sizeof(float) * N);
    }
    MPI_Gatherv(result_parc, rows, MPI_FLOAT, result, tam, despl, MPI_FLOAT, 0, MPI_COMM_WORLD);
    if(DEBUG) {
        if(rank == 0) {
            printf("[%d] Resultado parcial enviado\n", rank);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
            printf("[%d] Resultados parciales recibidos\n", rank);
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                printf("Resultado final:\n");
                print_Vector(result, N);
                printf("=================================================================\n\n");
            }
        }
        else {
            sprintf(message, "[%d] Resultado parcial enviado", rank);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Tiempo después de recibir los resultados parciales
    gettimeofday(&tv2, NULL);
    microseconds_comm += (tv2.tv_usec - tv1.tv_usec) + 1000000 * (tv2.tv_sec - tv1.tv_sec);

    // Enviar tiempos de envío y cálculo
    MPI_Reduce(&microseconds_comm, &media_comm, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
    MPI_Reduce(&microseconds_calculo, &media_calculo, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
    if(DEBUG) {
        if(rank == 0) {
            printf("[%d] Tiempos de envío y cálculo enviados\n", rank);
            for(int i = 1; i < numprocs; i++) {
                MPI_Recv(message, 100, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                printf("%s\n", message);
            }
            printf("[%d] Tiempos de envío y cálculo recibidos\n", rank);
            if(SUPERDEBUG) {
                printf("=================================================================\n");
                printf("Envio = %d\nCalculo = %d\n", media_comm, media_calculo);
                printf("=================================================================\n\n");
            }
        }
        else {
            sprintf(message, "[%d] Tiempos de envío y cálculo enviados", rank);
            MPI_Send(message, strlen(message) + 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Calcular media e imprimir resultados
    if(rank == 0) {
        microseconds_comm /= numprocs;
        microseconds_calculo /= numprocs;

        printf("%s=================================================================\n", !SUPERDEBUG ? "\n" : "");
        if(DEBUG && !SUPERDEBUG) {
            printf("Resultado final:\n");
            print_Vector(result, N);
            printf("\n");
        }

        printf("Tiempos (segundos):\n");
        printf ("Tiempo de comunicacion medio = %lf\n", (double) microseconds_comm/1E6);
        printf ("Tiempo de calculo medio = %lf\n", (double) microseconds_calculo/1E6);
        printf("=================================================================\n");
    }

    // Liberar memoria
    if(rank == 0) {
        free(result);
    }
    free(rec_matrix);
    free(tam);
    free(despl);
    free(result_parc);
    
    MPI_Finalize();
    return 0;
}

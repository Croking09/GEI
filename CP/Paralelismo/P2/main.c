#include <stdio.h>
#include <math.h>
#include <mpi/mpi.h>

int main(int argc, char *argv[])
{
    int rank, numprocs;
    int i, done = 0, n;
    double PI25DT = 3.141592653589793238462643;
    double local_pi, h, sum, x, received_pi;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    while (!done)
    {
        //El rango 0 es el encargado de recibir la n (intervalos)
        if(rank == 0) {
            printf("Enter the number of intervals: (0 quits) \n");
            scanf("%d",&n);
            
            //Por lo tanto el 0 es el encargado de enviar la n al resto de proceso
            for(int i = 1; i < numprocs; i++) {
                MPI_Send(&n, 1, MPI_INT, i, i, MPI_COMM_WORLD); //lo q envias, cuantas envias, tipo, destino, tag, comm
            }
            
        } else {
            //Wait, mientras no reciba n todos los procesos estan parados en el recv esperando
            //El 0 es el último en salir
            MPI_Recv(&n, 1, MPI_INT, 0, rank, MPI_COMM_WORLD, MPI_STATUS_IGNORE); //donde lo guarda, cuantos recibe, tipo, origen, tag, comm, status
        }

        if (n == 0) break; //Si pones 0 intervalos sale del programa, esta fuera de la condicion para que haga la comprobacion despues de que los procesos (numprocs) hayan recibido n

        //Calculo de pi
        h   = 1.0 / (double) n;
        sum = 0.0;
        for (i = rank + 1; i <= n; i+=numprocs) { //Para distribuir bien el trabajo entre los procesos
            x = h * ((double)i - 0.5);
            sum += 4.0 / (1.0 + x*x);
        }
        local_pi = h * sum; //pi local en cada proceso
        
        //Ya calculado pi local en trodos los procesos
        if (rank != 0) {
            //Todos los procesos diferentes a 0 le manda a 0 el valor de su pi local
            MPI_Send(&local_pi, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
        } else {
            //El proceso 0 va recibiendo y sumando todos los pi locales
            for (int source = 1; source < numprocs; source++) {
                MPI_Recv(&received_pi, 1, MPI_DOUBLE, source, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE); //Los local pi se van recibiendo en received_pi
                local_pi += received_pi; //local_pi guarda la suma
            }
            //Printea pi
            printf("pi is approximately %.16f, Error is %.16f\n", local_pi, fabs(local_pi - PI25DT));
        }
    }

    MPI_Finalize(); //Se finaliza el MPI
    return 0;
}

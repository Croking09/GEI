#include <stdio.h>
#include <math.h>
#include <mpi/mpi.h>

int main(int argc, char *argv[])
{
    int rank, numprocs;
    int i, done = 0, n;
    double PI25DT = 3.141592653589793238462643;
    double local_pi, h, sum, x;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    while (!done)
    {
        if(rank == 0) {
            printf("Enter the number of intervals: (0 quits) \n");
            scanf("%d",&n);

            for(int i = 1; i < numprocs; i++) {
                MPI_Send(&n, 1, MPI_INT, i, i, MPI_COMM_WORLD);
            }
        }

        if(rank != 0) {
            MPI_Recv(&n, 1, MPI_INT, 0, rank, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        if (n == 0) break;
  
        h   = 1.0 / (double) n;
        sum = 0.0;
        for (i = rank + 1; i <= n; i+=numprocs) {
            x = h * ((double)i - 0.5);
            sum += 4.0 / (1.0 + x*x);
        }
        local_pi = h * sum;

        if (rank != 0) {
            MPI_Send(&local_pi, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
        } else {
            double received_pi;
            for (int source = 1; source < numprocs; source++) {
                MPI_Recv(&received_pi, 1, MPI_DOUBLE, source, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                local_pi += received_pi;
            }
            printf("pi is approximately %.16f, Error is %.16f\n", local_pi, fabs(local_pi - PI25DT));
        }
    }

    MPI_Finalize();
    return 0;
}
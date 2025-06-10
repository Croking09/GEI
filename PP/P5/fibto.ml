let rec fib n =
  if n <= 1 then n
  else fib (n-1) + fib (n-2)
;;

let printFib n = print_endline (string_of_int (fib n));;

let printRec n =
  let rec bucle i =
    if n < 0 || fib i > n then Printf.printf("")
    else begin printFib i; bucle (i+1) end
  in if n < 0 then raise (Failure ("Argumento no valido")) else bucle 0
;;

if Array.length(Sys.argv) = 2 then printRec (int_of_string (Array.get Sys.argv(1))) else raise (Failure ("Nº de argumentos incorrecto"));;
let is_prime n =
  let rec check_from i =
    i >= n ||
    (n mod i <> 0 && check_from (i+1))
  in check_from 2
;;

let rec next_prime n =
  if n <=1 then raise(Failure ("Argumento no valido"))
  else if is_prime (n+1) then n+1
  else next_prime (n+1)
;;

let rec last_prime_to n =
  if n <=1 then raise(Failure ("Argumento no valido"))
  else if is_prime (n) then n
  else last_prime_to (n-1)
;;

let is_prime2 n =
  let rec check_from i =
    i * i >= n ||
    (n mod i <> 0 && check_from (i+1))
  in if n < 0 then raise(Failure ("Argumento no valido")) else check_from 2
;;
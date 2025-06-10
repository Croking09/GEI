let f n = if n mod 2 = 0 then n / 2 else 3 * n + 1;;

let rec orbit n =
  if n <= 0 then raise (Failure ("Argumento no valido"))
  else if n = 1 then "1"
  else (string_of_int n) ^ ", " ^ orbit (f n)
;;

let rec length n =
  if n <= 0 then raise (Failure ("Argumento no valido"))
  else if n = 1 then 0
  else 1 + length (f n)
;;

let rec top n =
  if n <= 0 then raise (Failure ("Argumento no valido"))
  else if n=1 then 1
  else max n (top (f n))
;;

let rec length'n'top n =
  if n <= 0 then raise (Failure ("Argumento no valido"))
  else if n = 1 then (0, 1)
  else let x = length'n'top (f n) in (1 + (fst x), max n (snd x))
;;

let rec longest_in m n =
  if m <= 0 || n <= 0 then raise (Failure ("Argumento no valido"))
  else if m = n then (m, length m)
  else if length m >= length n then longest_in m (n - 1)
  else longest_in (m + 1) n
;;

let rec highest_in m n =
  if m <= 0 || n <= 0 then raise (Failure ("Argumento no valido"))
  else if m = n then (m, top m)
  else if top m >= top n then highest_in m (n - 1)
  else highest_in (m + 1) n
;;
let rec sumto = function 0 -> 0 | n -> n + sumto (n-1);;

let rec exp10 = function 0 -> 1 | n -> 10 * exp10 (n-1);;

let rec num_cifras = function x ->
  if (x / 10 = 0) then
    1
  else
    1 + num_cifras (x / 10)
;;

let rec sum_cifras = function x ->
  if (x / 10 = 0) then
    abs x
  else
    abs (x mod 10) + sum_cifras (x / 10)
;;
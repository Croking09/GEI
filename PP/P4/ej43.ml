let rec reverse x =
  if x < 10 then x
  else (x mod 10) * int_of_float (10. ** float_of_int (int_of_float (log10 (float_of_int x)))) + reverse (x / 10)
;;

let rec palindromo s =
  if String.length s = 0 || String.length s = 1 then true
  else if s.[0] <> s.[String.length s - 1] then false
  else palindromo (String.sub s 1 (String.length s - 2))
;;

let rec mcd (x, y) =
  if y = 0 then x
  else mcd (y, x mod y)
;;
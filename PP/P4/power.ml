let rec power x y =
  if y = 0 then 1
  else if y = 1 then x
  else x * power x (y - 1)
;;

let rec power' x y =
  if y mod 2 = 0 then int_of_float(float_of_int(x * x) ** float_of_int(y / 2))
  else x * power' x (y - 1)
;;

(*La funcion power' es mejor que power porque como mucho solo debe llamarse a si misma 1 vez más mientras que la funcion power*)
(*debe llamarse y - 1 veces como máximo*)

let rec powerf (x: float) (y: int) =
  if y = 0 then 1.
  else if y = 1 then x
  else x *. powerf x (y-1)
;;
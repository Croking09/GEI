let next (x,y) =
  if x = 1 then (if (x + y - 1) mod 2 = 1 then (x,y+1) else (x+1,y-1))
  else if y = 1 then (if (x + y - 1) mod 2 = 0 then (x+1,y) else (x-1,y+1))
  else if (x + y - 1) mod 2 = 0 then (x+1,y-1)
  else (x-1,y+1)
;;

let rec steps_from (x,y) n =
  if n = 0 then (x,y)
  else steps_from (next (x,y)) (n-1)
;;

let rec pair a =
  if a = 1 then (1,1)
  else steps_from (1,1) (a-1)
;;

(*
let crono f x =
  let t = Sys.time () in
  f x;
  Sys.time () -. t
;;
*)

let pair_i p =
  let rec find i =
    if pair i = p then i
    else find (i+1)
  in find 1
;;

(*crono pair_i (12,130) = 1.139*)
(*crono pair_i (100,101) = 4.427*)

let prev (x,y) =
  if x = 1 && y = 1 then (1,1)
  else if x = 1 then (if (x + y - 1) mod 2 = 0 then (x,y-1) else (x+1,y-1))
  else if y = 1 then (if (x + y - 1) mod 2 = 1 then (x-1,y) else (x-1,y+1))
  else if (x + y - 1) mod 2 = 0 then (x-1,y+1)
  else (x+1,y-1)
;;

let rec pair_i' p =
  if p = (1,1) then 1
  else 1 + pair_i' (prev p)
;;

(*crono pair_i' (12,130) = 0.00054*)
(*crono pair_i' (100,101) = 0.00129*)

(*Utilizo la funcion crono comentada anteriormente para medir con exactitud los tiempos de ambas funciones.*)
(*La implementacion proporcionada era lenta porque aun siendo ella misma recursiva llamaba a otra funcion recursiva, steps_from, por lo que tardaba mucho.*)
(*La nueva implementacion se apoya en una funcion que devuelve el anterior a un par dado y que no es recursiva. De este modo podemos mejorar los tiempos de ejecucion enormemente.*)
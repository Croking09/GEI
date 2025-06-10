open MinPrioQueue (*Para que el archivo compile es necesario que el archivo "minPrioQueue.cmo"
                    esté en el mismo directorio de forma que el nombre del módulo empleado sea
                    "MinPrioQueue". En caso de que el módulo tenga otro nombre será necesario
                    cambiar la instrucción open de la forma: open Nombre_modulo*)

let dijkstra w =
  let es_cuadrada w = (*Funcion que comprueba si la matriz es cuadrada*)
    Array.for_all (function fila -> Array.length fila = Array.length w) w
  in

  let solo_positivos w = (*Funcion que comprueba que la matriz solo contega positivos o None*)
    let es_valido x = x >= Some 0 || x = None in
    Array.for_all (function fila -> Array.for_all es_valido fila) w
  in

  if (not (es_cuadrada w)) || (not (solo_positivos w)) then raise (Invalid_argument "dijkstra") (*Se lanza la excepcion*)
  else (*Utilizo una implementacion del algoritmo que solo devuelve un int option array de un nodo determinado al resto*)
    let dijkstra_help w start =
      let num_nodos = Array.length w in
      let q = new_queue () in
      let distances = Array.make num_nodos None in
      let visited = Array.make num_nodos false in
    
      distances.(start) <- Some 0;
      insert q distances.(start) start;
    
      let item_opt = ref None in
      while match extract_opt q with
            | None -> false
            | Some item -> 
                item_opt := Some item;
                true
      do
        match Option.get !item_opt with
        | Some current_distance, current_node ->
            if not visited.(current_node) then begin
              visited.(current_node) <- true;

              for i = 0 to num_nodos - 1 do
                match w.(current_node).(i) with
                | Some weight ->
                    let distance = current_distance + weight in

                    if Some distance < distances.(i) || distances.(i) = None then begin
                      distances.(i) <- Some distance;
                      insert q (Some distance) i;
                    end;
                | None -> ()
              done;
            end
        | None, current_node -> ()
      done;
    
      distances
    in

    let result matrix = (*Utilizo esta funcion para crear el int option array array deseado*)
      let num_nodos = Array.length matrix in
      let m = Array.make_matrix num_nodos num_nodos None in
      for i = 0 to num_nodos - 1 do
        m.(i) <- dijkstra_help matrix i
      done;
      m
    in

    result w
;;
let p = function (x: float) ->
  if (x>0.) then
    2. *. Float.pi *. x
  else
-1.;;

let area = function (x: float) ->
  if( x>0.) then
    Float.pi *. x *. x
  else
-1.;;

let absf = function (x: float) ->
  if (x<=0.) then
    (-1.) *. x
  else
x;;

let even = function (x: int) ->
  if (x mod 2 = 0) then
    true
  else
false;;

let next3 = function (x: int) ->
  if (x mod 3 = 0) then
    x
  else
x + (3 - (x mod 3));;

let is_a_letter = function (x: char) ->
  if((x>='A' && x<='Z') || (x>='a' && x<='z')) then
    true
  else
false;;

let string_of_bool = function (x: bool) ->
  if (x = true) then
    "verdadero"
  else
"falso";;
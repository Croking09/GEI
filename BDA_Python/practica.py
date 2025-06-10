import psycopg2
import psycopg2.extras
import psycopg2.errorcodes

## ------------------------------------------------------------
def connect_db():
    """
     Como estamos en Windows, se debe usar usuario y contraseña (no existe conexión local).
     """
    try:
        #conn = psycopg2.connect() # En linux esto funciona
        conn = psycopg2.connect(
            host='localhost',
            user='bda',
            password='bda',
            dbname='bda'
        )
        print("Conexión exitosa")
        return conn
    except Exception as e:
        print("Error de conexión:", e)
        return None

## ------------------------------------------------------------
def disconnect_db(conn):
    """
    Commitea y desconecta de la BD.
    """
    conn.commit()
    conn.close()

## ------------------------------------------------------------
def darDeAltaMasaje(conn):
    sNombre = input("Introduce un nombre para el masaje: ")
    nombre = None if sNombre == "" else sNombre
    sDuracion = input("Introduce cuanto dura el masaje en minutos: ")
    duracion = None if sDuracion == "" else int(sDuracion)
    sPrecio = input("Introduce cuanto cuesta el masaje: ")
    precio = None if sPrecio == "" else float(sPrecio)

    sql = """
        insert into masaje(nombre, duracion, precio)
            values(%(n)s,%(d)s,%(p)s)
    """
    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_READ_COMMITTED
    with conn.cursor() as cursor:
        try:
            cursor.execute(sql, {'n': nombre, 'd': duracion, 'p': precio})
            conn.commit()
            print("Masaje añadido")
        except psycopg2.Error as e:
            if e.pgcode == psycopg2.errorcodes.NOT_NULL_VIOLATION:
                if e.diag.column_name == "nombre":
                    print("Debes especificar un nombre")
                elif e.diag.column_name == "duracion":
                    print("Debes especificar cuanto dura el masaje")
                else:
                    print("Debes especificar un precio") 
            elif e.pgcode == psycopg2.errorcodes.CHECK_VIOLATION:
                print("El precio debe ser positivo")          
            else:
                print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()

## ------------------------------------------------------------
def darDeAltaMasajista(conn):
    sDNI = input("Introduce el DNI del masajista (formato 12345678A): ")
    dni = None if sDNI == "" else sDNI

    sNombre = input("Introduce el nombre del masajista: ")
    nombre = None if sNombre == "" else sNombre

    sCorreo = input("Introduce el correo del masajista (opcional): ")
    correo = None if sCorreo == "" else sCorreo

    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_READ_COMMITTED
    with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
        try:
            cursor.execute(
                "INSERT INTO persona(dni, nombre, correo) VALUES (%(dni)s, %(nombre)s, %(correo)s) RETURNING id;",
                {'dni': dni, 'nombre': nombre, 'correo': correo}
            )
            idPersona = cursor.fetchone()['id']

            cursor.execute(
                "INSERT INTO masajista(id_persona) VALUES (%(id)s);",
                {'id': idPersona}
            )

            conn.commit()
            print("Masajista añadido")

        except psycopg2.Error as e:
            if e.pgcode == psycopg2.errorcodes.NOT_NULL_VIOLATION:
                print(f"Falta un dato obligatorio: {e.diag.column_name}")
            elif e.pgcode == psycopg2.errorcodes.UNIQUE_VIOLATION:
                if 'dni' in e.diag.constraint_name:
                    print("Ya existe una persona con ese DNI.")
                elif 'correo' in e.diag.constraint_name:
                    print("Ese correo ya está en uso.")
            elif e.pgcode == psycopg2.errorcodes.CHECK_VIOLATION:
                print("El formato del DNI es incorrecto. Debe tener 8 números seguidos de una letra mayúscula.")
            else:
                print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()

## ------------------------------------------------------------
def darDeBajaMasajista(conn):
    while True:
        sDNI = input("Introduce el DNI del masajista a eliminar: ")
        dni = None if sDNI == "" else sDNI
        if dni is None:
            print("Debes introducir un DNI")
        else:
            break

    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_SERIALIZABLE # Porque no queremos Phantom reads
    with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
        try:
            cursor.execute("""
                select id
                from persona join masajista on persona.id = masajista.id_persona
                where dni = %(d)s
            """, {'d': dni})

            masajistaData = cursor.fetchone()
            if masajistaData is None:
                print("No existe ese masajista")
                conn.rollback()
                return
            idMasajista = masajistaData['id']

            cursor.execute("""
                select id, dni, nombre
                from persona join cliente on persona.id = cliente.id_persona
                where id_masajista=%(i)s
            """, {'i': idMasajista})
            clientesAfectados = cursor.fetchall()
            
            cursor.execute("select count(*) from masajista")
            masajistasRegistrados = cursor.fetchone()['count']

            if masajistasRegistrados == 1 and len(clientesAfectados) > 0:
                print("No puedes eliminar a este masajista porque no podrías reasignar a sus clientes")
                conn.rollback()
                return

            cursor.execute("delete from persona where id=%(i)s", {'i': idMasajista})

            for cliente in clientesAfectados:

                print("A " + str(cliente['nombre']) + ", con DNI " + str(cliente['dni']) + ", se le debe asignar un nuevo masajista")
                while True:
                    sDNINuevoMasajista = input("Introduce el DNI del nuevo masajista: ")
                    DNINuevoMasajista = None if sDNINuevoMasajista == "" else sDNINuevoMasajista
                    try:
                        cursor.execute("""
                        select id
                        from persona join masajista on persona.id = masajista.id_persona
                        where dni = %(d)s
                        """, {'d': DNINuevoMasajista})

                        nuevoMasajistaData = cursor.fetchone()
                        if nuevoMasajistaData is None:
                            raise LookupError()
                        idNuevoMasajista = nuevoMasajistaData['id']

                        cursor.execute("update cliente set id_masajista=%(i)s where id_persona=%(j)s", {'i': idNuevoMasajista, 'j': cliente['id']})
                        break;
                    except LookupError:
                        print("Ese masajista no existe, vuelve a probar\n")
                    except psycopg2.Error as e:
                        if e.pgcode == psycopg2.errorcodes.CHECK_VIOLATION:
                            print("El cliente no puede ejercer de masajista consigo mismo, vuelve a probar\n")
                        elif e.pgcode == psycopg2.errorcodes.FOREIGN_KEY_VIOLATION:
                            print("Ese masajista no existe, vuelve a probar\n")
                        else:
                            print(f"Error {e.pgcode}: {e.pgerror}")

            print("\nMasajista eliminado")
            conn.commit()
        except psycopg2.Error as e:
            if e.pgcode == psycopg2.errorcodes.SERIALIZATION_FAILURE:
                print("Otro usuario ya ha eliminado este masajista")
            else:
                print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()

## ------------------------------------------------------------
def aumentarPrecioMasaje(conn, max_intentos=3):
    sId = input("Introduce el ID del masaje a aumentar: ")
    id = None if sId == "" else int(sId)

    for intento in range(max_intentos):
        try:
            conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_SERIALIZABLE
            with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
                cursor.execute("select precio from masaje where id=%(i)s", {'i': id})
                masajeData = cursor.fetchone()
                if masajeData is None:
                    print("No existe ese masaje")
                    conn.rollback()
                    return
                
                precioMasaje = masajeData['precio']
                print("El masaje con ID " + str(id) + " cuesta " + str(precioMasaje) + "€ actualmente")

                sIncremento = input("Introduce qué % aumentar el precio: ")
                incremento = None if sIncremento == "" else float(sIncremento) / 100 + 1
                
                if incremento is None:
                    print("Debes introducir un incremento válido")
                    conn.rollback()
                    return
                
                nuevoPrecio = float(precioMasaje) * incremento

                cursor.execute("update masaje set precio=%(p)s where id=%(i)s", {'p': nuevoPrecio, 'i': id})

                input("ENTER")

                print("Precio actualizado, nuevo precio: " + str(nuevoPrecio) + "€")
                conn.commit()
                return
        except psycopg2.Error as e:
            if e.pgcode == psycopg2.errorcodes.CHECK_VIOLATION:
                print("El precio debe ser positivo")
            elif e.pgcode == psycopg2.errorcodes.SERIALIZATION_FAILURE:
                print(f"Otro usuario ha actualizado el precio. Reintentando... ({intento + 1}/{max_intentos})")
            else:
                print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()

    print("No se pudo completar la operación tras varios intentos.")

## ------------------------------------------------------------

def verInformacionCliente(conn, control_tx=True):
    sDNI = input("Introduce el DNI del cliente: ")
    dni = None if sDNI == "" else sDNI

    sql = """
        SELECT p.id, p.nombre, p.correo, c.id_masajista, c.telefono
        FROM persona p
        JOIN cliente c ON p.id = c.id_persona
        WHERE p.dni = %(dni)s
    """

    if control_tx:
        conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_READ_COMMITTED

    with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
        try:
            cursor.execute(sql, {'dni': dni})
            row = cursor.fetchone()
            if row:
                correo = row['correo'] if row['correo'] else "Desconocido"
                telefono = row['telefono'] if row['telefono'] else "Desconocido"
                id_masajista = row['id_masajista'] if row['id_masajista'] else "Desconocido"
                print(f"""
                      Datos del cliente ({dni}):
                      ID: {row['id']}
                      Nombre: {row['nombre']}
                      Correo: {correo}
                      Teléfono: {telefono}""")
            else:
                print(f"No se encontro ningun cliente con DNI: {dni}.")
            if control_tx:
                conn.commit()
        except psycopg2.Error as e:
            print(f"Error {e.pgcode}: {e.pgerror}")
            if control_tx:
                conn.rollback()

## ------------------------------------------------------------

def actualizarCorreo(conn):
    
    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_SERIALIZABLE

    sDNI = input("Introduce el DNI de la persona: ")
    dni = None if sDNI == "" else sDNI

    sql_select = """
        SELECT id, nombre, correo
        FROM persona
        WHERE dni = %(dni)s
    """

    with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
        try:
            cursor.execute(sql_select, {'dni': dni})
            row = cursor.fetchone()
            if row is None:
                print(f"No se encontró a ninguna persona con ese DNI")
                return

            print(f"""
                      Datos actuales:
                      ID: {row['id']}
                      Nombre: {row['nombre']}
                      Correo: {row['correo'] or 'Desconocido'}""")

            nuevoCorreo = input("Introduce el nuevo correo: ")

            sql_update = """
                UPDATE persona
                SET correo = %(correo)s
                WHERE id = %(id)s
            """
            cursor.execute(sql_update, {'correo': nuevoCorreo, 'id': row['id']})

            conn.commit()
            print("Correo actualizado correctamente.")

        except psycopg2.Error as e:
            if e.pgcode == psycopg2.errorcodes.UNIQUE_VIOLATION:
                print("Ese correo ya está en uso.")
            elif e.pgcode == psycopg2.errorcodes.SERIALIZATION_FAILURE:
                print("El registro fue modificado por otro usuario, intentalo de nuevo.")
            else:
                print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()

## ------------------------------------------------------------

def eliminarMasaje(conn):
    sId = input("Introduce el ID del masaje a eliminar: ")
    if sId == "":
        print("Debes introducir un ID")
        return
    id_masaje = int(sId)

    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_READ_COMMITTED
    with conn.cursor() as cursor:
        try:
            cursor.execute("SELECT * FROM masaje WHERE id = %s", (id_masaje,))
            if cursor.fetchone() is None:
                print("No existe un masaje con ese ID.")
                return

            cursor.execute("DELETE FROM masaje WHERE id = %s", (id_masaje,))
            conn.commit()
            print("Masaje eliminado correctamente.")
        except psycopg2.Error as e:
            print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()
## ------------------------------------------------------------

def obtenerMasajistasCapacitados(conn):
    sIdMasaje = input("Introduce el ID del masaje: ")
    if sIdMasaje == "":
        print("Debes introducir un ID de masaje")
        return
    id_masaje = int(sIdMasaje)

    sql = """
        SELECT p.nombre, p.dni, p.correo
        FROM persona p
        JOIN masajista m ON p.id = m.id_persona
        JOIN capacitado c ON m.id_persona = c.id_masajista
        WHERE c.id_masaje = %s
    """

    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_READ_COMMITTED
    with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
        try:
            cursor.execute(sql, (id_masaje,))
            rows = cursor.fetchall()
            if not rows:
                print("No hay masajistas capacitados para este masaje.")
                return
            print(f"\nMasajistas capacitados para el masaje con ID {id_masaje}:")
            for row in rows:
                print(f"- {row['nombre']} (DNI: {row['dni']}, Correo: {row['correo'] or 'N/A'})")
            conn.commit()
        except psycopg2.Error as e:
            print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()


## ------------------------------------------------------------

def registrarCliente(conn):
    sDNI = input("Introduce el DNI del cliente (formato 12345678A): ")
    dni = None if sDNI == "" else sDNI

    sNombre = input("Introduce el nombre del cliente: ")
    nombre = None if sNombre == "" else sNombre

    sCorreo = input("Introduce el correo del cliente (opcional): ")
    correo = None if sCorreo == "" else sCorreo

    sTelefono = input("Introduce el teléfono del cliente (opcional): ")
    telefono = None if sTelefono == "" else sTelefono

    conn.isolation_level = psycopg2.extensions.ISOLATION_LEVEL_SERIALIZABLE
    with conn.cursor(cursor_factory=psycopg2.extras.DictCursor) as cursor:
        try:
            # Buscar masajistas disponibles
            cursor.execute("""
                SELECT p.dni, p.nombre
                FROM masajista m JOIN persona p ON m.id_persona = p.id
            """)
            masajistas = cursor.fetchall()
            if not masajistas:
                print("No hay masajistas registrados. No se puede registrar cliente.")
                return

            print("\nMasajistas disponibles:")
            for m in masajistas:
                print(f"- {m['nombre']} (DNI: {m['dni']})")

            sMasajistaDNI = input("\nIntroduce el DNI del masajista asignado: ")
            cursor.execute("""
                SELECT id FROM persona 
                WHERE dni = %(dni)s AND id IN (SELECT id_persona FROM masajista)
            """, {'dni': sMasajistaDNI})
            masajista = cursor.fetchone()
            if masajista is None:
                print("Masajista no válido.")
                return
            idMasajista = masajista['id']

            # Insertar en persona
            cursor.execute("""
                INSERT INTO persona (dni, nombre, correo)
                VALUES (%(dni)s, %(nombre)s, %(correo)s)
                RETURNING id
            """, {'dni': dni, 'nombre': nombre, 'correo': correo})
            idPersona = cursor.fetchone()['id']

            # Insertar en cliente
            cursor.execute("""
                INSERT INTO cliente (id_persona, telefono, id_masajista)
                VALUES (%(id)s, %(tel)s, %(id_masajista)s)
            """, {'id': idPersona, 'tel': telefono, 'id_masajista': idMasajista})

            conn.commit()
            print("Cliente registrado correctamente.")

        except psycopg2.Error as e:
            if e.pgcode == psycopg2.errorcodes.UNIQUE_VIOLATION:
                print("El DNI, correo o teléfono ya están en uso.")
            elif e.pgcode == psycopg2.errorcodes.NOT_NULL_VIOLATION:
                print(f"Falta un dato obligatorio: {e.diag.column_name}")
            elif e.pgcode == psycopg2.errorcodes.CHECK_VIOLATION:
                print("Violación de alguna restricción de datos (teléfono o referencias).")
            else:
                print(f"Error {e.pgcode}: {e.pgerror}")
            conn.rollback()

## ------------------------------------------------------------

def menu(conn):
    MENU_TEXT = """
      -- MENÚ --
1 - Dar de alta masaje
2 - Dar de alta masajista
3 - Dar de baja masajista
4 - Aumentar precio de un masaje
5 - Ver información de cliente
6 - Actualizar el correo de una persona
7 - Eliminar masaje
8 - Ver masajistas capacitados para un masaje
9 - Registrar nuevo cliente y asignarle masajista
q - Salir
"""
    while True:
        print(MENU_TEXT)
        tecla = input('Opción> ')
        if tecla == 'q':
            break
        elif tecla == '1':
            darDeAltaMasaje(conn)
        elif tecla == '2':
            darDeAltaMasajista(conn)
        elif tecla == '3':
            darDeBajaMasajista(conn)
        elif tecla == '4':
            aumentarPrecioMasaje(conn)
        elif tecla == '5':
            verInformacionCliente(conn)
        elif tecla == '6':
            actualizarCorreo(conn)
        elif tecla == '7':
            eliminarMasaje(conn)
        elif tecla == '8':
            obtenerMasajistasCapacitados(conn)
        elif tecla == '9':
            registrarCliente(conn)

## ------------------------------------------------------------
def main():
    print('Conectando a PostgreSQL...')
    conn = connect_db()
    print('Conectado.')
    menu(conn)
    disconnect_db(conn)

## ------------------------------------------------------------

if __name__ == '__main__':
    main()

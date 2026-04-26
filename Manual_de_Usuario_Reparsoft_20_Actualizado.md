# MANUAL DE USUARIO REPARSOFT 2.0
## Sistema de Gestion para Laboratorios de Reparaciones Electronicas

**Desarrollado por:** Diego H. Bertossi  
**Version:** Reparsoft 2.0

---

## Tabla de Contenidos

1. [Introduccion](#1-introduccion)
2. [Requisitos del Sistema](#2-requisitos-del-sistema)
3. [Inicio del Sistema](#3-inicio-del-sistema)
4. [Pantalla Principal](#4-pantalla-principal)
5. [Modulo de Equipos](#5-modulo-de-equipos)
6. [Modulo de Clientes](#6-modulo-de-clientes)
7. [Modulo de Salidas y Remitos](#7-modulo-de-salidas-y-remitos)
8. [Modulo de Busquedas](#8-modulo-de-busquedas)
9. [Modulo de Presupuestos](#9-modulo-de-presupuestos)
10. [Modulo de Listados y Estadisticas](#10-modulo-de-listados-y-estadisticas)
11. [Modulo de Usuarios](#11-modulo-de-usuarios)
12. [Modulo de Backup](#12-modulo-de-backup)
13. [Modulo de Configuracion](#13-modulo-de-configuracion)
14. [Comunicaciones (Email y WhatsApp)](#14-comunicaciones-email-y-whatsapp)
15. [Archivos Excel](#15-archivos-excel)
16. [Codigos de Color y Estados](#16-codigos-de-color-y-estados)
17. [Preguntas Frecuentes (FAQ)](#17-preguntas-frecuentes-faq)
18. [Glosario](#18-glosario)

---

## 1. Introduccion

Reparsoft 2.0 es un sistema de gestion integral diseñado para laboratorios de reparaciones electronicas. Permite administrar el ciclo completo de una reparacion, desde el ingreso del equipo hasta su entrega al cliente, incluyendo presupuestacion, facturacion, generacion de remitos, comunicaciones con clientes y control estadistico.

### Caracteristicas principales

- **Gestion de equipos:** Registro de ingreso, visualizacion, edicion, seguimiento de estados (fisico, tecnico y comercial).
- **ELS:** Identificacion unica de cada reparacion mediante numero ELS.
- **Gestion de clientes:** Alta, baja y modificacion de clientes y sus sucursales.
- **Presupuestos:** Generacion, envio por email, registro de pagos, cotizacion del dolar en tiempo real, historial de precios.
- **Remitos de salida:** Generacion de remitos, marcado de envio, impresion.
- **Busquedas avanzadas:** Por componente original o de reemplazo.
- **Listados y estadisticas:** Listados completos con filtros, graficos por año/tecnico/cliente, resumen mensual tecnico, facturacion por cliente.
- **Usuarios y permisos:** Sistema de roles con permisos granulares por modulo.
- **Backup:** Local y remoto (Clever Cloud), generacion e importacion.
- **Comunicaciones:** Envio de correos electronicos y mensajes por WhatsApp.
- **Reportes:** Generacion de reportes en PDF (presupuestos, registros de entrada, remitos, resumenes tecnicos).
- **Corrector ortografico:** Integrado en campos de texto extensos. Revision gramatical asistida por API.
- **Gestion de archivos Excel:** Vinculados (Reparaciones, Caja, Detalle de Gastos).
- **Soporte multi-sucursal:** Base de datos separada para Bariloche y Buenos Aires, con acceso adicional a bases de datos antiguas.
- **Gestion de repuestos:** Con trazabilidad de componentes originales y de reemplazo.

---

## 2. Requisitos del Sistema

| Requisito | Detalle |
|---|---|
| Sistema operativo | Windows 7 / 8 / 10 / 11 64 bit |
| Java | JRE 8 o superior |
| Base de datos | MySQL 8.x (local) |
| Resolucion minima | 1024 x 768 px |
| Conexion a Internet | Requerida para backup remoto, cotizacion dolar, WhatsApp y correo electronico |

---

## 3. Inicio del Sistema

### 3.1 Seleccion de Ubicacion de Base de Datos

Al iniciar Reparsoft, lo primero que se muestra es la Ventana de Ubicacion de Base de Datos. Esta ventana permite seleccionar a que sede se desea conectar:

1. Se despliega un combo desplegable con dos opciones:
   - Buenos Aires
   - Bariloche
2. Seleccione la ubicacion correspondiente.
3. Haga clic en el boton "Acceder".

> **Nota:** Cada ubicacion tiene su propia base de datos independiente. Los datos de Buenos Aires y Bariloche son completamente separados.

Si no selecciona una ubicacion y presiona "Acceder", el sistema mostrara un mensaje: *"Debe seleccionar una UBICACION para acceder"*.

#### Estructura de bases de datos

El sistema gestiona **cuatro bases de datos MySQL** independientes, dos por cada sede:

| Base de datos | Sede | Tipo | Descripcion |
|---|---|---|---|
| `ordenesbrc` | Bariloche | **Actual** | Base de datos principal con los equipos actuales de Bariloche |
| `ordenesbrcantiguas` | Bariloche | **Antigua** | Base de datos historica con equipos antiguos de Bariloche (ELS desde 1) |
| `ordenesbsas` | Buenos Aires | **Actual** | Base de datos principal con los equipos actuales de Buenos Aires |
| `ordenesbsasantiguas` | Buenos Aires | **Antigua** | Base de datos historica con equipos antiguos de Buenos Aires (ELS desde 16550) |

Las bases **actuales** contienen los datos operativos del laboratorio:
- Bariloche: ELS desde el 988 en adelante.
- Buenos Aires: ELS desde el 24333 en adelante.

Las bases **antiguas** contienen registros historicos migrados desde sistemas anteriores:
- Bariloche Antigua: ELS desde el 1.
- Buenos Aires Antigua: ELS desde el 16550.

El cambio entre base actual y antigua se realiza desde el **Modulo de Configuracion** (ver seccion 13).

### 3.2 Inicio de Sesion (Login)

Una vez seleccionada la ubicacion, se presenta la Ventana de Login:

1. Ingrese su Usuario en el campo correspondiente.
2. Ingrese su Contraseña en el campo correspondiente.
3. Presione el boton "Aceptar" o presione la tecla Enter en el campo de contraseña.
4. Si desea salir del sistema, presione "Cancelar". El sistema pedira confirmacion.

**Comportamiento:**
- Si las credenciales son correctas, se cierra la ventana de login y se habilita la pantalla principal con el mensaje: *"BIENVENIDO/A: [Nombre del usuario]"*.
- Si las credenciales son incorrectas, se muestra el mensaje: *"Usuario o contraseña incorrecta"* y se limpian los campos para reintentar.
- Los botones del panel principal se habilitan o deshabilitan segun los permisos asignados al rol del usuario.

---

## 4. Pantalla Principal

La pantalla principal presenta un panel de control con 9 botones de acceso a los modulos del sistema:

| Boton | Descripcion |
|---|---|
| EQUIPOS | Ingreso y visualizacion de equipos al sistema |
| SALIDAS | Egreso de equipos y generacion de remitos |
| BUSCAR | Busqueda de equipos por componente original o de reemplazo |
| LISTADOS | Listados completos de equipos/reparaciones |
| CLIENTES | Visualizacion y alta de clientes |
| PRESUPUESTOS | Generacion de presupuestos, pendientes e ingreso de pagos |
| BACKUP | Backup del sistema (local y remoto) |
| USUARIOS | Gestion de los usuarios del sistema |
| CONFIGURACION | Configuraciones del sistema |

### Elementos adicionales de la pantalla principal

- **Usuario logueado:** Muestra el nombre del usuario actual.
- **Version del software:** Reparsoft 2.0.
- **Ubicacion de la base de datos:** Muestra la sede activa y el modo (ej: "BARILOCHE - ACTUAL" o "BUENOS AIRES - ANTIGUA").
- **Boton Cerrar Sesion:** Cierra la sesion del usuario actual y vuelve al login.
- **Boton Salir:** Cierra la aplicacion completamente.

> **Importante:** Los botones del panel de control se habilitan o deshabilitan segun los permisos del rol asignado al usuario logueado. Si un boton esta deshabilitado, el usuario no tiene permiso para acceder a esa funcionalidad. Ademas, al estar en modo de **Base Antigua**, la mayoria de los modulos se bloquean para usuarios que no sean administradores (ver seccion 13.2).

---

## 5. Modulo de Equipos

Este modulo es el nucleo del sistema. Permite gestionar todo el ciclo de vida de una reparacion. Al hacer clic en EQUIPOS, se presenta una ventana con dos opciones:

- **Agregar Equipos:** Para registrar un nuevo ingreso de equipo a reparar.
- **Visualizar Equipos:** Para consultar, editar y gestionar equipos ya ingresados.

### 5.1 Agregar Equipos

#### Paso a paso para ingresar un nuevo equipo

1. Haga clic en Agregar Equipos desde la ventana de Equipos.
2. El sistema abrira la ventana de ingreso con un numero ELS asignado automaticamente (secuencial) y la fecha de entrada establecida en la fecha actual.
3. Complete los siguientes campos:

| Campo | Descripcion | Obligatorio |
|---|---|---|
| ELS | Numero secuencial automatico (no editable) | Automatico |
| Fecha de Entrada | Fecha de ingreso del equipo | Si (automatica) |
| Cliente | Seleccione de la lista desplegable con autocompletado | Si |
| Sucursal | Se carga automaticamente segun el cliente seleccionado | Si |
| Nombre del Equipo | Tipo de equipo (ej: Osciloscopio, Multimetro) | Si |
| Marca | Marca del equipo; se actualiza segun el nombre seleccionado | Si |
| Modelo | Modelo del equipo; se actualiza segun la marca seleccionada | No |
| N° de Serie | Numero de serie del equipo | No |
| Fecha de Fabricacion | Fecha de fabricacion del equipo | No |
| Falla Reportada | Descripcion de la falla informada por el cliente | No |
| Aviso | Codigo o nota de aviso del cliente | No |
| Cliente del Cliente | Cliente final (si aplica) | No |
| Estado Fisico | Estado fisico al ingreso: Bueno, Regular, Malo | Si |
| Remito del Cliente | Numero de remito con el que ingreso el equipo | No |

4. Haga clic en Guardar para registrar el equipo.
5. El sistema le preguntara si desea generar el Registro de Ingreso (documento PDF).
6. Si acepta, se generara y mostrara un PDF con los datos del equipo ingresado que puede imprimir y entregar al cliente como comprobante.

> **Nota:** En modo de Base Antigua, el boton "Agregar Equipos" esta bloqueado para usuarios no administradores. El sistema mostrara el mensaje: *"NO ES POSIBLE ACCEDER A ESTE MODULO CON DATOS ANTIGUOS."*

#### Verificacion de Ingreso Anterior

Antes de ingresar un equipo, puede verificar si el mismo ya fue ingresado anteriormente:

1. Haga clic en el boton Verificar Ingreso Anterior.
2. En la ventana emergente, puede buscar por:
   - **ELS:** Seleccione un numero ELS anterior de la lista.
   - **N° de Serie:** Seleccione un numero de serie de la lista.
3. Haga clic en Verificar.
4. El sistema mostrara los datos del ingreso anterior y calculara los dias transcurridos.
5. Se mostrara una nota indicando:
   - **Menos de 30 dias:** "El equipo NO debera ingresarse nuevamente ya que han pasado menos de 30 dias desde su ingreso anterior."
   - **Entre 31 y 90 dias:** "El equipo se encuentra en periodo de GARANTIA. Verificar si corresponde."
   - **Mas de 90 dias:** "El equipo NO se encuentra dentro de los 90 dias de garantia."
6. Si decide utilizar los datos anteriores, haga clic en SI. Los campos se completaran automaticamente con los datos del equipo encontrado.
7. Si no desea utilizar los datos, haga clic en NO.

#### Agregar un nuevo cliente desde la ventana de ingreso

Si el cliente no existe en la lista:

1. Haga clic en el boton Agregar Cliente disponible en la ventana de ingreso.
2. Se abrira la ventana de gestion de clientes (ver seccion 6).
3. Al guardar el nuevo cliente y cerrar la ventana, los combos de la ventana de ingreso se actualizaran automaticamente.

#### Nueva Reparacion

Despues de guardar un equipo, puede iniciar inmediatamente otro ingreso haciendo clic en Nueva Reparacion. Esto limpiara todos los campos y generara un nuevo numero ELS.

### 5.2 Visualizar Equipos

La ventana de visualizacion permite consultar y gestionar todos los datos de un equipo ingresado. Al abrirse, muestra automaticamente el ultimo equipo ingresado.

#### Navegacion entre equipos

Utilice los botones de navegacion ubicados en la parte superior:

| Boton | Accion |
|---|---|
| Primero | Va al primer equipo registrado |
| Anterior | Va al equipo anterior |
| Siguiente | Va al equipo siguiente |
| Ultimo | Va al ultimo equipo registrado |

La navegacion es automatica segun la ubicacion de la base de datos y el modo:
- **Bariloche (Actual):** Navega ELS desde el 988 en adelante.
- **Buenos Aires (Actual):** Navega ELS desde el 24333 en adelante.
- **Bariloche (Antigua):** Navega ELS desde el 1 en adelante.
- **Buenos Aires (Antigua):** Navega ELS desde el 16550 en adelante.

#### Informacion mostrada

La ventana de visualizacion muestra toda la informacion del equipo organizada en secciones:

**Datos generales:**
- ELS, Fecha de Entrada, Cliente, Sucursal.
- Nombre del Equipo, Marca, Modelo, N° de Serie.
- Fecha de Fabricacion, Aviso, Cliente del Cliente.
- Estado Fisico, Remito del Cliente.

**Diagnostico y solucion:**
- Falla reportada.
- Diagnostico / Solucion del tecnico.
- Informe al cliente (con corrector ortografico integrado).

**Estados (mediante ComboBox):**

Los tres estados del equipo se muestran y editan directamente mediante **listas desplegables (ComboBox)** integradas en la ventana de visualizacion:

- **Estado Tecnico** - ComboBox con las opciones:
  - Sin Revisar
  - En Reparacion
  - Reparado
  - Reparado En Garantia
  - Vendido
  - Sin Falla
  - Sin Reparacion
  - No Aceptaron Reparacion
  - Sin Rep-Recambio Propuesto

- **Estado Comercial** - ComboBox con las opciones:
  - A la Espera de Aceptacion
  - Aceptado
  - NO Aceptado
  - Garantia
  - Garantia Siemens

- **Estado Fisico (Ubicacion)** - ComboBox con las opciones:
  - CABA
  - MDP
  - BRC
  - Enviado
  - Desguace

> **Nota:** Los estados se modifican directamente desde los ComboBox en la ventana de visualizacion. Para que los cambios se apliquen, es necesario hacer clic en **Guardar Cambios**. No existe un boton separado "Editar Estados" ya que la edicion se realiza de forma integrada en la propia ventana.

**Informacion economica (segun permisos):**
- Presupuesto ($ y U$$).
- Pago realizado.
- Estado del presupuesto (indicado por color de fondo).
- **N° Factura:** Campo con formato `#####-########` que muestra el numero de factura asociado al equipo. Este campo se encuentra en el panel de presupuesto de la ventana de visualizacion. Es de solo lectura durante la navegacion y se habilita al editar el equipo.

**Datos de remito:**
- Numero de remito asignado.
- Ubicacion del remito.

**Tecnico asignado:**
- Nombre del usuario/tecnico que trabaja en el equipo.

**Tabla de repuestos:**
- Referencia, Componente Original, Componente de Reemplazo, Notas.

#### Editar un equipo

1. Haga clic en el boton Editar.
2. Los campos se habilitaran para edicion (los combos reemplazaran a las etiquetas de texto).
3. Modifique los datos necesarios, incluyendo los estados mediante los ComboBox.
4. Haga clic en Guardar Cambios para confirmar las modificaciones.

> **Nota:** Si cierra la ventana con cambios sin guardar, el sistema le preguntara si desea guardar antes de salir.

#### Copiar Pago

En el panel de presupuesto de la ventana de visualizacion se encuentra el boton **"COPIAR PAGO"**. Este boton permite copiar rapidamente el monto del presupuesto al campo de pago:

1. **COPIAR PAGO:** Haga clic en este boton para copiar automaticamente el monto del presupuesto al campo "Pago". Esto es util cuando el cliente paga exactamente el monto presupuestado. Una vez copiado, el boton cambia su texto a **"LIMPIAR PAGO"**.

2. **LIMPIAR PAGO:** Si necesita eliminar el monto copiado, haga clic nuevamente en el boton (que ahora muestra "LIMPIAR PAGO"). El sistema pedira confirmacion: *"Se va a eliminar el monto del pago. ¿Desea continuar?"*. Si confirma, el pago se establece en $0 y el boton vuelve a mostrar "COPIAR PAGO".

> **Nota:** El boton solo se habilita cuando hay un presupuesto cargado para el equipo actual. Los cambios de pago se reflejan inmediatamente en el estado visual del presupuesto (color de fondo).

#### Copiar Factura

El boton **"COPIAR FACTURA"** abre la ventana **"Datos para Facturacion"**, que presenta los datos necesarios para generar la factura electronica en ARCA (ex AFIP) de forma organizada y con botones para copiar cada dato al portapapeles:

La ventana muestra tres secciones:

| Seccion | Contenido | Boton |
|---|---|---|
| **DATOS CLIENTE** | Nombre del cliente y numero de CUIT | COPIAR CUIT |
| **ITEM FACTURA** | Descripcion del equipo y datos de la reparacion | COPIAR ITEM |
| **TOTAL** | Monto del presupuesto | COPIAR TOTAL |

Cada seccion incluye un boton que copia el dato correspondiente al portapapeles del sistema, facilitando el pegado directo en el formulario de facturacion electronica de ARCA.

#### Gestionar Repuestos

**Agregar Repuesto:**
1. Haga clic en el boton "Agregar Repuesto".
2. Se abre la ventana de Repuestos.
3. Complete los campos: Referencia, Original, Reemplazo y Nota.
4. Haga clic en "Guardar".

**Editar un repuesto:**
1. Haga doble clic en la celda de la tabla de repuestos que desea modificar.
2. Edite directamente en la tabla.
3. Presione Enter o Tab para confirmar. Los cambios se guardan automaticamente.

**Eliminar un repuesto:**
1. Seleccione la fila del repuesto en la tabla.
2. Haga clic en Eliminar Repuesto.
3. Confirme la eliminacion en el cuadro de dialogo.

#### Generar Registro de Ingreso

Desde la ventana de visualizacion tambien puede generar (o reimprimir) el Registro de Ingreso de un equipo:
1. Haga clic en Registro de Ingreso.
2. Se generara un documento PDF con todos los datos del equipo.

#### Presupuestar desde Visualizacion

1. Haga clic en Presupuestar.
2. Se abrira la ventana de generacion de presupuesto con los datos del equipo cargados.
3. Para mas detalles, ver la seccion 9. Modulo de Presupuestos.

> **Importante:** Debe guardar los cambios pendientes antes de presupuestar.

#### Facturar

1. Haga clic en Facturar.
2. Se mostrara una ventana con los datos de facturacion: Cliente, CUIT, Item de factura, Presupuesto.
3. El sistema le preguntara si desea abrir la pagina de ARCA (ex AFIP) para generar la factura electronica.

#### Generar Remito desde Visualizacion

1. Haga clic en Generar Remito.
2. Si el equipo ya posee un remito, se le indicara que debe anularlo o eliminarlo primero.
3. Se abrira la ventana de remitos con los datos del equipo cargados.
4. Para mas detalles, ver la seccion 7. Modulo de Salidas y Remitos.

#### Enviar Avisos por Correo Electronico

Desde la ventana de visualizacion puede enviar tres tipos de avisos por email:

| Boton | Tipo de Aviso | Destinatario |
|---|---|---|
| Aviso Informe | Notificacion de informe tecnico listo | Direccion configurada |
| Aviso Equipo Listo | Notificacion de equipo terminado | Direccion configurada |
| Respuesta del Cliente | Respuesta del cliente al presupuesto | Correo del Cliente |

Al enviar un aviso de informe, se marca automaticamente el checkbox "Aviso Enviado" y se guarda en la base de datos.

#### Busqueda dentro de Visualizacion

1. Haga clic en Buscar (icono de binoculares).
2. Se abrira la ventana de busqueda en campos del equipo (ver seccion 8. Modulo de Busquedas - Busqueda por campos).

#### Abrir Archivos Excel

1. Haga clic en Abrir Excel.
2. Se mostrara una ventana con opciones para abrir: Archivo de Reparaciones, Archivo de Caja, Detalle de Gastos del año actual, o abrir todos los archivos en secuencia.
Para mas detalles, ver la seccion 15. Archivos Excel.

#### Enviar por WhatsApp o Correo

1. Haga clic en Enviar Correo/WSP.
2. Se abrira la ventana de comunicaciones donde puede elegir entre WhatsApp o correo electronico.

#### Restricciones en modo Base Antigua

Cuando el sistema esta conectado a una base de datos antigua (ver seccion 13.2), la ventana de visualizacion funciona en **modo de solo lectura** para usuarios no administradores. Se deshabilitan los siguientes botones:

- Editar
- Guardar Cambios
- Aviso Equipo Listo
- Respuesta al Tecnico
- Aviso Informe
- Presupuestar
- Facturar
- Abrir Excel
- Generar Remito
- Enviar Correo/WSP
- Copiar Factura

Solo se permite la **navegacion y consulta** de los equipos historicos. El usuario administrador (Rol ID 1) mantiene acceso completo incluso en modo de base antigua.

---

## 6. Modulo de Clientes

El modulo de Clientes permite gestionar la informacion de todos los clientes del laboratorio y sus sucursales.

### 6.1 Vista General

Al acceder al modulo de Clientes, se muestra una tabla con todos los clientes registrados:

| Campo | Descripcion |
|---|---|
| Nombre / Razon Social | Nombre del cliente (obligatorio) |
| CUIT | Numero de CUIT |
| Direccion | Domicilio del cliente |
| Telefono Empresa | Telefono de la empresa |
| Contacto | Nombre de la persona de contacto |
| Telefono Contacto | Telefono del contacto |
| Correo Electronico | Direcciones de email (una por linea) |

La tabla cuenta con autofiltros que permiten filtrar rapidamente por cualquier columna. Al hacer clic en un cliente, se muestran sus datos en los campos de detalle debajo.

### 6.2 Agregar un Cliente

1. Haga clic en Agregar.
2. Complete los campos del formulario.
3. Haga clic en Guardar.
4. El sistema creara automaticamente una sucursal por defecto (vacia) asociada al nuevo cliente.

> **Nota:** Validaciones: El nombre no puede estar vacio. No se pueden registrar clientes con nombres duplicados. Los correos electronicos deben tener formato valido.

### 6.3 Editar un Cliente

1. Seleccione un cliente en la tabla haciendo clic sobre su fila.
2. Haga clic en Editar.
3. Los campos se habilitaran con los datos del cliente seleccionado.
4. Modifique los datos necesarios.
5. Haga clic en Guardar para confirmar los cambios.
6. Para cancelar, haga clic en Cancelar.

### 6.4 Eliminar un Cliente

1. Seleccione el cliente en la tabla.
2. Haga clic en "Borrar".
3. El sistema verificara que el cliente no tenga reparaciones asociadas y que no tenga sucursales (excepto la sucursal por defecto sin nombre).
4. Si se cumplen las condiciones, se pedira confirmacion.
5. Se solicitara ingresar una Contraseña de Seguridad (0000).
6. Si la contraseña es correcta, el cliente sera eliminado.

> **Importante:** No se puede eliminar un cliente que tenga reparaciones o sucursales asociadas. Esto protege la integridad de los datos historicos.

### 6.5 Gestionar Sucursales

Cada cliente puede tener multiples sucursales:

**Visualizar Sucursales:**
1. Seleccione un cliente en la tabla.
2. Haga clic en "Visualizar Sucursales".
3. Se abre la ventana de Sucursales con la lista de sucursales del cliente.

**Agregar Sucursal:**
1. Seleccione un cliente y acceda a "Generar Sucursales".
2. Haga clic en "Agregar".
3. Ingrese el nombre de la sucursal, direccion, telefono, contacto y correos.
4. Haga clic en "Guardar".

**Editar Sucursal:**
1. Seleccione la sucursal en la tabla.
2. Haga clic en "Editar".
3. Modifique los campos necesarios.
4. Haga clic en "Guardar".

**Eliminar Sucursal:**
1. Seleccione la sucursal.
2. Haga clic en Borrar.
3. El sistema verificara que la sucursal no tenga reparaciones asociadas.
4. Se solicitara la contraseña de seguridad (0000).

---

## 7. Modulo de Salidas y Remitos

Acceda haciendo clic en SALIDAS desde la pantalla principal. Este modulo permite gestionar los remitos de salida de equipos reparados.

### 7.1 Opciones del Modulo

| Boton | Funcion |
|---|---|
| Generar Remito | Crear un nuevo remito de salida |
| Marcar Enviados | Marcar equipos como enviados dentro de un remito |
| Desvincular Remito | Anular o eliminar un remito existente |

### 7.2 Generar un Remito de Salida

1. Haga clic en Generar Remito.
2. Seleccionar Cliente: Elija el cliente de la lista desplegable (con autocompletado). Seleccione la sucursal correspondiente. Haga clic en Aceptar.
3. Ventana de Remito: Se cargara la tabla con todos los equipos del cliente/sucursal sin remito asignado. Seleccione la Ubicacion, ingrese el Numero de Remito y la Cantidad de Bultos. Marque con el checkbox los equipos a incluir.
4. Haga clic en Visualizar Remito para generar una vista previa en PDF.
5. Haga clic en Guardar Remito para registrar el remito en la base de datos.

> **Nota:** Al guardar el remito, los equipos seleccionados quedan vinculados al remito y su estado fisico se actualiza automaticamente.

### 7.3 Marcar Equipos como Enviados

1. Haga clic en Marcar Enviados.
2. Seleccione la Ubicacion del remito.
3. Seleccione el Numero de Remito de la lista.
4. Se mostrara la tabla con los equipos del remito seleccionado.
5. Marque los equipos que ya fueron enviados (checkbox). Puede usar Marcar Todos para seleccionar todos.
6. Haga clic en Guardar para registrar el estado de envio.

### 7.4 Desvincular / Anular / Eliminar un Remito

1. Haga clic en Desvincular Remito.
2. Seleccione la ubicacion y el numero de remito.
3. Se mostraran los equipos asociados al remito.
4. Elija una accion:

| Accion | Descripcion |
|---|---|
| Anular | Los equipos se desvinculan del remito, pero el remito sigue existiendo. El numero NO podra reutilizarse. |
| Eliminar | El remito se elimina completamente. El numero PODRA ser reutilizado. |

5. Confirme la operacion.

---

## 8. Modulo de Busquedas

El sistema ofrece dos tipos de busqueda, accesibles desde diferentes lugares.

### 8.1 Busqueda por Componentes (Menu Principal)

Acceda haciendo clic en BUSCAR desde la pantalla principal. Permite buscar equipos por componentes de repuesto.

1. Seleccione el tipo de busqueda:
   - **Componente Original:** Busca equipos donde se reemplazo un componente especifico.
   - **Componente de Reemplazo:** Busca equipos donde se utilizo un componente especifico como reemplazo.
2. Seleccione el componente de la lista desplegable (con autocompletado).
3. Haga clic en Buscar.
4. Se mostrara una tabla de resultados con los equipos encontrados, incluyendo ELS, Fecha, Cliente, Sucursal, Equipo, Marca, Modelo y Componentes.
5. Puede hacer clic en la columna ELS para abrir directamente la ventana de visualizacion del equipo seleccionado.

### 8.2 Busqueda por Campos (Desde Visualizacion)

Desde la ventana de Visualizacion de Equipos, haga clic en el boton Buscar (binoculares):

1. Seleccione el campo donde buscar: Falla, Diagnostico o Informe Cliente.
2. Ingrese el texto a buscar.
3. Haga clic en Buscar.
4. Los resultados se muestran como enlaces clicables (numeros ELS en azul y subrayado).
5. Al hacer clic en un numero ELS, se carga directamente el equipo correspondiente en la ventana de visualizacion.

---

## 9. Modulo de Presupuestos

Acceda haciendo clic en PRESUPUESTOS desde la pantalla principal. Tambien puede presupuestar directamente desde la ventana de visualizacion de equipos.

### 9.1 Opciones del Modulo

| Boton | Funcion |
|---|---|
| Presupuesto por ELS | Generar presupuesto para un ELS especifico |
| Ingresar Pago | Registrar un pago recibido |
| Marcar Aceptaciones | Marcar masivamente las aceptaciones/rechazos de presupuestos |

### 9.2 Generar un Presupuesto

1. Haga clic en Presupuesto por ELS (o Presupuestar desde la ventana de visualizacion).
2. Si accede desde el modulo de Presupuestos, seleccione el ELS de la lista desplegable.
3. Haga clic en Aceptar.
4. Se abrira la ventana de generacion de presupuesto con los datos del equipo cargados.

#### Ventana de Generacion de Presupuesto

La ventana incluye: datos del equipo (ELS, cliente, equipo, marca, modelo), campo de informe al cliente con corrector ortografico, precio en pesos ($) y precio en dolares (U$$), y boton de cotizacion del dolar.

**Editar el informe y precios:**
1. Haga clic en Editar Informe. Los campos se habilitaran para edicion con fondo amarillo claro.
2. Redacte el informe tecnico.
3. Establezca los precios en pesos y/o dolares.
4. Haga clic en Guardar Cambios.

**Revision gramatical:**
1. Con los campos en modo edicion, haga clic en Corrector.
2. El sistema consultara una API de revision gramatical.
3. Se mostrara un dialogo con los errores encontrados y sugerencias de correccion.

**Consultar cotizacion del dolar:**
1. Haga clic en Cotizacion Dolar.
2. Se mostrara la cotizacion oficial y blue del dolar en tiempo real.
3. El sistema calculara automaticamente una sugerencia de precio en la otra moneda.

**Generar PDF del presupuesto:**
1. Haga clic en Visualizar Presupuesto PDF para ver una vista previa.
2. Haga clic en Guardar Presupuesto PDF para generar el archivo.
3. El sistema le preguntara si desea enviar el presupuesto por correo electronico.

**Generar Informe Word (Siemens):**
1. Haga clic en Generar Informe Siemens.
2. Se generara un documento Word basado en una plantilla, reemplazando los marcadores con los datos del equipo.
3. El documento se guardara en la ruta configurada.
4. Se le preguntara si desea enviarlo por correo.

**Agregar imagenes al presupuesto:**
1. Haga clic en Agregar Imagenes.
2. Seleccione hasta 6 imagenes del diagnostico.
3. Las imagenes se incluiran en el documento generado.

### 9.2.1 Historial de Precios

Desde la ventana de generacion de presupuesto, el boton **"HISTORIAL DE PRECIOS"** permite consultar los precios aplicados anteriormente a equipos similares. Esto facilita la definicion de precios consistentes basados en antecedentes.

#### Acceso

1. Desde la ventana de generacion de presupuesto, haga clic en el boton **"HISTORIAL DE PRECIOS"** ubicado en la parte inferior derecha de la ventana.
2. Se abrira la ventana **"Historial de Precios"**.

#### Ventana de Historial de Precios

La ventana se compone de tres secciones principales:

**1. Panel de Filtros**

Permite buscar reparaciones anteriores por tres criterios:

| Filtro (Radio Button) | Descripcion |
|---|---|
| **NOMBRE DE EQUIPO** | Busca por el nombre/tipo de equipo (seleccionado por defecto) |
| **MARCA** | Busca por la marca del equipo |
| **MODELO** | Busca por el modelo del equipo |

- El campo de busqueda se **pre-carga automaticamente** con el dato correspondiente del equipo que se esta presupuestando. Por ejemplo, si el radio button "NOMBRE DE EQUIPO" esta seleccionado, el campo mostrara el nombre del equipo actual.
- Al cambiar de radio button, el campo de busqueda se actualiza automaticamente con el dato de contexto correspondiente (nombre, marca o modelo del equipo en presupuesto).
- Haga clic en **BUSCAR** para ejecutar la busqueda.
- Haga clic en **LIMPIAR** para restablecer los filtros y la tabla a su estado inicial (el campo de busqueda vuelve al valor de contexto del radio button activo).

**2. Tabla de Resultados**

Muestra los resultados de la busqueda con las siguientes columnas:

| Columna | Descripcion |
|---|---|
| ELS | Numero de ELS de la reparacion anterior |
| EQUIPO | Nombre del equipo |
| MARCA | Marca del equipo |
| MODELO | Modelo del equipo |
| FECHA DIAGNOSTICO | Fecha en que se realizo el diagnostico (dd/MM/yyyy) |
| PRECIO $ | Precio en pesos argentinos aplicado |
| PRECIO U$S | Precio en dolares estadounidenses aplicado |

Al hacer clic en una fila de la tabla, los datos se cargan en el panel de detalle inferior.

**3. Panel de Detalle del Registro Seleccionado**

Muestra en campos de solo lectura los datos completos del registro seleccionado en la tabla:
- ELS
- Marca
- Equipo
- Fecha de Diagnostico
- Modelo
- Precio en Pesos
- Precio en Dolares

#### Usar Precios del Historial

1. Seleccione un registro de la tabla haciendo clic sobre la fila deseada.
2. Verifique los datos en el panel de detalle.
3. Haga clic en el boton **"USAR ESTOS PRECIOS"**.
4. El sistema pedira confirmacion mostrando los precios que se van a aplicar:
   *"¿Deseas usar estos precios en el presupuesto actual? Precio en PESOS: [monto] / Precio en DOLARES: [monto]"*
5. Si confirma con **SI**, los precios se copian automaticamente a los campos de precio de la ventana de presupuesto y la ventana de historial se cierra.
6. Si selecciona **NO**, la ventana permanece abierta para continuar buscando.

> **Nota:** Si intenta usar precios sin haber seleccionado un registro, el sistema mostrara el mensaje: *"Primero selecciona un registro de la tabla."*

### 9.3 Ingresar un Pago

1. Desde la ventana de Presupuestos, haga clic en "Ingresar Pago".
2. Seleccione el ELS.
3. Se mostrara la ventana con el precio en pesos y dolares del presupuesto y el campo para ingresar el monto del pago.
4. Si ya hay un pago registrado, el campo aparecera como no editable.
5. Para modificar un pago existente, haga clic en Editar Precios.
6. Ingrese el monto y haga clic en Guardar Cambios.

> **Nota:** Los montos se formatean automaticamente con el simbolo de moneda ($ para pesos, U$$ para dolares).

### 9.4 Marcar Aceptaciones

1. Desde la ventana de Presupuestos, haga clic en "Marcar Aceptaciones".
2. Se muestra una tabla con todos los presupuestos pendientes de respuesta.
3. La tabla incluye filtros por Aviso, Cliente, Sucursal y ELS.
4. Cada fila tiene un checkbox para marcar la aceptacion.
5. Seleccione la nueva condicion comercial (Aceptado, No Aceptado, etc.).
6. Los cambios se guardan al presionar "Guardar Cambios".

### 9.5 Enviar Presupuesto por Email

1. Al generar un presupuesto PDF o informe Word, el sistema preguntara si desea enviarlo por correo.
2. Si acepta, se abrira la ventana de email con cliente y contacto precargados, email del contacto y archivo adjunto ya configurado.
3. Puede agregar archivos adjuntos adicionales.
4. Redacte el asunto y cuerpo del mensaje.
5. Haga clic en Enviar.

---

## 10. Modulo de Listados y Estadisticas

Acceda haciendo clic en LISTADOS desde la pantalla principal. Este modulo permite ver, filtrar y analizar todas las reparaciones registradas.

### 10.1 Listado de Reparaciones

La ventana principal muestra una tabla con todas las reparaciones con las siguientes columnas:

| Columna | Descripcion |
|---|---|
| ELS | Numero de etiqueta |
| ENTRADA | Fecha de ingreso |
| CLIENTE | Nombre del cliente |
| SUCURSAL | Sucursal del cliente |
| EQUIPO | Nombre del equipo |
| MARCA | Marca del equipo |
| MODELO | Modelo |
| N° SERIE | Numero de serie |
| AVISO | Codigo de aviso |
| REVISION | Fecha de revision |
| SALIDA | Fecha de salida |
| CLIENTE/CLIENTE | Cliente final del equipo |
| ESTADO TEC | Estado tecnico |
| ESTADO COM | Estado comercial |
| ESTADO FIS | Estado fisico |
| TECNICO | Tecnico asignado |
| UBIC. REM | Ubicacion del remito |
| NUM REM | Numero de remito |
| PRESUP. GEN | Presupuesto generado (Si/No) |
| PRESUP. ENV | Presupuesto enviado a cliente (Si/No) |
| PRECIO $ | Precio en pesos |
| PRECIO U$$ | Precio en dolares |
| PAGO | Monto pagado |
| INGRESO | Lugar de ingreso (CABA, MDP, BRC) |

> **Nota:** Las columnas de precios (PRECIO $, PRECIO U$$, PAGO) y el boton de estadisticas se ocultan si el usuario no tiene permiso de "Presupuestos".

La tabla cuenta con autofiltros en cada columna. Puede utilizar los checkboxes del boton OCULTAR COLUMNAS para mostrar/ocultar columnas especificas. Haciendo clic en el numero ELS de cualquier fila se abre la ventana de visualizacion del equipo correspondiente. Puede abrir multiples ventanas de visualizacion simultaneamente.

### 10.2 Estadisticas

1. Haga clic en Estadisticas desde la ventana de listados.
2. Se abrira la ventana de estadisticas.

Para acceder a los detalles de facturacion se requiere un codigo de seguridad:
1. Haga clic en Configuracion.
2. Ingrese el codigo de seguridad (0000).
3. Se habilitaran los datos detallados.

**Estadisticas por Año:**
Permite ver metricas anuales: total de ingresos por año, total de diagnosticos por año, facturacion total en pesos y en dolares por año.
- Sin detalle: solo graficos basicos.
- Con detalle: Se habilita un nuevo grafico de barras "FACTURACION" debajo y el boton "FACTURACION POR CLIENTE".

**Estadisticas por Tecnico:**
Permite ver el rendimiento de cada tecnico: cantidad de diagnosticos, reparaciones aceptadas, facturacion en pesos y dolares, reparados, en reparacion, sin falla, sin reparacion, equipos en garantia, ventas, y porcentajes de diagnostico y reparacion.
- Sin detalle: datos basicos.
- Con detalle: Se habilitan datos de facturacion a la derecha, un nuevo grafico de barras "FACTURACION" debajo y el boton "RESUMEN MENSUAL".

**Resumen Mensual por Tecnico:**
1. Desde la ventana de Estadisticas, haga clic en "Resumen Mensual Tecnico".
2. Seleccione el mes en el combo desplegable.
3. Se muestran revisados del mes, aceptados, facturacion en pesos y dolares, reparados, en reparacion, sin falla, sin reparacion, garantias, ventas, aceptados, no aceptados y en espera.

**Calculo de Comisiones:**
1. Ingrese el porcentaje de comision en el campo correspondiente.
2. Haga clic en "Calcular Comisiones".
3. El sistema calcula automaticamente el total de comisiones en pesos.

Haga clic en "Mostrar Resumen" para generar un reporte PDF del resumen tecnico mensual.

**Estadisticas por Cliente:**
Permite ver metricas por cliente: total de ingresos, equipos reparados, aceptaciones y rechazos, porcentajes de reparacion y facturacion.

**Facturacion por Cliente:**
1. Desde la ventana de Estadisticas, haga clic en "Facturacion por Cliente".
2. Se muestra una tabla con nombre del cliente, cantidad de equipos, facturacion en pesos y dolares.
3. Incluye un grafico de torta con la distribucion porcentual de facturacion.

---

## 11. Modulo de Usuarios

Acceda haciendo clic en USUARIOS desde la pantalla principal. Este modulo permite administrar usuarios y sus roles.

### 11.1 Gestion de Usuarios

**Agregar Usuario:**
1. Desde la pantalla principal, haga clic en "USUARIOS".
2. Haga clic en "Agregar Usuario".
3. Complete los campos: Nombre, Apellido, DNI, Telefono, Email, Login, Contraseña y Rol.
4. Haga clic en "Guardar Nuevo".

> **Nota:** Todos los campos son obligatorios. El DNI y Login no pueden estar repetidos. El email debe tener formato valido.

**Editar Usuario:**
1. Seleccione el usuario en la tabla.
2. Haga clic en "Editar Usuario".
3. Los campos se habilitan para edicion.
4. Realice las modificaciones.
5. Haga clic en "Guardar Edicion" o "Cancelar Edicion".

> **Nota:** No se puede editar al usuario "Administrador Programador".

**Eliminar Usuario:**
1. Seleccione el usuario en la tabla.
2. Haga clic en "Eliminar Usuario".
3. Se pedira confirmacion y una Contraseña de Seguridad (0000).
4. Si el usuario tiene reparaciones asociadas, en la base de datos se respetara su nombre, pero no podra elegirse para proximas reparaciones.

> **Nota:** No se puede eliminar al "Administrador Programador".

### Roles y Permisos

1. En la ventana de Usuarios, haga clic en "Permisos por Rol".
2. Se muestra la ventana de Permisos con la lista de pantallas/modulos del sistema y checkboxes para habilitar/deshabilitar cada permiso.
3. Los permisos se gestionan a nivel de rol (no de usuario individual).

Modulos con permisos configurables: Equipos, Busquedas, Clientes, Listados, Presupuestos, Salidas, Usuarios, BackUp y Configuracion.

> **Nota:** Si un usuario no tiene permiso de Presupuestos: en la ventana de visualizacion de equipos el panel de presupuesto se oculta, y en el listado de reparaciones las columnas de precios y pagos se ocultan.

---

## 12. Modulo de Backup

Acceda haciendo clic en BACKUP desde la pantalla principal. Permite crear respaldos de la base de datos y restaurarlos.

### Tipos de Backup

| Tipo | Descripcion |
|---|---|
| Local | Genera un archivo .sql en el disco local |
| Remoto | Envia el backup a un servidor en la nube (Clever Cloud) |

**Generar un Backup Local:**
1. Desde la pantalla principal, haga clic en "BACKUP".
2. Seleccione la opcion "Local".
3. Haga clic en "Generar Backup".
4. Se abre la ventana de Opciones de Backup: el nombre del archivo se genera automaticamente con formato "Backup Reparsoft DD-MM-AAAA.sql" y se sugiere una ruta por defecto segun la ubicacion.
5. Puede modificar el nombre y la ruta haciendo clic en "Cambiar Nombre".
6. Haga clic en "Guardar Local" para generar el backup.
7. Se muestra una barra de progreso durante el proceso.

**Generar un Backup Remoto:**
1. Seleccione la opcion "Remoto".
2. Haga clic en "Generar Backup".
3. El sistema pedira confirmacion: "Se sobrescribira el archivo remoto anterior".
4. Haga clic en "Si" para continuar.
5. El proceso se ejecuta en segundo plano: creacion del dump local, lectura de sentencias SQL, conexion al servidor remoto, limpieza de la base remota, ejecucion de sentencias y finalizacion.

> **Importante:** El backup remoto requiere conexion a internet estable. El proceso puede demorar varios minutos dependiendo del tamaño de la base de datos.

**Importar / Restaurar Backup:**

*Importar desde Local:*
1. Seleccione la opcion "Local".
2. Haga clic en "Importar Backup".
3. Seleccione el archivo .sql de backup.
4. El sistema restaurara la base de datos con los datos del archivo.

*Importar desde Remoto:*
1. Seleccione la opcion "Remoto".
2. Haga clic en "Importar Backup".
3. El sistema pedira confirmacion: "Se sobrescribira la base de datos local".
4. Se descargaran los datos del servidor remoto a la base local.

> **Importante:** La importacion sobrescribe TODOS los datos actuales. Asegurese de tener un backup actual antes de importar.

---

## 13. Modulo de Configuracion

El modulo de configuracion ofrece cuatro funcionalidades principales, accesibles mediante botones en la ventana de configuracion:

| Boton | Funcion |
|---|---|
| CAMBIAR UBICACION DEL SISTEMA | Permite cambiar entre sedes (Buenos Aires / Bariloche) |
| EQUIPOS ANTIGUOS | Conecta a la base de datos historica/antigua de la sede actual |
| EQUIPOS ACTUALES | Vuelve a la base de datos principal/actual de la sede |
| MIGRACION | Herramienta de migracion Access -> MySQL (solo administrador) |

### 13.1 Cambiar Ubicacion del Sistema

1. Desde la pantalla principal, haga clic en "CONFIGURACION".
2. Se muestra la ventana de Configuracion.
3. Haga clic en "CAMBIAR UBICACION DEL SISTEMA".
4. Se pedira confirmacion para cambiar la ubicacion.
5. Si acepta, se cierra la sesion actual y se vuelve a la ventana de seleccion de ubicacion de base de datos.

> **Nota:** Esta funcionalidad permite cambiar entre las sedes de Buenos Aires y Bariloche sin necesidad de reiniciar la aplicacion.

### 13.2 Bases de Datos Antiguas (BSAS y BRC)

Las bases de datos antiguas contienen los registros historicos de reparaciones realizadas antes de la implementacion del sistema actual. Existen dos bases antiguas, una por cada sede:

| Base de datos | Sede | Rango ELS | Descripcion |
|---|---|---|---|
| `ordenesbrcantiguas` | Bariloche | Desde ELS 1 | Datos historicos migrados del sistema anterior de Bariloche |
| `ordenesbsasantiguas` | Buenos Aires | Desde ELS 16550 | Datos historicos migrados del sistema anterior de Buenos Aires |

#### Acceder a Equipos Antiguos

1. Desde la pantalla principal, haga clic en **CONFIGURACION**.
2. En la ventana de Configuracion, haga clic en **"EQUIPOS ANTIGUOS"**.
3. El sistema preguntara: *"¿Desea ver los equipos antiguos?"*
4. Si confirma con **SI**, el sistema:
   - Se conecta a la base de datos antigua correspondiente a la sede actual (ej: si esta en Bariloche, se conecta a `ordenesbrcantiguas`).
   - Actualiza la etiqueta de ubicacion en la pantalla principal para mostrar el modo (ej: "BARILOCHE - ANTIGUA").
   - Cierra la ventana de Configuracion automaticamente.
5. A partir de este momento, todos los modulos trabajaran sobre la base de datos antigua.

#### Volver a Equipos Actuales

1. Desde la pantalla principal, haga clic en **CONFIGURACION**.
2. En la ventana de Configuracion, haga clic en **"EQUIPOS ACTUALES"**.
3. El sistema preguntara: *"¿Desea volver a ver los equipos actuales?"*
4. Si confirma con **SI**, el sistema:
   - Se reconecta a la base de datos actual/normal (ej: `ordenesbrc` o `ordenesbsas`).
   - Actualiza la etiqueta de ubicacion (ej: "BARILOCHE - ACTUAL").
   - Cierra la ventana de Configuracion.

#### Restricciones en Modo de Base Antigua

Al estar conectado a una base de datos antigua, se aplican las siguientes restricciones para todos los usuarios que **no sean administradores** (Rol ID distinto de 1):

**Modulos completamente bloqueados:**

Los siguientes modulos muestran el mensaje *"NO ES POSIBLE ACCEDER A ESTE MODULO CON DATOS ANTIGUOS."* al intentar abrirlos:

- **Agregar Equipos** (dentro del modulo Equipos)
- **Salidas y Remitos**
- **Clientes**
- **Presupuestos**
- **Usuarios**
- **Backup**

**Modulo de Equipos - Visualizacion en modo solo lectura:**

El modulo de **Visualizar Equipos** si esta disponible, pero funciona en modo de solo lectura. Se deshabilitan los siguientes botones:

| Boton deshabilitado | Motivo |
|---|---|
| Editar | No se pueden modificar datos historicos |
| Guardar Cambios | No se pueden guardar modificaciones |
| Aviso Equipo Listo | No se pueden enviar avisos sobre equipos antiguos |
| Respuesta al Tecnico | No aplica a equipos historicos |
| Aviso Informe | No se pueden enviar informes de equipos antiguos |
| Presupuestar | No se pueden generar presupuestos para equipos antiguos |
| Facturar | No se puede facturar equipos antiguos |
| Abrir Excel | No aplica a la base antigua |
| Generar Remito | No se pueden generar remitos para equipos antiguos |
| Enviar Correo/WSP | No se pueden enviar comunicaciones sobre equipos antiguos |
| Copiar Factura | No se pueden copiar datos de facturacion |

**Modulo de Listados:**

El modulo de Listados **si** se encuentra disponible en modo de base antigua, permitiendo la consulta y analisis de datos historicos.

**Excepcion - Usuario Administrador:**

El usuario con Rol ID 1 (Administrador Programador) tiene acceso completo a todos los modulos incluso en modo de base antigua. Esto permite la gestion y mantenimiento de los datos historicos cuando sea necesario.

> **Nota:** La etiqueta de ubicacion en la pantalla principal siempre indica el modo actual. Verifique que muestre "ACTUAL" antes de realizar operaciones de ingreso, presupuestacion o facturacion.

### 13.3 Herramienta de Migracion

El boton **MIGRACION** abre la herramienta de migracion de datos desde bases Access hacia MySQL. Esta herramienta permite:

- Seleccionar una base de datos Access de origen (staging).
- Elegir la base de datos MySQL de destino entre las cuatro disponibles:
  - Datos de Bariloche Antiguos (`ordenesbrcantiguas`)
  - Datos de Buenos Aires Antiguos (`ordenesbsasantiguas`)
  - Datos Bariloche Actuales (`ordenesbrc`)
  - Datos Buenos Aires Actuales (`ordenesbsas`)
- Ejecutar la migracion de datos preservando la integridad referencial.

> **Nota:** Esta herramienta es de uso avanzado y esta destinada unicamente al administrador del sistema. El boton solo es visible para usuarios con permisos de Configuracion.

---

## 14. Comunicaciones (Email y WhatsApp)

### 14.1 Correo Electronico

El sistema permite enviar correos electronicos en diferentes contextos:

| Tipo | Descripcion | Contexto |
|---|---|---|
| Presupuesto PDF | Envio del presupuesto generado en PDF | Desde ventana de presupuestos |
| Informe Word | Envio del informe tecnico en formato Word | Desde ventana de presupuestos |
| Aviso de Informe | Notificacion de que el informe tecnico esta listo | Desde visualizacion de equipos |
| Equipo Terminado | Notificacion de que el equipo esta terminado | Desde visualizacion de equipos |
| Respuesta al tecnico | Envio de la respuesta/decision del cliente al tecnico asignado | Desde visualizacion de equipos |

La ventana de email muestra: Cliente y sucursal precargados, nombre y email del contacto, archivo adjunto (si aplica), posibilidad de agregar archivos adicionales, asunto y cuerpo del mensaje con la firma de la empresa. El envio se realiza en segundo plano para no bloquear la interfaz.

### 14.2 WhatsApp

El sistema integra envio de mensajes por WhatsApp:

**Enviar un mensaje por WhatsApp:**
1. Desde la ventana de visualizacion de equipos, haga clic en el boton WSP.
2. Se abrira la ventana de WhatsApp con los datos del contacto del cliente actual precargados.
3. Seleccione o ingrese el numero de destino.
4. Haga clic en Utilizar Contacto para usar el numero seleccionado.
5. Redacte el mensaje.
6. Haga clic en Enviar.

**Gestion de Contactos WhatsApp:**
1. En la ventana de WhatsApp, haga clic en Clientes.
2. Se abrira la ventana de gestion de contactos WSP.

*Agregar contacto:*
1. Haga clic en Agregar.
2. Complete: Nombre, Organizacion, Telefono.
3. Haga clic en Guardar.

*Editar contacto:*
1. Seleccione un contacto en la tabla.
2. Haga clic en Editar, modifique los datos y haga clic en Guardar.

*Eliminar contacto:*
1. Seleccione un contacto, haga clic en Eliminar y confirme la eliminacion.

> **Nota:** La base de datos de contactos WhatsApp es independiente de la base de datos de clientes del sistema.

---

## 15. Archivos Excel

El sistema permite acceder directamente a archivos Excel de gestion administrativa. Esta funcionalidad esta disponible desde la ventana de visualizacion de equipos.

### 15.1 Archivos Disponibles

| Archivo | Descripcion |
|---|---|
| ReparBRC_Mysql / ReparBSAS_Mysql | Planilla de reparaciones (segun ubicacion) |
| Caja BRC | Planilla de caja (tiene vinculos al archivo de reparaciones) |
| Detalle Gastos [Año] | Planilla de detalle de gastos del año seleccionado |

### 15.2 Abrir Archivos Individuales

- **ReparBRC_Mysql / ReparBSAS_Mysql:** Se abrira el archivo Excel correspondiente a la ubicacion actual.
- **Caja:** El sistema le ofrecera tres opciones: Actualizar primero (abre primero el archivo de Reparaciones, recomendado), Abrir directamente o Cancelar.
- **Detalle de Gastos:** Haga clic en Detalle Gastos. Se abrira el archivo del año actual.

### 15.3 Abrir Todos los Archivos

1. Haga clic en Abrir Todos.
2. El sistema le guiara paso a paso:
   - Paso 1: Abre el archivo de Reparaciones. Ingrese la contraseña cuando se solicite.
   - Paso 2: Abre el archivo de Caja. Ingrese la contraseña y actualice los vinculos.
   - Paso 3: Seleccione el año para el archivo de Detalle de Gastos.
3. Cada paso espera su confirmacion antes de avanzar al siguiente.

### 15.4 Rutas de Archivos

| Ubicacion | Ruta Base |
|---|---|
| Bariloche | `F:\els\Bariloche\Administracion\Sistema\Excels\` |
| Buenos Aires | `F:\els\Administracion\Sistema\Excels\` |

---

## 16. Codigos de Color y Estados

### 16.1 Colores de Estado de Presupuesto (Ventana de Visualizacion)

El fondo de la ventana de visualizacion cambia de color segun el estado del presupuesto y pago:

| Color | Estado | Significado |
|---|---|---|
| Verde claro | PAGADO | El equipo esta completamente pagado |
| Gris claro | SIN PRESUPUESTAR | No se ha generado presupuesto |
| Amarillo claro | PARCIAL | Se realizo un pago parcial |
| Rosa | FALTA PAGO | Presupuesto aceptado pero sin pago |
| Violeta claro | NO ACEPTADO | El presupuesto fue rechazado por el cliente |
| Celeste | ESPERANDO | Se espera la respuesta del cliente |
| Durazno | SIN REPARACION | El equipo no tiene reparacion posible |

---

## 17. Preguntas Frecuentes (FAQ)

**P: ¿Como cambio de sede (Bariloche/Buenos Aires)?**  
R: Si ya esta logueado, vaya a Configuracion > Ubicacion del Sistema. Se cerrara la sesion y podra seleccionar la nueva ubicacion. Si no esta logueado, antes de iniciar sesion se pregunta la ubicacion.

**P: ¿Que hago si olvide mi contraseña?**  
R: Contacte al administrador del sistema. Un usuario con rol de administrador puede modificar su contraseña desde el modulo de Usuarios.

**P: ¿Puedo tener el sistema abierto en dos ubicaciones simultaneamente?**  
R: Si, puede tener una instancia conectada a Bariloche y otra a Buenos Aires, ya que utilizan bases de datos diferentes.

**P: ¿Por que no veo las columnas de precios en el listado?**  
R: Su rol de usuario no tiene asignado el permiso de "Presupuestos". Contacte al administrador para que le otorgue el permiso si corresponde.

**P: ¿Puedo modificar un remito ya generado?**  
R: No directamente. Debe anularlo o eliminarlo desde el modulo de Salidas y generar uno nuevo. Al anular, el numero de remito queda reservado; al eliminar, el numero queda disponible para uso futuro.

**P: ¿Como funciona la verificacion de garantia?**  
R: Al usar "Verificar Ingreso Anterior", el sistema calcula los dias desde el ultimo ingreso: menos de 30 dias no se debe re-ingresar; entre 31 y 90 dias esta en periodo de garantia; mas de 90 dias esta fuera del periodo de garantia.

**P: ¿Puedo abrir multiples equipos a la vez desde el listado?**  
R: Si. Al hacer clic en un ELS desde el listado, se abre una ventana de visualizacion independiente. Puede abrir varias simultaneamente.

**P: La cotizacion del dolar no se muestra.**  
R: Se requiere conexion a internet para consultar la API de cotizacion. Si no hay conexion, el sistema mostrara un mensaje informativo.

**P: No puedo enviar correos electronicos.**  
R: Verifique su conexion a internet y que el servidor SMTP este accesible. Si el problema persiste, contacte al administrador.

**P: No puedo acceder a ciertos modulos. Los botones aparecen deshabilitados.**  
R: Su usuario tiene permisos restringidos o el sistema esta en modo de Base Antigua. Contacte al administrador del sistema para solicitar los permisos necesarios. Si esta en modo de Base Antigua, vuelva a la base actual desde Configuracion > Equipos Actuales.

**P: No puedo eliminar un cliente.**  
R: Verifique que el cliente no tenga reparaciones ni sucursales asociadas. Solo se pueden eliminar clientes sin datos vinculados.

**P: ¿Como accedo a los equipos historicos/antiguos?**  
R: Vaya a Configuracion > Equipos Antiguos. El sistema se conectara a la base de datos historica de la sede actual. Recuerde que en este modo la mayoria de las funciones estan en modo de solo lectura.

**P: ¿Para que sirve el boton "Historial de Precios" en la ventana de presupuesto?**  
R: Permite consultar los precios que se aplicaron a equipos similares en reparaciones anteriores. Puede buscar por nombre de equipo, marca o modelo, y aplicar directamente esos precios al presupuesto actual.

**P: ¿Que es el campo "N° Factura" en la ventana de visualizacion?**  
R: Es el campo donde se registra el numero de factura electronica asociado al equipo, con formato punto de venta - numero (XXXXX-XXXXXXXX). Se completa al facturar el equipo.

**P: ¿Como funciona "Copiar Pago"?**  
R: El boton "Copiar Pago" copia automaticamente el monto del presupuesto al campo de pago, util cuando el cliente paga el monto completo del presupuesto. Si necesita revertirlo, el boton cambia a "Limpiar Pago".

---

## 18. Glosario

| Termino | Definicion |
|---|---|
| ELS | Numero secuencial unico que identifica cada reparacion. |
| Remito | Documento que acompaña la salida/envio de uno o mas equipos reparados al cliente. |
| Estado Tecnico | Indica la etapa tecnica de la reparacion (Sin Revisar, En Reparacion, Reparado, Sin Reparacion, etc.). |
| Estado Comercial | Indica la etapa comercial del presupuesto (Sin Presupuestar, Presupuesto Enviado, Aceptado, No Aceptado, Esperando). |
| Estado Fisico | Ubicacion fisica del equipo (CABA, MDP, BRC, Enviado, Desguace). |
| Presupuesto | Estimacion del costo de reparacion que se presenta al cliente para su aceptacion. |
| Aviso | Codigo o numero de referencia que el cliente utiliza internamente para identificar la reparacion. |
| Sucursal | Division o sede de un cliente. Cada cliente puede tener multiples sucursales. |
| Clever Cloud | Servicio de hosting en la nube utilizado para almacenar backups remotos de la base de datos. |
| Componente Original | Componente del equipo que fue reemplazado durante la reparacion. |
| Componente de Reemplazo | Componente nuevo que fue instalado en lugar del original. |
| ARCA | Agencia de Recaudacion y Control Aduanero (ex AFIP). Pagina web donde se genera la facturacion electronica. |
| WSP | Abreviatura de WhatsApp utilizada en el sistema. |
| Cotizacion dolar | Valor del dolar oficial y blue consultado en tiempo real para conversion de precios. |
| Registro de Ingreso | Documento PDF generado al momento de ingresar un equipo, que sirve como comprobante para el cliente. |
| Autofiltro | Funcionalidad de las tablas que permite filtrar filas segun valores de columna. |
| Base Antigua | Base de datos historica que contiene registros de reparaciones anteriores al sistema actual. Se accede desde Configuracion > Equipos Antiguos. |
| Base Actual | Base de datos principal operativa donde se registran las reparaciones vigentes. |
| N° Factura | Numero de factura electronica en formato XXXXX-XXXXXXXX (punto de venta - numero de comprobante). |
| Historial de Precios | Funcionalidad que permite consultar precios aplicados en reparaciones anteriores de equipos similares. |
| Copiar Pago | Funcion que copia el monto del presupuesto al campo de pago de forma automatica. |
| Copiar Factura | Funcion que abre una ventana con los datos de facturacion del equipo para copiarlos al portapapeles y usarlos en ARCA. |

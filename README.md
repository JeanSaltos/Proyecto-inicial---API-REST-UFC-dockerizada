# API REST de Peleadores de UFC con Docker y SQL Server

Este proyecto implementa una API REST para gestionar una base de datos de peleadores de UFC. La aplicación (escrita en FastAPI) y su base de datos (SQL Server) están completamente contenerizadas usando Docker y Docker Compose para un despliegue fácil y reproducible.

## Stack Tecnológico

* **Backend:** FastAPI (Python)
* **Base de Datos:** Microsoft SQL Server
* **Contenerización:** Docker & Docker Compose
* **Inicialización de DB:** Script SQL (`init.sql`) el cual se ejecuta automáticamente al arrancar.

## Prerrequisitos

Para ejecutar este proyecto, solo necesitas tener instalado:

* [Docker](https://www.docker.com/get-started)
* [Docker Compose](https://docs.docker.com/compose/install/) 
* [Git](https://git-scm.com/downloads) 

## Configuración e Instalación

Sigue estos pasos para poner en marcha el proyecto en tu máquina local.

### 1. Clona el Repositorio

git clone [https://github.com/tu-usuario/tu-repositorio.git]
cd tu-repositorio
(Reemplaza la URL por la de tu propio repositorio)

2. Crea el Archivo de Entorno (.env)
Este proyecto usa un archivo .env para manejar las contraseñas y configuraciones de la base de datos. Por seguridad, este archivo no se sube al repositorio.

He incluido una plantilla llamada .env.example. Solo necesitas copiarla y renombrarla a .env.

En Windows (PowerShell):

Copy-Item .env.example .env

# Configuración de SQL Server
DB_HOST=db
DB_USER=sa
DB_PASSWORD=TuContraseñaMuySeguraAquí # <-- ¡CAMBIA ESTO!
DB_NAME=UFCDB
ACCEPT_EULA=Y

**Ejecución**
Una vez configurado el .env, levanta todos los servicios (API y Base de Datos) con un solo comando desde la raíz del proyecto:

docker-compose up --build -d
--build: Reconstruye tus imágenes (API y DB-Setup) si hiciste algún cambio.

-d: Ejecuta los contenedores en modo "detached" (en segundo plano).

docker-compose orquestará el arranque en el siguiente orden:

db (SQL Server): Se inicia el contenedor de la base de datos.

db-setup: Este contenedor temporal espera 30 segundos (para dar tiempo a que SQL Server arranque) y luego ejecuta el script init.sql. Este script crea la base de datos UFCDB, la tabla Fighters e inserta 5 peleadores de ejemplo.

api: El servidor de FastAPI solo arrancará después de que el contenedor db-setup haya terminado su trabajo exitosamente, asegurando que la base de datos esté lista.

**Uso de la API**
¡La API ya está en línea y lista para recibir peticiones!

Ubicación de la API: http://localhost:8000


Pruebas (Postman o Swagger)
Puedes usar la interfaz de Swagger o una herramienta como Postman para probar los endpoints:

1. Obtener todos los peleadores
Método: GET

URL: http://localhost:8000/fighters

2. Crear un nuevo peleador
Método: POST

URL: http://localhost:8000/fighters

Body (JSON):

JSON

{
  "FirstName": "Marlon",
  "LastName": "Vera",
  "Nickname": "Chito",
  "WeightClass": "Bantamweight",
  "Wins": 23,
  "Losses": 11,
  "Draws": 1
}
3. Obtener un peleador por ID
Método: GET

URL: http://localhost:8000/fighters/1

**Detener el Entorno**
Para detener y eliminar todos los contenedores asociados a este proyecto, ejecuta:
docker-compose down
Si deseas eliminar también el volumen de la base de datos (borrando todos los datos guardados permanentemente, incluyendo los peleadores que hayas creado):
docker-compose down -v

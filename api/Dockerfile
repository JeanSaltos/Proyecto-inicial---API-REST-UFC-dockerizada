# Etapa 1: Builder - Instalar dependencias de compilación para pyodbc
FROM python:3.10-slim-bullseye AS builder 

# Instalar dependencias del sistema para el driver ODBC y pyodbc
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    gnupg \
    unixodbc-dev \
    g++ \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Instalar el driver ODBC 18 de Microsoft (para Debian 11)
RUN curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
RUN curl https://packages.microsoft.com/config/debian/11/prod.list > /etc/apt/sources.list.d/mssql-release.list # <- CAMBIO 2 (de 10 a 11)

RUN apt-get update
RUN ACCEPT_EULA=Y apt-get install -y msodbcsql18

# Instalar dependencias de Python en una ubicación separada
WORKDIR /install
COPY requirements.txt .
RUN pip install --no-cache-dir --prefix="/install" -r requirements.txt

# ---------------------------------------------------------------------

# Etapa 2: Final - Crear la imagen de producción
FROM python:3.10-slim-bullseye 

# Instalar dependencias de runtime (unixodbc y el driver)
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    gnupg \
    unixodbc \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Instalar el driver ODBC 18 de Microsoft (solo runtime, para Debian 11)
RUN curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
RUN curl https://packages.microsoft.com/config/debian/11/prod.list > /etc/apt/sources.list.d/mssql-release.list # <- CAMBIO 4 (de 10 a 11)

RUN apt-get update
RUN ACCEPT_EULA=Y apt-get install -y msodbcsql18

# Copiar las dependencias de Python instaladas desde la etapa builder
COPY --from=builder /install /usr/local

# Configurar el entorno de la aplicación
WORKDIR /app
COPY . .

# Exponer el puerto
EXPOSE 8000

# Comando para ejecutar la aplicación
# Usamos 0.0.0.0 para que sea accesible desde fuera del contenedor
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
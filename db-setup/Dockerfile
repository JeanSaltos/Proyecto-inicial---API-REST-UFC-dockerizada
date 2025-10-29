# Usamos la imagen oficial de herramientas de mssql
FROM mcr.microsoft.com/mssql-tools

# Copiamos los scripts al contenedor
COPY init.sql /app/init.sql
COPY configure-db.sh /app/configure-db.sh

# Damos permisos de ejecución
RUN chmod +x /app/configure-db.sh

# Establecemos el directorio de trabajo
WORKDIR /app

# Comando por defecto que ejecuta el script
CMD ["/bin/bash", "/app/configure-db.sh"]
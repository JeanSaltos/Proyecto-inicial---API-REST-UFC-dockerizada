#!/bin/bash
echo "Iniciando script de configuración de la base de datos..."

echo "Esperando 30 segundos a que SQL Server esté listo..."
sleep 30

echo "Ejecutando script de inicialización init.sql..."

# Ejecutar el script SQL usando sqlcmd
/opt/mssql-tools/bin/sqlcmd -S $DB_HOST -U $DB_USER -P $DB_PASSWORD -d master -i /app/init.sql

if [ $? -eq 0 ]; then
    echo "Script init.sql ejecutado exitosamente."
else
    echo "Error al ejecutar init.sql."
    exit 1
fi
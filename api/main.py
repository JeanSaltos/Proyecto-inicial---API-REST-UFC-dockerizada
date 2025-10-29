import os
import pyodbc
import uvicorn
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
import time

app = FastAPI(
    title="UFC Fighters API",
    description="API REST para gestionar peleadores de UFC",
    version="1.0.0"
)

# --- Configuración de la Base de Datos ---

DB_HOST = os.getenv("DB_HOST")
DB_USER = os.getenv("DB_USER")
DB_PASSWORD = os.getenv("DB_PASSWORD")
DB_NAME = os.getenv("DB_NAME")


connection_string = (
    f"DRIVER={{ODBC Driver 18 for SQL Server}};"
    f"SERVER={DB_HOST};"
    f"DATABASE={DB_NAME};"
    f"UID={DB_USER};"
    f"PWD={DB_PASSWORD};"
    "Encrypt=no;"
    "TrustServerCertificate=yes;"
)

def get_db_connection():
    """Intenta conectarse a la base de datos con reintentos."""
    retries = 10
    delay = 5
    for i in range(retries):
        try:
            conn = pyodbc.connect(connection_string)
            print("Conexión a la base de datos exitosa.")
            return conn
        except pyodbc.Error as ex:
            print(f"Error de conexión: {ex}")
            print(f"Reintentando en {delay} segundos... (Intento {i+1}/{retries})")
            time.sleep(delay)
    raise ConnectionError("No se pudo conectar a la base de datos después de varios intentos.")

@app.on_event("startup")
def startup_event():
    """Verifica la conexión a la DB al iniciar la app."""
    try:
        conn = get_db_connection()
        conn.close()
    except Exception as e:
        print(f"Error fatal: No se pudo conectar a la DB en el arranque. {e}")


# --- Modelos Pydantic (DTOs) ---

class FighterBase(BaseModel):
    FirstName: str
    LastName: str
    Nickname: Optional[str] = None
    WeightClass: str
    Wins: int = 0
    Losses: int = 0
    Draws: int = 0

class Fighter(FighterBase):
    FighterID: int

    class Config:
        from_attributes = True 

# --- Endpoints de la API ---

@app.get("/")
def read_root():
    return {"message": "Welcome to the UFC Fighters API. Visita /docs para la documentación."}

@app.get("/fighters", response_model=List[Fighter])
def get_fighters():
    """Obtiene una lista de todos los peleadores."""
    fighters = []
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT FighterID, FirstName, LastName, Nickname, WeightClass, Wins, Losses, Draws FROM Fighters")
        
        rows = cursor.fetchall()
        
        for row in rows:
            fighters.append(dict(zip([column[0] for column in cursor.description], row)))
        
        conn.close()
        return fighters
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/fighters/{fighter_id}", response_model=Fighter)
def get_fighter(fighter_id: int):
    """Obtiene un peleador por su ID."""
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM Fighters WHERE FighterID = ?", fighter_id)
        
        row = cursor.fetchone()
        conn.close()
        
        if row:
            fighter_dict = dict(zip([column[0] for column in cursor.description], row))
            return fighter_dict
        else:
            raise HTTPException(status_code=404, detail="Fighter not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/fighters", response_model=Fighter, status_code=201)
def create_fighter(fighter: FighterBase):
    """Crea un nuevo peleador."""
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        
        query = """
        INSERT INTO Fighters (FirstName, LastName, Nickname, WeightClass, Wins, Losses, Draws)
        OUTPUT INSERTED.FighterID
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """
        cursor.execute(
            query,
            fighter.FirstName, fighter.LastName, fighter.Nickname,
            fighter.WeightClass, fighter.Wins, fighter.Losses, fighter.Draws
        )
        
        new_id = cursor.fetchone()[0]
        conn.commit()
        conn.close()
        
        return Fighter(FighterID=new_id, **fighter.model_dump())
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
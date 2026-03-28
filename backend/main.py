from fastapi import FastAPI
from datetime import datetime

app = FastAPI()

@app.get("/api/status")
def get_status():
    return {
        "status": "ok",
        "time": datetime.utcnow().isoformat()
    }

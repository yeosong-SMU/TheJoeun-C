import uvicorn
import os, io, json
from typing import List, Tuple
from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from PIL import Image
import numpy as np
from tensorflow import keras

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
                        # 현재 파일이 있는 디렉토리 경로

MODEL_PATH = os.path.join(BASE_DIR, 'simple_cats_dogs.h5')  #학습모델
CLASSES_JSON = os.path.join(BASE_DIR, 'classes.json')   #라벨

IMG_SIZE = 32

model = keras.models.load_model(MODEL_PATH, compile=False)   #모델 로딩
with open(CLASSES_JSON, 'r', encoding="utf-8") as f:
    CLASSES: List[str] = json.load(f)

app = FastAPI()

def prepare(img: Image.Image) -> np.ndarray:
    img = img.convert("RGB").resize((IMG_SIZE, IMG_SIZE))  #칼라로 바꿔라. 32x32로 바꿔라
    arr = np.array(img, dtype=np.float32) / 255.0    #픽셀 0.0~1.0
    return np.expand_dims(arr, axis=0)

def predict_top1(image_bytes: bytes) -> Tuple[str, float]:
    img = Image.open(io.BytesIO(image_bytes))   #이미지 오픈
    x = prepare(img)   #처리
    prob_dog = float(model.predict(x, verbose=0).squeeze())   #분류 0.0~1.0
    if prob_dog >= 0.5:
        return ("dog", prob_dog)
    else:
        return ("cat", 1.0 - prob_dog)

@app.post("/upload/image")   #<==스프링 이미지 업로드
async def upload_image(file: UploadFile = File(...)):
    image_bytes = await file.read()   #업로드 완료될때까지 대기
    try:
        label, prob = predict_top1(image_bytes)
    except Exception as e:
        raise HTTPException(status_code=400, detail="추론 오류: {e}")
    return JSONResponse(
        {
            "top1": {"label": label, "prob": round(prob, 6)},
            "topk": [{"label": label, "prob": round(prob, 6)}],
        }
    )

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=int(os.getenv("PORT", 8000)))
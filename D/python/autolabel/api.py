#pip install fastapi uvicorn pydantic
from fastapi import FastAPI
from pydantic import BaseModel
import joblib
import os
import uvicorn

app = FastAPI(title="고객문의 자동분류기")
#app 이 앱 이름

class Query(BaseModel):
    text: str

base_dir = os.path.dirname(os.path.abspath(__file__))
                            #현재 작업중인 디렉토리
                                            #__ 현재파일

#join  디렉토리명이랑 파일명이랑 합침
model_path = os.path.join(base_dir, "svc_label_model.pkl")
vectorizer_path = os.path.join(base_dir, "tfidf_vectorizer_label.pkl")
label_encoder_path = os.path.join(base_dir, "label_encoder.pkl")

urgency_model_path = os.path.join(base_dir, "svc_urgency_model.pkl")
urgency_le_path = os.path.join(base_dir, "urgency_label_encoder.pkl")
urgency_vectorizer_path = os.path.join(base_dir, "tfidf_vectorizer_urgency.pkl")

svc_model = joblib.load(model_path)
tfidf_vectorizer = joblib.load(vectorizer_path)
label_encoder = joblib.load(label_encoder_path)

svc_urgency = joblib.load(urgency_model_path)
urgency_le = joblib.load(urgency_le_path)
tfidf_urgency = joblib.load(urgency_vectorizer_path)

def predict_label(text: str) -> str:
    text_vec = tfidf_vectorizer.transform([text])  #텍스트 => 숫자
    pred = svc_model.predict(text_vec)   #예측
    return label_encoder.inverse_transform(pred)[0]   #레이블숫자 => 텍스트

#긴급/보통
def predict_urgency_class(text: str) -> str:
    text_vec = tfidf_vectorizer.transform([text])
    pred = svc_urgency.predict(text_vec)
    return urgency_le.inverse_transform(pred)[0]

#@api변수명
@app.post("/predict")    #<== 스프링에서 post 요청
def predict(query: Query):   #json타입으로 전달
    label = predict_label(query.text)
    urgency_class = predict_urgency_class(query.text)
    return {"text": query.text, "label": label, "urgency_class": urgency_class}  #==> 스프링으로 전달

if __name__ == "__main__":
    uvicorn.run("api:app", host="0.0.0.0", port=8000, reload=True)
                #api.py의 fastapi 앱변수명
                           #모든ip허용
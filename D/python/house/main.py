from flask import Flask, request, jsonify
from joblib import load
from flask_cors import CORS

app = Flask(__name__)  #플라스크 앱 생성
CORS(app)    #외부접속 허용

model = load("d:/data/house/price_model.joblib")

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()    #json 데이터
    rooms = data.get("rooms")   #방의 개수
    area_m2 = data.get("area_m2")   #면적

    X = [[rooms, area_m2]]   #2차원 배열
    y_pred = model.predict(X)[0]   #예측    첫번째값

    #{ }  dictionary  ==>  json    hashmap ==> json같은 것
    return jsonify({
        "predicted_price": round(float(y_pred), 2),
    })

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000, debug=True)  #모든 ip 접속 허용
from flask import Flask, jsonify, request
import joblib, pandas as pd

FEATS = ["age", "income", "spend"]

MODEL_DIR = "d:/data/customer"
MODEL_PATH = MODEL_DIR + "/kmeans.joblib"
SCALER_PATH = MODEL_DIR + "/scaler.joblib"

app = Flask(__name__)

kmeans = joblib.load(MODEL_PATH)
scaler = joblib.load(SCALER_PATH)

@app.post("/predict")
def predict():
    payload = request.get_json(force=True)
    rows = payload if isinstance(payload, list) else [payload]
    
    X = pd.DataFrame(rows)[FEATS].values
    Xs = scaler.transform(X)
    labels = kmeans.predict(Xs).tolist()
    return jsonify({"clusters": labels})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
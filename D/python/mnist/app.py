from flask import Flask, request, jsonify
from flask_cors import CORS
import io
import numpy as np
from PIL import Image, ImageOps
import tensorflow as tf

app = Flask(__name__)
CORS(app)

model = tf.keras.models.load_model('d:/data/mnist/mnist_cnn.keras')

def preprocess(pil_img: Image.Image) -> np.ndarray:
    img = pil_img.convert("L")
    # (28, 28)로 리사이즈, 픽셀값 자연스럽게 보정
    img = ImageOps.fit(img, (28, 28), method=Image.Resampling.BILINEAR)
    arr = np.array(img).astype("float32")
    # 검은 배경에 흰 숫자 => 반전
    if arr.mean() > 127:
        arr = 255 - arr
    arr = arr / 255.0
    arr = arr[..., None]  #(28, 28, 1)
    return arr

@app.route("/predict", methods=["POST"])
def predict() :
    if "file" not in request.files:
        return jsonify({"error": "file not provided"}), 400
    f = request.files["file"]
    if f.filename == "":
        return jsonify({"error" : "empty filename"}), 400
    try:
        img = Image.open(io.BytesIO(f.read()))
        x = np.expand_dims(preprocess(img), axis=0)  #(1, 28, 28, 1)
        probs = model.predict(x, verbose=0)[0].astype(float)  #(10,)
        pred = int(np.argmax(probs))
        top3 = sorted([(i, float(p)) for i, p in enumerate(probs)],
                    key=lambda t: t[1], reverse=True)[:3]
        return jsonify({
            "prediction": pred,
            "probs": {str(i): float(p) for i, p in enumerate(probs)},
            "top3": [{"digit": int(i), "prob": float(p)} for i, p in top3],
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.get("/")
def health():
    return jsonify({"ok": True})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
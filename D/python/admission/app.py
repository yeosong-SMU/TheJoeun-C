from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import pandas as pd

app = Flask(__name__)
CORS(app)

model = joblib.load('d:/data/admission/knn_model.pkl')
major_encoder = joblib.load('d:/data/admission/major_encoder.pkl')

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()

    try: 
        kor = int(data['kor'])
        eng = int(data['eng'])
        math = int(data['math'])
        wish_major = data['wish_major']

        major_encoded = major_encoder.transform([wish_major])[0]
        input_data = pd.DataFrame([[kor, eng, math, major_encoded]], 
        columns=['kor', 'eng', 'math', 'major_encoded'])
        prediction = model.predict(input_data)[0]
        probability = model.predict_proba(input_data)[0][1]

        result = '합격' if prediction == 1 else '불합격'
        return jsonify({'result' : result, 'probability': round(probability, 4)})
    
    except Exception as e:
        return jsonify({'error' : str(e)}), 400

if __name__ == '__main__' :
    app.run(debug=True)
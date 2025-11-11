from flask import Flask, request, jsonify
import joblib
import numpy as np
from flask_cors import CORS  #import CORS

app = Flask(__name__)
CORS(app)   #외부 서비스 개방
model = joblib.load('d:/data/insurance/insurance_model.joblib')

@app.route('/predict', methods=['POST'])
def predict() :
    data = request.get_json()

    features = np.array([[float(data['age']), 
        int(data['gender']), 
        float(data['driving_experience']), 
        int(data['accident_history']), 
        int(data['vehicle_type']), 
        float(data['annual_mileage']), 
        int(data['vehicle_age']), 
        int(data['location']), 
        float(data['credit_score']), 
        float(data['vehicle_value'])
    ]])

    prediction = model.predict(features)

    return jsonify({'predicted_insurance': prediction[0]})

if __name__ == '__main__' :
    app.run(debug=True, host='0.0.0.0', port=5000)
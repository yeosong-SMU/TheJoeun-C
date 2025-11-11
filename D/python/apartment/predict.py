import sys
from joblib import load
import warnings

warnings.filterwarnings("ignore")

area = float(sys.argv[1])
floor = int(sys.argv[2])
rooms = int(sys.argv[3])
built_year = int(sys.argv[4])

model = load('d:/data/apartment/model.joblib')
pred = model.predict([[area, floor, rooms, built_year]])[0]
print(int(pred))
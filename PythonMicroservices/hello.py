from flask import Flask
import identification as I
from PythonMicroservices.k


app = Flask(__name__)
#yol = y.YOLO()

@app.route('/')
def hello_world():
    I.detect_imagee("G:\\bring.jpg")
    return 'Hello World!'

if __name__ == '__main__':
    app.run(threaded=True)
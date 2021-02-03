import flask
from flask import request
from datetime import datetime as dt
import pyrebase 
import numpy as np
import tensorflow as tf
import urllib.request
from tensorflow import keras
from tensorflow.keras import layers


#firebase configuration

config = {
  "apiKey": "AIzaSyAs0wv1a3qpNJzr4Uym8b3vlC-oEX2e0ak",
  "authDomain": "asean-hackathon.firebaseapp.com",
  "databaseURL": "https://asean-hackathon-default-rtdb.firebaseio.com",
  "projectId": "asean-hackathon",
  "storageBucket": "asean-hackathon.appspot.com",
  "messagingSenderId": "799205397057",
  "appId": "1:799205397057:web:ff97a084c97e43e5f52cc0",
  "measurementId": "G-S5Z2DRHCQ0"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()
app = flask.Flask(__name__)

#call for prediction class

def predictclass(imgURL):
    #loading model
    model = keras.models.load_model("./model/model.h5")
    img_height = 180
    img_width = 180
    imgURL = imgURL
    urllib.request.urlretrieve(imgURL, "filename.jpg")
    img_path = ''
    img = keras.preprocessing.image.load_img(
    img_path, target_size=(img_height, img_width)
    )
    img_array = keras.preprocessing.image.img_to_array(img)
    img_array = tf.expand_dims(img_array, 0) # Create a batch


    predictions = model.predict(img_array)
    score = tf.nn.softmax(predictions[0])
    class_names = ['clean_beach', 'macro', 'mega', 'meso']
    print(
    "This image most likely belongs to {} with a {:.2f} percent confidence."
    .format(class_names[np.argmax(score)], 100 * np.max(score))
    )
    return class_names[np.argmax(score)]

def predict(id):
    try:
        user=db.child("report").child("zVyqLkmCX5WFIoB5bkEb2Zt5hjE2").get()
        print("id")
        print(id)
        user =user.val()
        print("-------------------start----------------------")
        # print(user.val())
        for key, value in user.items(): 
            print("---------------------------------------------")
            print(key, value) 
            print("---------------------")
            print(value['idReport'],id)
            print("---------------------")
            if (value['idReport']==id):
                db.child("report").child("zVyqLkmCX5WFIoB5bkEb2Zt5hjE2").child(key).update({"class":"meso"})
                print(value['image'])
                print("--link--")
                predictclass(value['image'])
                print("updated")
        # print("invalid firebase value")
    except:
        print("invalid error")



@app.route('/', methods=['GET'])
def home():
    id = request.args['id']
    predict(id)
    try:
        return "success"
    except:
        return "failed"

app.run(debug=True)
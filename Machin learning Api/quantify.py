from flask import request,Flask
from datetime import datetime as dt
import pyrebase 
import numpy as np
import cv2
import tensorflow as tf
import urllib.request
from tensorflow import keras
from tensorflow.keras import layers
from flask_restful import Resource, Api, reqparse
import threading
import time


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

app = Flask(__name__)
api = Api(app)



global a

a = []
parser = reqparse.RequestParser()



def ImageKnnSegementation(coordinates):
     img=cv2.imread("C:\\Users\\vpras\\Downloads\\MonarchAPI-master\\MonarchAPI-master\\van Model\\2.jpeg")

     img_flat=img.reshape((-1,3))

     img_flat.shape,img.shape

     img_flat = np.float32(img_flat)
     criteria= (cv2.TERM_CRITERIA_EPS +cv2.TERM_CRITERIA_MAX_ITER,10,1.0)

     k=3
     attempt=10
     ret,label,center = cv2.kmeans(img_flat,k,None,criteria,attempt,cv2.KMEANS_PP_CENTERS)

     center = np.uint8(center)
     res = center[label.flatten()]
     res2=res.reshape(img.shape)
     cv2.imwrite("res.png",res2)

     from collections import defaultdict
     from PIL import Image
     im = Image.open('res.png')
     res_by_color = defaultdict(int)
     for pixel in im.getdata():
          res_by_color[pixel] += 1
          res_by_color

     # cordinates = coordinates


     for cords in coordinates:
          print(cords)
          x,y,w,h=cords[0][0],cords[0][1],cords[1][0],cords[1][1]
          im1 = im.crop((x, y,x+w,y+h))

          by_color=defaultdict(int)
          for pixel in im.getdata():
               by_color[pixel] += 1


          maximum=0

          for i in res_by_color.keys():
               maximum=max(maximum,res_by_color[i])

     percent_cover=maximum/(img.shape[0]*img.shape[1])

     addition=30*percent_cover

     print(addition)
     
     return addition








def yolotest(key):
## provide the path for testing cofing file and tained model form colab
     net = cv2.dnn.readNetFromDarknet("C:\\Users\\vpras\\Downloads\\MonarchAPI-master\\MonarchAPI-master\\van Model\\yolov3_custom.cfg",r"C:\\Users\\vpras\\Downloads\\MonarchAPI-master\\MonarchAPI-master\\van Model\\yolov3_custom_final.weights")

     ### Change here for custom classes for trained model ... new model
     classes = ['plastic_bottle','plastic_bag','glass_bottle','can','undefined']


     img = cv2.imread("C:\\Users\\vpras\\Downloads\\MonarchAPI-master\\MonarchAPI-master\\van Model\\2.jpeg")
     img = cv2.resize(img,(1280,720))
     height,width,_ = img.shape
     blob = cv2.dnn.blobFromImage(img, 1/255,(416,416),(0,0,0),swapRB = True,crop= False)
     net.setInput(blob)
     output_layers_name = net.getUnconnectedOutLayersNames()
     layerOutputs = net.forward(output_layers_name)

     boxes =[]
     confidences = []
     class_ids = []
     for output in layerOutputs:
          for detection in output:
               score = detection[5:]
               class_id = np.argmax(score)
               confidence = score[class_id]
               if confidence > 0.7:
                    center_x = int(detection[0] * width)
                    center_y = int(detection[1] * height)
                    w = int(detection[2] * width)
                    h = int(detection[3] * height)
                    x = int(center_x - w/2)
                    y = int(center_y - h/2)
                    boxes.append([x,y,w,h])
                    confidences.append((float(confidence)))
                    class_ids.append(class_id)
     # print("Class ids:",class_ids)
     indexes = cv2.dnn.NMSBoxes(boxes,confidences,.8,.4)
     # print("index:",indexes)
     boxes =[]
     confidences = []
     class_ids = []
     for output in layerOutputs:
          for detection in output:
               score = detection[5:]
               class_id = np.argmax(score)
               confidence = score[class_id]
               if confidence > 0.5:
                    center_x = int(detection[0] * width)
                    center_y = int(detection[1] * height)
                    w = int(detection[2] * width)
                    h = int(detection[3] * height)
                    x = int(center_x - w/2)
                    y = int(center_y - h/2)
                    boxes.append([x,y,w,h])
                    confidences.append((float(confidence)))
                    class_ids.append(class_id)
     # print("class ids:",class_ids)
     indexes = cv2.dnn.NMSBoxes(boxes,confidences,.5,.4)
     font = cv2.FONT_HERSHEY_PLAIN
     colors = np.random.uniform(0,255,size =(len(boxes),3))
     
     litter_content=[]
     coords=[]
     if  len(indexes)>0:
          for i in indexes.flatten():
               x,y,w,h = boxes[i]
               print("class:",classes[class_ids[i]])
               label = str(classes[class_ids[i]])
               confidence = str(round(confidences[i],2))
               color = colors[i]
               cv2.rectangle(img,(x,y),(x+w,y+h),color,2)
               litter_content.append(label)
               coords.append([[x,y],[x+w,y+h]])
               cv2.putText(img,label + " " + confidence, (x,y),font,5,color,5)
     # print(litter_content,"litter_content")
     db.child("report").child("zVyqLkmCX5WFIoB5bkEb2Zt5hjE2").child(key).update({"litter_content":str(litter_content)})
     # db.child("report").child("zVyqLkmCX5WFIoB5bkEb2Zt5hjE2").child(key).update({"check":"pass"})
     cv2.imwrite('detected_yolo.jpg',img)
     # cv2.waitKey(0)
     return coords,litter_content



def predictclass(imgURL):
    #loading model
    model = keras.models.load_model("./model/classification_model/model.h5")
    img_height = 180
    img_width = 180
    imgURL = imgURL
#     urllib.request.urlretrieve(imgURL, "filename.jpg")
    img_path = 'C:\\Users\\vpras\\Downloads\\MonarchAPI-master\\MonarchAPI-master\\van Model\\2.jpeg'
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
#     try:
     #    print("enter the predict function")
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
               predicted_class=predictclass(value['image'])
               print("updated")
               coordinates,litter_content = yolotest(key)
               quantifiyparam=0
               if len(coordinates)!=0:
                    quantifiyparam=ImageKnnSegementation(coordinates)
               print("cordinates")
               print(coordinates)
               print(predicted_class)
               if predicted_class=="meso":
                    image_litter_level = 0+quantifiyparam
               elif predicted_class=="macro":
                    image_litter_level = 30+quantifiyparam
               elif predicted_class=="mega":
                    image_litter_level = 60+quantifiyparam


               print("--------------------image_litter_level-------------------------")
               print(image_litter_level)
               print("--------------------predicted_class-------------------------")
               print(predicted_class)
               print("--------------------waster content-------------------------")
               print(image_litter_level)
               
               
               db.child("report").child("zVyqLkmCX5WFIoB5bkEb2Zt5hjE2").child(key).update({"image_litter_level":str(image_litter_level)})
               
               break
        # print("invalid firebase value")
#     except:
#         print("invalid error")


























def getConfidence(payload):
    print("payload", payload['id'])
    start = time.time()
    if (payload['id']):
        id =payload['id']
    data=[]
#     predict(id)
    print("Started Task ...")
#     print("passed")
    predict(id)
#     print(threading.current_thread().name)
    print("completed .....")
#     print("data", data)
    a.append(data)
    print(time.time()-start)

class BeachQuantify(Resource):
    def get(self):
     #    try:
            del a[:]

            parser.add_argument('id', type=str)
            payload = parser.parse_args()
            getConfidence(payload)
          #   t = threading.Thread(target=getConfidence, args=(payload,))
          #   t.start()
          #   t.join()

            return {
                'status': True,
                'message': 'Confidence score is generated. ',
                'Input': payload,
                'output': "Updated firebase"
            }
     #    except:
     #        return {
     #            'status': False,
     #            'message': 'Unable to generate confidence score. ' + str(a)
     #        }


API_RUL_PATH = '/'

api.add_resource(BeachQuantify, API_RUL_PATH + 'beachQuantify')

if __name__ == '__main__':
    app.run(debug=True,port=9081)

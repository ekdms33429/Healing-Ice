import RPi.GPIO as GPIO
from time import sleep
import datetime
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import Adafruit_DHT
from pytz import timezone
from datetime import datetime

import urllib2, urllib, httplib
import json
import os 
from functools import partial

cred = credentials.Certificate("/home/pi/firebase/raspberry-714ab-firebase-adminsdk-y7y1n-bc847fd1f7.json") #firebase 비공개 키 넣어야함
firebase_admin.initialize_app(cred)
db = firestore.client()



GPIO.setmode(GPIO.BCM)
GPIO.cleanup()
GPIO.setwarnings(False)

# Sensor should be set to Adafruit_DHT.DHT11,
# Adafruit_DHT.DHT22, or Adafruit_DHT.AM2302.
sensor = Adafruit_DHT.DHT11

# Example using a Beaglebone Black with DHT sensor
# connected to pin P8_11.
pin = 23

# Try to grab a sensor reading.  Use the read_retry method which will retry up
# to 15 times to get a sensor reading (waiting 2 seconds between each retry).
humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)

#firebase.put("/dht", "/temp", "0.00")
#firebase.put("/dht", "/humidity", "0.00")

def update_firestore():
    
    fmt = "%Y-%m-%d %H:%M:%S %Z%z"

    KST = datetime.now(timezone('Asia/Seoul'))
 
    now = KST.strftime(fmt)

    humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
    if humidity is not None and temperature is not None:
      sleep(5)
      str_temp = ' {0:0.2f} *C '.format(temperature)   
      str_hum  = ' {0:0.2f} %'.format(humidity)
      print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))
      print now
      
   
    doc_ref = db.collection(u'sensordata').document()
    doc_ref.set({
      u'humidity' : float(humidity), 
      u'temp' : float(temperature), 
      u'checktime' : datetime.now(timezone('Asia/Seoul'))
      
    })
   
   
      
while True:
      update_firestore()
      
        #sleepTime = int(sleepTime)
      sleep(5)
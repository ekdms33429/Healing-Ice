
import binascii
import serial
from datetime import datetime
import re
import thread
import json
import RPi.GPIO as pi
import time

from firebase import firebase

eof = "\xff\xff\xff"
port = serial.Serial('/dev/ttyS0', 9600, timeout=0.01)
Time = ""
path_ice = "/home/pi/Desktop/ice_config/config_ice.ini"
path_drink = "/home/pi/Desktop/ice_config/config_drink.ini"

R1=19 
R2=26 
R3=17
R5=27 
R6=23
R7=24 
R8=4
AC_R1=16 
AC_R2=20 

DAT =5 
CLK=12
DAT2=6 
CLK2=22
water_level=13 
ice_level=25 
    
pi.setmode(pi.BCM)
pi.setwarnings (False)
pi.setup(R1,pi.OUT)
pi.setup(R2,pi.OUT)
pi.setup(R3,pi.OUT)
pi.setup(R5,pi.OUT)
pi.setup(R6,pi.OUT)
pi.setup(R7,pi.OUT)
pi.setup(R8,pi.OUT)
pi.setup(AC_R1,pi.OUT) 
pi.setup(AC_R2,pi.OUT)

pi.setup(ice_level,pi.IN)
pi.setup(water_level,pi.IN)
pi.setup(CLK,pi.OUT)
pi.setup(CLK2,pi.OUT)

status_pic =2
mode_pic=2

def weight():
    
    global wei_avg
    i=0
    j=0
    num=0
    num2=0
    
    pi.setup(DAT, pi.OUT)
    pi.setup(DAT2, pi.OUT)
    pi.output(DAT,1)
    pi.output(DAT2,1)
    pi.output(CLK,0)
    pi.output(CLK2,0) 
    pi.setup(DAT, pi.IN)
    pi.setup(DAT2, pi.IN)
    
    while pi.input(DAT) == 1:
        i=0
    while pi.input(DAT2) == 1:
        j=0
        
    for i in range(24):
        pi.output(CLK,1)
        num=num<<1
        pi.output(CLK,0)
        
        if pi.input(DAT)== 0:
            num=num+1
            
    for j in range(24):
        pi.output(CLK2,1)
        num2=num2<<1
        pi.output(CLK2,0)
        
        if pi.input(DAT2)== 0:
            num2=num2+1
            
    pi.output(CLK,1)
    pi.output(CLK2,1)
    
    num=num^0x800000
    num2=num2^0x800000
    
    pi.output(CLK,0)
    pi.output(CLK2,0)
    
    wei=0
    wei2=0
    wei=((num)/(1406))
    wei2=((num2)/(1406))
    wei_avg=(5900-((wei+wei2)/2))/3.3 
    
    if(wei_avg>=95):
      wei_avg=100
    if(wei_avg<=5):
      wei_avg=0
      
        
    time.sleep(0.5)

def v1_drink_sequence(): 

    global status_pic, mode_pic
    
    
    mode_pic=141
    
    time.sleep(0.5)

    time.sleep(0.5)
    pi.output(R5,1)
    time.sleep(5) 
    pi.output(R5,0) 
   
def v1_ice_sequence(a,b): 
  
    pi.output(R1, 1)
    
    time.sleep(a)
   
    pi.output(R1, 0)
   
    pi.output(R3, 1)
    
    time.sleep(b)
    
    pi.output(R3, 0)
    time.sleep(6)

    pi.output(R2, 0)
    time.sleep(0.5)
    time.sleep(0.5)
  
    pi.output(R2, 1)
    time.sleep(2)
    pi.output(R1, 1)
    time.sleep(2)
    pi.output(R3, 1)
    time.sleep(2)

    pi.output(R1, 0)
    pi.output(R3, 0)  

    time.sleep(6)
    pi.output(R2, 0)

    time.sleep(2)                             
    pi.output(R5, 1)

    time.sleep(21) 

    pi.output(R5, 0)

  
    time.sleep(2) 

    pi.output(R6, 1) 

    time.sleep(2)

    time.sleep(539) 

    time.sleep(2)

    for i in range(55):

        pi.output(AC_R1, 1)
        time.sleep(0.03)
        pi.output(AC_R1, 0)
        time.sleep(0.1)

    time.sleep(1)

    pi.output(R7, 1) 

    time.sleep(10)

    pi.output(R7, 0)
    pi.output(R6, 0)

    time.sleep(9)

 
    time.sleep(1)

    for i in range(55):
        pi.output(AC_R2, 1)
        time.sleep(0.03)
        pi.output(AC_R2, 0)
        time.sleep(0.1)

    time.sleep(2)
 
    
def stop(): 

    global status_pic, mode_pic
    
    time.sleep(1)

    status_pic=138
   
    time.sleep(1)

    mode_pic=143
    

    pi.output(R1, 0)
    pi.output(R2, 0)
    pi.output(R3, 0)
    pi.output(R5, 0)
    pi.output(R6, 0)
    pi.output(R7, 0)
    pi.output(R8, 0)
    pi.output(AC_R1, 0)
    pi.output(AC_R2, 0)
  
fb = firebase.FirebaseApplication('https://kdejava-19d55.firebaseio.com/',None)
global powder_amount
global milk_amount

while True:
    result = fb.get('machine1/mode_status',None)
    pow_amount = fb.get('machine1/powder_amount',None)
    milk_amount = fb.get('machine1/milk_amount',None)

    if result=='drink_on':
        v1_drink_sequence()
        weight()
        fb.put('/machine1',"milk_weight",wei_avg) 
        
    elif result=='ice_on':
        v1_ice_sequence(milk_amount,pow_amount)
        fb.put('/machine1',"mode_status","ice_off")
    elif result=='clean_on':
        stop()
        fb.put('/machine1',"mode_status","clean_off")
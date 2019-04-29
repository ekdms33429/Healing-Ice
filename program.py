#-*-coding: utf-8-*- 
# 한글 주석 이용

import time
import os
import V1_runner
#import net_check
from AWSIoTPythonSDK.MQTTLib import AWSIoTMQTTClient
 
def run_V1(self, params, packet):
V1_runner.v1_ice_sequence()
myMQTTClient.publish('home/motorRunStatus', packet.payload, 0) #경로 설정 필요
 
myMQTTClient = AWSIoTMQTTClient("raspberryPiHome") #경로 설정 필요, random key, if another connection using the same key is opened the previous one is auto closed by AWS IOT
 
myMQTTClient.configureEndpoint("YOUR AWS IOT ENDPOINT HERE", 8883) #aws iot 설정과 같게 입력
 
certRootPath = '/home/pi/homeAutomation/configuration/certs/' #경로 설정 필요
myMQTTClient.configureCredentials("{}root-ca.pem".format(certRootPath), "{}cloud.pem.key".format(certRootPath), "{}cloud.pem.crt".format(certRootPath)) #aws iot 인증서
 
myMQTTClient.configureOfflinePublishQueueing(-1) # Infinite offline Publish queueing
myMQTTClient.configureDrainingFrequency(2) # Draining: 2 Hz
myMQTTClient.configureConnectDisconnectTimeout(10) # 10 sec
myMQTTClient.configureMQTTOperationTimeout(5) # 5 sec
 
myMQTTClient.connect()
myMQTTClient.subscribe("home/runMotor", 1, run_V1) #경로 설정 필요
 
def looper():
while True:
time.sleep(5) #sleep for 5 seconds and then sleep again
#check_internet()
 
looper()
 
def function_handler(event, context):
return
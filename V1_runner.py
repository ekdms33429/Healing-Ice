#-*-coding: utf-8-*- 
# 한글 주석 이용

import RPi.GPIO as pi
from time import sleep
import platform
import time

R1=19 # 우유통펌프

R2=26 # 배합기

R3=17 # 파우더

R5=27 # 얼음펌프

R6=23 # 컴프레셔

R7=24 # 응축기

R8=4 # 음료펌프

AC_R1=16 # AC모터 정회전

AC_R2=20 # AC모터 역회전


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

def v1_ice_sequence():
if platform.system().lower() != "windows": # 현재 사용하는 시스템환경 확인

    global status_pic, mode_pic

    a=0

    b=0

    mode_pic=140

    

    time.sleep(0.5)

 

    #port.write("Home.mode_image.pic=140" + eof) # 얼음 모드 140

    

    time.sleep(0.5)

 

    status_pic=128

    #port.write("Home.status_image.pic=128" + eof) # 음료 배합 중 128

 

    time.sleep(0.1)

    pi.output(R2, 1) # 배합기모터 먼저 작동

    time.sleep(2) # 2초가 지난 후에 다음 코딩으로 넘어감

    pi.output(R1, 1) # 우유모터가 동작

    time.sleep(2)

    pi.output(R3, 1) # 파우더모터가 동작

    time.sleep(2)

    pi.output(R1, 0) # 우유모터 정지

    pi.output(R3, 0) # 파우더모터 정지

    time.sleep(6) 

    pi.output(R2, 0) # 배합기모터가 정지

 

    status_pic=129

    #port.write("Home.status_image.pic=129" + eof) #음료 배합 완료 129

 

    time.sleep(2)                             

 

    pi.output(R8, 1) #워터펌프 동작

 

    status_pic=130

    #port.write("Home.status_image.pic=130" + eof) #음료 냉각 준비 중 130

 

    time.sleep(20) #50초간 동작

    pi.output(R8, 0) #워터펌프 정지

 

    status_pic=131

    #port.write("Home.status_image.pic=131" + eof) #음료 냉각 준비 완료 131

    

    time.sleep(2) 

    pi.output(R6, 1) #컴프레셔 작동

    

    status_pic=132

    #port.write("Home.status_image.pic=132" + eof) #냉각모드 작동 132

 

    time.sleep(2)

 

    status_pic=137

    #port.write("Home.status_image.pic=137" + eof) #얼음 생성 중 137

 

    time.sleep(538) #9m

 

    status_pic=133

    #port.write("Home.status_image.pic=133" + eof) #얼음 생성 완료 133

 

    time.sleep(2)

 

    for a in range(40): #AC모터 아래 동작 40번 반복 후 정지

        pi.output(AC_R1, 1) # 정회전 동작

        time.sleep(0.03) # 0.03초 동작

        pi.output(AC_R1, 0) # 정회전 정지

        time.sleep(0.1) # 0.1초 정지 

 

    time.sleep(1)

    pi.output(R7, 1) #응축기 동작 (얼음 분리)

 

    status_pic=134

    #port.write("Home.status_image.pic=134" + eof) #얼음 분리 중 134

 

    time.sleep(10)

 

    status_pic=135

    #port.write("Home.status_image.pic=135" + eof) #얼음 분리 완료 135

    

    pi.output(R7, 0) #응축기 정지

    pi.output(R6, 0) #컴프레셔 정지  

    time.sleep(9)

    status_pic=136

    #port.write("Home.status_image.pic=136" + eof) #냉각기 작동 종료 136

 

    time.sleep(1)

 

    for b in range(40): #AC모터 아래 동작 40번 반복 후 정지

        pi.output(AC_R2, 1) # 역회전 동작

        time.sleep(0.03) #0.03초 동작

        pi.output(AC_R2, 0) # 역회전 정지

        time.sleep(0.1) #0.1초

        

    time.sleep(2)

 

    status_pic=127

    #port.write("Home.status_image.pic=127" + eof) #작동 준비 완료 127

 
v1_ice_sequence()


pi.cleanup()
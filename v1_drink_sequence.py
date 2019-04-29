#-*-coding: utf-8-*- 

# 한글 주석 이용

import RPi.GPIO as pi

import time

 

 

# 라즈베리파이 GPIO 핀 17개 중 16개 사용 (남은 핀: 6, 21)

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

 

def v1_drink_sequence(): # 음료 시퀀스 동작 함수

 

    global status_pic, mode_pic

 

    mode_pic=141

    

    time.sleep(0.5)

 

    #port.write("Home.mode_image.pic=147" + eof) # 음료 모드 147

 

    time.sleep(0.5)

    pi.output(R5,1) # 음료모터 동작

    time.sleep(2.5) # 2.5초 동작

    pi.output(R5,0) # 음료모터 정지

    

v1_drink_sequence()
#-*-coding: utf-8-*- 
# 한글 주석 이용
from __future__ import unicode literals
from django.shortcuts import render
from django.http import HttpResponse
# sets pi 18 to output

import RPi.GPIO as pi
import time

R1=19

R2=26

R3=17

R5=27

R6=23

R7=24

AC_R1=16

AC_R2=20



pi.setmode(pi.BCM)

pi.setwarnings (False)

pi.setup(R1,pi.OUT)

pi.setup(R2,pi.OUT)

pi.setup(R3,pi.OUT)

pi.setup(R5,pi.OUT)

pi.setup(R6,pi.OUT)

pi.setup(R7,pi.OUT)

pi.setup(AC_R1,pi.OUT) 

pi.setup(AC_R2,pi.OUT)

def relaycontroller(request):
   if 'on' in request.POST:
      time.sleep(1)
      #음료 배합 중 128
      pi.output(R2, 1)
      time.sleep(2)
      pi.output(R1, 1)
      time.sleep(2)
      pi.output(R3, 1)
      time.sleep(2)
      pi.output(R1, 0)
      pi.output(R3, 0)  #여기까지 커피 만들기
      time.sleep(6)
      pi.output(R2, 0)

      time.sleep(0.5)
      
      time.sleep(0.5)
      #음료 배합 중 128
      time.sleep(0.1)

      pi.output(R2, 1)

      time.sleep(2)

      pi.output(R1, 1)

      time.sleep(2)

      pi.output(R3, 1)

      time.sleep(2)

      pi.output(R1, 0)

      pi.output(R3, 0)  #여기까지 커피 만들기

      time.sleep(6)

      pi.output(R2, 0)

      #음료 배합 완료 129
      time.sleep(2)                             

      pi.output(R5, 1)
      #음료 냉각 준비 중 130
      time.sleep(50) # 저수통 워터 펌프 시간 조정 필요

      pi.output(R5, 0)
      #음료 냉각 준비 완료 131
      time.sleep(2) #7m50s

      pi.output(R6, 1) #컴프레셔 작동
      #냉각모드 작동 132
      time.sleep(2)
      #얼음 생성 중 137
      time.sleep(538) #9m
      #얼음 생성 완료 133
      time.sleep(2)

      for i in range(40):

         pi.output(AC_R1, 1)

         time.sleep(0.03)

         pi.output(AC_R1, 0)

         time.sleep(0.1)
                
      time.sleep(1)
             
			 
      pi.output(R7, 1) #얼음 분리

      #얼음 분리 중 134

      time.sleep(10)
      #얼음 분리 완료 135

      pi.output(R7, 0)

      pi.output(R6, 0) #컴프레셔 작동x  

      time.sleep(9)
      #냉각기 작동 종료 136

      time.sleep(1)
      for i in range(40):

         pi.output(AC_R2, 1)

         time.sleep(0.03)

         pi.output(AC_R2, 0)

         time.sleep(0.1)

      time.sleep(2)
      #작동 준비 완료 127
      
   return render(request,'control.html')


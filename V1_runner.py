#-*-coding: utf-8-*- 
# �ѱ� �ּ� �̿�

import RPi.GPIO as pi
from time import sleep
import platform
import time

R1=19 # ����������

R2=26 # ���ձ�

R3=17 # �Ŀ��

R5=27 # ��������

R6=23 # ��������

R7=24 # �����

R8=4 # ��������

AC_R1=16 # AC���� ��ȸ��

AC_R2=20 # AC���� ��ȸ��


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
if platform.system().lower() != "windows": # ���� ����ϴ� �ý���ȯ�� Ȯ��

    global status_pic, mode_pic

    a=0

    b=0

    mode_pic=140

    

    time.sleep(0.5)

 

    #port.write("Home.mode_image.pic=140" + eof) # ���� ��� 140

    

    time.sleep(0.5)

 

    status_pic=128

    #port.write("Home.status_image.pic=128" + eof) # ���� ���� �� 128

 

    time.sleep(0.1)

    pi.output(R2, 1) # ���ձ���� ���� �۵�

    time.sleep(2) # 2�ʰ� ���� �Ŀ� ���� �ڵ����� �Ѿ

    pi.output(R1, 1) # �������Ͱ� ����

    time.sleep(2)

    pi.output(R3, 1) # �Ŀ�����Ͱ� ����

    time.sleep(2)

    pi.output(R1, 0) # �������� ����

    pi.output(R3, 0) # �Ŀ������ ����

    time.sleep(6) 

    pi.output(R2, 0) # ���ձ���Ͱ� ����

 

    status_pic=129

    #port.write("Home.status_image.pic=129" + eof) #���� ���� �Ϸ� 129

 

    time.sleep(2)                             

 

    pi.output(R8, 1) #�������� ����

 

    status_pic=130

    #port.write("Home.status_image.pic=130" + eof) #���� �ð� �غ� �� 130

 

    time.sleep(20) #50�ʰ� ����

    pi.output(R8, 0) #�������� ����

 

    status_pic=131

    #port.write("Home.status_image.pic=131" + eof) #���� �ð� �غ� �Ϸ� 131

    

    time.sleep(2) 

    pi.output(R6, 1) #�������� �۵�

    

    status_pic=132

    #port.write("Home.status_image.pic=132" + eof) #�ð���� �۵� 132

 

    time.sleep(2)

 

    status_pic=137

    #port.write("Home.status_image.pic=137" + eof) #���� ���� �� 137

 

    time.sleep(538) #9m

 

    status_pic=133

    #port.write("Home.status_image.pic=133" + eof) #���� ���� �Ϸ� 133

 

    time.sleep(2)

 

    for a in range(40): #AC���� �Ʒ� ���� 40�� �ݺ� �� ����

        pi.output(AC_R1, 1) # ��ȸ�� ����

        time.sleep(0.03) # 0.03�� ����

        pi.output(AC_R1, 0) # ��ȸ�� ����

        time.sleep(0.1) # 0.1�� ���� 

 

    time.sleep(1)

    pi.output(R7, 1) #����� ���� (���� �и�)

 

    status_pic=134

    #port.write("Home.status_image.pic=134" + eof) #���� �и� �� 134

 

    time.sleep(10)

 

    status_pic=135

    #port.write("Home.status_image.pic=135" + eof) #���� �и� �Ϸ� 135

    

    pi.output(R7, 0) #����� ����

    pi.output(R6, 0) #�������� ����  

    time.sleep(9)

    status_pic=136

    #port.write("Home.status_image.pic=136" + eof) #�ð��� �۵� ���� 136

 

    time.sleep(1)

 

    for b in range(40): #AC���� �Ʒ� ���� 40�� �ݺ� �� ����

        pi.output(AC_R2, 1) # ��ȸ�� ����

        time.sleep(0.03) #0.03�� ����

        pi.output(AC_R2, 0) # ��ȸ�� ����

        time.sleep(0.1) #0.1��

        

    time.sleep(2)

 

    status_pic=127

    #port.write("Home.status_image.pic=127" + eof) #�۵� �غ� �Ϸ� 127

 
v1_ice_sequence()


pi.cleanup()
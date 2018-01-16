import numpy as np
import argparse
import time
import cv2

#BGR
colorLower = np.array([0, 100, 100], dtype="uint8")
colorUpper = np.array([255, 200, 200], dtype="uint8")

#Hue from http://color.yafla.com/
lower_yellow = np.array([30, 60, 60])
upper_yellow = np.array([40, 255, 255])

camera = cv2.VideoCapture(1)
camera.set(cv2.CAP_PROP_AUTO_EXPOSURE, True)
print(camera.isOpened())

while True:
    (grabbed, frame) = camera.read(cv2.COLOR_BGR2HSV)
    cv2.imshow('original', frame)

    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(hsv, lower_yellow, upper_yellow)
    res = cv2.bitwise_and(frame, frame, mask=mask)
    cv2.imshow('frame', frame)
    cv2.imshow('mask', mask)
    cv2.imshow('res', res)
    frame = mask

    frame = cv2.GaussianBlur(frame, (3, 3), 0)

    (img, cnts, _) = cv2.findContours(frame.copy(), cv2.RETR_EXTERNAL,
                                      cv2.CHAIN_APPROX_SIMPLE)
    if len(cnts) > 0:
        # Detect the largest region
        cnt = sorted(cnts, key=cv2.contourArea, reverse=True)[0]
        rect = np.int32(cv2.boxPoints(cv2.minAreaRect(cnt)))
        # Draw a rectangular frame around the detected object
        frame = cv2.drawContours(frame, [rect], -1, (100, 100, 256), 4)

    cv2.imshow('classified', frame)
    time.sleep(0.025)
    if cv2.waitKey(1) & 0xFF == ord("q"):
        break
camera.release()

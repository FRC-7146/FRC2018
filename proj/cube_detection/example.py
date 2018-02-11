import numpy as np
import argparse
import time
import cv2

#BGR
colorLower = np.array([0, 100, 100], dtype="uint8")
colorUpper = np.array([255, 200, 200], dtype="uint8")

# Hue from http://color.yafla.com/
lower_yellow = np.array([25, 90, 90])
upper_yellow = np.array([30, 250, 250])

camera = cv2.VideoCapture(1)
camera.set(cv2.CAP_PROP_AUTO_EXPOSURE, True)
camera.set(cv2.CAP_PROP_AUTO_EXPOSURE, False)
print(camera.isOpened())

while True:
    (grabbed, frame) = camera.read(cv2.COLOR_RGB2HSV)
    cv2.imshow('original', frame)

    # Remove noise
    frame = cv2.GaussianBlur(frame, (3, 3), 0)
    frame = cv2.bilateralFilter(frame, 9, 75, 75)

    # Detect yellow specific area
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    mask = cv2.inRange(hsv, lower_yellow, upper_yellow)
    res = cv2.bitwise_and(frame, frame, mask=mask)
    cv2.imshow('mask', mask)

    (img, cnts, _) = cv2.findContours(mask.copy(), cv2.RETR_LIST,
                                      cv2.CHAIN_APPROX_SIMPLE)
    if len(cnts) > 0:
        # Detect the largest region
        cnt = sorted(cnts, key=cv2.contourArea, reverse=True)[0]
        approx = cv2.approxPolyDP(cnt, 0.01 * cv2.arcLength(cnt, True), True)
        rect = np.int32(cv2.boxPoints(cv2.minAreaRect(cnt)))
        # Draw a rectangular frame around the detected object
        frame = cv2.drawContours(frame, [rect, approx], -1, (100, 100, 256), 4)

    cv2.imshow('classified', frame)
    time.sleep(0.025)
    if cv2.waitKey(1) & 0xFF == ord("q"):
        break
camera.release()

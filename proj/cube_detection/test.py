#!/usr/bin/env python3
import os, sys, logging
import cv2

img = cv2.imread("./a.jpg")
img[:,:,2]=0
img[:,:,0]=0
cv2.imshow('test',img)
cv2.waitKey()

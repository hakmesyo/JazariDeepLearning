import cv2
import numpy as np

video=cv2.VideoCapture(0)
cv2.moveWindow('video',300,100);

while True:
    check, frame= video.read()
    #gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)   
    frame = cv2.flip(frame,1)
    
    # Combining the two different image frames in one window
    #combined_window = np.hstack([frame,gray_flip])
    height, width, channels=frame.shape
    px=int((width-height)/2)
    #print(height,width,channels,px)
    frame=frame[0:height,px:px+height]
    cv2.imshow("video",frame)
    key=cv2.waitKey(1)
    if key==27:
        break
video.release()
cv2.destroyAllWindows
package MineSweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CVAgent {
  //the hight of img to be processed, 100 has an acceptable CPU load
  int processH = 100;
  Mat _mapFrame;
  // a timer for acquiring the video stream
  private ScheduledExecutorService timer;
  // the OpenCV object that realizes the video capture
  private VideoCapture capture = new VideoCapture();
  // a flag to change the button behavior
  private boolean cameraActive = false;
  // the id of the camera to be used
  private static int cameraId = 0;

  // 这里需要解耦, 外部要持续从这里拿图片
  ImageView _cameraView = null;

  public void setCameraView(ImageView view) {
    _cameraView = view;
  }

  boolean[][] genTable() {
//    System.out.println("Generated map from image");
    boolean[][] table = new boolean[_mapFrame.rows()][_mapFrame.cols()];
//    System.out.println(_mapFrame);
    for (int i = 0; i < _mapFrame.rows(); i++) {
      for (int j = 0; j < _mapFrame.cols(); j++) {
        table[i][j] = _mapFrame.get(i,j)[0]<=254;
//        System.out.print(table[i][j]?1:0);
      }
//      System.out.println();
    }
    return table;
  }

  public void startCamera() {
    if (!this.cameraActive) {
      // start the video capture
      this.capture.open(cameraId);

      // is the video stream available?
      if (this.capture.isOpened()) {
        this.cameraActive = true;
        // grab a frame every 33 ms (30 frames/sec)
        Runnable frameGrabber = new Runnable() {

          @Override
          public void run() {
            // effectively grab and process a single frame
            Mat frame = grabFrame();
            if (frame == null) {
              return;
            }
            Core.flip(frame,frame,1);
            float ratio = ((float) frame.cols()) / frame.rows(); //w:h
            Imgproc.resize(frame, frame, new Size(processH * ratio, processH));
            frame = processFrame(frame);
//            frame.copyTo(_mapFrame);
//            System.out.println(frame);
            // resized for mine map generation
            Imgproc.resize(frame, frame, new Size(GLOBAL.ROWS * ratio, GLOBAL.ROWS));
            _mapFrame=frame;
            Mat f2show=new Mat();
            Imgproc.resize(frame, f2show, new Size(GLOBAL.HEAD_HEIGHT * ratio, GLOBAL.HEAD_HEIGHT));
            // convert and show the frame
            updateImageView(_cameraView, Utils.mat2Image(f2show));
          }
        };

        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

        // update the button content
//        this.button.setText("Stop Camera");
      } else {
        // log the error
        System.err.println("Impossible to open the camera connection...");
      }
    } else {
      // the camera is not active at this point
      this.cameraActive = false;
      // update again the button content
//      this.button.setText("Start Camera");

      // stop the timer
      this.stopAcquisition();
    }
  }

  Mat processFrame(Mat src) {
    Mat rslt = new Mat();
    Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
    Imgproc.blur(src, rslt, new Size(3, 3));

    // canny detector, with ratio of lower:upper threshold of 3:1
//    Imgproc.Canny(rslt, rslt,30, 90);
    Imgproc.adaptiveThreshold(rslt, rslt, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 5, 2);
    return rslt;
//    Mat dst=new Mat();
//    src.copyTo(dst,rslt);
//    return dst;
  }

  /**
   * Get a frame from the opened video stream (if any)
   *
   * @return the {@link Mat} to show
   */
  private Mat grabFrame() {
    // init everything
    Mat frame = new Mat();

    // check if the capture is open
    if (this.capture.isOpened()) {
      try {
        // read the current frame
        this.capture.read(frame);

        // if the frame is not empty, process it
        if (!frame.empty()) {
          return frame;
        }

      } catch (Exception e) {
        // log the error
        System.err.println("Exception during the image elaboration: " + e);
      }
    }

    return null;
  }

  /**
   * Stop the acquisition from the camera and release all the resources
   */
  public void stopAcquisition() {
    if (this.timer != null && !this.timer.isShutdown()) {
      try {
        // stop the timer
        this.timer.shutdown();
        this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        // log any exception
        System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
      }
    }

    if (this.capture.isOpened()) {
      // release the camera
      this.capture.release();
    }
  }

  /**
   * Update the {@link ImageView} in the JavaFX main thread
   *
   * @param view  the {@link ImageView} to update
   * @param image the {@link Image} to show
   */
  private void updateImageView(ImageView view, Image image) {
    if (view == null)
      return;
    Utils.onFXThread(view.imageProperty(), image);
  }

  /**
   * On application close, stop the acquisition from the camera
   */
  protected void setClosed() {
    this.stopAcquisition();
  }

}
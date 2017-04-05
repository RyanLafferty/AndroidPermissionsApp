package lafferty.com.abusivepermissions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.hardware.Camera;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureActivity extends AppCompatActivity
{
    private Camera mCamera;
    private Preview mPreview;
    private String fileName = "TEST_PICTURE.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            mPreview = new Preview(this);
            CameraOpen();
            mPreview.setCamera(mCamera);
            mCamera.takePicture(null, null, null, mPictureCallback);
            mCamera.release();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean CameraOpen() {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(0);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to open Camera.");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] imageData, Camera c) {
            System.out.println("PICTURE TAKEN...");

            if (imageData != null) {
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_WORLD_READABLE);
                    outputStream.write(imageData);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (outputStream != null) try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    };

}

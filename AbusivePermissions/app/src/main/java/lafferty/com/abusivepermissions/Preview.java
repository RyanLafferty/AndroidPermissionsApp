package lafferty.com.abusivepermissions;

import android.view.ViewGroup;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.hardware.Camera;
import java.io.IOException;
import java.util.List;
import android.util.Size;

/**
 * Created by toph on 05/04/17.
 */

class Preview extends ViewGroup implements SurfaceHolder.Callback {
    SurfaceView mSurfaceView;
    SurfaceHolder mHolder;

    private Camera mCamera;
    private List<Camera.Size> mSupportedPreviewSizes;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
    }

    Preview(Context context) {
        super(context);

        mSurfaceView = new SurfaceView(context);
        addView(mSurfaceView);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera camera) {
        if (mCamera == camera) {
            return;
        }

        stopPreviewAndFreeCamera();

        mCamera = camera;
        if (mCamera != null) {
//            List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
//            mSupportedPreviewSizes = localSizes;
//            System.out.println("Supported sizes: ");
//            for(Camera.Size size: mSupportedPreviewSizes){
//                System.out.println("   Size: " + size.width + "," + size.height);
//            }
            requestLayout();

            try {
                System.out.println("I've failed to fully initialize the surface HERE");
                mCamera.setPreviewDisplay(mHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Important: Call startPreview() to start updating the preview
            // surface. Preview must be started before you can take a picture.
            mCamera.startPreview();
        }
    }

    public void surfaceCreated(SurfaceHolder holder){
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (holder.getSurface() == null){
            return;
        }

        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(160,120); // 1px x 1px (unsupported) to hide preview from user
            requestLayout();

            mCamera.setParameters(parameters);
            try {
                mCamera.setPreviewDisplay(holder);
            }
            catch(IOException e){
                e.printStackTrace();
            }
            //mCamera.setPreviewCallback(mPreviewCallback);
            mCamera.startPreview();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    private void stopPreviewAndFreeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

}

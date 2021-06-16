/**
 * 这个类用来作为实时音量显示的接口
 * This class is designed as API to show real-time volumn of room chat.
 * 
 * 使用异步线程避免对原本音频传输流的干扰
 * Basic design thinking is use xxxx threads to avoid disturb to native methods.
 * 
 * 记录一些关于webrtc音频流的详细信息
 * 音频被解为pcm后，以采样Sample为单位，16bit ，10ms一次采样
 * Notes of webrtc audio stream :
 * After decoded to pcm format, audio uses Sample as unit.
 * Sample is signed 16bit. Its frequency is 1 Sample / 10ms. 
 * 
 */


import java.nio.ByteBuffer;

public class VolumnCompute {
    private static final String TAG = "VolumeCompute";

    private static final int NUM_OF_SAMPLES_PER_SECOND = 100;

    private static volatile VolumnCompute INSTANCE = null;

    private double volumeValue;

    private ByteBuffer byteBuffer;

    private VolumnCompute() {};

    public static VolumnCompute getInstance(){
        if(INSTANCE == null){
            synchronized(VolumnCompute.class){
                if(INSTANCE == null){
                    INSTANCE = new VolumnCompute();
                }
            }
        }
        return INSTANCE;
    }

    public void setBuffer(ByteBuffer byteBuffer){
        this.byteBuffer = byteBuffer.duplicate();
    }

    public double getVolumnPer10ms(){
        volumnValue = byte16bitToVolumn(byteBuffer);
        return volumnValue;
    }

    //16bit pcm  -->  0%-100% volumn display
    //
    private double byte16bitToVolumn(ByteBuffer buffer){
        double volumnOfOneSample = 0;

        volumnOfOneSample = (20 * Math.log10(buffer.getDouble() / math.pow(2,16)) + 60) / 60.0 * 100.0;

        return volumnOfOneSample;
    }

    
}

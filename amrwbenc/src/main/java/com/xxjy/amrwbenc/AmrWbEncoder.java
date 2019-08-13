package com.xxjy.amrwbenc;

public class AmrWbEncoder {

    public enum Mode {
        MD66,   /*!< 6.60kbps   */
        MD885,  /*!< 8.85kbps   */
        MD1265, /*!< 12.65kbps  */
        MD1425, /*!< 14.25kbps  */
        MD1585, /*!< 15.85bps   */
        MD1825, /*!< 18.25bps   */
        MD1985, /*!< 19.85kbps  */
        MD2305, /*!< 23.05kbps  */
        MD2385, /*!< 23.85kbps> */
        N_MODES /*!< Invalid mode */
    }

    static {
        System.loadLibrary("amr-wb-enc");
    }

    public static native void init();

    public static native int encode(int mode, short[] in, byte[] out, int dtx);

    public static native void exit();

}

#include <jni.h>
#include "enc_if.h"

#ifndef _Included_com_xxjy_amrwbenc_AmrWbEncoder
#define _Included_com_xxjy_amrwbenc_AmrWbEncoder

struct encoder_state *state;

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_xxjy_amrwbenc_AmrWbEncoder_init(JNIEnv *, jclass) {
    state = (encoder_state *) E_IF_init();
}


JNIEXPORT jint JNICALL
Java_com_xxjy_amrwbenc_AmrWbEncoder_encode(JNIEnv *env, jclass, jint mode, jshortArray in,
                                           jbyteArray out,
                                           jint dtx) {
    jsize inLen = env->GetArrayLength(in);
    jshort inBuf[inLen];
    env->GetShortArrayRegion(in, 0, inLen, inBuf);

    jsize outLen = env->GetArrayLength(out);
    jbyte outBuf[outLen];
    int encodeLength = E_IF_encode(state, mode, (const short *) inBuf, (unsigned char *) outBuf,
                                   dtx);

    env->SetByteArrayRegion(out, 0, outLen, outBuf);
    return encodeLength;
}


JNIEXPORT void JNICALL Java_com_xxjy_amrwbenc_AmrWbEncoder_exit(JNIEnv *, jclass) {
    E_IF_exit(state);
}

#ifdef __cplusplus
}
#endif
#endif

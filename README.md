# amr-wb-enc-android

## Description
- amr-wb-enc-android is a Android library that can convert wav/pcm audio files with a sampling rate of 16k into arm format
- The core conversion source is from [vo-amrwbenc](https://sourceforge.net/projects/opencore-amr/files/vo-amrwbenc/) with version 0.1.3

## Usage
Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    implementation 'com.github.xxjy:amr-wb-enc-android:1.0.0@aar'
}
```

## Example

``` 
AmrWbEncoder.init();

int mode = AmrWbEncoder.Mode.MD1825.ordinal();
short[] in = new short[320];
byte[] out = new byte[640];
int encodedSize = AmrWbEncoder.encode(mode, in, out, 0)

AmrEncoder.exit();        
```
See the [example](app/src/main/java/com/example/test/amrtest/MainActivity.java) for more details.
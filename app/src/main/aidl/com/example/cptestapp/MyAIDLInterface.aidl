// MyAIDLInterface.aidl
package com.example.cptestapp;

// Declare any non-default types here with import statements

interface MyAIDLInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
     //       double aDouble, String aString);

     //void AIDLService();
     void TransferData(int mData);
     int getData();
}

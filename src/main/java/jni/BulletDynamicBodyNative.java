//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package jni;

/**
 * @author Grégory Van den Borre
 */
public class BulletDynamicBodyNative {

    /**
     * Set the btbody position in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param posX           Position X value to set to the btbody.
     * @param posY           Position Y value to set to the btbody.
     * @param posZ           Position Z value to set to the btbody.
     */
    public native void setPosition(final long pointerAddress, final float posX, final float posY, final float posZ);

    /**
     * Retrieve the current position in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @return An Array with X, Y, Z position coordinates.
     */
    public native float[] getPosition(final long pointerAddress);

    /**
     * Retrieve the current direction in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @return An Array with X, Y, Z direction coordinates.
     */
    public native float[] getDirection(final long pointerAddress);

    /**
     * Set the body direction.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param x              Direction X value.
     * @param y              Direction Y value.
     * @param z              Direction Z value.
     */
    public native void setDirection(final long pointerAddress, final float x, final float y, final float z);

    /**
     * Set the orientation in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param quatW          Orientation quaternion W value.
     * @param quatX          Orientation quaternion X value.
     * @param quatY          Orientation quaternion Y value.
     * @param quatZ          Orientation quaternion Z value.
     */
    public native void setOrientation(final long pointerAddress, final float quatW, final float quatX, final float quatY, final float quatZ);

}

/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package jni;

/**
 * Native interface for the btghost.
 *
 * @author Grégory Van den Borre
 */
public class BulletGhostObjectNative {

    /**
     * Delete the object in native code and remove it from the world.
     *
     * @param pointerAddress Native btghost pointer address.
     * @param worldPointer   Native pointer address of the btdiscreetworld containing the ghost object.
     */
    public native void delete(final long pointerAddress, final long worldPointer);

    /**
     * Set the btghost position in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param x              Position X value to set to the btghost.
     * @param y              Position Y value to set to the btghost.
     * @param z              Position Z value to set to the btghost.
     */
    public native void setPosition(final long pointerAddress, final float x, final float y, final float z);

    public native float[] getPosition(long pointerAddress);
}

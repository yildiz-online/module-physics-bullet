/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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
package be.yildizgames.module.physics.bullet.internal;

public interface BulletBodyImplementation {

    /**
     * Set active or not the native object on physic simulation.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param activate       <code>true</code> to activate it, <code>false</code> to deactivate it.
     */
    void setActivate(long pointerAddress, boolean activate);

    /**
     * Delete the object in native code and remove it from the world.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param worldPointer   Native pointer address of the btdiscreetworld containing the body.
     */
    void delete(long pointerAddress, long worldPointer);

    /**
     * Scale the physic body.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param x              X scale factor.
     * @param y              Y scale factor.
     * @param z              Z scale factor.
     */
    void scale(long pointerAddress, float x, float y, float z);

    /**
     * Retrieve the current position in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @return An Array with X, Y, Z position coordinates [ensure result.size == 3].
     */
    float[] getPosition(long pointerAddress);

    /**
     * Retrieve the current direction in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @return An Array with X, Y, Z direction coordinates [ensure result.size == 3].
     */
    float[] getDirection(long pointerAddress);
}

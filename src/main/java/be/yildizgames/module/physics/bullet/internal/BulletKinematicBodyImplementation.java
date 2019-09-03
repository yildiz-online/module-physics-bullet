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

public interface BulletKinematicBodyImplementation extends BulletBodyImplementation {

    /**
     * Set the position in native code.
     *
     * @param pointerAddress Pointer address for the btbody.
     * @param x              Position X value.
     * @param y              Position Y value.
     * @param z              Position Z value.
     */
    void setPosition(long pointerAddress, float x, float y, float z);

    /**
     * Set the direction in native code.
     *
     * @param pointerAddress Pointer address for the btbody.
     * @param x              Direction X value.
     * @param y              Direction Y value.
     * @param z              Direction Z value.
     */
    void setDirection(long pointerAddress, float x, float y, float z);

    /**
     * Make a rotation in native code.
     *
     * @param pointerAddress Pointer address for the btbody.
     * @param w              Rotation W value.
     * @param x              Rotation X value.
     * @param y              Rotation Y value.
     * @param z              Rotation Z value.
     */
    void rotate(long pointerAddress, float w, float x, float y, float z);
}

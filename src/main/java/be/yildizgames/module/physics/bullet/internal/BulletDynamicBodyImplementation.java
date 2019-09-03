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

public interface BulletDynamicBodyImplementation extends BulletBodyImplementation {

    /**
     * Set the btbody position in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param x              Position X value to set to the btbody.
     * @param y              Position Y value to set to the btbody.
     * @param z              Position Z value to set to the btbody.
     */
    void setPosition(long pointerAddress, float x, float y, float z);

    /**
     * Set the body direction.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param x              Direction X value.
     * @param y              Direction Y value.
     * @param z              Direction Z value.
     */
    void setDirection(long pointerAddress, float x, float y, float z);

    /**
     * Set the orientation in native code.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param w              Orientation quaternion W value.
     * @param x              Orientation quaternion X value.
     * @param y              Orientation quaternion Y value.
     * @param z              Orientation quaternion Z value.
     */
    void setOrientation(long pointerAddress, float w, float x, float y, float z);

    /**
     * Apply a force to the body.
     *
     * @param pointerAddress Native btbody pointer address.
     * @param x              Direction X value.
     * @param y              Direction Y value.
     * @param z              Direction Z value.
     */
    void applyForce(long pointerAddress, float x, float y, float z);
}

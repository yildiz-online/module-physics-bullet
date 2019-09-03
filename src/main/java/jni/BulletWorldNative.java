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

package jni;

import be.yildizgames.module.physics.bullet.internal.BulletWorldImplementation;

/**
 * @author Grégory Van den Borre
 */
public class BulletWorldNative implements BulletWorldImplementation {

    @Override
    public native long constructor();

    @Override
    public native long[] raycast(final long pointerAddress, final float beginX, final float beginY, final float beginZ, final float endX, final float endY, final float endZ);

    @Override
    public native long simpleRaycast(final long pointerAddress, final float beginX, final float beginY, final float beginZ, final float endX, final float endY, final float endZ);

    @Override
    public native long deserializeMesh(final long pointerAddress, final String file);

    @Override
    public native void setGravity(final long pointerAddress, final float gravityX, final float gravityY, final float gravityZ);

    @Override
    public native long createStaticBody(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z, final float dX, final float dY, final float dZ);

    @Override
    public native long createKinematicBody(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z);

    @Override
    public native long createDynamicBody(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z, final float mass);

    @Override
    public native long createBoxShape(final float width, final float height, final float depth);

    @Override
    public native long createPlaneShape(int width, int depth);

    @Override
    public native long createSphereShape(final float radius);

    @Override
    public native long[] update(final long pointerAddress, final long time);

    @Override
    // FIXME check if unused, delete here and in native code.
    public native void removeBody(final long pointerAddress, final long bodyPointer);

    @Override
    public native long createGhostObject(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z);

    @Override
    public native long[] getGhostCollisionResult(final long pointerAddress);

    @Override
    public native void delete(final long pointerAddress);
}

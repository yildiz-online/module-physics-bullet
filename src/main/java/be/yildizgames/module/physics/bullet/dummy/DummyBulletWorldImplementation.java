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
package be.yildizgames.module.physics.bullet.dummy;

import be.yildizgames.module.physics.bullet.internal.BulletWorldImplementation;

public class DummyBulletWorldImplementation implements BulletWorldImplementation {

    @Override
    public long constructor() {
        return 0;
    }

    @Override
    public long[] raycast(long pointerAddress, float beginX, float beginY, float beginZ, float endX, float endY, float endZ) {
        return new long[0];
    }

    @Override
    public long simpleRaycast(long pointerAddress, float beginX, float beginY, float beginZ, float endX, float endY, float endZ) {
        return 0;
    }

    @Override
    public long deserializeMesh(long pointerAddress, String file) {
        return 0;
    }

    @Override
    public void setGravity(long pointerAddress, float gravityX, float gravityY, float gravityZ) {
        //does nothing
    }

    @Override
    public long createStaticBody(long pointerAddress, long shape, long id, float x, float y, float z, float dX, float dY, float dZ) {
        return 0;
    }

    @Override
    public long createKinematicBody(long pointerAddress, long shape, long id, float x, float y, float z) {
        return 0;
    }

    @Override
    public long createDynamicBody(long pointerAddress, long shape, long id, float x, float y, float z, float mass) {
        return 0;
    }

    @Override
    public long createBoxShape(float width, float height, float depth) {
        return 0;
    }

    @Override
    public long createPlaneShape(int width, int depth) {
        return 0;
    }

    @Override
    public long createSphereShape(float radius) {
        return 0;
    }

    @Override
    public long[] update(long pointerAddress, long time) {
        return new long[0];
    }

    @Override
    public void removeBody(long pointerAddress, long bodyPointer) {
        //does nothing
    }

    @Override
    public long createGhostObject(long pointerAddress, long shape, long id, float x, float y, float z) {
        return 0;
    }

    @Override
    public long[] getGhostCollisionResult(long pointerAddress) {
        return new long[0];
    }

    @Override
    public void delete(long pointerAddress) {
        //does nothing
    }
}

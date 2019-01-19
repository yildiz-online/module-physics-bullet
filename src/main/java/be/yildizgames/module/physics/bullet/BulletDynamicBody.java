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

package be.yildizgames.module.physics.bullet;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.geometry.Quaternion;
import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.module.physics.DynamicBody;
import jni.BulletBodyNative;
import jni.BulletDynamicBodyNative;

/**
 * Bullet dynamic body.
 *
 * @author Grégory Van den Borre
 */
final class BulletDynamicBody extends BulletBody implements DynamicBody {

    /**
     * Native pointer for the btbody.
     */
    private final NativePointer pointer;

    /**
     * Contains the native calls.
     */
    private final BulletDynamicBodyNative bodyNative = new BulletDynamicBodyNative();

    private final BulletBodyNative bulletBodyNative = new BulletBodyNative();

    /**
     * Body current mass.
     */
    private final float mass;

    /**
     * Full constructor.
     *
     * @param bodyPointer  Pointer of the associated btbody object.
     * @param worldPointer Pointer of the associated btdiscreetworld object containing the body.
     * @param id           Body unique identifier.
     */
    BulletDynamicBody(final NativePointer bodyPointer, final NativePointer worldPointer, final EntityId id, final float mass) {
        super(bodyPointer, worldPointer, id);
        this.pointer = bodyPointer;
        this.mass = mass;
    }

    @Override
    public Point3D getPosition() {
        float[] v = this.bulletBodyNative.getPosition(this.pointer.getPointerAddress());
        return Point3D.valueOf(v[0], v[1], v[2]);
    }

    @Override
    public void setPosition(final float x, final float y, final float z) {
        this.bodyNative.setPosition(this.pointer.getPointerAddress(), x, y, z);
    }

    @Override
    public Point3D getDirection() {
        float[] v = this.bulletBodyNative.getDirection(this.pointer.getPointerAddress());
        return Point3D.valueOf(v[0], v[1], v[2]);
    }

    @Override
    public void setDirection(final float x, final float y, final float z) {
        this.bodyNative.setDirection(this.pointer.getPointerAddress(), x, y, z);
    }

    @Override
    public void setOrientation(final Quaternion q) {
        this.bodyNative.setOrientation(this.pointer.getPointerAddress(), q.w, q.x, q.y, q.z);
    }

    @Override
    public void setOrientation(final float x, final float y, final float z, final float w) {
        this.bodyNative.setOrientation(this.pointer.getPointerAddress(), w, x, y, z);
    }

    public float getMass() {
        return this.mass;
    }
}

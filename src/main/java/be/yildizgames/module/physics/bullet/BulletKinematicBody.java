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

package be.yildizgames.module.physics.bullet;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.geometry.Quaternion;
import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.module.physics.KinematicBody;
import jni.BulletBodyNative;
import jni.BulletKinematicBodyNative;

/**
 * A kinematic body is intended to be moved manually, it has a mass of 0 and is not affected by physic forces.
 *
 * @author Grégory Van den Borre
 */
final class BulletKinematicBody extends BulletBody implements KinematicBody {

    /**
     * Pointer to the native object (btbody).
     */
    private final NativePointer pointer;

    /**
     * Contains the native calls.
     */
    private final BulletKinematicBodyNative bodyNative = new BulletKinematicBodyNative();

    private final BulletBodyNative bulletBodyNative = new BulletBodyNative();

    /**
     * Full constructor.
     *
     * @param bodyPointer  Pointer of the associated btbody object.
     * @param worldPointer Pointer of the associated btdiscreetworld object containing the body.
     * @param id           Body unique identifier.
     */
    BulletKinematicBody(final NativePointer bodyPointer, final NativePointer worldPointer, final EntityId id) {
        super(bodyPointer, worldPointer, id);
        this.pointer = bodyPointer;
    }

    @Override
    public void setDirection(final float dirX, final float dirY, final float dirZ) {
        this.bodyNative.setDirection(this.pointer.getPointerAddress(), dirX, dirY, dirZ);
    }

    @Override
    public void setPosition(final float posX, final float posY, final float posZ) {
        this.bodyNative.setPosition(this.pointer.getPointerAddress(), posX, posY, posZ);
    }

    @Override
    public void setOrientation(final float x, final float y, final float z, final float w) {
        this.bodyNative.rotate(this.pointer.getPointerAddress(), w, x, y, z);
    }

    @Override
    public void setOrientation(final Quaternion q) {
        this.bodyNative.rotate(this.pointer.getPointerAddress(), q.w, q.x, q.y, q.z);
    }


    /**
     * @return The current position.
     */
    @Override
    public Point3D getPosition() {
        float[] v = this.bulletBodyNative.getPosition(this.pointer.getPointerAddress());
        return Point3D.valueOf(v[0], v[1], v[2]);
    }

    /**
     * @return The current direction.
     */
    @Override
    public Point3D getDirection() {
        float[] v = this.bulletBodyNative.getDirection(this.pointer.getPointerAddress());
        return Point3D.valueOf(v[0], v[1], v[2]);
    }
}

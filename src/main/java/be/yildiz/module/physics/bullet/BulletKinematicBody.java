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

package be.yildiz.module.physics.bullet;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.nativeresources.NativePointer;
import be.yildiz.common.vector.Point3D;
import be.yildiz.common.vector.Quaternion;
import be.yildiz.module.physics.KinematicBody;
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
        this.checkDeleted();
        this.bodyNative.setDirection(this.pointer.address, dirX, dirY, dirZ);
    }

    @Override
    public void setPosition(final float posX, final float posY, final float posZ) {
        this.checkDeleted();
        this.bodyNative.setPosition(this.pointer.address, posX, posY, posZ);
    }

    @Override
    public void setOrientation(final float x, final float y, final float z, final float w) {
        this.checkDeleted();
        this.bodyNative.rotate(this.pointer.address, w, x, y, z);
    }

    @Override
    public void setOrientation(final Quaternion q) {
        this.checkDeleted();
        this.bodyNative.rotate(this.pointer.address, q.w, q.x, q.y, q.z);
    }


    /**
     * @return The current position.
     */
    @Override
    public Point3D getPosition() {
        this.checkDeleted();
        return Point3D.xyz(this.bulletBodyNative.getPosition(this.pointer.address));
    }

    /**
     * @return The current direction.
     */
    @Override
    public Point3D getDirection() {
        this.checkDeleted();
        return Point3D.xyz(this.bulletBodyNative.getDirection(this.pointer.address));
    }
}

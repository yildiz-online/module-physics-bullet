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

import be.yildizgames.common.gameobject.Movable;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.jni.Native;
import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.module.physics.AbstractMovableObject;
import be.yildizgames.module.physics.GhostObject;
import jni.BulletGhostObjectNative;

/**
 * A ghost object is meant only to be aware of objects colliding it, it is useful as trigger for example.
 *
 * @author Grégory Van den Borre
 */
final class BulletGhostObject extends AbstractMovableObject implements GhostObject, Native {


    /**
     * Pointer address to the associated native object.
     */
    private final NativePointer pointer;

    /**
     * Pointer address to the world containing this object.
     */
    private final NativePointer worldPointer;

    /**
     * Contains the native calls.
     */
    private final BulletGhostObjectNative ghostNative = new BulletGhostObjectNative();

    private final EntityId id;

    /**
     * Create a new instance.
     *
     * @param id             Unique identifier.
     * @param pointerAddress Native address.
     * @param world          World containing this object native address.
     */
    BulletGhostObject(final EntityId id, final NativePointer pointerAddress, final NativePointer world) {
        super();
        this.id = id;
        this.pointer = pointerAddress;
        this.worldPointer = world;
    }

    @Override
    public NativePointer getPointer() {
        return this.pointer;
    }

    @Override
    public void delete() {
        this.ghostNative.delete(this.pointer.getPointerAddress(), this.worldPointer.getPointerAddress());
        this.pointer.delete();
    }

    @Override
    public Point3D getPosition() {
        var f = this.ghostNative.getPosition(this.pointer.getPointerAddress());
        return Point3D.valueOf(f[0], f[1], f[2]);
    }

    @Override
    public Point3D getDirection() {
        return Point3D.BASE_DIRECTION;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.ghostNative.setPosition(this.pointer.getPointerAddress(), x, y, z);
    }

    @Override
    public void setDirection(float dirX, float dirY, float dirZ) {
        //does nothing
    }

    @Override
    public final Movable getInternal() {
        return this;
    }

    @Override
    public final EntityId getId() {
        return id;
    }

}

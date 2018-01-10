/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.module.physics.bullet;

import be.yildiz.module.physics.AbstractMovableObject;
import be.yildiz.module.physics.BaseBody;
import be.yildizgames.common.geometry.Movable;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.nativeresources.Native;
import be.yildizgames.common.nativeresources.NativePointer;
import jni.BulletBodyNative;

/**
 * Common class for kinematic, static and dynamic bodies.
 *
 * @author Gregory Van den Borre
 */
abstract class BulletBody extends AbstractMovableObject implements Native, BaseBody {

    /**
     * Associated id.
     */
    private final EntityId id;

    /**
     * Contains the native calls.
     */
    private final BulletBodyNative nativeBody = new BulletBodyNative();

    /**
     * Pointer to the native object (btbody).
     */
    private final NativePointer pointer;

    /**
     * Pointer to the native world object (btdiscreteworld).
     */
    private final NativePointer world;

    /**
     * Flag to check if the object but be ignored in the physic simulation.
     */
    private boolean sleeping;

    /**
     * Full constructor.
     *
     * @param pointerAddress Pointer to the native object.
     * @param worldPointer   Pointer of the associated world.
     * @param id             Object unique Id.
     */
    protected BulletBody(final NativePointer pointerAddress, final NativePointer worldPointer, final EntityId id) {
        super();
        this.pointer = pointerAddress;
        this.world = worldPointer;
        this.sleeping = false;
        this.id = id;
    }

    /**
     * Delete the bullet object.
     */
    @Override
    public final void delete() {
        this.nativeBody.delete(this.pointer.getPointerAddress(), this.world.getPointerAddress());
        this.pointer.delete();
    }

    @Override
    public Point3D getPosition() {
        // Not final to be overidden by static body
        float[] p = this.nativeBody.getPosition(this.pointer.getPointerAddress());
        return Point3D.valueOf(p[0], p[1], p[2]);
    }

    /**
     * Set this object activated or not.
     *
     * @param b If <code>true</code> the object will no longer be active in physic simulation, <code>false</code> will set it active.
     */
    @Override
    public final void sleep(final boolean b) {
        if (this.sleeping != b) {
            this.nativeBody.setActivate(this.pointer.getPointerAddress(), !b);
            this.sleeping = b;
        }
    }

    /**
     * Scale the physic body.
     *
     * @param x X scale factor.
     * @param y Y scale factor.
     * @param z Z scale factor.
     */
    @Override
    public final void scale(final float x, final float y, final float z) {
        this.nativeBody.scale(this.pointer.getPointerAddress(), x, y, z);
    }

    @Override
    public final EntityId getId() {
        return id;
    }

    @Override
    public final NativePointer getPointer() {
        return pointer;
    }

    @Override
    public final Movable getInternal() {
        return this;
    }
}

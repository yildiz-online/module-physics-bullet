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
import be.yildiz.common.nativeresources.Native;
import be.yildiz.common.nativeresources.NativePointer;
import be.yildiz.module.physics.BaseBody;
import jni.BulletBodyNative;
import lombok.Getter;

/**
 * Common class for kinematic, static and dynamic bodies.
 *
 * @author Gregory Van den Borre
 */
abstract class BulletBody implements Native, BaseBody {

    /**
     * Associated id.
     */
    @Getter
    private final EntityId id;

    /**
     * Contains the native calls.
     */
    private final BulletBodyNative nativeBody = new BulletBodyNative();

    /**
     * Pointer to the native object (btbody).
     */
    @Getter
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
}

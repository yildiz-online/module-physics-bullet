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

import be.yildiz.common.id.EntityId;
import be.yildiz.common.nativeresources.Native;
import be.yildiz.common.nativeresources.NativePointer;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.physics.AbstractMovableObject;
import be.yildiz.module.physics.GhostObject;
import jni.BulletGhostObjectNative;
import lombok.Getter;

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

    @Getter
    private final EntityId id;

    /**
     * Create a new instance.
     *
     * @param id             Unique identifier.
     * @param pointerAddress Native address.
     * @param world          World containing this object native address.
     * @param position       Object position when created.
     */
    BulletGhostObject(final EntityId id, final NativePointer pointerAddress, final NativePointer world, final Point3D position) {
        super(position);
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
    protected void setPositionImpl(final Point3D pos) {
        this.ghostNative.setPosition(this.pointer.getPointerAddress(), pos.x, pos.y, pos.z);
    }
}

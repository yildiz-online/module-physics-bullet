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

import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.module.physics.GhostObject;
import be.yildizgames.module.physics.PhysicObjectBuilder;
import be.yildizgames.module.physics.bullet.exception.IdNotProvidedException;
import be.yildizgames.module.physics.bullet.exception.ShapeNotProvidedException;
import be.yildizgames.module.physics.bullet.shape.BulletShapeProvider;
import jni.BulletWorldNative;

/**
 * @author Grégory Van den Borre
 */
class BulletPhysicObjectBuilder extends PhysicObjectBuilder {

    private final BulletShapeProvider provider;

    private final NativePointer worldPointer;

    private BulletWorldNative worldNative = new BulletWorldNative();

    BulletPhysicObjectBuilder(BulletShapeProvider provider, NativePointer worldPointer) {
        this.provider = provider;
        this.worldPointer = worldPointer;
    }

    @Override
    public BulletStaticBody buildStatic() {
        if(this.id == null) {
            throw new IdNotProvidedException();
        }
        final long bodyAddress = this.worldNative.createStaticBody(this.worldPointer.getPointerAddress(), this.getShapePointer().getPointerAddress(), id.value, position.x, position.y, position.z, direction.x, direction.y, direction.z);
        return new BulletStaticBody(NativePointer.create(bodyAddress), this.worldPointer, position, direction, id);
    }

    @Override
    public BulletKinematicBody buildKinematic() {
        if(this.id == null) {
            throw new IdNotProvidedException();
        }
        final long bodyAddress = this.worldNative.createKinematicBody(this.worldPointer.getPointerAddress(), this.getShapePointer().getPointerAddress(), id.value, position.x, position.y, position.z);
        return new BulletKinematicBody(NativePointer.create(bodyAddress), this.worldPointer, id);
    }

    @Override
    public BulletDynamicBody buildDynamic() {
        if(this.id == null) {
            throw new IdNotProvidedException();
        }
        final long bodyAddress = this.worldNative.createDynamicBody(this.worldPointer.getPointerAddress(), this.getShapePointer().getPointerAddress(), id.value, position.x, position.y, position.z, mass);
        return new BulletDynamicBody(NativePointer.create(bodyAddress), this.worldPointer, id, mass);
    }

    @Override
    public GhostObject buildGhost() {
        if(this.id == null) {
            throw new IdNotProvidedException();
        }
        final long ghostAddress = this.worldNative.createGhostObject(this.worldPointer.getPointerAddress(), this.getShapePointer().getPointerAddress(), id.value, position.x, position.y, position.z);
        return new BulletGhostObject(id, NativePointer.create(ghostAddress), this.worldPointer);
    }


    private NativePointer getShapePointer() {
        if(this.box != null) {
            return this.provider.getShape(this.box);
        } else if(this.sphere != null) {
            return this.provider.getShape(sphere);
        } else if(this.plane != null) {
            return this.provider.getShape(plane);
        } else if (this.mesh != null) {
            return this.provider.getShape(this.mesh);
        }
        throw new ShapeNotProvidedException();
    }
}

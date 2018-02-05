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

package be.yildiz.module.physics.bullet;

import be.yildiz.module.physics.StaticBody;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.nativeresources.NativePointer;

/**
 * A static body is intended to stay at its original position and will never move, it has a mass of 0 and is not affected by physic forces.
 *
 * @author Grégory Van den Borre
 */
final class BulletStaticBody extends BulletBody implements StaticBody {

    private final Point3D position;

    private final Point3D direction;

    /**
     * Full constructor.
     *
     * @param pointerAddress Pointer to the native object.
     * @param worldPointer   World pointer address.
     * @param id             Associated id.
     */
    BulletStaticBody(final NativePointer pointerAddress, final NativePointer worldPointer, final Point3D position, final Point3D direction, final EntityId id) {
        super(pointerAddress, worldPointer, id);
        this.position = position;
        this.direction = direction;
    }

    @Override
    public Point3D getPosition() {
        return position;
    }

    @Override
    public Point3D getDirection() {
        return direction;
    }

    @Override
    public void setPosition(float posX, float posY, float posZ) {
        //does nothing.
    }

    @Override
    public void setDirection(float dirX, float dirY, float dirZ) {
        //does nothing.
    }
}

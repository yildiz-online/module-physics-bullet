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

import be.yildizgames.module.physics.bullet.dummy.DummyBulletWorldImplementation;
import be.yildizgames.common.geometry.Point3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BulletWorldTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            var world = givenABulletWorld();
            Assertions.assertNotNull(world);
        }
    }

    @Nested
    class CreateObject {

        @Test
        void happyFlow() {
            var world = givenABulletWorld();
            Assertions.assertNotNull(world.createObject());
        }
    }

    @Nested
    class ThrowRay {

        @Test
        void happyFlow() {
            var world = givenABulletWorld();
            var result = world.throwRay(Point3D.valueOf(1,2,3), Point3D.valueOf(4,5,6));
            Assertions.assertNotNull(result);
        }

        @Test
        void nullOrigin() {
            var world = givenABulletWorld();
            Assertions.assertThrows(NullPointerException.class, () -> world.throwRay(null, Point3D.valueOf(4,5,6)));
        }

        @Test
        void nullDestination() {
            var world = givenABulletWorld();
            Assertions.assertThrows(NullPointerException.class, () -> world.throwRay(Point3D.valueOf(1,2,3), null));
        }

    }

    private static BulletWorld givenABulletWorld() {
        return new BulletWorld(new DummyBulletWorldImplementation());
    }
}

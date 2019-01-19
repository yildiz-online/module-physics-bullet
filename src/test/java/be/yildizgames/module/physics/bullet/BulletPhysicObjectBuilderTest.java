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

import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.shape.Box;
import be.yildizgames.module.physics.bullet.exception.IdNotProvidedException;
import be.yildizgames.module.physics.bullet.exception.ShapeNotProvidedException;
import be.yildizgames.module.physics.bullet.shape.BulletShapeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BulletPhysicObjectBuilderTest {

    @Nested
    class BuildStatic {

        @Test
        void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildStatic);
        }

        @Test
        void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildStatic());
        }
    }

    @Nested
    class BuildKinematic {

        @Test
        void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildKinematic);
        }

        @Test
        void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildKinematic());
        }
    }

    @Nested
    class BuildDynamic {

        @Test
        void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildDynamic);
        }

        @Test
        void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildDynamic());
        }
    }

    @Nested
    class BuildGhost {

        @Test
        void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildGhost);
        }

        @Test
        void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildGhost());
        }
    }

    private static BulletPhysicObjectBuilder givenABuilder() {
        NativePointer p = NativePointer.create(5);
        BulletShapeProvider provider = Mockito.mock(BulletShapeProvider.class);
        Mockito.when(provider.getShape(Box.cube(3))).thenReturn(NativePointer.create(3));
        return new BulletPhysicObjectBuilder(provider, p);
    }

}

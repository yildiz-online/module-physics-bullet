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
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.module.physics.PhysicMesh;
import be.yildizgames.module.physics.bullet.exception.IdNotProvidedException;
import be.yildizgames.module.physics.bullet.exception.ShapeNotProvidedException;
import be.yildizgames.module.physics.bullet.shape.BulletShapeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Grégory Van den Borre
 */
public class BulletPhysicObjectBuilderTest {

    @Nested
    public class BuildStatic {

        @Test
        public void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildStatic);
        }

        @Test
        public void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildStatic());
        }
    }

    @Nested
    public class BuildKinematic {

        @Test
        public void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildKinematic);
        }

        @Test
        public void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildKinematic());
        }
    }

    @Nested
    public class BuildDynamic {

        @Test
        public void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildDynamic);
        }

        @Test
        public void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildDynamic());
        }
    }

    @Nested
    public class BuildGhost {

        @Test
        public void noId(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(IdNotProvidedException.class, builder::buildGhost);
        }

        @Test
        public void noShape(){
            BulletPhysicObjectBuilder builder = givenABuilder();
            Assertions.assertThrows(ShapeNotProvidedException.class, () -> builder.withId(1).buildGhost());
        }
    }

    private static BulletPhysicObjectBuilder givenABuilder() {
        NativePointer p = NativePointer.create(5);
        BulletShapeProvider provider = new DummyBulletShapeProvider();
        return new BulletPhysicObjectBuilder(provider, p);
    }

    private static class DummyBulletShapeProvider implements BulletShapeProvider {

        @Override
        public NativePointer getShape(Box box) {
            if(box.depth == 3 && box.height == 3 && box.width == 3) {
                return NativePointer.create(3);
            }
            return NativePointer.create(10);
        }

        @Override
        public NativePointer getShape(Sphere sphere) {
            return null;
        }

        @Override
        public NativePointer getShape(Plane plane) {
            return null;
        }

        @Override
        public NativePointer getShape(PhysicMesh mesh) {
            return null;
        }
    }

}

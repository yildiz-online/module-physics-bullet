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

package be.yildiz.module.physics.bullet.test;

import be.yildiz.common.nativeresources.NativeOperatingSystem;
import be.yildiz.common.nativeresources.NativeResourceLoader;
import be.yildiz.common.nativeresources.SystemLinux64;
import be.yildiz.common.nativeresources.SystemWin32;
import be.yildiz.common.shape.Box;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.physics.PhysicEngine;
import be.yildiz.module.physics.GhostObject;
import be.yildiz.module.physics.KinematicBody;
import be.yildiz.module.physics.PhysicWorld;
import be.yildiz.module.physics.bullet.BulletPhysicEngine;

/**
 * @author Grégory Van den Borre
 */
public class ManualTest {

    public static void main(String[] s) throws InterruptedException {
        NativeOperatingSystem[] systems = {
                new SystemLinux64(),
                new SystemWin32()
        };
        PhysicEngine engine = BulletPhysicEngine.create(NativeResourceLoader.inJar(systems));

        PhysicWorld world = engine.createWorld();

        world.addCollisionListener(objects -> System.out.println("objects:" + objects.object1 + ", " + objects.object2));
        world.createObject()
                .atPosition(Point3D.valueOf(10))
                .withId(1L)
                .withShape(new Box(5))
                .buildKinematic();
        KinematicBody body2 = world.createObject()
                .atPosition(Point3D.valueOf(500))
                .withShape(new Box(5))
                .withId(2L)
                .buildKinematic();

        engine.update();
        Thread.sleep(50);
        engine.update();
        body2.setPosition(11, 11, 11);
        engine.update();
        Thread.sleep(50);
        engine.update();


        world.addGhostCollisionListener(objects -> System.out.println("ghost:" + objects.object1 + ", " + objects.object2));
        GhostObject g = world.createObject()
                .withId(5L)
                .withShape(new Box(5))
                .atPosition(Point3D.valueOf(100))
                .buildGhost();
        engine.update();
        Thread.sleep(50);
        engine.update();
        g.setPosition(11, 11, 11);
        engine.update();
        Thread.sleep(50);
        engine.update();

        engine.close();
    }

}

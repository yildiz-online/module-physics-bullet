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

package be.yildiz.module.physics.bullet.test;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.shape.Box;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.physics.AbstractPhysicEngine;
import be.yildiz.module.physics.GhostObject;
import be.yildiz.module.physics.KinematicBody;
import be.yildiz.module.physics.PhysicWorld;
import be.yildiz.module.physics.bullet.BulletPhysicEngine;

/**
 * @author Grégory Van den Borre
 */
public class ManualTest {

    public static void main(String[] s) throws InterruptedException {
        AbstractPhysicEngine engine = new BulletPhysicEngine();

        PhysicWorld world = engine.createPhysicWorld();

        world.addCollisionListener(objects -> System.out.println("objects:" + objects.object1 + ", " + objects.object2));
        KinematicBody body = world.createKinematicBody(EntityId.get(1L), new Box(5), Point3D.xyz(10));
        KinematicBody body2 = world.createKinematicBody(EntityId.get(2L), new Box(5), Point3D.xyz(500));
        engine.update();
        Thread.sleep(50);
        engine.update();
        body2.setPosition(11, 11, 11);
        engine.update();
        Thread.sleep(50);
        engine.update();


        world.addGhostCollisionListener(objects -> System.out.println("ghost:" + objects.object1 + ", " + objects.object2));
        GhostObject g = world.createGhostObject(EntityId.get(5l), new Box(5), Point3D.xyz(100));
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
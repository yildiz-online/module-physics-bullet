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

import be.yildiz.common.log.Logger;
import be.yildiz.common.nativeresources.NativeResourceLoader;
import be.yildiz.module.physics.AbstractPhysicEngine;
import be.yildiz.module.physics.PhysicWorld;

/**
 * Bullet implementation for the physic engine.
 *
 * @author Van den Borre Grégory
 */
public final class BulletPhysicEngine extends AbstractPhysicEngine {

    /**
     * Simple constructor, load the native lib.
     */
    public BulletPhysicEngine() {
        super();
        Logger.info("Initializing Bullet physic engine...");
        NativeResourceLoader.loadBaseLibrary("libwinpthread-1", "libgcc_s_seh-1", "libstdc++-6");
        NativeResourceLoader.loadLibrary("libbullet");
        Logger.info("Bullet physic engine initialized.");
    }

    @Override
    protected PhysicWorld createPhysicWorldImpl() {
        return new BulletWorld();
    }
}

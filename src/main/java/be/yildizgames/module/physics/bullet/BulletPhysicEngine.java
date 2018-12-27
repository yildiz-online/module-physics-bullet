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

import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.physics.BasePhysicEngine;
import be.yildizgames.module.physics.PhysicWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bullet implementation for the physic engine.
 *
 * @author Van den Borre Grégory
 */
public final class BulletPhysicEngine extends BasePhysicEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulletPhysicEngine.class);

    /**
     * Simple constructor, load the native lib.
     * @param loader Loader for the native libraries.
     * @throws AssertionError if loader is null.
     */
    private BulletPhysicEngine(final NativeResourceLoader loader) {
        super();
        assert loader != null;
        LOGGER.info("Initializing Bullet physic engine...");
        loader.loadBaseLibrary();
        loader.loadLibrary(
                "libBullet3Common",
                "libLinearMath",
                "libBulletCollision",
                "libBulletDynamics",
                "libBulletInverseDynamics",
                "libyildizbullet");
        LOGGER.info("Bullet physic engine initialized.");
    }

    /**
     * Create a new bullet physics engine.
     * @param loader Loader for the native libraries.
     * @return The created engine.
     * @throws AssertionError if loader is null.
     */
    public static BulletPhysicEngine create(final NativeResourceLoader loader) {
        return new BulletPhysicEngine(loader);
    }

    @Override
    protected PhysicWorld createPhysicWorldImpl() {
        return new BulletWorld();
    }
}

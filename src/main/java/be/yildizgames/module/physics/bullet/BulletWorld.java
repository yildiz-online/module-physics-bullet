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

import be.yildizgames.common.exception.business.InvalidIdException;
import be.yildizgames.common.file.exception.FileMissingException;
import be.yildizgames.common.gameobject.CollisionListener;
import be.yildizgames.common.gameobject.CollisionResult;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.jni.Native;
import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.common.time.Timer;
import be.yildizgames.module.physics.Gravity;
import be.yildizgames.module.physics.PhysicMesh;
import be.yildizgames.module.physics.PhysicWorld;
import be.yildizgames.module.physics.RaycastResult;
import be.yildizgames.module.physics.World;
import be.yildizgames.module.physics.bullet.shape.BulletShapeProvider;
import jni.BulletWorldNative;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bullet implementation for an abstract world, provide basic facilities to create and manage bullet objects.
 *
 * @author Grégory Van den Borre
 */
final class BulletWorld implements PhysicWorld, Native, BulletShapeProvider {


    private static final System.Logger LOGGER = System.getLogger(BulletWorld.class.getName());

    /**
     * Contains all listeners to notify when a collision occurs or is lost.
     */
    private final List<CollisionListener> collisionListeners = new ArrayList<>();
    /**
     * Contains all listeners to notify when a collision occurs or is lost with a ghost object.
     */
    private final List<CollisionListener> ghostCollisionListeners = new ArrayList<>();
    /**
     * Contains The pointer for the btshape associated to PhysicMesh object.
     */
    private final Map<String, NativePointer> shapeList = new HashMap<>();
    /**
     * Contains The pointer for the btshape associated to Box object.
     */
    private final Map<Box, NativePointer> boxList = new HashMap<>();

    /**
     * Contains The pointer for the btshape associated to Plane object.
     */
    private final Map<Plane, NativePointer> planeList = new HashMap<>();

    /**
     * Contains The pointer for the btshape associated to Sphere object.
     */
    private final Map<Sphere, NativePointer> sphereList = new HashMap<>();
    /**
     * Contains the native calls.
     */
    private final BulletWorldNative worldNative = new BulletWorldNative();
    /**
     * Pointer address to the native associated yz::World.
     */
    private final NativePointer pointer = NativePointer.create(this.worldNative.constructor());
    /**
     * Timer to compute time between 2 updates.
     */
    private final Timer timer = new Timer();
    /**
     * Contains all collisions between objects for the current physic state.
     */
    private List<CollisionResult> collisions = new ArrayList<>();
    /**
     * Contains all collisions between objects and ghost objects for the current physic state.
     */
    private List<CollisionResult> ghostCollisions = new ArrayList<>();
    /**
     * Current gravity applied on this world.
     */
    private Point3D gravity = Point3D.ZERO;

    /**
     * Create the world and update it to initiate the inner timer.
     */
    BulletWorld() {
        super();
        this.update();
    }

    @Override
    public void update() {
        final List<CollisionResult> newCollisions = this.getCollisionList();
        final List<CollisionResult> oldCollisions = this.collisions;
        for (final CollisionResult pair : newCollisions) {
            if (!oldCollisions.contains(pair)) {
                this.collisionListeners.forEach(l -> l.newCollision(pair));
            }
        }

        for (final CollisionResult pair : oldCollisions) {
            if (!newCollisions.contains(pair)) {
                this.collisionListeners.forEach(l -> l.lostCollision(pair));
            }
        }
        this.collisions.clear();
        this.collisions = newCollisions;

        // Ghost objects
        final List<CollisionResult> newGhostCollisions = this.getGhostCollisionList();
        final List<CollisionResult> oldGhostCollisions = this.ghostCollisions;

        for (final CollisionResult pair : newGhostCollisions) {
            if (!oldGhostCollisions.contains(pair)) {
                this.ghostCollisionListeners.forEach(l -> l.newCollision(pair));
            }
        }

        for (final CollisionResult pair : oldGhostCollisions) {
            if (!newGhostCollisions.contains(pair)) {
                this.ghostCollisionListeners.forEach(l -> l.lostCollision(pair));
            }
        }
        this.ghostCollisions.clear();
        this.ghostCollisions = newGhostCollisions;
    }

    private List<CollisionResult> getCollisionList() {
        final long[] result = this.worldNative.update(this.pointer.getPointerAddress(), this.timer.getActionTime());
        final List<CollisionResult> foundCollisions = new ArrayList<>(result.length);
        for (int i = 0; i < result.length; i += 2) {
            long first = result[i];
            long second = result[i + 1];
            if (first != 0L && second != 0L) {
                EntityId e1 = EntityId.valueOf(first);
                EntityId e2 = EntityId.valueOf(second);
                if (!e1.equals(e2)) {
                    foundCollisions.add(new CollisionResult(e1, e2));
                }
            }
        }
        return foundCollisions;
    }

    private List<CollisionResult> getGhostCollisionList() {
        final long[] result = this.worldNative.getGhostCollisionResult(this.pointer.getPointerAddress());
        final List<CollisionResult> foundCollisions = new ArrayList<>();
        for (int i = 0; i < result.length; i += 2) {
            if (result[i] != 0 && result[i + 1] != 0) {
                try {
                    EntityId e1 = EntityId.valueOf(result[i]);
                    EntityId e2 = EntityId.valueOf(result[i + 1]);
                    // FIXME in native code, do not return already existing
                    // collision, use event instead(collision event will
                    // populate a list, clean it once retrieved, collision lost
                    // will populate another list, clean it once retrieved)
                    if (!e1.equals(e2)) {
                        foundCollisions.add(new CollisionResult(e1, e2));
                    }
                } catch (InvalidIdException e) {
                    LOGGER.log(System.Logger.Level.ERROR,"Invalid id:", e);
                    // FIXME notify destruction instead
                }
            }
        }
        return foundCollisions;
    }

    @Override
    public RaycastResult throwRay(final Point3D origin, final Point3D destination) {
        final int raycastId = 0;
        final int raycastX = 1;
        final int raycastY = 2;
        final int raycastZ = 3;
        final long[] values = this.worldNative.raycast(this.pointer.getPointerAddress(), origin.x, origin.y, origin.z, destination.x, destination.y, destination.z);
        return new RaycastResult(values[raycastX], values[raycastY], values[raycastZ], values[raycastId]);
    }

    @Override
    public EntityId throwSimpleRay(final Point3D origin, final Point3D destination) {
        assert origin != null;
        assert destination != null;
        return EntityId.valueOf(this.worldNative.simpleRaycast(this.pointer.getPointerAddress(), origin.x, origin.y, origin.z, destination.x, destination.y, destination.z));
    }

    @Override
    public EntityId throwSimpleRay(final Point3D origin, final Point3D direction, final float distance) {
        assert origin != null;
        assert direction != null;
        assert distance >= 0;
        Point3D end = Point3D.normalizeAndMultiply(direction, distance);
        return this.throwSimpleRay(origin, end);
    }

    @Override
    public NativePointer getShape(final Box box) {
        assert box != null;
        return this.boxList.computeIfAbsent(box, b ->
            NativePointer.create(this.worldNative.createBoxShape(b.width, b.height, b.depth)));
    }

    @Override
    public NativePointer getShape(final Sphere sphere) {
        assert sphere != null;
        return this.sphereList.computeIfAbsent(sphere, s -> NativePointer.create(this.worldNative.createSphereShape(s.radius)));
    }

    @Override
    public NativePointer getShape(final Plane plane) {
        assert plane != null;
        return this.planeList.computeIfAbsent(plane, p -> NativePointer.create(this.worldNative.createPlaneShape(p.width, p.depth)));
    }


    @Override
    public NativePointer getShape(final PhysicMesh mesh) {
        assert mesh != null;
        NativePointer shapePointer = this.shapeList.get(mesh.file);
        if (shapePointer == null) {
            final File file = new File(mesh.file);
            if (!file.exists()) {
                throw new FileMissingException("No physic trimesh for " + file.getAbsolutePath());
            }
            final long address = this.worldNative.deserializeMesh(this.pointer.getPointerAddress(), file.getAbsolutePath());
            shapePointer = NativePointer.create(address);
            this.shapeList.put(mesh.file, shapePointer);
        }
        return shapePointer;
    }

    @Override
    public BulletPhysicObjectBuilder createObject() {
        return new BulletPhysicObjectBuilder(this, this.pointer);
    }

    @Override
    public void delete() {
        this.worldNative.delete(this.pointer.getPointerAddress());
        this.pointer.delete();
    }

    @Override
    public World setGravity(final Gravity gravityValue) {
        this.setGravity(0, gravityValue.value, 0);
        return this;
    }

    @Override
    public void setGravity(final float gravityX, final float gravityY, final float gravityZ) {
        this.gravity = Point3D.valueOf(gravityX, gravityY, gravityZ);
        this.worldNative.setGravity(this.pointer.getPointerAddress(), gravityX, -gravityY, gravityZ);
    }

    @Override
    public final void addCollisionListener(final CollisionListener listener) {
        assert listener != null;
        this.collisionListeners.add(listener);
    }

    @Override
    public final void addGhostCollisionListener(final CollisionListener listener) {
        assert listener != null;
        this.ghostCollisionListeners.add(listener);
    }

    @Override
    public NativePointer getPointer() {
        return pointer;
    }

    @Override
    public Point3D getGravity() {
        return gravity;
    }
}

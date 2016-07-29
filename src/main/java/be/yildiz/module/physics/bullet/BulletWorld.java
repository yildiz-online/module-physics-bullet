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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.exeption.InvalidIdException;
import be.yildiz.common.exeption.ResourceMissingException;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.log.Logger;
import be.yildiz.common.nativeresources.Native;
import be.yildiz.common.nativeresources.NativePointer;
import be.yildiz.common.shape.Box;
import be.yildiz.common.shape.Sphere;
import be.yildiz.common.util.Checker;
import be.yildiz.common.util.Timer;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.physics.*;
import jni.BulletWorldNative;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Bullet implementation for an abstract world, provide basic facilities to create and manage bullet objects.
 *
 * @author Grégory Van den Borre
 */
public final class BulletWorld implements PhysicWorld, Native {

    /**
     * Contains all listeners to notify when a collision occurs or is lost.
     */
    private final List<CollisionListener> collisionListeners = Lists.newList();
    /**
     * Contains all listeners to notify when a collision occurs or is lost with a ghost object.
     */
    private final List<CollisionListener> ghostCollisionListeners = Lists.newList();
    /**
     * Contains The pointer for the btshape associated to PhysicMesh object.
     */
    private final Map<String, NativePointer> shapeList = Maps.newMap();
    /**
     * Contains The pointer for the btshape associated to Box object.
     */
    private final Map<Box, NativePointer> boxList = Maps.newMap();
    /**
     * Contains The pointer for the btshape associated to Sphere object.
     */
    private final Map<Sphere, NativePointer> sphereList = Maps.newMap();
    /**
     * Contains the native calls.
     */
    private final BulletWorldNative worldNative = new BulletWorldNative();
    /**
     * Pointer address to the native associated YZ::World.
     */
    @Getter
    private final NativePointer pointer = NativePointer.create(this.worldNative.constructor());
    /**
     * Timer to compute time between 2 updates.
     */
    private final Timer timer = new Timer();
    /**
     * Contains all collisions between objects for the current physic state.
     */
    private List<CollisionResult> collisions = Lists.newList();
    /**
     * Contains all collisions between objects and ghost objects for the current physic state.
     */
    private List<CollisionResult> ghostCollisions = Lists.newList();
    /**
     * Current gravity applied on this world.
     */
    @Getter
    private Point3D gravity = Point3D.ZERO;

    /**
     * Create the world and update it to initiate the inner timer.
     */
    BulletWorld() {
        super();
        this.update();
    }

    /**
     * Update the world and compute all collision.
     */
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
        final long[] result = this.worldNative.update(this.pointer.address, this.timer.getActionTime());
        final List<CollisionResult> collisions = Lists.newList(result.length);
        for (int i = 0; i < result.length; i += 2) {
            long first = result[i];
            long second = result[i + 1];
            if (first != 0L && second != 0L) {
                EntityId e1 = EntityId.get(first);
                EntityId e2 = EntityId.get(second);
                if (!e1.equals(e2)) {
                    collisions.add(new CollisionResult(e1, e2));
                }
            }
        }
        return collisions;
    }

    private List<CollisionResult> getGhostCollisionList() {
        final long[] result = this.worldNative.getGhostCollisionResult(this.pointer.address);
        final List<CollisionResult> collisions = Lists.newList();
        for (int i = 0; i < result.length; i += 2) {
            if (result[i] != 0 && result[i + 1] != 0) {
                try {
                    EntityId e1 = EntityId.get(result[i]);
                    EntityId e2 = EntityId.get(result[i + 1]);
                    // FIXME in native code, do not return already existing
                    // collision, use event instead(collision event will
                    // populate a list, clean it once retrieved, collision lost
                    // will populate another list, clean it once retrieved)
                    if (!e1.equals(e2)) {
                        collisions.add(new CollisionResult(e1, e2));
                    }
                } catch (InvalidIdException e) {
                    Logger.error(e);
                    // FIXME notify destruction instead
                }
            }
        }
        return collisions;
    }

    @Override
    public RaycastResult throwRay(final Point3D origin, final Point3D destination) {
        final int raycastId = 0;
        final int raycastX = 1;
        final int raycastY = 2;
        final int raycastZ = 3;
        final long[] values = this.worldNative.raycast(this.pointer.address, origin.x, origin.y, origin.z, destination.x, destination.y, destination.z);
        return new RaycastResult(values[raycastX], values[raycastY], values[raycastZ], values[raycastId]);
    }

    @Override
    public EntityId throwSimpleRay(final Point3D origin, final Point3D destination) {
        assert Checker.notNull(origin);
        assert Checker.notNull(destination);
        return EntityId.get(this.worldNative.simpleRaycast(this.pointer.address, origin.x, origin.y, origin.z, destination.x, destination.y, destination.z));
    }

    @Override
    public EntityId throwSimpleRay(final Point3D origin, final Point3D direction, final float distance) {
        assert Checker.notNull(origin);
        assert Checker.notNull(direction);
        assert Checker.isPositive(distance);
        Point3D end = Point3D.normalizeAndMultiply(direction, distance);
        return this.throwSimpleRay(origin, end);
    }

    /**
     * Get the pointer to the btshape associated with this box.
     *
     * @param box Shape representation.
     * @return The pointer to the physique shape matching the Box.
     */
    public NativePointer getShape(@NonNull final Box box) {
        return this.boxList.computeIfAbsent(box, b -> {
            return NativePointer.create(this.worldNative.createBoxShape(b.width, b.height, b.depth));
        });
    }

    /**
     * Get the pointer to the btshape associated with this sphere.
     *
     * @param sphere Shape representation.
     * @return The pointer to the physique shape matching the Sphere.
     */
    public NativePointer getShape(@NonNull final Sphere sphere) {
        return this.sphereList.computeIfAbsent(sphere, s -> {
            return NativePointer.create(this.worldNative.createSphereShape(s.radius));
        });
    }

    /**
     * Get the pointer to the btshape associated with this mesh.
     *
     * @param mesh Shape representation.
     * @return The pointer to the physique shape matching the PhysicMesh.
     */
    public NativePointer getShape(final PhysicMesh mesh) {
        assert Checker.notNull(mesh);
        NativePointer shapePointer = this.shapeList.get(mesh.file);
        if (shapePointer == null) {
            final File file = new File(mesh.file);
            if (!file.exists()) {
                throw new ResourceMissingException("No physic trimesh for " + file.getAbsolutePath());
            }
            final long address = this.worldNative.deserializeMesh(this.pointer.address, file.getAbsolutePath());
            shapePointer = NativePointer.create(address);
            this.shapeList.put(mesh.file, shapePointer);
        }
        return shapePointer;
    }

    /**
     * Create a body non movable, not affected by forces with a 0 mass.
     *
     * @param shape     Body shape.
     * @param position  Initial and immutable position.
     * @param direction Initial and immutable direction.
     * @param id        Associated id.
     * @return The built object.
     */
    private BulletStaticBody createStaticBody(final NativePointer shape, final EntityId id, final Point3D position, final Point3D direction) {
        final long bodyAddress = this.worldNative.createStaticBody(this.pointer.address, shape.address, id.value, position.x, position.y, position.z, direction.x, direction.y, direction.z);
        return new BulletStaticBody(NativePointer.create(bodyAddress), this.pointer, id);

    }

    /**
     * Create a physic movable body, affected by forces.
     *
     * @param shape    Body shape.
     * @param id       Associated id.
     * @param position Body initial position.
     * @param mass     Object mass.
     * @return The built object.
     */
    private BulletDynamicBody createDynamicBody(final NativePointer shape, final EntityId id, final Point3D position, final float mass) {
        final long bodyAddress = this.worldNative.createDynamicBody(this.pointer.address, shape.address, id.value, position.x, position.y, position.z, mass);
        return new BulletDynamicBody(NativePointer.create(bodyAddress), this.pointer, id, mass);
    }

    /**
     * Create a manually movable body, not affected by forces with a 0 mass.
     *
     * @param shape    Body shape.
     * @param id       Associated id.
     * @param position Body initial position.
     * @return The built object.
     */
    private BulletKinematicBody createKinematicBody(final NativePointer shape, final EntityId id, final Point3D position) {
        final long bodyAddress = this.worldNative.createKinematicBody(this.pointer.address, shape.address, id.value, position.x, position.y, position.z);
        return new BulletKinematicBody(NativePointer.create(bodyAddress), this.pointer, id);
    }

    /**
     * Create a ghost object.
     *
     * @param shape    Shape to build the object.
     * @param id       Id associated to that ghost, will be return when collision occurs.
     * @param position Ghost object initial position.
     * @return The newly created object.
     */
    private BulletGhostObject createGhostObject(final NativePointer shape, final EntityId id, final Point3D position) {
        final long ghostAddress = this.worldNative.createGhostObject(this.pointer.address, shape.address, id.value, position.x, position.y, position.z);
        return new BulletGhostObject(id, NativePointer.create(ghostAddress), this.pointer, position);
    }

    @Override
    public GhostObject createGhostObject(final EntityId id, final Box box, final Point3D position) {
        return this.createGhostObject(this.getShape(box), id, position);
    }

    @Override
    public GhostObject createGhostObject(final EntityId id, final Sphere sphere, final Point3D position) {
        return this.createGhostObject(this.getShape(sphere), id, position);
    }

    @Override
    public void delete() {
        this.worldNative.delete(this.pointer.address);
    }

    @Override
    public BulletStaticBody createStaticBody(final EntityId id, final Box box, final Point3D position, final Point3D direction) {
        return this.createStaticBody(this.getShape(box), id, position, direction);
    }

    @Override
    public BulletStaticBody createStaticBody(final EntityId id, final Sphere sphere, final Point3D position, final Point3D direction) {
        return this.createStaticBody(this.getShape(sphere), id, position, direction);
    }

    @Override
    public BulletStaticBody createStaticBody(final EntityId id, final PhysicMesh mesh, final Point3D position, final Point3D direction) {
        return this.createStaticBody(this.getShape(mesh), id, position, direction);
    }

    @Override
    public BulletKinematicBody createKinematicBody(final EntityId id, final Box box, final Point3D position) {
        return this.createKinematicBody(this.getShape(box), id, position);
    }

    @Override
    public BulletKinematicBody createKinematicBody(final EntityId id, final Sphere sphere, final Point3D position) {
        return this.createKinematicBody(this.getShape(sphere), id, position);
    }

    @Override
    public BulletKinematicBody createKinematicBody(final EntityId id, final PhysicMesh mesh, final Point3D position) {
        return this.createKinematicBody(this.getShape(mesh), id, position);
    }

    @Override
    public DynamicBody createDynamicBody(EntityId id, Box box, Point3D position, float mass) {
        return this.createDynamicBody(this.getShape(box), id, position, mass);
    }

    @Override
    public DynamicBody createDynamicBody(EntityId id, Sphere sphere, Point3D position, float mass) {
        return this.createDynamicBody(this.getShape(sphere), id, position, mass);
    }

    @Override
    public DynamicBody createDynamicBody(EntityId id, PhysicMesh mesh, Point3D position, float mass) {
        return this.createDynamicBody(this.getShape(mesh), id, position, mass);
    }

    @Override
    public void setGravity(final Gravity gravityValue) {
        this.setGravity(0, gravityValue.value, 0);
    }

    @Override
    public void setGravity(final float gravityX, final float gravityY, final float gravityZ) {
        this.gravity = Point3D.xyz(gravityX, gravityY, gravityZ);
        this.worldNative.setGravity(this.pointer.address, gravityX, -gravityY, gravityZ);
    }

    @Override
    public final void addCollisionListener(@NonNull final CollisionListener listener) {
        this.collisionListeners.add(listener);
    }

    @Override
    public final void addGhostCollisionListener(@NonNull final CollisionListener listener) {
        this.ghostCollisionListeners.add(listener);
    }
}

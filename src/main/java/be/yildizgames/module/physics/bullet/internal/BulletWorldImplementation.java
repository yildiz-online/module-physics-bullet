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

package be.yildizgames.module.physics.bullet.internal;

/**
 * @author Grégory Van den Borre
 */
public interface BulletWorldImplementation {

    /**
     * Create a new yz::World in native code.
     *
     * @return The pointer value of the created object.
     */
    long constructor();

    /**
     * Throw a ray and retrieve its collision point and collided object.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param beginX         Ray origin X value.
     * @param beginY         Ray origin Y value.
     * @param beginZ         Ray origin Z value.
     * @param endX           Ray end X value.
     * @param endY           Ray end Y value.
     * @param endZ           Ray end Z value.
     * @return an array containing the id of the intersected object, the x, y and z values of the intersection point.
     */
    long[] raycast(long pointerAddress, float beginX, float beginY, float beginZ, float endX, float endY, float endZ);

    /**
     * Throw a ray and retrieve its collided object, faster than raycast.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param beginX         Ray origin X value.
     * @param beginY         Ray origin Y value.
     * @param beginZ         Ray origin Z value.
     * @param endX           Ray end X value.
     * @param endY           Ray end Y value.
     * @param endZ           Ray end Z value.
     * @return an array containing the id of the intersected object, the x, y and z values of the intersection point.
     */
    long simpleRaycast(long pointerAddress, float beginX, float beginY, float beginZ, float endX, float endY, float endZ);

    /**
     * Create a btshape from a serialized file.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param file           File to deserialize.
     * @return A pointer to the btshape retrieved from the serialized file.
     */
    long deserializeMesh(long pointerAddress, String file);

    /**
     * Set the gravity in native code.
     *
     * @param pointerAddress Pointer address of this associated YZ::World.
     * @param gravityX       Gravity X value.
     * @param gravityY       Gravity Y value.
     * @param gravityZ       Gravity Z value.
     */
    void setGravity(long pointerAddress, float gravityX, float gravityY, float gravityZ);

    /**
     * Create a static btrigidbody in native code.
     *
     * @param pointerAddress Pointer address of this associated btworld.
     * @param shape          btshape pointer to build the body.
     * @param id             Id to associate with the body.
     * @param x              Body position X value.
     * @param y              Body position Y value.
     * @param z              Body position Z value.
     * @param dX             Body direction X value.
     * @param dY             Body direction Y value.
     * @param dZ             Body direction Z value.
     * @return A pointer to the newly created btrigidbody.
     */
    long createStaticBody(long pointerAddress, long shape, long id, float x, float y, float z, float dX, float dY, float dZ);

    /**
     * Create a kinematic btrigidbody in native code.
     *
     * @param pointerAddress Pointer address of this associated btworld.
     * @param shape          btshape pointer to build the body.
     * @param id             Id to associate with the body.
     * @param x              Initial X position.
     * @param y              Initial Y position.
     * @param z              Initial Z position.
     * @return A pointer to the newly created btrigidbody.
     */
    long createKinematicBody(long pointerAddress, long shape, long id, float x, float y, float z);

    /**
     * Create a kinematic btrigidbody in native code.
     *
     * @param pointerAddress Pointer address of this associated btworld.
     * @param shape          btshape pointer to build the body.
     * @param id             Id to associate with the body.
     * @param x              Initial X position.
     * @param y              Initial Y position.
     * @param z              Initial Z position.
     * @param mass           Initial mass.
     * @return A pointer to the newly created btrigidbody.
     */
    long createDynamicBody(long pointerAddress, long shape, long id, float x, float y, float z, float mass);

    /**
     * Create a box btshape in native code.
     *
     * @param width  Box width size.
     * @param height Box height size.
     * @param depth  Box depth size.
     * @return A pointer to the newly create btshape.
     */
    long createBoxShape(float width, float height, float depth);

    long createPlaneShape(int width, int depth);

    /**
     * Create a sphere btshape in native code.
     *
     * @param radius Sphere radius size.
     * @return A pointer to the newly create btshape.
     */
    long createSphereShape(float radius);

    /**
     * Update the world in native code.
     *
     * @param pointerAddress Pointer address to the associated yz::World.
     * @param time           Time since the last call.
     * @return An array containing all the collision occurred during the execution.
     */
    long[] update(long pointerAddress, long time);

    /**
     * Remove the body from the world but does not delete it.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param bodyPointer    Pointer address of btbody to remove from the world.
     */
    void removeBody(long pointerAddress, long bodyPointer);

    /**
     * Create a ghost object in native code.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param shape          btshape pointer to build the ghost.
     * @param id             Id to associate with the ghost.
     * @param x              Initial X position.
     * @param y              Initial Y position.
     * @param z              Initial Z position.
     * @return A pointer to the newly created btGhostObject*.
     */
    long createGhostObject(long pointerAddress, long shape, long id, float x, float y, float z);

    /**
     * Retrieve collision with ghost objects in native code.
     *
     * @param pointerAddress Pointer address of this associated btworld.
     * @return An array containing the Id values of collided objects going by pair(i.e:result[0] is colliding with result[1], and so on...).
     */
    long[] getGhostCollisionResult(long pointerAddress);

    /**
     * Delete this world in native code.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     */
    void delete(long pointerAddress);
}

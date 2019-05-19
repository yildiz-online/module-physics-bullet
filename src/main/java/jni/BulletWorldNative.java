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

package jni;

/**
 * @author Grégory Van den Borre
 */
public class BulletWorldNative {


    /**
     * Create a new yz::World in native code.
     *
     * @return The pointer value of the created object.
     */
    public native long constructor();

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
    public native long[] raycast(final long pointerAddress, final float beginX, final float beginY, final float beginZ, final float endX, final float endY, final float endZ);

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
    public native long simpleRaycast(final long pointerAddress, final float beginX, final float beginY, final float beginZ, final float endX, final float endY, final float endZ);

    /**
     * Create a btshape from a serialized file.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param file           File to deserialize.
     * @return A pointer to the btshape retrieved from the serialized file.
     */
    public native long deserializeMesh(final long pointerAddress, final String file);

    /**
     * Set the gravity in native code.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param gravityX       Gravity X value.
     * @param gravityY       Gravity Y value.
     * @param gravityZ       Gravity Z value.
     */
    public native void setGravity(final long pointerAddress, final float gravityX, final float gravityY, final float gravityZ);

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
    public native long createStaticBody(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z, final float dX, final float dY, final float dZ);

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
    public native long createKinematicBody(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z);

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
    public native long createDynamicBody(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z, final float mass);

    /**
     * Create a box btshape in native code.
     *
     * @param width  Box width size.
     * @param height Box height size.
     * @param depth  Box depth size.
     * @return A pointer to the newly create btshape.
     */
    public native long createBoxShape(final float width, final float height, final float depth);

    public native long createPlaneShape(int width, int depth);

    /**
     * Create a sphere btshape in native code.
     *
     * @param radius Sphere radius size.
     * @return A pointer to the newly create btshape.
     */
    public native long createSphereShape(final float radius);

    /**
     * Update the world in native code.
     *
     * @param pointerAddress Pointer address to the associated yz::World.
     * @param time           Time since the last call.
     * @return An array containing all the collision occurred during the execution.
     */
    public native long[] update(final long pointerAddress, final long time);

    /**
     * Remove the body from the world but does not delete it.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     * @param bodyPointer    Pointer address of btbody to remove from the world.
     */
    // FIXME check if unused, delete here and in native code.
    private native void removeBody(final long pointerAddress, final long bodyPointer);

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
    public native long createGhostObject(final long pointerAddress, final long shape, final long id, final float x, final float y, final float z);

    /**
     * Retrieve collision with ghost objects in native code.
     *
     * @param pointerAddress Pointer address of this associated btworld.
     * @return An array containing the Id values of collided objects going by pair(i.e:result[0] is colliding with result[1], and so on...).
     */
    public native long[] getGhostCollisionResult(final long pointerAddress);

    /**
     * Delete this world in native code.
     *
     * @param pointerAddress Pointer address of this associated yz::World.
     */
    public native void delete(final long pointerAddress);
}

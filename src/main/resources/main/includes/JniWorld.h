/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
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

#include "stdafx.h"

#ifndef JNI_BULLET_H
#define JNI_BULLET_H

/**
* @author Grégory Van den Borre
*/

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_constructor(JNIEnv* env, jobject o);

JNIEXPORT void JNICALL Java_jni_BulletWorldNative_delete(JNIEnv* env, jobject, jlong pointer);

JNIEXPORT void JNICALL Java_jni_BulletWorldNative_setGravity(JNIEnv* env, jobject o, jlong pointer, jfloat x, jfloat y, jfloat z);

JNIEXPORT jlongArray JNICALL Java_jni_BulletWorldNative_update(JNIEnv* env, jobject o, jlong pointer, jlong time);

JNIEXPORT jlongArray JNICALL Java_jni_BulletWorldNative_getGhostCollisionResult(JNIEnv* env, jobject o, jlong pointer);

JNIEXPORT jlongArray JNICALL Java_jni_BulletWorldNative_raycast(JNIEnv* env, jobject o, jlong pointer, jfloat startX,
    jfloat startY, jfloat startZ, jfloat endX, jfloat endY, jfloat endZ);

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_simpleRaycast(JNIEnv* env, jobject o, jlong pointer, jfloat startX,
    jfloat startY, jfloat startZ, jfloat endX, jfloat endY, jfloat endZ);

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_deserializeMesh(JNIEnv* env, jobject o, jlong pointer, jstring jfile);

JNIEXPORT void JNICALL Java_jni_BulletWorldNative_removeBody(JNIEnv* env, jobject o, jlong pointer, jlong bodyPointer);

/**
 * Build a box shape.
 * @param env
 *           Pointer to the java environment.
 * @param o
 *           Associated object.
 * @param width
 *           Box width, in meter.
 * @param height
 *           Box height, in meter.
 * @param depth
 *           Box depth, in meter.
 *
 * @return A pointer of the newly build shape(type is btCollisionShape).
 */
JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createBoxShape(JNIEnv* env, jobject o, jfloat width, jfloat height, jfloat depth);

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createPlaneShape(JNIEnv* env, jobject o, jfloat width, jfloat depth);

/**
 * Build a sphere shape.
 * @param env
 *           Pointer to the java environment.
 * @param o
 *           Associated object.
 * @param radius
 *           Sphere radius, in meter.
 *
 * @return A pointer of the newly build shape(type is btCollisionShape).
 */
JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createSphereShape(JNIEnv* env, jobject o, jfloat radius);

/**
 * Build a static physic body.
 * @param env
 *           Pointer to the java environment.
 * @param o
 *           Associated object.
 * @param worldPointer
 *           Pointer of the world to use(type must be yz::World).
 * @param shapePointer
 *           Pointer of the shape to use(type must be btCollisionShape).
 * @param x
 *           Body initial position X value.
 * @param y
 *           Body initial position Y value.
 * @param z
 *           Body initial position Z value.
 *
 * @return A pointer of the newly build object(type is btRigidBody).
 */
JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createStaticBody(JNIEnv* env, jobject o, jlong worldPointer,
    jlong shapePointer, jlong id, jfloat posX, jfloat posY, jfloat posZ, jfloat dirX, jfloat dirY, jfloat dirZ);

/**
 * Build a kinematic physic body.
 * @param env
 *           Pointer to the java environment.
 * @param o
 *           Associated object.
 * @param worldPointer
 *           Pointer of the world to use(type must be yz::World).
 * @param shapePointer
 *           Pointer of the shape to use(type must be btCollisionShape).
 * @param x
 *           Body initial position X value.
 * @param y
 *           Body initial position Y value.
 * @param z
 *           Body initial position Z value.
 *
 * @return A pointer of the newly build object(type is btRigidBody).
 */
JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createKinematicBody( JNIEnv* env, jobject o, jlong worldPointer,
    jlong shapePointer, jlong id, jfloat x, jfloat y, jfloat z);

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createDynamicBody( JNIEnv* env, jobject o, jlong worldPointer,
    jlong shapePointer, jlong id, jfloat x, jfloat y, jfloat z, jfloat mass);

/**
 * Build a ghost object.
 * @param env
 *           Pointer to the java environment.
 * @param o
 *           Associated object.
 * @param worldPointer
 *           Pointer of the world to use(type must be yz::World).
 * @param shapePointer
 *           Pointer of the shape to use(type must be btCollisionShape).
 * @param x
 *           Body initial position X value.
 * @param y
 *           Body initial position Y value.
 * @param z
 *           Body initial position Z value.
 *
 * @return A pointer of the newly build object(type is btGhostObject).
 */
JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createGhostObject(JNIEnv* env, jobject o, jlong pointer,
    jlong shapePointer, jlong id, jfloat x, jfloat y, jfloat z);

#ifdef __cplusplus
}
#endif
#endif


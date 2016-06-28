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

#include "stdafx.h"

#ifndef _JNI_SPHERE_H_
#define _JNI_SPHERE_H_

/**
* @author Grégory Van den Borre
*/
#ifdef __cplusplus
extern "C" {
#endif

/**
 * Get the body current position.
 * @param env
 *           Pointer to the java environement.
 * @param o
 *           Associated object.
 * @param pointer
 *           Pointer of the body(type must be btRigidBody).
 *
 * @return An array of floats with the position coordinates(0 is X, 1 is Y, 2 is Z).
 */
JNIEXPORT jfloatArray JNICALL Java_jni_BulletDynamicBody_getPosition(
		JNIEnv* env, jobject o, jlong pointer);

JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_setPosition
(JNIEnv* env, jobject o, jlong pointer, jfloat x, jfloat y, jfloat z);

JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_setOrientation
(JNIEnv*, jobject, jlong pointer, jfloat w, jfloat x, jfloat y, jfloat z);

JNIEXPORT void JNICALL Java_jni_BulletKinematicBodyNative_setPosition
(JNIEnv* env, jobject o, jlong pointer, jfloat x, jfloat y, jfloat z);

JNIEXPORT void JNICALL Java_jni_BulletKinematicBodyNative_setDirection
(JNIEnv*, jobject, jlong pointer, jfloat w, jfloat x, jfloat y, jfloat z);

JNIEXPORT void JNICALL Java_jni_BulletBodyNative_delete
(JNIEnv*, jobject, jlong pointer, jlong worldPointer);

JNIEXPORT void JNICALL Java_jni_BulletBodyNative_setActivate
(JNIEnv*, jobject, jlong pointer, jboolean activate);

/**
 * Apply a force on a body.
 * @param env
 *           Pointer to the java environement.
 * @param o
 *           Associated object.
 * @param pointer
 *           Pointer of the body(type must be btRigidBody).
 * @param x
 *			Force strength in X direction.
 * @param y
 *			Force strength in Y direction.
 * @param z
 *			Force strength in Z direction.
 */
JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_applyForce
(JNIEnv* env, jobject o, jlong pointer, jfloat x, jfloat y, jfloat z);

#ifdef __cplusplus
}
#endif
#endif

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

#include "../includes/stdafx.h"
#include "../includes/JniGhost.h"
#include "../includes/World.hpp"
#include "../includes/JniUtil.h"

/**
* @author Grégory Van den Borre
*/

JNIEXPORT jfloatArray JNICALL Java_jni_BulletGhostObjectNative_getPosition(
    JNIEnv* env,
    jobject o,
    jlong pointer) {
    LOG_FUNCTION
    try {
        btGhostObject* ghost = reinterpret_cast<btGhostObject*>(pointer);
        btTransform& trans = ghost->getWorldTransform();
        btVector3 pos = trans.getOrigin();
        jfloat buf[3];
        buf[0] = pos.getX();
        buf[1] = pos.getY();
        buf[2] = pos.getZ();
        jfloatArray result = env->NewFloatArray(3);
        env->SetFloatArrayRegion(result, 0, 3, buf);
        return result;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return env->NewFloatArray(1);
}

JNIEXPORT void JNICALL Java_jni_BulletGhostObjectNative_setPosition(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        btGhostObject* ghost = reinterpret_cast<btGhostObject*>(pointer);
        btTransform& transform = ghost->getWorldTransform();
        transform.setOrigin(btVector3(x, y, z));
        ghost->setWorldTransform(transform);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletGhostObjectNative_setOrientation(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat w,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        btGhostObject* ghost = reinterpret_cast<btGhostObject*>(pointer);
        btTransform& transform = ghost->getWorldTransform();
        transform.setRotation(btQuaternion(x, y, z, w));
        ghost->setWorldTransform(transform);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletGhostObjectNative_delete(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jlong worldPointer) {
    LOG_FUNCTION
    try {
        btGhostObject* ghost = reinterpret_cast<btGhostObject*>(pointer);
        yz::World* world = reinterpret_cast<yz::World*>(worldPointer);
        world->removeGhost(ghost);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}


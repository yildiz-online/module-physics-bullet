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
#include "../includes/JniBody.h"
#include "../includes/World.hpp"
#include "../includes/JniUtil.h"
#include "../includes/DynamicMotionState.hpp"

/**
* @author Grégory Van den Borre
*/

JNIEXPORT jfloatArray JNICALL Java_jni_BulletBodyNative_getPosition(
    JNIEnv* env,
    jobject o,
    jlong pointer) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        btVector3 pos = body->getCenterOfMassPosition();
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

JNIEXPORT jfloatArray JNICALL Java_jni_BulletBodyNative_getDirection(
    JNIEnv* env,
    jobject o,
    jlong pointer) {
        jfloatArray result = env->NewFloatArray(3);
        jfloat buf[3];
        //FIXME implement
        buf[0] = 0;
        buf[1] = 0;
        buf[2] = -1;
        env->SetFloatArrayRegion(result, 0, 3, buf);
        return result;
    }

JNIEXPORT void JNICALL Java_jni_BulletBodyNative_setActivate
(JNIEnv* env, jobject o, jlong pointer, jboolean activate) {
    yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
    body->activate(activate);
}

JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_setPosition(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        btTransform transform = body->getCenterOfMassTransform();
        transform.setOrigin(btVector3(x, y, z));
        body->setCenterOfMassTransform(transform);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_setOrientation(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat w,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        btTransform transform = body->getCenterOfMassTransform();
        transform.setRotation(btQuaternion(x, y, z, w));
        body->setCenterOfMassTransform(transform);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletKinematicBodyNative_setPosition(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        KinematicMotionState* state =
                static_cast<KinematicMotionState*>(body->getMotionState());
        btTransform trans;
        trans.setIdentity();
        trans.setOrigin(btVector3(x, y, z));
        state->setKinematicPos(trans);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletKinematicBodyNative_setDirection(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat w,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        KinematicMotionState* state =
                static_cast<KinematicMotionState*>(body->getMotionState());
        btTransform trans;
        trans.setRotation(btQuaternion(x, y, z, w));
        state->setKinematicPos(trans);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_applyForce(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        body->applyCentralForce(btVector3(x, y, z));
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT void JNICALL Java_jni_BulletDynamicBodyNative_attachNative(
    JNIEnv* env, jobject o, jlong pointer, jlong other) {
    LOG_FUNCTION
        try {
            yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
            yz::NativeMovableComponent* element = reinterpret_cast<yz::NativeMovableComponent*>(other);
            body->attachMovable(element);
        } catch (std::exception& e) {
            throwException(env, e.what());
        }
    }

JNIEXPORT void JNICALL Java_jni_BulletBodyNative_delete(
    JNIEnv* env,
    jobject o,
    jlong pointer,
    jlong worldPointer) {
    LOG_FUNCTION
    try {
        yz::RigidBody* body = reinterpret_cast<yz::RigidBody*>(pointer);
        yz::World* world = reinterpret_cast<yz::World*>(worldPointer);
        body->activate(false);
        world->removeBody(body);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

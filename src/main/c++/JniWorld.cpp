/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
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
#include "../includes/JniWorld.h"
#include "../includes/World.h"
#include "../includes/JniUtil.h"

/**
* @author Grégory Van den Borre
*/

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_constructor(
    JNIEnv* env,
    jobject) {
    LOG_FUNCTION
    try {
        return reinterpret_cast<jlong>(new YZ::World());
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT void JNICALL Java_jni_BulletWorldNative_delete(
    JNIEnv* env,
    jobject,
    jlong pointer) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        delete world;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createStaticBody(
    JNIEnv* env,
    jobject,
    jlong worldPointer,
    jlong shapePointer,
    jlong id,
    jfloat posX,
    jfloat posY,
    jfloat posZ,
    jfloat dirX,
    jfloat dirY,
    jfloat dirZ) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(worldPointer);
        btCollisionShape* shape =
                reinterpret_cast<btCollisionShape*>(shapePointer);
        btRigidBody* body = world->createStaticBody(shape,
                btVector3(posX, posY, posZ), btVector3(dirX, dirY, dirZ), id);
        return reinterpret_cast<jlong>(body);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createKinematicBody(
    JNIEnv* env,
    jobject,
    jlong worldPointer,
    jlong shapePointer,
    jlong id,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(worldPointer);
        btCollisionShape* shape =
                reinterpret_cast<btCollisionShape*>(shapePointer);
        return reinterpret_cast<jlong>(world->createKinematicBody(shape, id, x,
                y, z));
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT void JNICALL Java_jni_BulletWorldNative_setGravity(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        world->setGravity(x, y, z);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT jlongArray JNICALL Java_jni_BulletWorldNative_update(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jlong time) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        std::vector<jlong> list = world->update(time);

        if (list.empty()) {
            jlong buf[1];
            jlongArray result = env->NewLongArray(0);
            env->SetLongArrayRegion(result, 0, 0, buf);
            return result;
        }
        const int size = list.size();
        jlongArray result = env->NewLongArray(size);
        env->SetLongArrayRegion(result, 0, size, &list[0]);
        return result;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return env->NewLongArray(1);
}

JNIEXPORT jlongArray JNICALL Java_jni_BulletWorldNative_getGhostCollisionResult(
    JNIEnv* env,
    jobject,
    jlong pointer) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        std::vector<jlong> list = world->getGhostCollisionResult();

        if (list.empty()) {
            jlong buf[1];
            jlongArray result = env->NewLongArray(0);
            env->SetLongArrayRegion(result, 0, 0, buf);
            return result;
        }
        const int size = list.size();
        jlongArray result = env->NewLongArray(size);
        env->SetLongArrayRegion(result, 0, size, &list[0]);
        return result;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return env->NewLongArray(0);
}

JNIEXPORT void JNICALL Java_jni_BulletWorldNative_removeBody(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jlong bodyPointer) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        btRigidBody* body = reinterpret_cast<btRigidBody*>(bodyPointer);
        world->removeBody(body);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

JNIEXPORT jlongArray JNICALL Java_jni_BulletWorldNative_raycast(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jfloat startX,
    jfloat startY,
    jfloat startZ,
    jfloat endX,
    jfloat endY,
    jfloat endZ) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        btVector3 start = btVector3(startX, startY, startZ);
        btVector3 end = btVector3(endX, endY, endZ);
        float collision[4];
        world->rayCastPoint(start, end, collision);
        jlong buf[4];
        jlongArray result = env->NewLongArray(4);
        buf[0] = collision[0];
        buf[1] = collision[1];
        buf[2] = collision[2];
        buf[3] = collision[3];
        env->SetLongArrayRegion(result, 0, 4, buf);
        return result;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return env->NewLongArray(1);
}

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_simpleRaycast(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jfloat startX,
    jfloat startY,
    jfloat startZ,
    jfloat endX,
    jfloat endY,
    jfloat endZ) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        btVector3 start = btVector3(startX, startY, startZ);
        btVector3 end = btVector3(endX, endY, endZ);
        return world->rayCast(start, end);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_deserializeMesh(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jstring jfile) {
    LOG_FUNCTION
   /** try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        const char* file = env->GetStringUTFChars(jfile, 0);
        btCollisionShape* shape = world->deserializeShape(file);
        env->ReleaseStringUTFChars(jfile, file);
        return reinterpret_cast<jlong>(shape);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }**/
    return -1L;
}


JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createBoxShape(
    JNIEnv* env,
    jobject,
    jfloat width,
    jfloat height,
    jfloat depth) {
    LOG_FUNCTION
    try {
        btCollisionShape* shape = new btBoxShape(
                btVector3(width * 0.5F, height * 0.5F, depth * 0.5F));
        return reinterpret_cast<jlong>(shape);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createSphereShape(
    JNIEnv* env,
    jobject,
    jfloat radius) {
    LOG_FUNCTION
    try {
        btCollisionShape* shape = new btSphereShape(radius);
        return reinterpret_cast<jlong>(shape);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}


JNIEXPORT jlong JNICALL Java_jni_BulletWorldNative_createGhostObject(
    JNIEnv* env,
    jobject,
    jlong pointer,
    jlong shapePointer,
    jlong id,
    jfloat x,
    jfloat y,
    jfloat z) {
    LOG_FUNCTION
    try {
        YZ::World* world = reinterpret_cast<YZ::World*>(pointer);
        btCollisionShape* shape =
                reinterpret_cast<btCollisionShape*>(shapePointer);
        return reinterpret_cast<jlong>(world->createGhostObject(shape, id, x, y,
                z));
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

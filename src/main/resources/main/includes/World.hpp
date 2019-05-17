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

#ifndef BULLET_WORLD_H
#define BULLET_WORLD_H

#include "stdafx.h"
#include "KinematicMotionState.hpp"
#include "RigidBody.hpp"

namespace yz {

/**
 * Wrap a btdiscreetworld, provide linking between collision object and their id.
 * @author Van den Borre Grégory
 */
class World {

public:

    World();

    ~World();

    /**
     * Create a body intended to stay at a given position.
     * @param shape Shape to assign to the body.
     * @param position Immutable position.
     * @param direction Immutable direction.
     * @param id Id to assign to this body.
     * @return The created static rigid body.
     */
    yz::RigidBody* createStaticBody(
        btCollisionShape* shape,
        const btVector3& position,
        const btVector3& direction,
        const long id);

    /**
     * Create a body intended be moved externally.
     * @param shape Shape to assign to the body.
     * @param id Id to assign to this body.
     * @return The created kinematic rigid body.
     */
    yz::RigidBody* createKinematicBody(
        btCollisionShape* shape,
        const long id,
        const float x,
        const float y,
        const float z);

    yz::RigidBody* createDynamicBody(
        btCollisionShape* shape,
        const long id,
        const float x,
        const float y,
        const float z,
        const float mass);

    std::vector<jlong> update(const long time);

    long rayCast(const btVector3& origin, const btVector3& end) const;

    long rayCast(
        const btVector3& origin,
        const btVector3& end,
        btCollisionWorld::ClosestRayResultCallback& result) const;

    void rayCastPoint(
        const btVector3& origin,
        const btVector3& end,
        float* resultArray) const;

    void removeGhost(btGhostObject* ghost);

    inline void setGravity(const float x, const float y, const float z) {
        this->world->setGravity(btVector3(x, y, z));
    }

    inline void addBody(btRigidBody* body, const long id) {
        this->world->addRigidBody(body);
        this->ids[body] = id;
    }

    inline void removeBody(btRigidBody* body) {
        this->world->removeRigidBody(body);
        this->ids.erase(body);
        if (body->getMotionState()) {
            delete body->getMotionState();
        }
        delete body;
    }

    inline btDiscreteDynamicsWorld* getWorld() const {
        return this->world;
    }

    inline void setDebug(btIDebugDraw* drawer) {
        this->world->setDebugDrawer(drawer);
    }

    btGhostObject* createGhostObject(btCollisionShape* shape, const long id, const float x, const float y, const float z);

    /**
     * @return The list of collisions occurred with ghost objects during the
     * last world update.
     */
    inline std::vector<jlong> getGhostCollisionResult() const {
        return this->ghostCollisionResult;
    }

    inline long getIdFromObject(const btCollisionObject* object) {
        long id = this->ids[object];
        //FIXME URGENT what if entity id is 0???
        return id == 0 ? -1L : id;
    }

private:

    /**
     * Wrapped bullet world.
     */
    btDiscreteDynamicsWorld* world;

    btOverlappingPairCallback* ghostPairCallback;

    btSequentialImpulseConstraintSolver* solver;

    btCollisionDispatcher* dispatcher;

    btDefaultCollisionConfiguration* collisionConfiguration;

    btBroadphaseInterface* broadphase;

    /**
     * Mapping between the collidable objects and their given ids.
     */
    std::map<const btCollisionObject*, jlong> ids;

    std::map<btCollisionObject*, jlong> ghostIds;

    std::vector<jlong> ghostCollisionResult;

};
}
#endif

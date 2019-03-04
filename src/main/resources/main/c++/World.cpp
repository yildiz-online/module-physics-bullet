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

#include "../includes/World.hpp"
#include "../includes/KinematicMotionState.hpp"
#include "../includes/DynamicMotionState.hpp"
#include <algorithm>

/**
* @author Grégory Van den Borre
*/

yz::World::World() {
    this->ghostPairCallback = new btGhostPairCallback();
    this->broadphase = new btDbvtBroadphase();
    this->broadphase->getOverlappingPairCache()->setInternalGhostPairCallback(
            this->ghostPairCallback);
    this->collisionConfiguration = new btDefaultCollisionConfiguration();
    this->dispatcher = new btCollisionDispatcher(collisionConfiguration);
    this->solver = new btSequentialImpulseConstraintSolver;
    this->world = new btDiscreteDynamicsWorld(this->dispatcher,
            this->broadphase, this->solver, this->collisionConfiguration);
}

yz::World::~World() {
    for (int i = this->world->getNumCollisionObjects() - 1; i >= 0; i--) {
        btCollisionObject* obj = this->world->getCollisionObjectArray()[i];
        btRigidBody* body = btRigidBody::upcast(obj);
        if (body && body->getMotionState()) {
            delete body->getMotionState();
        }
        this->world->removeCollisionObject(obj);
        delete obj;
    }
    delete this->world;
    delete this->solver;
    delete this->dispatcher;
    delete this->collisionConfiguration;
    delete this->broadphase;
    delete this->ghostPairCallback;
}

yz::RigidBody* yz::World::createStaticBody(
    btCollisionShape* shape,
    const btVector3& position,
    const btVector3& direction,
    const long id) {
    btScalar mass = 0;
    btVector3 inertia(0, 0, 0);

    btTransform trans;
    trans.setIdentity();
    trans.setOrigin(position);
    yz::RigidBody* body = new yz::RigidBody(mass, new btDefaultMotionState(trans),
            shape, inertia, new yz::NativeMovable());
    body->setCollisionFlags(
            body->getCollisionFlags() | btCollisionObject::CF_STATIC_OBJECT);
    body->setActivationState(ISLAND_SLEEPING);
    this->ids[body] = id;
    world->addRigidBody(body);
    return body;
}

yz::RigidBody* yz::World::createKinematicBody(
    btCollisionShape* shape,
    const long id,
    const float x,
    const float y,
    const float z) {
    btScalar mass = 0;
    btVector3 inertia(0, 0, 0);
    btTransform transform;
    transform.setOrigin(btVector3(x, y, z));
    yz::RigidBody* body = new yz::RigidBody(mass, new KinematicMotionState(transform), shape, inertia,
        new yz::NativeMovable());
    body->setCollisionFlags(body->getCollisionFlags() | btCollisionObject::CF_KINEMATIC_OBJECT);
    body->setActivationState(DISABLE_DEACTIVATION);
    world->addRigidBody(body,
            body->getCollisionFlags() | btCollisionObject::CF_KINEMATIC_OBJECT,
            btCollisionObject::CF_KINEMATIC_OBJECT
                    | btCollisionObject::CO_GHOST_OBJECT);
    this->ids[body] = id;
    return body;
}

yz::RigidBody* yz::World::createDynamicBody(
    btCollisionShape* shape,
    const long id,
    const float x,
    const float y,
    const float z,
    const float mass) {
    btVector3 inertia(0, 0, 0);
    shape->calculateLocalInertia(mass, inertia);
    btTransform transform;
    transform.setOrigin(btVector3(x, y, z));
    yz::DynamicMotionState* s = new yz::DynamicMotionState(transform);
    yz::RigidBody* body = new yz::RigidBody(mass, s, shape, inertia, s->getMovable());
    world->addRigidBody(body);
    this->ids[body] = id;
    return body;
}

btGhostObject* yz::World::createGhostObject(btCollisionShape* shape, const long id, const float x, const float y,
    const float z) {
    btGhostObject* ghostObject = new btGhostObject();
    ghostObject->setCollisionShape(shape);
    ghostObject->setWorldTransform(btTransform(btQuaternion(0, 0, 0, 1), btVector3(x, y, z)));
    this->world->addCollisionObject(ghostObject, btCollisionObject::CO_GHOST_OBJECT,
        btCollisionObject::CF_KINEMATIC_OBJECT);
    this->ghostIds[ghostObject] = id;
    return ghostObject;
}

void yz::World::removeGhost(btGhostObject* ghost) {
    ghost->activate(false);
    this->world->removeCollisionObject(ghost);
    long id = this->ghostIds[ghost];
    this->ghostCollisionResult.erase(
            std::remove(this->ghostCollisionResult.begin(),
            this->ghostCollisionResult.end(), id),
            this->ghostCollisionResult.end());
    this->ghostIds.erase(ghost);
    delete ghost;
}

std::vector<jlong> yz::World::update(const long time) {
    this->world->stepSimulation(time / 1000.0f, 7);
    this->ghostCollisionResult.clear();

    btCollisionObjectArray& collisionObjects = this->world->getCollisionObjectArray();
    for (int i = 0; i < this->world->getNumCollisionObjects(); i++) {
        btGhostObject* ghost = btGhostObject::upcast(collisionObjects.at(i));
        if (ghost) {
            btAlignedObjectArray<btCollisionObject*> objsInsidePairCachingGhostObject;
            objsInsidePairCachingGhostObject.resize(0);
            for (int j = 0; j < ghost->getNumOverlappingObjects(); j++) {
                btCollisionObject* co = ghost->getOverlappingObject(j);
                if (co) {
                    btRigidBody *pRigidBody = btRigidBody::upcast(co);
                    if (pRigidBody) {
                        jlong g = this->ghostIds[ghost];
                        jlong b = this->ids[pRigidBody];
                        if (b != g) {
                            this->ghostCollisionResult.push_back(g);
                            this->ghostCollisionResult.push_back(b);
                        }
                    }
                }
            }
        }
    }

    //Retrieve rigid to rigid collisions.
    int numManifolds = this->world->getDispatcher()->getNumManifolds();
    std::vector<jlong> collisionList;

    for (int i = 0; i < numManifolds; i++) {
        btPersistentManifold* contactManifold = this->world->getDispatcher()->getManifoldByIndexInternal(i);
        int numContacts = contactManifold->getNumContacts();
        if (numContacts > 0) {
            const btCollisionObject* firstCo = contactManifold->getBody0();
            const btCollisionObject* secondCo = contactManifold->getBody1();
            if (btRigidBody::upcast(firstCo) && btRigidBody::upcast(secondCo)) {
                jlong firstId = this->ids[firstCo];
                jlong secondId = this->ids[secondCo];
                if (firstId && secondId) {
                    collisionList.push_back(firstId);
                    collisionList.push_back(secondId);
                }
            }
        }
    }
    return collisionList;
}

long yz::World::rayCast(const btVector3& origin, const btVector3& end) const {
    btCollisionWorld::ClosestRayResultCallback result(origin, end);
    this->world->getCollisionWorld()->rayTest(origin, end, result);
    long id = this->ids.at(result.m_collisionObject);
    return id == 0 ? -1L : id;
}

long yz::World::rayCast(
    const btVector3& origin,
    const btVector3& end,
    btCollisionWorld::ClosestRayResultCallback& result) const {
    this->world->getCollisionWorld()->rayTest(origin, end, result);
    long id = this->ids.at(result.m_collisionObject);
    return id == 0 ? -1L : id;
}

void yz::World::rayCastPoint(
    const btVector3& origin,
    const btVector3& end,
    float* resultArray) const {
    btCollisionWorld::ClosestRayResultCallback result(origin, end);
    this->world->getCollisionWorld()->rayTest(origin, end, result);

    if (!result.hasHit()) {
        resultArray[0] = -1;
        resultArray[1] = 0;
        resultArray[2] = 0;
        resultArray[3] = 0;
    } else {
        btVector3 contact = result.m_hitPointWorld;
        resultArray[0] = this->ids.at(result.m_collisionObject);
        resultArray[1] = contact.getX();
        resultArray[2] = contact.getY();
        resultArray[3] = contact.getZ();
    }
}

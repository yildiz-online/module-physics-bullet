/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
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

#ifndef KINEMATIC_MOTION_STATE_H
#define KINEMATIC_MOTION_STATE_H

#include "stdafx.h"

/**
* This motion state is intended to be used with kinematic bodies,
* the dynamic will not affect it and its position and orientation must be updated manually.
*
* @author Van den Borre Grégory
*/
class KinematicMotionState : public btMotionState {
public:

    /**
    * Full constructor, initialize the position and rotation.
    * @param initial
    *           Initial position and rotation values.
    */
    KinematicMotionState(const btTransform& initial = btTransform::getIdentity(),const btTransform& centerOfMassOffset = btTransform::getIdentity())
		: graphicsWorldTrans(initial),
		centerOfMassOffset(centerOfMassOffset),
		startWorldTrans(initial) {

    }

    /**
    * Destructor.
    */
    virtual ~ KinematicMotionState() {
        /*delete userPointer;
        delete graphicsWorldTrans;
        delete centerOfMassOffset;
        delete startWorldTrans;*/
    }

    /**
    * Set a btTransform reference pointing to the one wrapped in this motion state.
    * @param worldTrans
    *           Reference to set to the wrapped btTransform.
    */
    virtual void getWorldTransform(btTransform& worldTrans) const {
        worldTrans = centerOfMassOffset.inverse() * graphicsWorldTrans ;
    }

    /**
    * Manually update the wrapped transform object.
    * @param current
    *           New position and orientation values.
    */
    void setKinematicPos(const btTransform& current) {
        graphicsWorldTrans = current * centerOfMassOffset ;
    }

    /**
    * This will not do anything, preventing the objects using this motion state to be affected by dynamic.
    */
    virtual void setWorldTransform(const btTransform&) {
    }

private:

    /**
    * Contains position and orientation data.
    */
    btTransform graphicsWorldTrans;

	/**
	* Offset to have the real position of the center of mass.
	*/
	btTransform	centerOfMassOffset;
	btTransform startWorldTrans;

};

#endif

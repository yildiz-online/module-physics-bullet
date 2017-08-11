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

#ifndef DYNAMIC_MOTION_STATE_H
#define DYNAMIC_MOTION_STATE_H

#include "stdafx.h"

/**
* This motion state is intended to be used with dynamic bodies.
*
* @author Van den Borre Grégory
*/
class DynamicMotionState : public btMotionState {
public:

    /**
    * Full constructor, initialize the position and rotation.
    * @param initial
    *           Initial position and rotation values.
    */
    DynamicMotionState(const btTransform& initial = btTransform::getIdentity()) : transform(initial) {

    }

    /**
    * Destructor.
    */
    virtual ~ DynamicMotionState() {
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
        worldTrans = this->transform;
    }

    virtual void setWorldTransform(const btTransform& t) {
        this->transform = t;
    }

private:

    /**
    * Contains position and orientation data.
    */
    btTransform transform;

};

#endif

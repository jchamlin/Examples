/*
 * $Id$
 * 
 * Copyright (c) 2019, Simsilica, LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the 
 *    distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package sigem.sim;

import org.slf4j.*;

import com.simsilica.es.*;
import com.simsilica.es.common.Decay;
import com.simsilica.mathd.*;
import com.simsilica.sim.*;

import sigem.es.*;

/**
 *  Factory methods for common game entities.
 *
 *  @author    Paul Speed
 */
public class GameEntities extends AbstractGameSystem {
    
    static Logger log = LoggerFactory.getLogger(GameEntities.class);

    private EntityData ed;
                                       
    public GameEntities( EntityData ed ) {
        this.ed = ed;
    }
    
    @Override
    protected void initialize() {
    }

    @Override
    protected void terminate() {
    }

    public EntityId createAsteroid( Vec3d location, Vec3d linVelocity, Vec3d angVelocity, double size ) {
    
        double mass = size * size * size * 5;
    
        EntityId asteroid = ed.createEntity();
        ed.setComponents(asteroid,
            new Position(location),
            new MassProperties(1/mass),
            ObjectType.create(ObjectType.TYPE_ASTEROID, ed),
            new SphereShape(size, new Vec3d()),
            new Impulse(linVelocity, angVelocity)
            );    
            
        return asteroid;
    }

    public EntityId createAsteroidChunk( Vec3d location, Vec3d linVelocity, Vec3d angVelocity, double size ) {
 
        SimTime time = getManager().getStepTime();

        double mass = size * size * size * 5;
    
        EntityId asteroid = ed.createEntity();
        ed.setComponents(asteroid,
            new Position(location),
            new MassProperties(1/mass),
            ObjectType.create(ObjectType.TYPE_ASTEROID_CHUNK, ed),
            new SphereShape(size, new Vec3d()),
            new Impulse(linVelocity, angVelocity),
            new Decay(time.getTime(), time.getFutureTime(2))
            );    
            
        return asteroid;
    }
         
    public EntityId createExplosion( Vec3d location, double size ) {
        SimTime time = getManager().getStepTime();
        
        EntityId result = ed.createEntity();
        ed.setComponents(result,
            new Position(location),
            new SphereShape(size, new Vec3d()),
            ObjectType.create(ObjectType.TYPE_PLASMA_EXPLOSION, ed),
            new Decay(time.getTime(), time.getFutureTime(2))
            );
        return result;
    }
}



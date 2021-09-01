package de.golgolex.cloudnet.simplifier.objectives.claydriveruser;

/*
===========================================================================================================================
# 
# Copyright (c) 2021 Pascal Kurz
# Class created at 27.08.2021, 15:35
# Class created by: Pascal
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation 
# files (the "Software"),
# to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, 
# distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software 
# is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
# INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE 
# AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
#  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# 
===========================================================================================================================
*/

import java.util.ArrayList;
import java.util.UUID;

public class SimplifierUserCache extends ArrayList<ISimplifierUser> implements ISimplifierUserManager {

    @Override
    public void registerClayDriverUser(ISimplifierUser iSimplifierUser) {
        add(iSimplifierUser);
    }

    @Override
    public void unregisterClayDriverUser(ISimplifierUser iSimplifierUser) {
        remove(iSimplifierUser);
    }

    @Override
    public ISimplifierUser getIClayDriverUser(String name) {
        return stream().filter(iClayDriverUser -> iClayDriverUser.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public ISimplifierUser getIClayDriverUser(UUID uniqueId) {
        return stream().filter(iClayDriverUser -> iClayDriverUser.getUniqueID().equals(uniqueId)).findFirst().orElse(null);
    }

    @Override
    public boolean exist(String name) {
        return stream().noneMatch(iClayDriverUser -> iClayDriverUser.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean exist(UUID uuid) {
        return stream().noneMatch(iClayDriverUser -> iClayDriverUser.getUniqueID().equals(uuid));
    }


}

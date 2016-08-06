package com.chicken.invasion.Weapons;

import com.chicken.invasion.Enemy_Throwable.*;
import com.chicken.invasion.Enemy_Throwable.Throwable;
import com.chicken.invasion.Store.StoreCollection;
import com.chicken.invasion.Store.StoreItem;

import java.util.List;

/**
 * Created by Albin on 2016-08-06.
 */
public class WeaponCollection implements ThrowableCollection,StoreCollection {

    @Override
    public <T extends Throwable> List<T> getThrowables() {
        return null;
    }

    @Override
    public <T extends StoreItem> List<T> getStoreItems() {
        return null;
    }
}

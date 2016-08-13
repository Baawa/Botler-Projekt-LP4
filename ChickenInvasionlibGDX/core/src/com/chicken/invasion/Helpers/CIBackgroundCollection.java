package com.chicken.invasion.Helpers;

import com.chicken.invasion.Store.StoreCollection;
import com.chicken.invasion.Store.StoreItem;

import java.util.List;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIBackgroundCollection implements StoreCollection {
    @Override
    public <T extends StoreItem> List<T> getStoreItems() {
        return null;
    }
}

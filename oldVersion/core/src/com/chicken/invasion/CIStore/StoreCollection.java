package com.chicken.invasion.CIStore;

import java.util.ArrayList;

/**
 * Created by Albin on 2016-08-06.
 */
public interface StoreCollection {
    <T extends Storeitem> ArrayList<T> getStoreItems();
}

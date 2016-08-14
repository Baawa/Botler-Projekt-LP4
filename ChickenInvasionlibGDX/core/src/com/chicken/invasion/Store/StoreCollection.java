package com.chicken.invasion.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albin on 2016-08-06.
 */
public interface StoreCollection {
    <T extends StoreItem> ArrayList<T> getStoreItems();
}

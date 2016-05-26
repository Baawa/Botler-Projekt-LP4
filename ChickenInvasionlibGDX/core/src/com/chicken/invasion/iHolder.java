package com.chicken.invasion;

import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-25.
 */
public interface iHolder{
    <T extends iItem> List<T> getThrowables();
}

package com.atbj.crowd.service.api;


import com.atbj.crowd.domain.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);
    void updateMenu(Menu menu);
    void removeMenu(Integer id);
}

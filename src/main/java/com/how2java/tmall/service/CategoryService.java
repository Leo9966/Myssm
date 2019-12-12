package com.how2java.tmall.service;

import java.util.*;

import com.how2java.tmall.pojo.Category;

public interface CategoryService {
	List<Category> list();
	Category getcategorybyid(int id);
}

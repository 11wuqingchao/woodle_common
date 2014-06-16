package com.woodle.api.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.woodle.api.enums.DiffResultType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wuqingchao
 * Time: 14-6-16 下午9:21
 */
public class ListUtils {

    /**
     * 比较2个list
     * @param list1
     * @param list2
     * @param diffType  比较的结果类型 see @com.woodle.api.enums.DiffResultType
     * @return
     */
    public static <T> List<T> difference(List<T> list1, List<T> list2, DiffResultType diffType) {
        Preconditions.checkArgument(list1 != null && list1.size() > 0, "list 1 must not be empty!");
        Preconditions.checkArgument(list2 != null && list2.size() > 0, "list 2 must not be empty");

        switch (diffType) {

            case IN_1_NOT_IN_2:
                return Lists.newArrayList(Iterators.find(list1.iterator(), Predicates.not(Predicates.in(list2))));

            case IN_2_NOT_IN_1:
                return Lists.newArrayList(Iterators.find(list2.iterator(), Predicates.not(Predicates.in(list1))));
            case IN_1_AND_IN_2:
                return Lists.newArrayList(Iterators.find(list1.iterator(), Predicates.and(Predicates.in(list1),Predicates.in(list2))));
            case IN_2_OR_IN_2:
                return Lists.newArrayList(Iterators.find(list1.iterator(), Predicates.or(Predicates.in(list1),Predicates.in(list2))));
            default :
                return Lists.newArrayList(Iterators.find(list1.iterator(), Predicates.not(Predicates.in(list2))));
        }
    }
}

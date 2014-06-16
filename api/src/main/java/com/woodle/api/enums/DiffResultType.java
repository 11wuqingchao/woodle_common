package com.woodle.api.enums;

/**
 * Created with IntelliJ IDEA.
 * User: wuqingchao
 * Time: 14-6-16 下午9:22
 */
public enum DiffResultType {
    IN_1_NOT_IN_2("diff result in 1 but not in 2"),
    IN_2_NOT_IN_1("diff result in 2 but not in 1"),
    IN_1_AND_IN_2("diff result in 1 and in 2"),
    IN_2_OR_IN_2("diff result in 1 or in 2");

    private String desc;

    private DiffResultType(String desc) {
        this.desc = desc;
    }
}

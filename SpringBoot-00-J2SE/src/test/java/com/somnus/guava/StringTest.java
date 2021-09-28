/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.somnus.guava;

import java.util.*;

import com.google.common.base.*;
import org.junit.Test;

/**
 * @author Somnus
 * @ClassName: StringTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年9月28日
 */
public class StringTest {

    @Test
    public void Joiner() {
        System.out.println(Joiner.on("; ").skipNulls().join(Arrays.asList(1)));
        System.out.println(Joiner.on("-").skipNulls().join("a","b","c","d"));
    }

    @Test
    public void Splitter() {
        System.out.println(Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux"));
        System.out.println(Splitter.on("|")
                .trimResults()
                .omitEmptyStrings()
                .splitToList("foo"));
        System.out.println(Splitter.onPattern("\\s+")
                .split("四川省  \t   成都市 金牛区").toString());
        System.out.println(Splitter.on(" ")
                .withKeyValueSeparator("=")
                .split("userName=Nimo phone=123 address=浙江省杭州市滨江区XXXX"));
    }

    @Test
    public void CaseFormat() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE
                .to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"));
    }

}

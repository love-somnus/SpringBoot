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

import org.junit.Test;

import com.google.common.base.Preconditions;

/**
 * @author Somnus
 * @ClassName: PreconditionsTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年9月27日
 */
public class PreconditionsTest {

    @Test
    public void checkArgument1st() {
        Preconditions.checkArgument(false);
    }

    @Test
    public void checkArgument2nd() {
        Preconditions.checkArgument(1 > 2, "1 > 2 is wrong");
    }

    @Test
    public void checkArgument3rd() {
        Preconditions.checkArgument(1 > 2, "%s is wrong", "1 > 2");
    }

    @Test
    public void checkState1st() {
        Preconditions.checkState(false);
    }

    @Test
    public void checkState2nd() {
        Preconditions.checkState(1 > 2, "1 > 2 is wrong");
    }

    @Test
    public void checkState3rd() {
        Preconditions.checkState(1 > 2, "%s is wrong", "1 > 2");
    }

    @Test
    public void checkNotNull1st() {
        Preconditions.checkNotNull(null);
    }

    @Test
    public void checkNotNull2nd() {
        Preconditions.checkNotNull(null, "xxxx不能为空");
    }

    @Test
    public void checkNotNull3rd() {
        Preconditions.checkNotNull(null, "%s不能为空", "xxxx");
    }

}

/*
 * Copyright 2020 webtau maintainers
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.testingisdocumenting.webtau.http.json;

import org.testingisdocumenting.webtau.http.request.HttpApplicationMime;
import org.testingisdocumenting.webtau.http.request.HttpRequestBody;
import org.testingisdocumenting.webtau.utils.JsonUtils;

import java.util.Collection;
import java.util.Map;

public class JsonRequestBody implements HttpRequestBody {
    private final String asString;
    private final Object original;

    public JsonRequestBody(Map<String, ?> data) {
        this.asString = JsonUtils.serialize(data);
        this.original = data;
    }

    public JsonRequestBody(Collection<?> data) {
        this.asString = JsonUtils.serialize(data);
        this.original = data;
    }

    public boolean isMap() {
        return original instanceof Map;
    }

    public Object getOriginal() {
        return original;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public String type() {
        return HttpApplicationMime.JSON;
    }

    @Override
    public boolean isEmpty() {
        return asString.isEmpty();
    }

    @Override
    public String asString() {
        return asString;
    }
}

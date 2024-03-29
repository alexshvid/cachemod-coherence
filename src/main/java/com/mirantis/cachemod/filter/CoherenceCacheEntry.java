/*
 * Copyright 2011 Mirantis Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mirantis.cachemod.filter;

import java.io.IOException;
import java.util.Locale;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

public class CoherenceCacheEntry extends CacheEntry implements PortableObject {

  private static final long serialVersionUID = 42342342L;
  
  @Override
  public void readExternal(PofReader reader) throws IOException {
    userData = reader.readObject(0);
    String language = reader.readString(1);
    String country = reader.readString(2);
    if (language != null) {
      try {
        locale = new Locale(language, country);
      }
      catch(Exception e) {
        // do not log
      }
    }
    expires = reader.readLong(3);
    lastModified = reader.readLong(4);
    maxAge = reader.readLong(5);
    contentEncoding = reader.readString(6);
    contentType = reader.readString(7);
    content = reader.readByteArray(8);
  }

  @Override
  public void writeExternal(PofWriter writer) throws IOException {
    writer.writeObject(0, userData); 
    writer.writeString(1, locale != null ? locale.getLanguage() : null);
    writer.writeString(2, locale != null ? locale.getCountry() : null);
    writer.writeLong(3, expires);
    writer.writeLong(4, lastModified);
    writer.writeLong(5, maxAge);    
    writer.writeString(6, contentEncoding);
    writer.writeString(7, contentType);
    writer.writeByteArray(8, content);
  }

}

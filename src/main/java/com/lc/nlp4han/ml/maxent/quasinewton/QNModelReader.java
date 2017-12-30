/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.lc.nlp4han.ml.maxent.quasinewton;

import java.io.File;
import java.io.IOException;

import com.lc.nlp4han.ml.maxent.gis.GISModelReader;
import com.lc.nlp4han.ml.model.Context;
import com.lc.nlp4han.ml.model.DataReader;


public class QNModelReader extends GISModelReader {
  public QNModelReader(DataReader dataReader) {
    super(dataReader);
  }

  public QNModelReader(File file) throws IOException {
    super(file);
  }

  @Override
  public void checkModelType() throws IOException {
    String modelType = readUTF();
    if (!modelType.equals("QN"))
      System.out.println("Error: attempting to load a " + modelType
          + " model as a MAXENT_QN model." + " You should expect problems.");
  }

  public QNModel constructModel() throws IOException {
    String[] outcomeLabels = getOutcomes();
    int[][] outcomePatterns = getOutcomePatterns();
    String[] predLabels = getPredicates();
    Context[] params = getParameters(outcomePatterns);

    return new QNModel(params, predLabels, outcomeLabels);
  }
}

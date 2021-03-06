/**
 * Copyright 2015 LinkedIn Corp. All rights reserved.
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
 */
package metadata.etl.lineage;

import akka.actor.UntypedActor;
import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by zsun on 9/16/15.
 */
@Slf4j
public class AzLineageExtractorActor extends UntypedActor {

  public AzLineageExtractorActor() {
    super();
  }

  @Override
  public void onReceive(Object message) {
    try {
      if (message instanceof AzExecMessage) {
        log.debug("Recieved a message : " + message.toString());
        AzLineageExtractor.extract((AzExecMessage) message);
      } else {
        log.error("Not an AzExecMessage message");
      }
    } catch (Exception e) {
      log.error("Actor failed!");
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      log.error(sw.toString());
      e.printStackTrace();
    } finally {
      log.debug("Actor finished for message : " + message.toString());
      getSender().tell("finished", null);
    }
  }
}

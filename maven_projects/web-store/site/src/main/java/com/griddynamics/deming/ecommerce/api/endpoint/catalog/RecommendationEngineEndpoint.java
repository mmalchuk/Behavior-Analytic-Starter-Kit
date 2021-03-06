/*
* Copyright 2013 Grid Dynamics, Inc.
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

package com.griddynamics.deming.ecommerce.api.endpoint.catalog;

import com.griddynamics.deming.ecommerce.recommendation.RecommendationService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.broadleafcommerce.core.web.api.endpoint.BaseEndpoint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Component
@Scope("singleton")
@Path("/recommendations")
public class RecommendationEngineEndpoint extends BaseEndpoint {

    @Resource(name = "dmgRecommendationService")
    private RecommendationService recommendationService;

    @POST
    @Path("/import")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCatalog(@FormDataParam("file") InputStream uploadInputStream,
                                  @FormDataParam("file") FormDataContentDisposition fileDetail) {

        try {
            recommendationService.loadRecommendations(uploadInputStream);

            return Response.status(Response.Status.OK)
                    .entity("Recommendations was imported successfully!\n")
                    .build();

        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Unable load recommendations\n")
                    .build();
        }
    }
}

package dev.ifrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("/fault")
public class Service {

    @GET
    @Path("/retry/{value}")
    @Retry(maxRetries = 2, delay = 1000)
    @Fallback(fallbackMethod = "recover")
    @Timeout(7000)
    public String retry(@PathParam("value") String value) {
        System.out.println("Eai malandro");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("timeout");
        }

        if(value.equalsIgnoreCase("error")){
            throw new WebApplicationException("Caiu no retry dos guri");
        }

        return value;
    }

    public String recover(String value) {
        return "coe rapaziada";
    }

    @GET
    @Path("/bulkhead/{value}")
    @Bulkhead(1)
    public String bulkhead(@PathParam("value") String value){
        return value;
    }

}

package com.deadmoose.fairisle.server;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class FairIsleApplication extends ResourceConfig
{
    public FairIsleApplication ()
    {
    }
}

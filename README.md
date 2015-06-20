# ZZ2h

RDF2h rendering and LD2h embedding for Apache Clerezza.

## Description

This projects provides a  bundle to render resources served by Clerezza 
using RDF2h templates.

## Compiling

You need Apache Maven and Java 8. Compile with:

    mvn install

## Deploying

You can deploy to a clerezza instance running on localhost:8080 with

    mvn install -PinstallBundle

## Using

Add some data to the content graph and dereference one of the resources. Assuming
that no more specific Renderlet is registered with clerezza an ld2h based page
will use RDF2h matchers to render the resource.
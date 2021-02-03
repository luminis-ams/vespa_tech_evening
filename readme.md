# Vespa - Tech Evening - 03/02/2021

Tech evening 

**Attendees:**
* Byron Voorbach
* Jettro Coenradie
* Daniel Spee

## Set schema

```
schema ecommproducts {
    document ecommproducts {
        field id type string {
            indexing: summary | index | attribute
        }
        field img_src type string {
            indexing: summary | index | attribute
        }
        field price type double {
            indexing: summary | attribute
        }
        field tags type array<string> {
            indexing: summary | index | attribute
        }
        field title type string {
            indexing: summary | index | attribute
        }
        field type type string {
            indexing: summary | index | attribute
        }
        field vendor type string {
            indexing: summary | index | attribute
        }
        field description type string {
            indexing: summary | index | attribute
        }
        field numberOfRatings type int {
            indexing: summary | attribute
        }
        field rating type double {
            indexing: summary | attribute
        }
    }
}
```

## Startup
```
docker run --detach --name vespa --hostname vespa-container \
  --volume /Users/byronvoorbach/Development/Projects/Luminis/vespa/custom/:/ecommdemo --publish 8080:8080 vespaengine/vespa
  ```

## Verify (wait till 200)

```
docker exec vespa bash -c 'curl -s --head http://localhost:19071/ApplicationStatus'
```

## Start vespa with application

```
docker exec vespa bash -c '/opt/vespa/bin/vespa-deploy prepare \
  /ecommdemo/application/ && \
  /opt/vespa/bin/vespa-deploy activate'
```

## Insert Docs
```
curl -H "Content-Type:application/json" --data-binary @data/1.json \
  http://localhost:8080/document/v1/luminis/ecommproducts/docid/1
```
```
curl -H "Content-Type:application/json" --data-binary @data/2.json \
  http://localhost:8080/document/v1/luminis/ecommproducts/docid/2
```
```
curl -H "Content-Type:application/json" --data-binary @data/3.json \
  http://localhost:8080/document/v1/luminis/ecommproducts/docid/3
```
```
curl -H "Content-Type:application/json" --data-binary @data/4.json \
  http://localhost:8080/document/v1/luminis/ecommproducts/docid/4
```

## Find All (with size)
```
curl "http://localhost:8080/document/v1/?cluster=ecommproducts&wantedDocumentCount=5"
```

## Go to editor:

```
http://localhost:8080/querybuilder/#
```

## Search on Doc
```
SELECT * from SOURCES ecommproducts where "title" CONTAINS "iPad";
```

## Aggregate on color
```
SELECT * from SOURCES ecommproducts where "title" CONTAINS "iPad" | 
all(group(color) each(max(4) each(output(summary()))));
```

## Clean documents

```
docker exec vespa bash -c '/opt/vespa/bin/vespa-stop-services && /opt/vespa/bin/vespa-remove-index -force && /opt/vespa/bin/vespa-start-services'
```
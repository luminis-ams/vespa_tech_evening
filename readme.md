#README

##Startup
```
docker run --detach --name vespa --hostname vespa-container \
  --volume /Users/byronvoorbach/Development/Projects/Luminis/vespa/custom/:/ecommdemo --publish 8080:8080 vespaengine/vespa
  ```

##Verify (wait till 200)
docker exec vespa bash -c 'curl -s --head http://localhost:19071/ApplicationStatus'

##Start vespa with application

```
docker exec vespa bash -c '/opt/vespa/bin/vespa-deploy prepare \
  /ecommdemo/application/ && \
  /opt/vespa/bin/vespa-deploy activate'
```

##Insert Docs
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

##Find All (with size)
```
curl "http://localhost:8080/document/v1/?cluster=ecommproducts&wantedDocumentCount=5"
```

##Go to editor:

```
http://localhost:8080/querybuilder/#
```

##Search on Doc
```
SELECT * from SOURCES ecommproducts where "title" CONTAINS "iPad";
```

##Aggregate on color
```
SELECT * from SOURCES ecommproducts where "title" CONTAINS "iPad" | 
all(group(color) each(max(4) each(output(summary()))));
```

